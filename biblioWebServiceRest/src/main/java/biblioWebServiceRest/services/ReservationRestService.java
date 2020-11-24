package biblioWebServiceRest.services;

import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import biblioWebServiceRest.criteria.ReservationCriteria;
import biblioWebServiceRest.dto.ReservationDTO;
import biblioWebServiceRest.entities.Pret;
import biblioWebServiceRest.entities.Reservation;
import biblioWebServiceRest.exceptions.BookAvailableException;
import biblioWebServiceRest.exceptions.BookNotAvailableException;
import biblioWebServiceRest.exceptions.EntityAlreadyExistsException;
import biblioWebServiceRest.exceptions.EntityNotFoundException;
import biblioWebServiceRest.exceptions.RentAlreadyExistsException;
import biblioWebServiceRest.exceptions.WrongNumberException;
import biblioWebServiceRest.mapper.ReservationMapper;
import biblioWebServiceRest.metier.IReservationMetier;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/biblio")
@Api(value="Gestion des réservations de livres")
public class ReservationRestService {
	
	@Autowired
	IReservationMetier reservationMetier;
	@Autowired
	ReservationMapper reservationMapper; 
	
	/**
	 * CRUD : CREATE ce endpoint permet de créer la réservation d'un exemplaire d'un livre qui n'a aucun exemplaire disponible
	 * Le livre à emprunter n'a aucun exemplaire en stock = réservation IMPOSSIBLE
	 * Il y a un exemplaire à emprunter pour le livre et la liste de reservations est vide = réservation IMPOSSIBLE
	 * La réservation ne doit pas faire passer le nombre de réservations en cours > 2x le nombre d'exmplaires = réservation IMPOSSIBLE
	 * La réservation ne doit pas porter sur un livre qui a déjà été emprunté = réservation REFUSEE
	 * @param reservationDTO
	 * @return
	 * @throws EntityNotFoundException
	 * @throws BookNotAvailableException
	 * @throws BookAvailableException
	 * @throws EntityAlreadyExistsException 
	 * @throws RentAlreadyExistsException 
	 */
	@ApiOperation(value = "Enregistrement d'une nouvelle réservation", response = Reservation.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 201, message = "La réservation a été créée"),
	        @ApiResponse(code = 404, message = "Ressource inexistante"),
	        @ApiResponse(code = 406, message = "Vous ne pouvez pas réserver un livre que vous avez déjà réservé ou emprunté"),
	        @ApiResponse(code = 417, message = "Vous ne pouvez pas réserver un livre dont un exemplaire est disponible au prêt"),
	        @ApiResponse(code = 423, message = "Il n'existe aucun exemplaire de ce livre, la réservation est impossible")
	        
	})
	@PostMapping(value="/reservations", produces = "application/json", consumes = "application/json")
	public ResponseEntity<Reservation> createReservation(@Valid @RequestBody ReservationDTO reservationDTO) throws EntityNotFoundException, BookNotAvailableException, BookAvailableException, EntityAlreadyExistsException, RentAlreadyExistsException{
		Reservation newReservation = reservationMetier.createReservation(reservationDTO);
		return new ResponseEntity<Reservation>(newReservation, HttpStatus.CREATED);
	}
	
	/**
	 * Ce endpoint permet de  modifier le statut d'une réservation ENREGISTREE pour le passer à NOTIFIEE
	 * Lorsque le livre qui fait l'objet de la réservation a un exemplaire disponible et que toutes les autres réservations
	 * dont le statut est ENREGISTREE ont une date de création postérieure à la réservation, la réservation passe en statut NOTIFIEE
	 * Toutes les réservations au statut notifiée font l'obejt d'un envoi de mail 
	 * @param numReservation
	 * @return
	 * @throws EntityNotFoundException
	 * @throws BookNotAvailableException
	 * @throws WrongNumberException
	 */
	@ApiOperation(value = "Notification d'une réservation en cours pour envoi d'un mail demandant à saisir le pret", response = Reservation.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 202, message = "Le statut de la réservation a été passé à NOTIFIEE"),
	        @ApiResponse(code = 400, message = "Le statut de cette réservation ne permet pas de la notifier"),
	        @ApiResponse(code = 404, message = "Ressource inexistante"),
	        
	})
	@PutMapping(value="/reservations/notification/{numReservation}", produces="application/json")
	public ResponseEntity<Reservation> notifierPret(@PathVariable Long numReservation) throws EntityNotFoundException, BookNotAvailableException, WrongNumberException {
		
		Reservation notificationReservation = reservationMetier.notifierReservation(numReservation);
		return new ResponseEntity<Reservation>(notificationReservation, HttpStatus.ACCEPTED);
	
	}
	
	/**
	 * Ce endpoint permet de  modifier le statut d'une réservation NOTIFIEE pour le passer à LIVREE
	 * Lorsque le livre qui fait l'objet de la réservation NOTIFIEE par mail est emprunté dans un délai de 48 heures 
	 * suite à l'envoi du mail de notification :
	 * *la réservation est passée en statut LIVREE   
	 * *le pret du livre est créé automatiquement
	 * Si le délai de 48 heures est dépassé une exception BookNotAvaiable est levée et la réservation est passée en statut
	 * REFUSEE 
	 * @param numReservation
	 * @return
	 * @throws EntityNotFoundException
	 * @throws BookNotAvailableException
	 * @throws WrongNumberException
	 */
	@ApiOperation(value = "Livraison d'une réservation notifiée et création d'un pret pour le livre correspondant", response = Reservation.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 202, message = "Le statut de la réservation a été passé à LIVREE"),
	        @ApiResponse(code = 400, message = "Le statit de cette livraison ne permet pas de la livrer"),
	        @ApiResponse(code = 404, message = "Ressource inexistante"),
	        @ApiResponse(code = 423, message = "La date limite pour livrer cette réservation a été dépassée")
	        
	})
	@PutMapping(value="/reservations/livraison/{numReservation}", produces="application/json")
	public ResponseEntity<Reservation> livrerPret(@PathVariable Long numReservation) throws EntityNotFoundException, BookNotAvailableException, WrongNumberException {
		
		Reservation livraisonReservation = reservationMetier.livrerReservationAndCreerPret(numReservation);
		return new ResponseEntity<Reservation>(livraisonReservation, HttpStatus.ACCEPTED);
	
	}
	
	/**
	 * Ce endpoint permet de à l'utilisateur de modifier le statut d'une réservation ENREGISTREE ou NOTIFIEE pour le passer à SUPPRIMEE
	 * Mais la réservation reste persistée : elle n'est pas delete de la base de données
	 * Cela permet de conserver l'information 
	 * * pour des analyses statistiques par exemple 
	 * * ou pour confirmer à l'utilisateur que sa demande de suppression de réservation a bien été prise en compte
	 * * ou pour justifier la suppression par l'utilisateur (en cas de mise en place d'un malus pour abus de réservations sans suite)
	 */
	@ApiOperation(value = "Suppression d'une réservation enregistrée ou notifiée", response = Reservation.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 202, message = "Le statut de la réservation a été passé à SUPPRIMEE"),
	        @ApiResponse(code = 400, message = "Le statut de cette livraison ne permet pas de la SUPPRIMER"),
	        @ApiResponse(code = 404, message = "Ressource inexistante"),
	})
	@PutMapping(value="/reservations/suppression/{numReservation}", produces="application/json")
	public ResponseEntity<Reservation> suppressReservation(@PathVariable Long numReservation) throws EntityNotFoundException, BookNotAvailableException, WrongNumberException {
		
		Reservation suppressionReservation = reservationMetier.suppressReservation(numReservation);
		return new ResponseEntity<Reservation>(suppressionReservation, HttpStatus.ACCEPTED);
	
	}
	
	/**
	 * endpoint pour la recherche Multicritères des reservations enregistrées 
	 * @param reservationCriteria
	 * @param page
	 * @param size
	 * @return
	 */
	@ApiOperation(value = "Recherche multi-critères d'une ou plusieurs reservations", response = Reservation.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "La recherche a été réalisée avec succés"),
	        @ApiResponse(code = 404, message = "Ressource inexistante"),
	})
	@GetMapping(value="/reservations", produces="application/json")
	public ResponseEntity<Page<Reservation>> searchAllReservationsByCriteria(@PathParam("reservationCriteria")ReservationCriteria reservationCriteria, @RequestParam int page, @RequestParam int size ) {
		System.out.println("controller="+reservationCriteria.getReservationStatutCode());
		Page<Reservation> reservations = reservationMetier.searchAllReservationsByCriteria(reservationCriteria, PageRequest.of(page, size));
		return new ResponseEntity<Page<Reservation>>(reservations, HttpStatus.OK);
	}
	
	/**
	 * Endpoint pour éditer la liste des réservations A NOTIFIER
	 * @return
	 * @throws EntityNotFoundException  
	 * @throws WrongNumberException
	 */
	@ApiOperation(value = "Recherche des réservations en statut NOTIFIEE", response = Pret.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "La recherche a été réalisée avec succés"),
	        @ApiResponse(code = 404, message = "Ressource inexistante"),
	})
	@GetMapping(value="/reservations/notifiees", produces="application/json")
	public ResponseEntity<List<Reservation>> selectReservationANotifier() throws EntityNotFoundException, WrongNumberException {
		List<Reservation> reservationsANotifierList = reservationMetier.searchAndNotifierReservations();
		return new ResponseEntity<List<Reservation>>(reservationsANotifierList, HttpStatus.OK); 
	}
	
	/**
	 * Endpoint pour la recherche d'une réservation par son identifiant
	 * @param numReservation
	 * @return
	 * @throws EntityNotFoundException
	 */
	@ApiOperation(value = "Recherche d'une réservation par son identifiant)", response = Reservation.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "La recherche a été réalisée avec succés"),
	        @ApiResponse(code = 404, message = "Ressource inexistante"),
	})
	@GetMapping(value="reservations/{numReservation}", produces="application/json")
	public ResponseEntity<Reservation> readReservation(@PathVariable Long numReservation) throws EntityNotFoundException {
		Reservation searchedReservation = reservationMetier.readReservation(numReservation);
		return new ResponseEntity<Reservation>(searchedReservation, HttpStatus.OK); 
	}
	
	
}
