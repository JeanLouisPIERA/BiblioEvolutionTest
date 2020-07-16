/**
 * * Permet de gérer l'affichage des pages html de l'Appli WEB pour les Livres
 */
package biblioWebAppli.controllers;



import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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


import biblioWebAppli.criteria.LivreCriteria;

import biblioWebAppli.dto.LivreDTO;


import biblioWebAppli.metier.ILivreMetier;
import biblioWebAppli.objets.Livre;
import biblioWebServiceRest.exceptions.EntityNotDeletableException;


/**
 * @author jeanl
 *
 */
@Controller
public class LivreController {
	
	@Autowired
    private ILivreMetier livreMetier;
    @Autowired
    private ObjectMapper mapper;
    
    
    /**
     * Permet d'afficher une sélection de livres sous forme de page
     * @param model
     * @param livreCriteria
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value="/livres", method = RequestMethod.GET)
    public String searchByCriteria(Model model, @PathParam(value = "livreCriteria") LivreCriteria livreCriteria, @RequestParam(name="page", defaultValue="0") int page, 
			@RequestParam(name="size", defaultValue="3") int size){
    	model.addAttribute("livreCriteria", new LivreCriteria());
    	
    	Page<Livre> livres = livreMetier.searchByCriteria(livreCriteria, PageRequest.of(page, size));
    	
    	model.addAttribute("livres", livres.getContent());
    	model.addAttribute("page", Integer.valueOf(page));
		model.addAttribute("number", livres.getNumber());
        model.addAttribute("totalPages", livres.getTotalPages());
        model.addAttribute("totalElements", livres.getTotalElements());
        model.addAttribute("size", livres.getSize());
        
        return "livres/livreListe";
    }
    
    
    
    /**
     * Permet d'afficher le formulaire de création d'un livre
     * @param model
     * @return
     */
    @GetMapping("/livres/newLivre")
    public String newLivre(Model model){
        model.addAttribute("livreDTO", new LivreDTO());
        return "livres/livreCreation";
    }

    
    /**
     * Permet de valider l'enregistrement d'un nouveau livre
     * @param model
     * @param livreDTO
     * @return
     */
    @PostMapping("/livres/newLivre")
    public String createLivre(Model model, @ModelAttribute("livreDTO") LivreDTO livreDTO) {
		
    	Object livreToCreate = new Object();
    	
    	try {
			livreToCreate = livreMetier.createLivre(livreDTO);
			model.addAttribute((Livre)livreToCreate);
		} catch (HttpClientErrorException e) {
			 model.addAttribute("error", e.getResponseBodyAsString());
		     return"/error";
		}
    	
    	
		return "livres/livreConfirmation";
        
    }
    
    /**
     * Permet d'afficher le formulaire de modification d'un livre
     * @param model
     * @param numLivre
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value="/livres/updateLivre/{numLivre}")
    public String ShowLivrePageToUpdate(Model model, @PathVariable long numLivre, @RequestParam(name="page", defaultValue="0") int page, 
			@RequestParam(name="size", defaultValue="1") int size) {
    
    	LivreCriteria livreCriteria = new LivreCriteria();
    	livreCriteria.setNumLivre(numLivre);
    	model.addAttribute("livreCriteria", livreCriteria);
    	
    	Livre livre = livreMetier.searchByCriteria(livreCriteria, PageRequest.of(page, size)).getContent().get(0);
    	
		model.addAttribute("livre", livre);
    	return "livres/livreEdition";
    	
    }
    
	
	/**
	 * Permet d'afficher le formulaire de modification d'un livre
	 * @param model
	 * @param livre
	 * @param livreDTO
	 * @return
	 */
    @RequestMapping(value = "livres/updateLivre/{numLivre}", method = RequestMethod.POST)
    public String addNewLivreToUpdate(Model model, Livre livre){
        
        try {
        	
			Livre livreToUpdate = livreMetier.updateLivre(livre); 
			model.addAttribute((Livre)livreToUpdate);
		} catch (HttpClientErrorException e) {
			System.out.println("error:"+e.getResponseBodyAsString() );
			 model.addAttribute("error", e.getResponseBodyAsString());
		     return"/error";
		}
      
        return "livres/livreModification";
        
        
    }
    

    /**
     * permet de supprimer une catégorie existante
     * @param numCategorie
     * @return
     * @throws biblioWebAppli.exceptions.EntityNotDeletableException 
     * @throws EntityNotFoundException 
     * @throws EntityNotDeletableException 
     */
        
    @RequestMapping(value="/livres/{numLivre}", method = RequestMethod.GET)
    public String delete(Model model, @PathVariable("numLivre") Long numLivre) {
        try {
			livreMetier.delete(numLivre);
		} catch (HttpClientErrorException e) {
			 model.addAttribute("error", e.getResponseBodyAsString());
		     return"/error";
		}
        return "/livres/livreSuppression";
    }

}
