/**
 * Classe permettant l'implémentation des méthodes métiers de l'application Web 
 * Grace à RestTemplate, ces méthodes l'application Web consomme en tant que client l'API REST biblioWebServiceRest
 */
package biblioWebAppli.metier;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import biblioWebAppli.criteria.CategorieCriteria;
import biblioWebAppli.dto.CategorieDTO;
import biblioWebAppli.entities.Categorie;
import biblioWebServiceRest.exceptions.EntityAlreadyExistsException;
import biblioWebServiceRest.exceptions.EntityNotDeletableException;





/**
 * @author jeanl
 *
 */
@Service
public class CategorieMetierImpl implements ICategorieMetier{
			    
	    @Autowired
	    private RestTemplate restTemplate;
	    
	    public final String uRL = "http://localhost:8080/categories";
	    
	    
	    /**
	     * Permet d'obtenir une sélection paginée des catégories
	     * @param categorieCriteria
	     * @param page
	     * @param size
	     * @return
	     */
	    @Override
		public Page<Categorie> searchByCriteria(CategorieCriteria categorieCriteria, int page, int size) {
	    	
	    	HttpHeaders headers = new HttpHeaders();
	    	headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

	    	UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uRL)
	    	        .queryParam("nomCategorie", categorieCriteria.getNomCategorie())
	    	        .queryParam("page", page)
	    	        .queryParam("size", size);
	    	
	    	System.out.println("uri =" + builder.toUriString());

	    	HttpEntity<?> entity = new HttpEntity<>(headers);
	    	
	    	ResponseEntity<RestResponsePage<Categorie>> categories = restTemplate.exchange
	    			(builder.toUriString(), 
    				HttpMethod.GET,
    				entity,
	    			new ParameterizedTypeReference<RestResponsePage<Categorie>>(){});
	        Page<Categorie> pageCategorie = categories.getBody();
	        
	            	
	        return pageCategorie;
	    	
	    	
	    	
	    }
	    
	   

	    /**
	     * @throws biblioWebAppli.exceptions.EntityAlreadyExistsException 
		 * Permet de créer une nouvelle catégorie de livre
		 * @param categorieDTO
		 * @return
	     * @throws  
		 */
	    public Categorie createCategorie(CategorieDTO categorieDTO) throws biblioWebAppli.exceptions.EntityAlreadyExistsException {
	    	
	    	HttpHeaders headers = new HttpHeaders();
	    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    	headers.setContentType(MediaType.APPLICATION_JSON);

	    	Categorie categorieToCreate = new Categorie();
	    	
	    	HttpEntity<CategorieDTO> requestEntity = 
	    	     new HttpEntity<>(categorieDTO, headers);
	    	ResponseEntity<Categorie> response;
			try {
				response = restTemplate.exchange(uRL, HttpMethod.POST, requestEntity, 
				              Categorie.class);
				System.out.println(response.getStatusCodeValue());
				categorieToCreate = response.getBody();
			} catch (HttpClientErrorException exception) {
				if(exception.getStatusCode() == HttpStatus.CONFLICT) 
					throw new biblioWebAppli.exceptions.EntityAlreadyExistsException("La categorie que vous souhaitez creer existe deja");
				
			}
	        
	    	return categorieToCreate;
	    }
	    
	    /**
		 * Permet de supprimer une categorie de livre
		 * @param numCategorie
	     * @throws EntityNotDeletableException 
		 */
	   
	    public void delete(Long numCategorie) throws EntityNotDeletableException {
	    	
	        try {
				restTemplate.delete(uRL+"/"+numCategorie);
			} catch (HttpClientErrorException exception) {
				if(exception.getStatusCode() == HttpStatus.PRECONDITION_FAILED) 
					throw new EntityNotDeletableException("Vous ne pouvez pas supprimer cette categorie qui contient des livres"); 
			}
	    	
	    	
	    	
	           
	     }
	     
	    	
	    }
	    



