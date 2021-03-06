/**
 * 
 */
package biblioBatch.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import biblioBatch.objets.Pret;
import biblioBatch.objets.Reservation;


/**
 * @author jeanl
 *
 */
@Service
public class ProxyService {
	
	@Autowired
    private RestTemplate restTemplate;
	@Autowired
    private HttpHeadersFactory httpHeadersFactory; 
	@Autowired
	private MailService mailService;
	
    
    
    @Value("${application.username}")
	private String username;
	@Value("${application.password}")
	private String password;
    @Value("${application.uRLPret}")
	private String uRL;
    @Value("${application.uRLReservation}")
	private String uRLR;
    
	
	/**
	 * Cette méthode permet d'obtenir la liste des prêts échus à la date du batch
	 * @return
	 */
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
	

	/**
	 * Cette méthode permet d'obtenir la liste des prêts à échoir à la date du batch
	 * @return
	 */
	public Pret[] relancePretsAEchoir() {
		
		String url1 = uRL + "/aechoir";
		
		HttpHeaders headers = httpHeadersFactory.createHeaders(username, password);
		
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	
    	HttpEntity<?> requestEntity = 
    	     new HttpEntity<>(headers);
    	
    	ResponseEntity<Pret[]> response = restTemplate.exchange(url1, HttpMethod.GET, requestEntity, Pret[].class);
    	
    	Pret[] pretsAEchoirList = response.getBody(); 
    
		return pretsAEchoirList;
	}

	public Reservation[] notificationReservations() {
		
		String url = uRLR + "/notifiees";
		
		HttpHeaders headers = httpHeadersFactory.createHeaders(username, password);
		
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	
    	HttpEntity<?> requestEntity = 
    	     new HttpEntity<>(headers);
    	
    	ResponseEntity<Reservation[]> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Reservation[].class);
    	
    	Reservation[] reservationsNotifieesList = response.getBody(); 
    
		return reservationsNotifieesList;
		
		
	}
	


}
