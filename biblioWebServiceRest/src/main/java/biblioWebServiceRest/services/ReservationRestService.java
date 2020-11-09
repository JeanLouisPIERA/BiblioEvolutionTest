package biblioWebServiceRest.services;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import biblioWebServiceRest.dto.PretDTO;
import biblioWebServiceRest.dto.ReservationDTO;
import biblioWebServiceRest.entities.Pret;
import biblioWebServiceRest.entities.Reservation;
import biblioWebServiceRest.exceptions.BookAvailableException;
import biblioWebServiceRest.exceptions.BookNotAvailableException;
import biblioWebServiceRest.exceptions.EntityNotFoundException;
import biblioWebServiceRest.exceptions.RentAlreadyExistsException;
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
	 * CRUD : CREATE Créer la réservation d'un exemplaire d'un livre qui n'a aucun exemplaire disponible
	 * Le livre à emprunter n'a aucun exemplaire en stock = réservation IMPOSSIBLE
	 * Il y a un exemplaire à emprunter pour le livre et la liste de reservations est vide = réservation IMPOSSIBLE
	 * La réservation ne doit pas faire passer le nombre de réservations en cours > 2x le nombre d'exmplaires = réservation IMPOSSIBLE
	 * La réservation ne doit pas porter sur un livre qui a déjà été emprunté = réservation REFUSEE
	 * @param reservationDTO
	 * @return
	 * @throws EntityNotFoundException
	 * @throws BookNotAvailableException
	 * @throws BookAvailableException
	 * @throws RentAlreadyExistsException 
	 */
	@ApiOperation(value = "Enregistrement d'une nouvelle réservation", response = Reservation.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 201, message = "La réservation a été créée"),
	        @ApiResponse(code = 404, message = "Ressource inexistante"),
	        @ApiResponse(code = 406, message = "Vous ne pouvez pas réserver un livre que vous avez déjà emprunté"),
	        @ApiResponse(code = 417, message = "Vous ne pouvez pas réserver un livre dont un exemplaire est disponible au prêt"),
	        @ApiResponse(code = 423, message = "Il n'existe aucun exemplaire de ce livre, la réservation est impossible")
	        
	})
	@PostMapping(value="/reservations", produces = "application/json", consumes = "application/json")
	public ResponseEntity<Reservation> createReservation(@Valid @RequestBody ReservationDTO reservationDTO) throws EntityNotFoundException, BookNotAvailableException, BookAvailableException, RentAlreadyExistsException{
		Reservation newReservation = reservationMetier.createReservation(reservationDTO);
		return new ResponseEntity<Reservation>(newReservation, HttpStatus.CREATED);
	}
	

}
