/**
 * Permet de gérer l'affichage des pages html de l'Appli WEB pour les Catégories
 */
package biblioWebAppli.controllers;

import java.io.IOException;



import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



import com.fasterxml.jackson.databind.ObjectMapper;

import biblioWebAppli.criteria.CategorieCriteria;
import biblioWebAppli.dto.CategorieDTO;

import biblioWebAppli.entities.Categorie;
import biblioWebAppli.exceptions.EntityAlreadyExistsException;
import biblioWebAppli.metier.ICategorieMetier;
import biblioWebServiceRest.exceptions.EntityNotDeletableException;




/**
 * @author jeanl
 *
 */
@Controller
public class CategorieController {
	
	 	@Autowired
	    private ICategorieMetier categorieMetier;
	    @Autowired
	    private ObjectMapper mapper;
	    
	    /**
	     * Permet d'afficher une sélection de catégories sous forme de page
	     * @param model
	     * @param categorieCriteria
	     * @param page
	     * @param size
	     * @return
	     */
	    @RequestMapping(value="/categories", method = RequestMethod.GET)
	    public String searchByCriteria(Model model, @PathParam(value = "categorieCriteria") CategorieCriteria categorieCriteria, @RequestParam(name="page", defaultValue="0") int page, 
				@RequestParam(name="size", defaultValue="3") int size){
	    	model.addAttribute("categorieCriteria", new CategorieCriteria());
	    	Page<Categorie> pageCategories = categorieMetier.searchByCriteria(categorieCriteria, page, size);
	    	model.addAttribute("categories", pageCategories.getContent());
	    	model.addAttribute("page", Integer.valueOf(page));
			model.addAttribute("number", pageCategories.getNumber());
	        model.addAttribute("totalPages", pageCategories.getTotalPages());
	        model.addAttribute("totalElements", pageCategories.getTotalElements());
	        model.addAttribute("size", pageCategories.getSize());
	        return "categories/categorieListe";
	    }
	    
	    /**
	     * permet d'afficher le formulaire de création d'une catégorie
	     * @param model
	     * @return
	     */
	    @GetMapping("/categories/newCategorie")
	    public String newCategorie(Model model){
	        model.addAttribute("categorie", new CategorieDTO());
	        return "categories/categorieCreation";
	    }

	    /**
	     * Permet de valider la création d'une nouvelle catégorie
	     * @param categorieDTO
	     * @return
	     * @throws EntityAlreadyExistsException 
	     */
	    @PostMapping("/categories/newCategorie")
	    public String createCategorie(Model model, @ModelAttribute("categorie") CategorieDTO categorieDTO) throws EntityAlreadyExistsException{
			
	        Categorie categorieToCreate = categorieMetier.createCategorie(categorieDTO);
	        
	        /**
	        if(result.hasErrors()) {
	        	String errorMessage = messageCreationCategorie.getStatusCode().toString();
				model.addAttribute(errorMessage);
				return "categories/categorieException";
			}
	        **/
	        
	        
	        model.addAttribute(categorieToCreate);
			return "categories/categorieConfirmation";
	        
	    }
	    
	    /**
	     * permet de supprimer une catégorie existante
	     * @param numCategorie
	     * @return
	     * @throws EntityNotDeletableException 
	     */
	    @RequestMapping(value="/categories/delete/{numCategorie}", method = RequestMethod.GET)
	    public String delete(@PathVariable("numCategorie") Long numCategorie) throws EntityNotDeletableException{
	        categorieMetier.delete(numCategorie);
	        return "/categories/categorieSuppression";
	    }
	    
	    /**
	     * Permet de gérer l'affichage des messages d'erreur
	     * @param ex
	     * @param model
	     * @return
	     * @throws IOException
	     */
	    /**
	    @ExceptionHandler(HttpClientErrorException.class)
	    public String handleClientError(HttpClientErrorException ex, Model model) throws IOException {
	        MessageDTO dto = mapper.readValue(ex.getResponseBodyAsByteArray(), MessageDTO.class);
	        model.addAttribute("error", dto.getMessage());
	        return "errorMessage"; 
	    }
		**/
}
