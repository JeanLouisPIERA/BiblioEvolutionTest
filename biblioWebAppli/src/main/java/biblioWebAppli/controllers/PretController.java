/**
 * 
 */
package biblioWebAppli.controllers;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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


import biblioWebAppli.criteria.PretCriteria;

import biblioWebAppli.dto.PretDTO;


import biblioWebAppli.metier.IPretMetier;

import biblioWebAppli.objets.Pret;
import biblioWebAppli.objets.UserAuth;

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
    @Value("${application.idUser}")
	private Long idUser;

    
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
    	
    	//UserAuth userInLogged = (UserAuth) model.getAttribute("user");
    	//String userInLoggedName = userInLogged.getUsername();
    	
    	Page<Pret> pagePrets = pretMetier.searchByCriteria(pretCriteria, PageRequest.of(page, size));
    	
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
    /**
    @GetMapping("/prets/livre/{numLivre}")
    public String newLivre(Model model, @PathVariable Long numLivre){
    	PretDTO pretDTO = new PretDTO();
    	pretDTO.setNumLivre(numLivre);
        model.addAttribute("pretDTO", pretDTO);
        return "prets/pretCreation";
    }
**/
    
    /**
     * Permet de valider l'enregistrement d'un nouveau pret
     * @param model
     * @param pretDTO
     * @return
     */
    @GetMapping("/prets/livre/{numLivre}")
    public String createPret(Model model, @PathVariable Long numLivre){
        try {
        	PretDTO pretDTO = new PretDTO();
        	pretDTO.setNumLivre(numLivre);
        	pretDTO.setIdUser(idUser);
        	System.out.println("pretIdUserControl"+pretDTO.getIdUser()); 
        	System.out.println("pretNumLivreControl"+pretDTO.getNumLivre()); 
        	model.addAttribute("pretDTO", pretDTO);
			Pret pretToCreate = pretMetier.createPret(pretDTO);
			model.addAttribute(pretToCreate);
		} catch (HttpClientErrorException e) {
			model.addAttribute("error", e.getResponseBodyAsString());
		     return"/error";
		}
		return "prets/pretConfirmation";
        
    }



	/**
	 * @param numPret
	 * @return
	 * @see biblioWebAppli.metier.IPretMetier#prolongerPret(java.lang.Long)
	 */
    @GetMapping("/prets/prolongation/{numPret}")
	public String prolongerPret(Model model, @PathVariable("numPret") Long numPret) {
		try {
			Pret pretAProlonger = pretMetier.prolongerPret(numPret);
			model.addAttribute(pretAProlonger);
		} catch (HttpClientErrorException e) {
			model.addAttribute("error", e.getResponseBodyAsString());
		     return"/error";
		}
		return "prets/pretProlongation";
	}
    
    @GetMapping("/prets/cloture/{numPret}")
	public String cloturerPret(Model model, @PathVariable("numPret") Long numPret) {
		try {
			Pret pretACloturer = pretMetier.cloturerPret(numPret);
			model.addAttribute(pretACloturer);
		} catch (HttpClientErrorException e) {
			model.addAttribute("error", e.getResponseBodyAsString());
		     return"/error";
		}
		return "prets/pretCloture";
	}
    
	

}
