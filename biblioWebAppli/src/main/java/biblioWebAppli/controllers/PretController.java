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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.databind.ObjectMapper;

import biblioWebAppli.criteria.PretCriteria;
import biblioWebAppli.exceptions.PretsExceptionsMessage;
import biblioWebAppli.exceptions.ReservationsExceptionsMessage;
import biblioWebAppli.metier.IPretMetier;

import biblioWebAppli.objets.Pret;

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
    @Autowired
    PretsExceptionsMessage pretExceptionMessage;
    
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
			@RequestParam(name="size", defaultValue="4") int size){
    	model.addAttribute("pretCriteria", new PretCriteria());
    	
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
	 * Permet de prolonger la durée d'un prêt
	 * @param numPret
	 * @return
	 * @see biblioWebAppli.metier.IPretMetier#prolongerPret(java.lang.Long)
	 */
    @GetMapping("/prets/prolongation/{numPret}")
	public String prolongerPret(Model model, @PathVariable("numPret") Long numPret) {
		try {
			Pret pretAProlonger = pretMetier.prolongerPret(numPret);
			model.addAttribute("pret", pretAProlonger);
		} catch (HttpClientErrorException e) {
			String errorMessage = pretExceptionMessage.convertCodeStatusToExceptionMessage(e.getRawStatusCode());
			model.addAttribute("error", errorMessage);
			return"/error";
		}
		return "prets/pretProlongation";
	}
    
    /**
     * Permet de lire la fiche d'un prêt
     * @param model
     * @param numPret
     * @return
     */
    @GetMapping("/prets/{numPret}")
    public String readPret(Model model, @PathVariable("numPret") Long numPret) {
    	try {
			Pret searchedPret = pretMetier.readPret(numPret);
			model.addAttribute("pret", searchedPret);
		} catch (HttpClientErrorException e) {
			String errorMessage = pretExceptionMessage.convertCodeStatusToExceptionMessage(e.getRawStatusCode());
			model.addAttribute("error", errorMessage);
			return"/error";
		}
    	return "prets/pretView";
    
    }

 }
