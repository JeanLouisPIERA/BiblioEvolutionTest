/**
 * 
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


import biblioWebAppli.criteria.PretCriteria;

import biblioWebAppli.dto.PretDTO;
import biblioWebAppli.entities.Livre;
import biblioWebAppli.entities.Pret;
import biblioWebAppli.exceptions.BookNotAvailableException;
import biblioWebAppli.exceptions.EntityAlreadyExistsException;
import biblioWebAppli.exceptions.EntityNotFoundException;

import biblioWebAppli.metier.IPretMetier;

/**
 * @author jeanl
 *
 */
@Controller
public class PretController {
	
	@Autowired
    private IPretMetier pretMetier;
    @Autowired
    private ObjectMapper mapper;
    

    
    /**
     * Permet d'afficher une sélection de prets sous forme de page
     * @param model
     * @param pretCriteria
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value="/prets", method = RequestMethod.GET)
    public String searchByCriteria(Model model, @PathParam(value = "pretCriteria") PretCriteria pretCriteria, @RequestParam(name="page", defaultValue="0") int page, 
			@RequestParam(name="size", defaultValue="3") int size){
    	model.addAttribute("pretCriteria", new PretCriteria());
    	Page<Pret> pagePrets = pretMetier.searchByCriteria(pretCriteria, PageRequest.of(page, size), page, size);
    	model.addAttribute("prets", pagePrets.getContent());
    	model.addAttribute("page", Integer.valueOf(page));
		model.addAttribute("number", pagePrets.getNumber());
        model.addAttribute("totalPages", pagePrets.getTotalPages());
        model.addAttribute("totalElements", pagePrets.getTotalElements());
        model.addAttribute("size", pagePrets.getSize());
        return "prets/pretListe";
    }
    
    
    
    /**
     * Permet d'afficher le formulaire de création d'un pret
     * @param model
     * @return
     */
    @GetMapping("/prets/newPret/{numLivre}")
    public String newLivre(Model model, @PathVariable Long numLivre){
    	PretDTO pretDTO = new PretDTO();
    	pretDTO.setNumLivre(numLivre);
        model.addAttribute("pretDTO", pretDTO);
        return "prets/pretCreation";
    }

    
    /**
     * Permet de valider l'enregistrement d'un nouveau pret
     * @param model
     * @param pretDTO
     * @return
     * @throws EntityNotFoundException
     * @throws EntityAlreadyExistsException
     * @throws BookNotAvailableException
     */
    @PostMapping("/prets/newPret/{numLivre}")
    public String createPret(Model model, @ModelAttribute("pretDTO") PretDTO pretDTO) throws EntityNotFoundException, EntityAlreadyExistsException, BookNotAvailableException{
		
        Pret pretToCreate = pretMetier.createPret(pretDTO);
        model.addAttribute(pretToCreate);
		return "prets/pretConfirmation";
        
    }
	

}
