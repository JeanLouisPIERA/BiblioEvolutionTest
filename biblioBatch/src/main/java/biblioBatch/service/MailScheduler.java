/**
 * 
 */
package biblioBatch.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import biblioBatch.objets.Pret;


/**
 * @author jeanl
 *
 */
@Service
public class MailScheduler {
	
	@Autowired
    private ProxyService proxyService;
	@Autowired
	MailService mailService;
	
	private Map<String, Object> model= new HashMap<String, Object>();
	
	@Value("${application.mail}")
	private String mailFrom;
    @Value("${application.subject}")
    private String subject;	
    
    	/**
    	 * Cette méthode permet de créer le modèle à peupler à partir de la liste de réponse de l'API 
    	 * @param string
    	 * @param object
    	 * @return
    	 */
    	private void populateModel(String string, Object object) {
	         model.put(string, object);
    	}

 		/**
 		 * Cette méthode permet d'envoyer une série de mails à partir de la liste des prêts échus récupérée dans l'API Biblio
 	     * Elle fournit le modèle et appelle la méthode d'envoi d'un mail dans la classe MailService
 	     * Gestion de la date en LocalDateTime gérée par Thymeleaf
 	     * Gestion de new InternetAddress pour le destinataire
 	     * Importation du sujet depuis application.properties
 		 * @throws MessagingException
 		 * @throws UnsupportedEncodingException 
 		 * @throws IOException
 		 */
 		
 		
 	 	@Scheduled(cron= "${application.cron}")
 	    public void sendMailsList() throws MessagingException, UnsupportedEncodingException{
 	    	
 	    	Pret[] relancePretsList = proxyService.relancePretsEchus(); 
 	    	
 	    	for (Pret pretARelancer : relancePretsList) {
 		    	String mailTo = pretARelancer.getUser().getAdresseMail(); 
 		    	String nomUser = pretARelancer.getUser().getUsername();
 		    	
 		    	this.populateModel("numPret", pretARelancer.getNumPret()); 
 		    	this.populateModel("echeance", pretARelancer.getDateRetourPrevue().atStartOfDay()); 
 		    	this.populateModel("debut", pretARelancer.getDatePret().atStartOfDay()); 
 		    	this.populateModel("nomUser", nomUser); 
 		    	this.populateModel("nomLivre", pretARelancer.getLivre().getTitre()); 
 		    	this.populateModel("nomAuteur", pretARelancer.getLivre().getAuteur()); 
 		    	
 		          
 		         mailService.sendMessageUsingThymeleafTemplate(mailTo, nomUser, subject, model);
 	    	
 		    	}
 	    
 	    }
 	    
    }

