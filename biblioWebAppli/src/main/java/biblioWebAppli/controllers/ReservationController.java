package biblioWebAppli.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;

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


import biblioWebAppli.criteria.ReservationCriteria;
import biblioWebAppli.exceptions.ReservationsExceptionsMessage;
import biblioWebAppli.metier.IReservationMetier;
import biblioWebAppli.objets.Reservation;



@Controller
public class ReservationController {
	
	@Autowired
    private IReservationMetier reservationMetier;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    ReservationsExceptionsMessage reservationExceptionMessage;
    @Value("${application.idUser}")
	private Long idUser;
    
    /**
     * Ce endpoint permet l'affichage des reservations de l'utilisateur sous forme de pages numérotées
     * Ce endpoint permet aussi de gérer des requêtes multi-critères sur ces réservations
     * @param model
     * @param reservationCriteria
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value="/reservations", method = RequestMethod.GET)
    public String searchByCriteria(Model model, @PathParam(value = "reservationCriteria") ReservationCriteria reservationCriteria, @RequestParam(name="page", defaultValue="0") int page, 
			@RequestParam(name="size", defaultValue="3") int size){
    	model.addAttribute("reservationCriteria", new ReservationCriteria());
    	
    	Page<Reservation> pageReservations = reservationMetier.searchAllReservationsByCriteria(reservationCriteria, PageRequest.of(page, size));
    	
    	model.addAttribute("reservations", pageReservations.getContent());
    	model.addAttribute("page", Integer.valueOf(page));
		model.addAttribute("number", pageReservations.getNumber());
        model.addAttribute("totalPages", pageReservations.getTotalPages());
        model.addAttribute("totalElements", pageReservations.getTotalElements());
        model.addAttribute("size", pageReservations.getSize());
        
        return "reservations/reservationListe";
    }
    
    /**
     * Ce endpoint permet de créer une réservation
     * @param model
     * @param numLivre
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    @GetMapping("/reservations/creation/{numLivre}")
	public String createReservation(Model model, @PathVariable("numLivre") Long numLivre) throws FileNotFoundException, IOException {
		try {
			Reservation reservation = reservationMetier.createReservation(numLivre);
			model.addAttribute(reservation);
		} catch (HttpClientErrorException e) {
			String errorMessage = reservationExceptionMessage.convertCodeStatusToExceptionMessage(e.getRawStatusCode());
			model.addAttribute("error", errorMessage);
		     return"/error";
		}
		return "reservations/reservationConfirmationCreation";
	}
    
    /**
     * Ce endpoint permet de passer le statut d'une réservation en SUPPRIMEE
     * @param model
     * @param numReservation
     * @return
     */
    @GetMapping(value="/reservations/suppression/{numReservation}", consumes="application/json")
	public String suppressReservation(Model model, @PathVariable("numReservation") Long numReservation) {
		try {
			Reservation reservation = reservationMetier.suppressReservation(numReservation);
			model.addAttribute(reservation);
		} catch (HttpClientErrorException e) {
			String errorMessage = reservationExceptionMessage.convertCodeStatusToExceptionMessage(e.getRawStatusCode());
			model.addAttribute("error", errorMessage);
		}
		return "reservations/reservationConfirmationSuppression";
	}

}
