package biblioWebServiceRest.services;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import biblioWebServiceRest.criteria.LivreCriteria;
import biblioWebServiceRest.dto.LivreCriteriaDTO;
import biblioWebServiceRest.dto.LivreDTO;
import biblioWebServiceRest.entities.Livre;
import biblioWebServiceRest.entities.Pret;
import biblioWebServiceRest.mapper.LivreCriteriaMapper;
import biblioWebServiceRest.mapper.LivreMapper;
import biblioWebServiceRest.metier.ILivreMetier;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@Api(value="Gestion des livres en referencement dans la Bibliotheque")
public class LivreRestService {
	
	
	@Autowired
	private ILivreMetier livreMetier;
	
	@Autowired
	private LivreMapper livreMapper;
	
	@Autowired
	private LivreCriteriaMapper livreCriteriaMapper;

	
	/**
	 * Si aucun paramètre n'est renseigné, la méthode renvoie la liste de tous les livres enregistrés dans la base
	 * Le titre et le nom de l'auteur doivent simplement matcher
	 * Le nom de la catégorie doit être égal sinon la méthode Méthode catche une exception
	 * Gestion de la serialization DTO
	 * @param livreCriteriaDTO
	 * @param page
	 * @param size
	 * @return
	 * @see biblioWebServiceRest.metier.ILivreMetier#searchByCriteria(biblioWebServiceRest.criteria.LivreCriteria, int, int)
	 */
	@ApiOperation(value = "Recherche multi-critères d'un ou plusieurs livres", response = Pret.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "La recherche a été réalisée avec succés"),
	        @ApiResponse(code = 201, message = "Code erreur non utilisé"),
	        @ApiResponse(code = 401, message = "Pas d'autorisation pour accéder à cette ressource"),
	        @ApiResponse(code = 403, message = "Accès interdit à cette ressource "),
	        @ApiResponse(code = 404, message = "Ressource inexistante"),
	        @ApiResponse(code = 500, message = "Erreur interne au Serveur")
	})
	@GetMapping(value="/livres", produces = "application/json")
	public Page<LivreDTO> searchByLivreCriteriaDTO(@PathParam("searched by") LivreCriteriaDTO livreCriteriaDTO, @RequestParam int page, @RequestParam int size) {
		LivreCriteria livreCriteria = livreCriteriaMapper.livreCriteriaDTOToLivreCriteria(livreCriteriaDTO);
		List<Livre> livres = livreMetier.searchByCriteria(livreCriteria);
		List<LivreDTO> livreDTOs = livreMapper.livresToLivresDTOs(livres);
		int end = (page + size > livres.size() ? livres.size() : (page + size));
		Page<LivreDTO> pageLivreDTO = new PageImpl<LivreDTO>(livreDTOs.subList(page, end));
	
		return pageLivreDTO;
	}		
		
}
	
	
	

