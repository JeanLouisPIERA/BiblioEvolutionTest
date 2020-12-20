/**
 * 
 */
package biblioBatch.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import biblioBatch.objets.Pret;
import biblioBatch.objets.Reservation;




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

    @Value("${application.subject.Rappel}")
    private String subjectRappel;	

    @Value("${application.subject.R}")
    private String subjectR;	

    
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
 		    	this.populateModel("numLivre", pretARelancer.getLivre().getNumLivre()); 
 		    	
 		          
 		         mailService.sendMessageUsingThymeleafTemplate(mailTo, nomUser, subject, model);
 	    	
 		    	}
 	    
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
 	    public void sendMailsRappelList() throws MessagingException, UnsupportedEncodingException{
 	    	
 	    	Pret[] relancePretsAEchoirList = proxyService.relancePretsAEchoir(); 
 	    	
 	    	for (Pret pretAEchoir : relancePretsAEchoirList) {
 		    	String mailTo = pretAEchoir.getUser().getAdresseMail(); 
 		    	String nomUser = pretAEchoir.getUser().getUsername();
 		    	
 		    	this.populateModel("numPretAEchoir", pretAEchoir.getNumPret()); 
 		    	this.populateModel("echeanceAEchoir", pretAEchoir.getDateRetourPrevue().atStartOfDay()); 
 		    	this.populateModel("debutAEchoir", pretAEchoir.getDatePret().atStartOfDay()); 
 		    	this.populateModel("nomUserAEchoir", nomUser); 
 		    	this.populateModel("nomLivreAEchoir", pretAEchoir.getLivre().getTitre()); 
 		    	this.populateModel("nomAuteurAEchoir", pretAEchoir.getLivre().getAuteur()); 
 		    	
 		          
 		         mailService.sendMessageUsingThymeleafTemplateRappel(mailTo, nomUser, subjectRappel, model);
 	    	}
 	 	}
 
        /**
 		 * Cette méthode permet d'envoyer une série de mails à partir de la liste des réservations A NOTIFIER récupérée dans l'API Biblio
 	     * Elle fournit le modèle et appelle la méthode d'envoi d'un mail dans la classe MailService
 	     * Gestion de la date en LocalDateTime gérée par Thymeleaf
 	     * Gestion de new InternetAddress pour le destinataire
 	     * Importation du sujet depuis application.properties
 		 * @throws MessagingException
 		 * @throws UnsupportedEncodingException 
 		 * @throws IOException
 		 */
 	 	@Scheduled(cron= "${application.cron}")
 	    public void sendMailsReservationList() throws MessagingException, UnsupportedEncodingException{
 	    	
 	    	Reservation[] notificationReservationsList = proxyService.notificationReservations();
 	    	
 	    	for (Reservation reservationANotifier : notificationReservationsList) {
 		    	String mailTo = reservationANotifier.getUser().getAdresseMail(); 
 		    	String nomUser = reservationANotifier.getUser().getUsername();
 		    	
 		    	this.populateModel("numReservation", reservationANotifier.getNumReservation());
 		    	this.populateModel("dateReservation", reservationANotifier.getDateReservation().atStartOfDay());
 		    	this.populateModel("dateDeadline", reservationANotifier.getDateDeadline().atStartOfDay());		
 		    	this.populateModel("nomUser", nomUser); 
 		    	this.populateModel("nomLivre", reservationANotifier.getLivre().getTitre());
 		    	this.populateModel("nomAuteur", reservationANotifier.getLivre().getAuteur());
 		    	this.populateModel("numLivre", reservationANotifier.getLivre().getNumLivre());
 		    			
 		         mailService.sendMessageUsingThymeleafTemplateR(mailTo, nomUser, subjectR, model);
 	    	
 		    	}
 	    
 	    }
 	 	
 	 	
 	    
    }

