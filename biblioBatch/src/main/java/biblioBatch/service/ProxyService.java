/**
 * 
 */
package biblioBatch.service;

import java.io.IOException;
import java.time.LocalDate;
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

import biblioBatch.objets.Mail;
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
	 * @throws TemplateException 
	 * @throws IOException 
	 * @throws MessagingException 
     */
    public void sendMailsList() throws MessagingException, IOException {
    	
    	Pret[] relancePretsList = this.relancePretsEchus(); 
    	
    	for (Pret pretARelancer : relancePretsList) {
	    	String mailTo = pretARelancer.getUser().getAdresseMail(); 
	    	
	    	Long numPret= pretARelancer.getNumPret();
	    	LocalDateTime echeance = pretARelancer.getDateRetourPrevue().atStartOfDay(); 
	    	LocalDateTime debut = pretARelancer.getDatePret().atStartOfDay(); 
	    	System.out.println("echeance"+echeance);
	    	String nomUser = pretARelancer.getUser().getUsername();
	    	String nomLivre = pretARelancer.getLivre().getTitre(); 
	    	
	    	/**
	    	String mailSubject = "Votre Pret de livre"+numPret+"est échu depuis le"+echeance;
	    	String nomUser = pretARelancer.getUser().getUsername(); 
	    	String nomLivre = pretARelancer.getLivre().getTitre(); 
	    	String mailText = "Bonjour"+nomUser+"Votre prêt est échu. Merci de ramener à la bibliothèque l'ouvrage suivant"+nomLivre;
	    	
	        mailService.sendMail(adresseMail, mailSubject, mailText);
	        **/
	    	
	    	
	    	/**
	    	 Mail mail = new Mail();
	         mail.setFrom(mailFrom);
	         mail.setTo(mailTo);
	         mail.setSubject("Relance ouvrage nom rendu - Bibliothèque Municipale");

	         Map model = new HashMap();
	         model.put("numPret", numPret);
	         model.put("dateEcheance", echeance);
	         model.put("nomUser", nomUser);
	         model.put("nomLivre", nomLivre);
	         
	         
	         mail.setModel(model);

	         mailService.sendSimpleMessage(mail);
	         
	         **/
	    	
	    	
	    	/**
	    	 Mail mail = new Mail();
	         mail.setFrom(mailFrom);//replace with your desired email
	         mail.setMailTo(mailTo);//replace with your desired email
	         mail.setSubject("Relance ouvrage nom rendu - Bibliothèque Municipale");
			**/
	    	
	    	String subject = "Relance ouvrage nom rendu - Bibliothèque Municipale BIBLIO";
	    	
	         Map<String, Object> model = new HashMap<String, Object>();
	         model.put("numPret", numPret);
	         model.put("debut", debut);
	         model.put("echeance", echeance);
	         model.put("nomUser", nomUser);
	         model.put("nomLivre", nomLivre);
	         
	         
	        
	         
	         mailService.sendMessageUsingThymeleafTemplate(new InternetAddress(mailTo,nomUser), subject, model);
    	
	    	}
    
    }
    

}
