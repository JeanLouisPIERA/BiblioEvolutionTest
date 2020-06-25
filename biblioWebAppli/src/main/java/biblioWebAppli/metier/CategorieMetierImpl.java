/**
 * Classe permettant l'implémentation des méthodes métiers de l'application Web 
 * Grace à RestTemplate, ces méthodes l'application Web consomme en tant que client l'API REST biblioWebServiceRest
 */
package biblioWebAppli.metier;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



import biblioWebAppli.criteria.CategorieCriteria;
import biblioWebAppli.dto.CategorieDTO;

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
		public Page<CategorieDTO> searchByCriteria(CategorieCriteria categorieCriteria, int page, int size) {
	        List<CategorieDTO> categorieDTOs = (List<CategorieDTO>) Arrays.stream(restTemplate
	        		.getForObject(uRL+categorieCriteria, CategorieDTO[].class)).collect(Collectors.toList());
	        int end = (page + size > categorieDTOs.size() ? categorieDTOs.size() : (page + size));
			return new PageImpl<CategorieDTO>(categorieDTOs.subList(page, end));
	    
	    }
	    
	   

	    /**
		 * Permet de créer une nouvelle catégorie de livre
		 * @param categorieDTO
		 * @return
		 */
	    public CategorieDTO createCategorie(CategorieDTO categorieDTO) {
	        //return restTemplate.postForObject(resource, categorieDTO, CategorieDTO.class);
	    	CategorieDTO categorieToCreate = restTemplate.postForObject(uRL, categorieDTO, CategorieDTO.class);
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
