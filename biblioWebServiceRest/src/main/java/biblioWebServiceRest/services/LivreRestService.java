package biblioWebServiceRest.services;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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


	/**
	 * Creation d'un nouveau livre à referencer 
	 * La creation d'une référence se fait sur la base d'un seul exemplaire
	 * L'enregistrement de plusieurs exemplaires présent à la création se fait par simple mise à jour en suivant
	 * La méthode envoie une exception si une réference existe déjà avec le même titre et le même auteur quelle que soit sa categorie
	 * Pour les références qui doivent enregistrer plusieurs tomes d'un même titre, il faut enregistrer le numéro du tome dans le titre (exemple Tome 1)
	 * La méthode envoie une exception si la catégorie dans laquelle doit être enregistrer le livre a creer n'existe pas
	 * @param titre
	 * @param auteur
	 * @param numCategorie
	 * @return
	 * @throws Exception
	 * @see biblioWebServiceRest.metier.ILivreMetier#createLivre(java.lang.String, java.lang.String, java.lang.Long)
	 */
	@ApiOperation(value = "Enregistrement d'un nouveau livre. Pour les références qui ont plusieurs tomes, enregistrer SVP la référence du tome dans le titre (exp : Fantomas Tome 1)", response = Livre.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 201, message = "Le livre a été créé"),
	        @ApiResponse(code = 401, message = "Pas d'autorisation pour accéder à cette ressource"),
	        @ApiResponse(code = 403, message = "Accès interdit à cette ressource "),
	        @ApiResponse(code = 404, message = "Ressource inexistante"),
	        @ApiResponse(code = 500, message = "Erreur interne au Serveur")
	})
	@PostMapping(value="/livres/titre/{titre}/auteur/{auteur}/categorie/{numCategorie}", produces = "application/json")
	public ResponseEntity<Livre> createLivre(@PathVariable String titre, @PathVariable String auteur, @PathVariable Long numCategorie) throws Exception {
		return new ResponseEntity<Livre>(livreMetier.createLivre(titre, auteur, numCategorie), HttpStatus.CREATED);
	}		
		
}
	
	
	

