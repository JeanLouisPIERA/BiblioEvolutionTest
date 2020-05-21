/**
 * 
 */
package biblioWebServiceRest.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
	 * Cette Requête permet de créer un prêt 
	 * Exceptions gérées en cas de formulaire vide, de références inexistantes ou d'absence d'exemplaire disponible 
	 * @param titre
	 * @param username
	 * @return
	 * @see biblioWebServiceRest.metier.IPretMetier#createPret(java.lang.String, java.lang.String)
	 */
	@PostMapping(value="/pret")
	public Pret createPret(@RequestParam String titre, @RequestParam String username) {
		return pretMetier.createPret(titre, username);
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
	@GetMapping(value="/pret")
	public Pret readPret(@RequestParam String titre, @RequestParam String username, @RequestParam String datePret) {
		return pretMetier.readPret(titre, username, datePret);
	}
	
	
	
	

}
