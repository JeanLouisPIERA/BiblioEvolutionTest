/**
 * 
 */
package biblioBatch.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

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
    @Value("${application.mail}")
	private String mailFrom;
    @Value("${application.subject}")
    private String subject;
	
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
     * Elle fournit le modèle et appelle la méthode d'envoi d'un mail dans la classe MailService
     * Gestion de la date en LocalDateTime gérée par Thymeleaf
     * Gestion de new InternetAddress pour le destinataire
     * Importation du sujet depuis application.properties
	 * @throws MessagingException
	 * @throws IOException
	 */
    public void sendMailsList() throws MessagingException, IOException {
    	
    	Pret[] relancePretsList = this.relancePretsEchus(); 
    	
    	for (Pret pretARelancer : relancePretsList) {
	    	String mailTo = pretARelancer.getUser().getAdresseMail(); 
	    	
	    	Long numPret= pretARelancer.getNumPret();
	    	LocalDateTime echeance = pretARelancer.getDateRetourPrevue().atStartOfDay(); 
	    	LocalDateTime debut = pretARelancer.getDatePret().atStartOfDay(); 
	    	String nomUser = pretARelancer.getUser().getUsername();
	    	String nomLivre = pretARelancer.getLivre().getTitre(); 
	    	String nomAuteur = pretARelancer.getLivre().getAuteur(); 
	    	
	         Map<String, Object> model = new HashMap<String, Object>();
	         model.put("numPret", numPret);
	         model.put("debut", debut);
	         model.put("echeance", echeance);
	         model.put("nomUser", nomUser);
	         model.put("nomLivre", nomLivre);
	         model.put("nomAuteur", nomAuteur);
	          
	         mailService.sendMessageUsingThymeleafTemplate(mailTo, nomUser, subject, model);
    	
	    	}
    
    }
    

}
