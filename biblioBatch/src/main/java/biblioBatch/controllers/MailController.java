/**
 * 
 */
package biblioBatch.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.scheduling.annotation.Scheduled;

import biblioBatch.service.MailService;

/**
 * @author jeanl
 *
 */
@Controller
public class MailController {
	
	 	@Autowired
	    private MailService mailService;
	 	
	    /**
	     * Cette méthode permet de lancer aux dates du schedule le batch pour recueillir les prêts 
	     * Elle déclenche ensuite l'envoi d'un mail à chaque utilisateur concerné géré par une méthode dans MailService  
	     */
	 	@Scheduled(initialDelay=60000, fixedRate=300000)
	 	//@Scheduled(cron="*/5 * * * * MON-FRI")
	 	@GetMapping(value = "/sendmail")
	    public void sendListMails() {
	    	
	    	mailService.sendMailsList();
	    }
	}


