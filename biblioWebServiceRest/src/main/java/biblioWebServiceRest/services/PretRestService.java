/**
 * 
 */
package biblioWebServiceRest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import biblioWebServiceRest.entities.Pret;
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
	
	/**
	 * @param titre
	 * @param username
	 * @return
	 * @see biblioWebServiceRest.metier.IPretMetier#createPret(java.lang.String, java.lang.String)
	 */
	@PostMapping(value="/pret")
	public Pret createPret(@RequestParam String titre, @RequestParam String username) {
		return pretMetier.createPret(titre, username);
	}
	
	
	
	
	
	

}
