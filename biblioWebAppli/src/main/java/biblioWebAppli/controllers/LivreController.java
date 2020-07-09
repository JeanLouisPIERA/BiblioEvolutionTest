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

import com.fasterxml.jackson.databind.ObjectMapper;


import biblioWebAppli.criteria.LivreCriteria;

import biblioWebAppli.dto.LivreDTO;

import biblioWebAppli.entities.Livre;
import biblioWebAppli.exceptions.EntityAlreadyExistsException;
import biblioWebAppli.exceptions.EntityNotFoundException;

import biblioWebAppli.metier.ILivreMetier;
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
    	Page<Livre> pageLivres = livreMetier.searchByCriteria(livreCriteria, PageRequest.of(page, size), page, size);
    	model.addAttribute("livres", pageLivres.getContent());
    	model.addAttribute("page", Integer.valueOf(page));
		model.addAttribute("number", pageLivres.getNumber());
        model.addAttribute("totalPages", pageLivres.getTotalPages());
        model.addAttribute("totalElements", pageLivres.getTotalElements());
        model.addAttribute("size", pageLivres.getSize());
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
     * @throws EntityAlreadyExistsException 
     * @throws EntityNotFoundException 
     */
    @PostMapping("/livres/newLivre")
    public String createLivre(Model model, @ModelAttribute("livreDTO") LivreDTO livreDTO) throws EntityNotFoundException, EntityAlreadyExistsException{
		
        Livre livreToCreate = livreMetier.createLivre(livreDTO);
        model.addAttribute(livreToCreate);
		return "livres/livreConfirmation";
        
    }
    
    /**
     * permet de supprimer une catégorie existante
     * @param numCategorie
     * @return
     * @throws biblioWebAppli.exceptions.EntityNotDeletableException 
     * @throws EntityNotFoundException 
     * @throws EntityNotDeletableException 
     */
        
    @RequestMapping(value="/livres/delete/{numLivre}", method = RequestMethod.GET)
    public String delete(@PathVariable("numLivre") Long numLivre) throws EntityNotFoundException, biblioWebAppli.exceptions.EntityNotDeletableException {
        livreMetier.delete(numLivre);
        return "/livres/livreSuppression";
    }

}
