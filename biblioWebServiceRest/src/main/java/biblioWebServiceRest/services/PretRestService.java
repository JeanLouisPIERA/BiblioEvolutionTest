/**
 * 
 */
package biblioWebServiceRest.services;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import biblioWebServiceRest.criteria.PretCriteria;
import biblioWebServiceRest.dto.LivreDTO;
import biblioWebServiceRest.dto.PretCriteriaDTO;
import biblioWebServiceRest.dto.PretDTO;
import biblioWebServiceRest.dto.UserDTO;
import biblioWebServiceRest.entities.Livre;
import biblioWebServiceRest.entities.Pret;
import biblioWebServiceRest.entities.User;
import biblioWebServiceRest.mapper.LivreMapper;
import biblioWebServiceRest.mapper.PretCriteriaMapper;
import biblioWebServiceRest.mapper.PretMapper;
import biblioWebServiceRest.mapper.UserMapper;
import biblioWebServiceRest.metier.ILivreMetier;
import biblioWebServiceRest.metier.IPretMetier;
import biblioWebServiceRest.metier.IUserMetier;

/**
 * @author jeanl
 *
 */

@RestController

public class PretRestService {
	
	@Autowired
	IPretMetier pretMetier;
	@Autowired
	ILivreMetier livreMetier;
	@Autowired
	IUserMetier userMetier;
	@Autowired
	PretMapper pretMapper; 
	@Autowired
	PretCriteriaMapper pretCriteriaMapper;
	@Autowired
	LivreMapper livreMapper;
	@Autowired
	UserMapper userMapper;

	
	/**
	 * Cette Requête permet de créer un prêt 
	 * Exceptions gérées en cas de paramètres inconnus en base de données ou d'absence d'exemplaire disponible 
	 * Gestion DTO 
	 * @param nomLivre
	 * @param nomEmprunteur
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/pret/livre/{nomLivre}/user/{nomEmprunteur}")
	public PretDTO createPret(@PathVariable String nomLivre, @PathVariable String nomEmprunteur ) throws Exception {
		String titre = nomLivre;
		String username = nomEmprunteur;
		
		Pret newPret = pretMetier.createPret(titre, username);
		Livre livreNewPret = newPret.getLivre();
		User userNewPret = newPret.getUser();
		
		LivreDTO livreNewPretDTO = livreMapper.livreToLivreDTO(livreNewPret);
		UserDTO usernewPretDTO = userMapper.userTouserDTO(userNewPret);
		PretDTO newPretDTO = pretMapper.pretToPretDTO(newPret);
		
		newPretDTO.setLivre(livreNewPretDTO);
		newPretDTO.setUser(usernewPretDTO);
		
		return newPretDTO;
	}

	/**
	 * Recherche Multicritères des prêts enregistrés 
	 * Gestion de la serialization DTO 
	 * @param pretCriteriaDTO
	 * @param page
	 * @param size
	 * @return
	 */
	@GetMapping(value="/prets")
	public Page<PretDTO> searchByPretCriteriaDTO(@PathParam("searched by")PretCriteriaDTO pretCriteriaDTO, @RequestParam int page, @RequestParam int size ) {
		PretCriteria pretCriteria = pretCriteriaMapper.pretCriteriaDTOToPretCriteria(pretCriteriaDTO);
		List<Pret> prets = pretMetier.searchByCriteria(pretCriteria);
		List<PretDTO> pretDTOs = pretMapper.pretsToPretsDTOs(prets);
		int end = (page + size > prets.size() ? prets.size() :(page + size));
		Page<PretDTO> pagePretDTO = new PageImpl<PretDTO>(pretDTOs.subList(page, end));
		return pagePretDTO;
	}

}
