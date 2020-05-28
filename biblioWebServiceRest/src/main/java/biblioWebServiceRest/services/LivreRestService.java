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
import biblioWebServiceRest.mapper.LivreCriteriaMapper;
import biblioWebServiceRest.mapper.LivreMapper;
import biblioWebServiceRest.metier.ILivreMetier;


@RestController
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
	@GetMapping(value="/livres")
	public Page<LivreDTO> searchByLivreCriteriaDTO(@PathParam("searched by") LivreCriteriaDTO livreCriteriaDTO, @RequestParam int page, @RequestParam int size) {
		LivreCriteria livreCriteria = livreCriteriaMapper.livreCriteriaDTOToLivreCriteria(livreCriteriaDTO);
		List<Livre> livres = livreMetier.searchByCriteria(livreCriteria);
		List<LivreDTO> livreDTOs = livreMapper.livresToLivresDTOs(livres);
		int end = (page + size > livres.size() ? livres.size() : (page + size));
		Page<LivreDTO> pageLivreDTO = new PageImpl<LivreDTO>(livreDTOs.subList(page, end));
	
		return pageLivreDTO;
	}		
		
}
	
	
	

