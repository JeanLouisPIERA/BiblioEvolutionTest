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
    private RestTemplate restTemplate;
    
    //public final String uRL = "http://localhost:8080/livres";
    @Value("${application.uRLLivre}")
	private String uRL;
	
    
	/**
	 * @param livreCriteria
	 * @param page
	 * @param size
	 * @return
	 */
	@Override
	public Page<Livre> searchByCriteria(LivreCriteria livreCriteria, Pageable pageable) {
		HttpHeaders headers = new HttpHeaders();
    	headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

    	UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uRL)
    	        .queryParam("numLivre", livreCriteria.getNumLivre())
    	        .queryParam("titre", livreCriteria.getTitre())
    	        .queryParam("auteur", livreCriteria.getAuteur())
    	        .queryParam("nomCategorie", livreCriteria.getNomCategorie())
    	        .queryParam("page", pageable.getPageNumber())
    	        .queryParam("size", pageable.getPageSize());

    	HttpEntity<?> entity = new HttpEntity<>(headers);
    	
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
		HttpHeaders headers = new HttpHeaders();
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
		/**
		HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	
    	//System.out.println("URI:"+ builder.toUriString());
    	//System.out.println("URI:"+ uRL+"/"+livre.getNumLivre());     
    	
    	String URI = uRL+"/"+livre.getNumLivre();
    	System.out.println("URI2:"+ uRL+"/"+livre.getNumLivre()); 
    	
    	headers.setConnection(URI);
    	
        Long numLivre = livre.getNumLivre();
        
        Map<String, String> param = new HashMap<String, String>();
        param.put("numLivre","numLivre");
    	
    	LivreDTO livreDTO = new LivreDTO(); 
    	livreDTO.setAuteur(livre.getAuteur());
    	livreDTO.setTitre(livre.getTitre());
    	livreDTO.setNbExemplaires(livre.getNbExemplaires());

    	HttpEntity<LivreDTO> requestEntity = 
    	     new HttpEntity<>(livreDTO, headers);
    	
    	System.out.println("requestEntity" + requestEntity); 
    	System.out.println("NbExemplaires" + livre.getNbExemplaires()); 
    	
ResponseEntity<Livre> response = 

//restTemplate.
    		
    			restTemplate.exchange(
    			URI, 
    			HttpMethod.PUT, 
    			requestEntity, 
			    Livre.class 
			    ,param
			    );
	
    	
    	
    	

    	//System.out.println("URI:"+ uRL+"/"+livre.getNumLivre());
    	//System.out.println("response" + response.getBody());
    	
    	
		  return response.getBody();
    	
    	
		  **/
		    	
		LivreDTO livreDTO = new LivreDTO(); 
    	livreDTO.setAuteur(livre.getAuteur());
    	livreDTO.setTitre(livre.getTitre());
    	livreDTO.setNbExemplaires(livre.getNbExemplaires());
    	livreDTO.setNumCategorie(livre.getCategorie().getNumCategorie());
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	
    	HttpEntity<LivreDTO> requestEntity = 
       	     new HttpEntity<>(livreDTO, headers);
		
		String url = uRL+"/"+livre.getNumLivre();
    	System.out.println("URI:"+ uRL+"/"+livre.getNumLivre()); 
		//String url = "http://test.com/Services/rest/{id}/Identifier";
    	
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", livre.getNumLivre().toString());
		URI uri = UriComponentsBuilder.fromUriString(url)
		        .buildAndExpand(params)
		        .toUri();
		System.out.println("URI1:"+ uri); 
		/**
		uri = UriComponentsBuilder
		        .fromUri(uri)
		        .queryParam("auteur", livreDTO.getAuteur())
		        .queryParam("titre", livreDTO.getTitre())
		        .queryParam("nbExemplaires", livreDTO.getNbExemplaires())
		        .queryParam("numCategorie", livreDTO.getNumCategorie())
		        .build()
		        .toUri();
		
		System.out.println("URI2:"+ uri); 
		**/
		
		
		
		ResponseEntity<Livre> response = restTemplate.exchange(uri , HttpMethod.PUT, requestEntity, Livre.class);
		
		System.out.println("response:"+ response.toString()); 
		
		return response.getBody(); 
		
	}
	

	/**
	 * @param numLivre
	 * @throws biblioWebAppli.exceptions.EntityNotFoundException,  
	 * @throws biblioWebAppli.exceptions.EntityNotDeletableException,
	 */
	@Override
	public String delete(Long numLivre) {
		// restTemplate.delete(uRL+"/"+numLivre);
		 
		HttpHeaders headers = new HttpHeaders();
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
