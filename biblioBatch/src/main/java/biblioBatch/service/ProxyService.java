/**
 * 
 */
package biblioBatch.service;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import biblioBatch.objets.Pret;

/**
 * @author jeanl
 *
 */
@Service
public class ProxyService {
	
	@Autowired
    private RestTemplate restTemplate;
	@Autowired
    private HttpHeadersFactory httpHeadersFactory; 
	@Autowired
	private MailService mailService;
    
    
    @Value("${application.username}")
	private String username;
	@Value("${application.password}")
	private String password;
    @Value("${application.uRLPret}")
	private String uRL;
	
	/**
	 * Cette méthode permet d'obtenir la liste des prêts échus à la date du batch
	 * @return
	 */
	public Pret[] relancePretsEchus() {
		
		String url = uRL + "/echus";
		
		HttpHeaders headers = httpHeadersFactory.createHeaders(username, password);
		
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	
    	HttpEntity<?> requestEntity = 
    	     new HttpEntity<>(headers);
    	
    	ResponseEntity<Pret[]> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Pret[].class);
    	
    	Pret[] pretsEchusList = response.getBody(); 
    
		return pretsEchusList;
	}
	
	
	
	/**
     * Cette méthode permet d'envoyer une série de mails à partir de la liste des prêts échus récupérée dans l'API Biblio
     */
    public void sendMailsList() {
    	
    	Pret[] relancePretsList = this.relancePretsEchus(); 
    	
    	for (Pret pretARelancer : relancePretsList) {
	    	String adresseMail = pretARelancer.getUser().getAdresseMail(); 
	    	Long numPret= pretARelancer.getNumPret();
	    	LocalDate echeance = pretARelancer.getDateRetourPrevue(); 
	    	String mailSubject = "Votre Pret de livre"+numPret+"est échu depuis le"+echeance;
	    	String nomUser = pretARelancer.getUser().getUsername(); 
	    	String nomLivre = pretARelancer.getLivre().getTitre(); 
	    	String mailText = "Bonjour"+nomUser+"Votre prêt est échu. Merci de ramener à la bibliothèque l'ouvrage suivant"+nomLivre;
	    	
	        mailService.sendMail(adresseMail, mailSubject, mailText);
	        
	    	}
    	
    }
    

}
