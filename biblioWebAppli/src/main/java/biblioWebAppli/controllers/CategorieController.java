/**
 Permet de gérer l'affichage des pages html de l'Appli WEB pour les Catégories
 */
package biblioWebAppli.controllers;

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
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.databind.ObjectMapper;

import biblioWebAppli.criteria.CategorieCriteria;
import biblioWebAppli.dto.CategorieDTO;
import biblioWebAppli.metier.ICategorieMetier;
import biblioWebAppli.objets.Categorie;


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
	    	
	    	System.out.println("test");
	    	
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
	      
	     */
	    @PostMapping("/categories/newCategorie")
	    public String createCategorie(Model model, @ModelAttribute("categorie") CategorieDTO categorieDTO){
			
	        Categorie categorieToCreate;
			try {
				categorieToCreate = categorieMetier.createCategorie(categorieDTO);
				model.addAttribute(categorieToCreate);
			} catch (HttpClientErrorException e) {
		        model.addAttribute("error", e.getResponseBodyAsString());
		        return"/error";
			}
	        
			return "categories/categorieConfirmation";
	        
	    }
	    
	    /**
	     * permet de supprimer une catégorie existante
	     * @param numCategorie
	     * @return
	     * @throws Exception 
	     */
	    @RequestMapping(value="/categories/{numCategorie}", method = RequestMethod.GET)
	    public String delete(Model model, @PathVariable("numCategorie") Long numCategorie){
	        try {
				categorieMetier.delete(numCategorie);
			} catch (HttpClientErrorException e) {
		        model.addAttribute("error", e.getResponseBodyAsString());
		        return"/error";
			}
	        return "/categories/categorieSuppression";
	    }
	    
	    
		
}
