package biblioWebServiceRest.metier;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import biblioWebServiceRest.criteria.ReservationCriteria;
import biblioWebServiceRest.dto.ReservationDTO;
import biblioWebServiceRest.entities.Reservation;

public interface IReservationMetier {
	
		
	/**
	 * CRUD : CREATE Créer la réservation d'un exemplaire d'un livre qui n'a aucun exemplaire disponible
	 * La réservation ne doit pas faire passer le nombre de réservations en cours > 2x le nombre d'exmplaires = réservation IMPOSSIBLE
	 * La réservation ne doit pas porter sur un livre qui a déjà été emprunté = réservation REFUSEE
	 * @param reservationDTO
	 * @return
	 */
	
	Reservation createReservation(ReservationDTO reservationDTO) ;
	
	/**
	 * CRUD : UPDATE notifier la réservation pour indiquer que l'exemplaire du livre demandé est disponible pour l'emprunteur
	 * ReservationStatut = NOTIFIEE
	 * @param numReservation
	 * @return
	 */
	Reservation notifierReservation(Long numReservation) ;

	/**
	 * CRUD : UPDATE modifier le statut de la réservation lorsque le livre réservé a été emprunté
	 * ReservationStatut = LIVREE
	 * @param numReservation
	 * @return
	 */
	Reservation livrerReservation(Long numReservation) ;
	

// AFFICHER LES PRETS ENCOURS ***************************************************************************
	
	/**
	 * Afficher les réservations : recherce les prêts par ReservarionCriteria
	 * @param reservationCriteria
	 * @param pageable
	 * @return
	 */
	 Page<Reservation> searchByCriteria(ReservationCriteria reservationCriteria, Pageable pageable);
	 
	 /**
	  * Identifie et change le statut des réservations pour lesquelles il faut informer l'utilisateur 
	  * qu'un exemplaire du livre demandé est disponible
	  * ET que leur tour est venu pour emprunter dans les 48 heures qui suivent la notification
	  * @return
	  */
	 List<Reservation> searchAndNotifierReservations();
	 
	 /**
	  * Identifie et change le statut des réservations si le livre n'est pas emprunté dans les 48 heures qui suivent sa notification
	  * @return
	  */
	 List<Reservation> searchAndUpdateReservationsAnnulées(); 
	 
	
}
