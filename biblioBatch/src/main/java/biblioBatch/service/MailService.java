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
	@Value("${application.template.R}")
	private String templateR;
	
	@Value("${application.nameFrom}")
	private String nameFrom;
	
	/**
	 * Cette méthode permet de customiser le message RELANCE PRETS envoyé en utilisant le template Thymeleaf indiqué
	 * @param to
	 * @param subject
	 * @param templateModel
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	 public void sendMessageUsingThymeleafTemplate(
		        String to, String name, String subject, Map<String, Object> templateModel)
		            throws MessagingException, UnsupportedEncodingException {
		                
		        Context thymeleafContext = new Context();
		        thymeleafContext.setVariables(templateModel);
		        
		        String htmlBody = thymeleafTemplateEngine.process(template, thymeleafContext);

		        sendHtmlMessage(to, name, subject, htmlBody);
		    }
	 
	 /**
		 * Cette méthode permet de customiser le message NOTIFICATION RESERVATIONS envoyé en utilisant le template Thymeleaf indiqué
		 * @param to
		 * @param subject
		 * @param templateModel
		 * @throws MessagingException
		 * @throws UnsupportedEncodingException
		 */
		 public void sendMessageUsingThymeleafTemplateR(
			        String to, String name, String subject, Map<String, Object> templateModel)
			            throws MessagingException, UnsupportedEncodingException {
			                
			        Context thymeleafContext = new Context();
			        thymeleafContext.setVariables(templateModel);
			        
			        String htmlBody = thymeleafTemplateEngine.process(templateR, thymeleafContext);

			        sendHtmlMessage(to, name, subject, htmlBody);
			    }
	 
	 
	
	/**
	 * Cette méthode permet de créer un message HTML en renseignant le destinataire, le sujet, l'émetteur et en créant le htmlBody du mail 
	 * @param to
	 * @param subject
	 * @param htmlBody
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public void sendHtmlMessage(String to, String name, String subject, String htmlBody) throws MessagingException, UnsupportedEncodingException {
        
		MimeMessage message = eMailSender.createMimeMessage();
                
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
     
        helper.setTo(new InternetAddress(to, name));
        helper.setSubject(subject);
        helper.setFrom(new InternetAddress(mail,nameFrom));
        
        helper.setText(htmlBody, true);
        

        eMailSender.send(message);
    }

	
    
}
