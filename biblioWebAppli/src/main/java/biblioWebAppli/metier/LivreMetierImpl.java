/**
 * 
 */
package biblioWebAppli.metier;



import java.util.Base64;

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
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import biblioWebAppli.criteria.LivreCriteria;


import biblioWebAppli.objets.Livre;
import biblioWebAppli.security.RestTemplateFactory;


/**
 * @author jeanl
 *
 */
@Service
public class LivreMetierImpl implements ILivreMetier {
	
	@Autowired
	private RestTemplate restTemplate;
	//@Autowired
	//private RestTemplateFactory restTemplateFactory;
	@Autowired
    private HttpHeadersFactory httpHeadersFactory; 
    
    
    
    @Value("${application.uRLLivre}")
	private String uRL;
    @Value("${application.username}")
	private String username;
	@Value("${application.password}")
	private String password;
	
    
	/**
	 * @param livreCriteria
	 * @param page
	 * @param size
	 * @return
	 */
	@Override
	public Page<Livre> searchByCriteria(LivreCriteria livreCriteria, Pageable pageable) {
		
		HttpHeaders headers = httpHeadersFactory.createHeaders(username,password);
    	
    	System.out.println("headers"+headers.toString());
    	
    	headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    	
    	UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uRL)
    	        .queryParam("numLivre", livreCriteria.getNumLivre())
    	        .queryParam("titre", livreCriteria.getTitre())
    	        .queryParam("auteur", livreCriteria.getAuteur())
    	        .queryParam("nomCategorie", livreCriteria.getNomCategorie())
    	        .queryParam("page", pageable.getPageNumber())
    	        .queryParam("size", pageable.getPageSize());
    	
    	System.out.println("URI"+builder.toUriString());
    	
    	HttpEntity<?> entity = new HttpEntity<>(headers);
    	
    	ResponseEntity<RestResponsePage<Livre>> livres = restTemplate.exchange
    			(builder.toUriString(), 
				HttpMethod.GET,
				entity,
    			new ParameterizedTypeReference<RestResponsePage<Livre>>(){});
        Page<Livre> pageLivre = livres.getBody();
        
        return pageLivre;
	}

	
}
