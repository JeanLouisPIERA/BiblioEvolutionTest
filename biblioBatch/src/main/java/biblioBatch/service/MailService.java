/**
 * 
 */
package biblioBatch.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
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


import biblioBatch.objets.Mail;




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
	
	
	/**
    public void EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    
    
     * Cette méthode permet de créer et d'envoyer un mail 
     * @param toEmail
     * @param subject
     * @param message
     */
    /**
    public void sendMail(String toEmail, String subject, String message) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailMessage.setFrom(mail);

        javaMailSender.send(mailMessage);
    }
    **/
    /**
    @throws UnsupportedEncodingException 
     * @Autowired
    private Configuration freemarkerConfig;

    public void sendSimpleMessage(Mail mail) throws MessagingException, IOException, TemplateException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        Template emailTemplate = freemarkerConfig.getTemplate("email-template.ftlh");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(emailTemplate, mail.getModel());

        helper.setTo(mail.getTo());
        helper.setText(html, true);
        helper.setSubject(mail.getSubject());
        helper.setFrom(mail.getFrom());

        javaMailSender.send(message);
    }
    **/
	
	 public void sendMessageUsingThymeleafTemplate(
		        InternetAddress to, String subject, Map<String, Object> templateModel)
		            throws MessagingException, UnsupportedEncodingException {
		                
		        Context thymeleafContext = new Context();
		        thymeleafContext.setVariables(templateModel);
		        
		        String htmlBody = thymeleafTemplateEngine.process("template-thymeleaf.html", thymeleafContext);

		        sendHtmlMessage(to, subject, htmlBody);
		    }
	
	public void sendHtmlMessage(InternetAddress to, String subject, String htmlBody) throws MessagingException, UnsupportedEncodingException {
        
		//Context context = new Context();
        //context.setVariables(mail.getProps());
		
		
		
		MimeMessage message = eMailSender.createMimeMessage();
        //MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        //helper.addAttachment("template-cover.png", new ClassPathResource("javabydeveloper-email.PNG"));
        
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
     
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setFrom(new InternetAddress(mail,"Biblio"));
        
        //String html = templateEngine.process("templates/email-template.html", context);
        helper.setText(htmlBody, true);

        eMailSender.send(message);
    }

    
    
}
