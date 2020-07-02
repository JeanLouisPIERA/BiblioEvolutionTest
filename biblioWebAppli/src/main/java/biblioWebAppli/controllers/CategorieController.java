/**
 * Permet de gérer l'affichage des pages html de l'Appli WEB
 */
package biblioWebAppli.controllers;

import java.io.IOException;



import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.databind.ObjectMapper;

import biblioWebAppli.criteria.CategorieCriteria;
import biblioWebAppli.dto.CategorieDTO;
import biblioWebAppli.dto.MessageDTO;
import biblioWebAppli.entities.Categorie;
import biblioWebAppli.metier.ICategorieMetier;



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
	    @GetMapping("/categories/search")
	    public String searchCategorieCriteria(Model model){
	        model.addAttribute("categorieCriteria", new CategorieCriteria());
	        model.addAttribute("page", null); 
	        model.addAttribute("size", null);
	        return "categories/categoriesSearch";
	    }
	    **/
	    
	    
	    /**
	     * Permet d'afficher une sélection de catégories sous forme de page
	     * @param model
	     * @param categorieCriteria
	     * @param page
	     * @param size
	     * @return
	     */
	    @RequestMapping(value="/categories", method = RequestMethod.GET)
	    public String searchByCriteria(Model model, @PathParam(value = "") CategorieCriteria categorieCriteria, @RequestParam(name="page", defaultValue="0") int page, 
				@RequestParam(name="size", defaultValue="3") int size){
	    	model.addAttribute("categorieCriteria", new CategorieCriteria());
	        //model.addAttribute("page", 0); 
	        //model.addAttribute("size", 3);
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
	        return "categorieCreation";
	    }

	    /**
	     * Permet de valider la création d'une nouvelle catégorie
	     * @param categorieDTO
	     * @return
	     */
	    @PostMapping("/categories/newCategorie")
	    public String createCategorie(@ModelAttribute("newCategorie") CategorieDTO categorieDTO){
	            categorieMetier.createCategorie(categorieDTO);
	            System.out.println("posted");
	            return "redirect:/";

	    }
	    
	    /**
	     * permet de supprimer une catégorie existante
	     * @param numCategorie
	     * @return
	     */
	    @DeleteMapping("/categories/{numCategorie}")
	    public String delete(@RequestParam Long numCategorie){
	        categorieMetier.delete(numCategorie);
	        return "redirect:/";
	    }
	    
	    /**
	     * Permet de gérer l'affichage des messages d'erreur
	     * @param ex
	     * @param model
	     * @return
	     * @throws IOException
	     */
	    @ExceptionHandler(HttpClientErrorException.class)
	    public String handleClientError(HttpClientErrorException ex, Model model) throws IOException {
	        MessageDTO dto = mapper.readValue(ex.getResponseBodyAsByteArray(), MessageDTO.class);
	        model.addAttribute("error", dto.getMessage());
	        return "errorMessage"; 
	    }

}
