/**
 * 
 */
package biblioBatch.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import biblioBatch.objets.Pret;
import biblioBatch.scheduledtasks.IScheduledTasks;

/**
 * @author jeanl
 *
 */

@Service
public class MailService {
	
	@Autowired
 	private IScheduledTasks scheduledTasks;
	
	@Autowired
	private JavaMailSender javaMailSender;
	

    public void EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    
    /**
     * Cette méthode permet de créer et d'envoyer un mail 
     * @param toEmail
     * @param subject
     * @param message
     */
    public void sendMail(String toEmail, String subject, String message) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailMessage.setFrom("jlpbibli@gmail.com");

        javaMailSender.send(mailMessage);
    }
    
    /**
     * Cette méthode permet d'envoyer une série de mails à partir de la liste des prêts échus récupérée dans l'API Biblio
     */
    public void sendMailsList() {
    	
    	Pret[] relancePretsList = scheduledTasks.relancePretsEchus(); 
    	
    	for (Pret pretARelancer : relancePretsList) {
	    	String adresseMail = pretARelancer.getUser().getAdresseMail(); 
	    	Long numPret= pretARelancer.getNumPret();
	    	LocalDate echeance = pretARelancer.getDateRetourPrevue(); 
	    	String mailSubject = "Votre Pret de livre"+numPret+"est échu depuis le"+echeance;
	    	String nomUser = pretARelancer.getUser().getUsername(); 
	    	String nomLivre = pretARelancer.getLivre().getTitre(); 
	    	String mailText = "Bonjour"+nomUser+"Votre prêt est échu. Merci de ramener à la bibliothèque l'ouvrage suivant"+nomLivre;
	    	
	        this.sendMail(adresseMail, mailSubject, mailText);
	        
	    	}
    	
    }

}
