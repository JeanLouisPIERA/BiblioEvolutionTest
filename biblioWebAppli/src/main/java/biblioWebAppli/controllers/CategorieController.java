/**
 * Permet de gérer l'affichage des pages html de l'Appli WEB
 */
package biblioWebAppli.controllers;

import java.io.IOException;



import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.databind.ObjectMapper;

import biblioWebAppli.criteria.CategorieCriteria;
import biblioWebAppli.dto.CategorieDTO;
import biblioWebAppli.dto.MessageDTO;

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
	     * Permet d'afficher une sélection de catégories sous forme de page
	     * @param model
	     * @param categorieCriteria
	     * @param page
	     * @param size
	     * @return
	     */
	    @GetMapping("/categories")
	    public String searchByCriteria(Model model, @PathParam("categorieCriteria") CategorieCriteria categorieCriteria, @RequestParam(name="page", defaultValue="0") int page, 
				@RequestParam(name="size", defaultValue="3") int size){
	        model.addAttribute("categories", categorieMetier.searchByCriteria(categorieCriteria, page, size));
	        return "categorieListe";
	    }
	    
	    /**
	     * permet d'afficher le formulaire de création d'une catégorie
	     * @param model
	     * @return
	     */
	    @GetMapping("/categorie")
	    public String newCategorie(Model model){
	        model.addAttribute("categorie", new CategorieDTO());
	        return "categorieCreation";
	    }

	    /**
	     * Permet de valider la création d'une nouvelle catégorie
	     * @param categorieDTO
	     * @return
	     */
	    @PostMapping("/categorie")
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
	    @DeleteMapping("/categorie/{numCategorie}")
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
