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

import biblioWebAppli.dto.PretDTO;

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
    
    
    @Value("${application.username}")
	private String username;
	@Value("${application.password}")
	private String password;
	@Value("${idUserLoggedIn}")
	private Long idUserLoggedIn;
	@Value("${usernameLoggedIn}")
	private String usernameLoggedIn;
	
    @Value("${application.uRLPret}")
	private String uRL;

	/**
	 * @param pretDTO
	 * @return
	 * @throws EntityNotFoundException
	 * @throws BookNotAvailableException
	 */
	@Override
	public Pret createPret(PretDTO pretDTO) {
		HttpHeaders headers = httpHeadersFactory.createHeaders(username,password);
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	
    	pretDTO.setIdUser(idUserLoggedIn);
    	System.out.println("pretIdUser"+pretDTO.getIdUser()); 

    	HttpEntity<PretDTO> requestEntity = new HttpEntity<>(pretDTO, headers);
    	ResponseEntity<Pret> response = restTemplate.exchange(uRL, HttpMethod.POST, requestEntity, Pret.class);
			System.out.println(response.getStatusCodeValue());
			
    	return response.getBody();
	}

	/**
	 * @param numPret
	 * @return
	 * @throws EntityNotFoundException
	 * @throws BookNotAvailableException
	 */
	@Override
	public Pret prolongerPret(Long numPret){
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
	 * @param numPret
	 * @return
	 * @throws EntityNotFoundException
	 */
	@Override
	public Pret cloturerPret(Long numPret){
		HttpHeaders headers = httpHeadersFactory.createHeaders(username,password);
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	
    	HttpEntity<?> requestEntity = 
       	     new HttpEntity<>(headers);
		
		String url = uRL+"/cloture/"+numPret;
    	
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
		HttpHeaders headers = httpHeadersFactory.createHeaders(username,password);
		
		System.out.println("headersPret"+headers.toString());
		
    	headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    	
    	//String loggedInUsername= SecurityContextHolder.getContext().getAuthentication().getName();
    	//System.out.println("securityNom"+SecurityContextHolder.getContext().getAuthentication().getName());
    	
    	
    	
    	UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uRL)
    	        .queryParam("numPret", pretCriteria.getNumPret())
    	        .queryParam("username", usernameLoggedIn)
    	        //.queryParam("userId", pretCriteria.getUserId())
    	        .queryParam("numLivre", pretCriteria.getNumLivre())
    	        .queryParam("titre", pretCriteria.getTitre())
    	        .queryParam("auteur", pretCriteria.getAuteur())
    	        .queryParam("nomCategorieLivre", pretCriteria.getNomCategorieLivre())
    	        .queryParam("page", pageable.getPageNumber())
    	        .queryParam("size", pageable.getPageSize());
    	
    	System.out.println("uriPret"+(builder.toUriString()));
    	
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
