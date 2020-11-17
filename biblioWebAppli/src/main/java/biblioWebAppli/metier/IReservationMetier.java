package biblioWebAppli.metier;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import biblioWebAppli.criteria.ReservationCriteria;
import biblioWebAppli.objets.Reservation;



public interface IReservationMetier {
	
		
	/**
	 * CRUD : CREATE Créer la réservation d'un exemplaire d'un livre qui n'a aucun exemplaire disponible
	 * Le livre à emprunter n'a aucun exemplaire en stock = réservation IMPOSSIBLE
	 * Il y a un exemplaire à emprunter pour le livre et la liste de reservations est vide = réservation IMPOSSIBLE
	 * La réservation ne doit pas faire passer le nombre de réservations en cours > 2x le nombre d'exmplaires = réservation IMPOSSIBLE
	 * La réservation ne doit pas porter sur un livre qui a déjà été emprunté = réservation REFUSEE
	 * @param numLivre
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */

	Reservation createReservation(Long numLivre) throws FileNotFoundException, IOException; 
	
	/**
	 * CRUD : DELETE un emprunteur supprime volontairement une réservation
	 * @param numReservation
	 * @return
	 * @throws EntityNotFoundException 
	 * @throws WrongNumberException 
	 */
	Reservation suppressReservation(Long numReservation);
	

// AFFICHER LES RESERVATIONS ENCOURS ***************************************************************************
	
	/**
	 * Afficher toutes les réservations en cours: recherche les réservations par ReservationCriteria
	 * @param reservationCriteria
	 * @param pageable
	 * @return
	 */
	 Page<Reservation> searchAllReservationsByCriteria(ReservationCriteria reservationCriteria, Pageable pageable);
	 

	 
	 
	 
	
}
