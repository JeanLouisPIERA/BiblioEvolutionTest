/**
 * 
 */
package biblioWebAppli.metier;


import java.util.Arrays;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import biblioWebAppli.criteria.PretCriteria;
import biblioWebAppli.objets.Pret;




/**
 * @author jeanl
 *
 */
@Service
public class PretMetierImpl implements IPretMetier{
	
	@Autowired
    private RestTemplate restTemplate;
	@Autowired
    private HttpHeadersFactory httpHeadersFactory; 
    
    /*
    @Value("${application.username}")
	private String username;
	*/
	@Value("${application.password}")
	private String password;
	
	@Value("${application.idUser}")
	private Long idUser;
	
    @Value("${application.uRLPret}")
	private String uRL;
    
    
    
 
	/**
	 * @param numPret
	 * @return
	 * @throws EntityNotFoundException
	 * @throws BookNotAvailableException
	 */
	@Override
	public Pret prolongerPret(Long numPret){
		 
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		HttpHeaders headers = httpHeadersFactory.createHeaders(username,password);
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	
    	HttpEntity<?> requestEntity = 
       	     new HttpEntity<>(headers);
		
		String url = uRL+"/prolongation/"+numPret;
    	
		ResponseEntity<Pret> response = restTemplate.exchange(url , HttpMethod.PUT, requestEntity, Pret.class);
		
		return response.getBody(); 
	}

	
	/**
	 * @param pretCriteria
	 * @param pageable
	 * @return
	 */
	@Override
	public Page<Pret> searchByCriteria(PretCriteria pretCriteria, Pageable pageable) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		System.out.println(username);
		
		HttpHeaders headers = httpHeadersFactory.createHeaders(username,password);
		
    	headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    	
    	UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uRL)
    	        .queryParam("numPret", pretCriteria.getNumPret())
    	        .queryParam("username", username)
    	        //.queryParam("userId", pretCriteria.getUserId())
    	        .queryParam("numLivre", pretCriteria.getNumLivre())
    	        .queryParam("titre", pretCriteria.getTitre())
    	        .queryParam("auteur", pretCriteria.getAuteur())
    	        .queryParam("nomCategorieLivre", pretCriteria.getNomCategorieLivre())
    	        .queryParam("page", pageable.getPageNumber())
    	        .queryParam("size", pageable.getPageSize());
    	
    	HttpEntity<?> entity = new HttpEntity<>(headers);
    	
    	ResponseEntity<RestResponsePage<Pret>> prets = restTemplate.exchange
    			(builder.toUriString(), 
				HttpMethod.GET,
				entity,
    			new ParameterizedTypeReference<RestResponsePage<Pret>>(){});
        Page<Pret> pagePret = prets.getBody();
        
            	
        return pagePret;
	}
	
	

}
