/**
 * 
 */
package biblioWebAppli.metier;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import biblioWebAppli.criteria.LivreCriteria;

import biblioWebAppli.dto.LivreDTO;
import biblioWebAppli.objets.Livre;
import biblioWebAppli.objets.Pret;

/**
 * @author jeanl
 *
 */
@Service
public class LivreMetierImpl implements ILivreMetier {
	
	@Autowired
	RestTemplate restTemplate;
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
    	headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

    	UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uRL)
    	        .queryParam("numLivre", livreCriteria.getNumLivre())
    	        .queryParam("titre", livreCriteria.getTitre())
    	        .queryParam("auteur", livreCriteria.getAuteur())
    	        .queryParam("nomCategorie", livreCriteria.getNomCategorie())
    	        .queryParam("page", pageable.getPageNumber())
    	        .queryParam("size", pageable.getPageSize());

    	HttpEntity<?> entity = new HttpEntity<>(headers);
    	
    	System.out.println("builderUI"+builder.toUriString());
    	
    	ResponseEntity<RestResponsePage<Livre>> livres = restTemplate.exchange
    			(builder.toUriString(), 
				HttpMethod.GET,
				entity,
    			new ParameterizedTypeReference<RestResponsePage<Livre>>(){});
        Page<Livre> pageLivre = livres.getBody();
        
        return pageLivre;
	}

	/**
	 * @throws biblioWebAppli.exceptions.EntityNotFoundException,  
	 * @throws  
	 * @param livreDTO
	 * @return
	 * @throws biblioWebAppli.exceptions.EntityAlreadyExistsException 
	
	 
	 */
	@Override
	public Livre createLivre(LivreDTO livreDTO){
		HttpHeaders headers = httpHeadersFactory.createHeaders(username,password);
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_JSON);

    	HttpEntity<LivreDTO> requestEntity = 
    	     new HttpEntity<>(livreDTO, headers);
    	ResponseEntity<Livre> response =restTemplate.exchange(uRL, HttpMethod.POST, requestEntity, 
			              Livre.class);
			
		  return response.getBody();
	}

	/**
	 * @param numLivre
	 * @param livreDTO
	 * @return
	 */
	@Override
	public Livre updateLivre(Livre livre) {
				    	
		LivreDTO livreDTO = new LivreDTO(); 
    	livreDTO.setAuteur(livre.getAuteur());
    	livreDTO.setTitre(livre.getTitre());
    	livreDTO.setNbExemplaires(livre.getNbExemplaires());
    	livreDTO.setNumCategorie(livre.getCategorie().getNumCategorie());
    	
    	HttpHeaders headers = httpHeadersFactory.createHeaders(username,password);
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	
    	HttpEntity<LivreDTO> requestEntity = 
       	     new HttpEntity<>(livreDTO, headers);
		
		String url = uRL+"/"+livre.getNumLivre();
    	
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", livre.getNumLivre().toString());
		URI uri = UriComponentsBuilder.fromUriString(url)
		        .buildAndExpand(params)
		        .toUri();
				
		ResponseEntity<Livre> response = restTemplate.exchange(uri , HttpMethod.PUT, requestEntity, Livre.class);
		
		return response.getBody(); 
		
	}
	

	/**
	 * @param numLivre
	 * @throws biblioWebAppli.exceptions.EntityNotFoundException,  
	 * @throws biblioWebAppli.exceptions.EntityNotDeletableException,
	 */
	@Override
	public String delete(Long numLivre) {
		 
		HttpHeaders headers = httpHeadersFactory.createHeaders(username,password);
    	headers.setAccept(Arrays.asList(MediaType.ALL));
    	headers.setContentType(MediaType.TEXT_PLAIN);
    	
    	HttpEntity<?> requestEntity = 
       	     new HttpEntity<>(headers);
		
		String url = uRL+"/"+numLivre;
    	
		ResponseEntity<String> response = restTemplate.exchange(url , HttpMethod.DELETE, requestEntity, String.class);
		
		System.out.println("response:"+ response.toString()); 
		
		return response.getBody(); 
		 
	}

}
