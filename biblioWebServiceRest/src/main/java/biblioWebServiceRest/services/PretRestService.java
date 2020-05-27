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
import biblioWebServiceRest.dto.PretCriteriaDTO;
import biblioWebServiceRest.dto.PretDTO;
import biblioWebServiceRest.entities.Pret;
import biblioWebServiceRest.mapper.PretCriteriaMapper;
import biblioWebServiceRest.mapper.PretMapper;
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
	
	
	/**
	 * Cette Requête permet de créer un prêt 
	 * Exceptions gérées en cas de formulaire vide, de références inexistantes ou d'absence d'exemplaire disponible 
	 * @param titre
	 * @param username
	 * @return
	 * @throws Exception 
	 * @see biblioWebServiceRest.metier.IPretMetier#createPret(java.lang.String, java.lang.String)
	 */
	@PostMapping(value="/pret/livre/{titre}/user/{username}")
	public Pret createPret(@PathVariable String titre, @PathVariable String username ) throws Exception {
		return pretMetier.createPret(titre, username);
	}

	/**
	 * @param pretCriteria
	 * @return
	 * @see biblioWebServiceRest.metier.IPretMetier#searchByCriteria(biblioWebServiceRest.criteria.PretCriteria)
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
	
	
	
	/**
	 * Cette Requête permet de retrouver un prêt 
	 * Les exceptions sont gérées en cas de référence inexistante
	 * @param titre
	 * @param username
	 * @param datePret
	 * @return
	 * @see biblioWebServiceRest.metier.IPretMetier#readPret(java.lang.String, java.lang.String, java.lang.String)
	 */
	/**
	@GetMapping(value="/pret")
	public Pret readPret(@RequestParam String titre, @RequestParam String username, @RequestParam String datePret) {
		return pretMetier.readPret(titre, username, datePret);
	}
	**/
	
	
	
	
	

}
