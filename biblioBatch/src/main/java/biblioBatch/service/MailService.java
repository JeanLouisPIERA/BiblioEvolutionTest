/**
 * 
 */
package biblioBatch.service;


import java.io.UnsupportedEncodingException;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;



/**
 * @author jeanl
 *
 */

@Service
public class MailService{
	
	@Autowired
	private JavaMailSender eMailSender;
	@Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;
	
	@Value("${application.mail}")
	private String mail;
	@Value("${application.template}")
	private String template;
	
	/**
	 * Cette méthode permet de customiser le message envoyé en utilisant le template Thymeleaf indiqué
	 * @param to
	 * @param subject
	 * @param templateModel
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	 public void sendMessageUsingThymeleafTemplate(
		        InternetAddress to, String subject, Map<String, Object> templateModel)
		            throws MessagingException, UnsupportedEncodingException {
		                
		        Context thymeleafContext = new Context();
		        thymeleafContext.setVariables(templateModel);
		        
		        String htmlBody = thymeleafTemplateEngine.process(template, thymeleafContext);

		        sendHtmlMessage(to, subject, htmlBody);
		    }
	
	/**
	 * Cette méthode permet de créer un message HTML en renseignant le destinataire, le sujet, l'émetteur et en créant le htmlBody du mail 
	 * @param to
	 * @param subject
	 * @param htmlBody
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public void sendHtmlMessage(InternetAddress to, String subject, String htmlBody) throws MessagingException, UnsupportedEncodingException {
        
		MimeMessage message = eMailSender.createMimeMessage();
                
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
     
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setFrom(new InternetAddress(mail,"Biblio"));
        
        helper.setText(htmlBody, true);

        eMailSender.send(message);
    }

    
    
}
