/**
 * Classe permettant l'implémentation des méthodes métiers de l'application Web 
 * Grace à RestTemplate, ces méthodes l'application Web consomme en tant que client l'API REST biblioWebServiceRest
 */
package biblioWebAppli.metier;

import org.springframework.beans.factory.annotation.Autowired;
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

import biblioWebAppli.criteria.CategorieCriteria;
import biblioWebAppli.dto.CategorieDTO;
import biblioWebAppli.entities.Categorie;





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
	    	        .queryParam("categorieCriteria", categorieCriteria)
	    	        .queryParam("page", page)
	    	        .queryParam("size", size);

	    	HttpEntity<?> entity = new HttpEntity<>(headers);
	    	
	    	ResponseEntity<Page<Categorie>> categories = restTemplate.exchange
	    			(builder.toUriString(), 
    				HttpMethod.GET,
    				entity,
	    			new ParameterizedTypeReference<Page<Categorie>>(){});
	        Page<Categorie> pageCategorie = categories.getBody();
	            	
	        return pageCategorie;
	    	
	    	
	    	
	    }
	    
	   

	    /**
		 * Permet de créer une nouvelle catégorie de livre
		 * @param categorieDTO
		 * @return
		 */
	    public Categorie createCategorie(CategorieDTO categorieDTO) {
	        //return restTemplate.postForObject(resource, categorieDTO, CategorieDTO.class);
	    	Categorie categorieToCreate = restTemplate.postForObject(uRL, categorieDTO, Categorie.class);
	    	return categorieToCreate;
	    }
	    
	    /**
		 * Permet de supprimer une categorie de livre
		 * @param numCategorie
		 */
	   
	    public void delete(Long numCategorie) {
	        restTemplate.delete(uRL+numCategorie);
	    }
	    
		

}
