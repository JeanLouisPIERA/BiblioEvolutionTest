/**
 * 
 */
package biblioBatch.service;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


/**
 * @author jeanl
 *
 */
@Service
public class MailScheduler {
	
	@Autowired
    private ProxyService proxyService;
	final long ID = 60000;
	final long FR = 300000;
	final String cron = "*/5 * * * * MON-FRI";
	
	
 	
    /**
     * Cette méthode permet de lancer aux dates du schedule le batch pour recueillir les prêts 
     * Elle déclenche ensuite l'envoi d'un mail à chaque utilisateur concerné géré par une méthode dans MailService  
     * @throws TemplateException 
     * @throws IOException 
     * @throws MessagingException 
     */
 	@Scheduled(initialDelay=ID, fixedRate=FR)
 	//@Scheduled(cron=cron)
    public void sendListMails() throws MessagingException, IOException{
    	
    	proxyService.sendMailsList();
    }

}
