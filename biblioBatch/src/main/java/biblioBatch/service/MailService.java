/**
 * 
 */
package biblioBatch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author jeanl
 *
 */

@Service
public class MailService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	@Value("${application.mail}")
	private String mail;

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

        mailMessage.setFrom(mail);

        javaMailSender.send(mailMessage);
    }
    
}
