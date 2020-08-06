/**
 * 
 */
package biblioBatch.scheduledtasks;

import java.util.Arrays;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import biblioBatch.objets.Pret;
import biblioBatch.service.HttpHeadersFactory;


/**
 * @author jeanl
 *
 */
@Component
public class ScheduledTasks implements IScheduledTasks {
	
	@Autowired
    private RestTemplate restTemplate;
	@Autowired
    private HttpHeadersFactory httpHeadersFactory; 
    
    
    @Value("${application.username}")
	private String username;
	@Value("${application.password}")
	private String password;
	
    @Value("${application.uRLPret}")
	private String uRL;
	

	/**
	 * Cette méthode permet d'obtenir la liste des prêts échus à la date du batch
	 * @return
	 */
	@Override
	//@Scheduled(cron="*/5 * * * * MON-FRI")
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
	
	

}
