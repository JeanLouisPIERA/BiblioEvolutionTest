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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.fasterxml.jackson.databind.ObjectMapper;
import biblioWebAppli.criteria.LivreCriteria;
import biblioWebAppli.metier.ILivreMetier;
import biblioWebAppli.objets.Livre;



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
			@RequestParam(name="size", defaultValue="6") int size){
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
    
    
    
}
