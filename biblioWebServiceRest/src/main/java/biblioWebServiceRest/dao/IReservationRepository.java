package biblioWebServiceRest.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import biblioWebServiceRest.entities.Livre;
import biblioWebServiceRest.entities.Reservation;
import biblioWebServiceRest.entities.ReservationStatut;
import biblioWebServiceRest.entities.User;

public interface IReservationRepository extends JpaRepository<Reservation, Long>, JpaSpecificationExecutor<Reservation>{
	
	Optional<List<Reservation>> findAllByReservationStatut(ReservationStatut reservationStatut);
	
	Optional<List<Reservation>> findAllByLivre(Livre livre);
	
	@Query("select reservation from Reservation reservation where (reservation.livre = ?1) " + " AND (reservation.livre.nbExemplairesDisponibles >= ?2)" + "ORDER BY reservation.numReservation ASC")
	Optional<List<Reservation>> findAllByLivreAndNbExemplairesDisponibleMinimumOrderByNumReservationASC(
			Livre livre, 
			Integer nbExemplairesDisponiblesMinimum);
	
	@Query("select reservation from Reservation reservation where (reservation.reservationStatut = ?1) " + " AND (reservation.livre.nbExemplairesDisponibles = ?2)" + " AND (reservation.rangReservation = ?3)" )
	Optional<List<Reservation>> findAllByReservationStatutAndNbExemplairesDisponiblesAndRangReservation(
			ReservationStatut reservationStatut, 
			Integer nbExemplairesDisponiblesMinimum, 
			Integer rangReservation);
	
	@Query("select reservation from Reservation reservation where (reservation.reservationStatut = ?1) " + " AND (reservation.livre.nbExemplairesDisponibles = ?2 "+" or reservation.livre.nbExemplairesDisponibles > ?2)")
	Optional<List<Reservation>> findAllByReservationStatutAndLivreNombreExemplairesDisponiblesNamedParams(
			ReservationStatut reservationStatut,
			int nbExemplairesDisponibles);
	
	@Query("select reservation from Reservation reservation where (reservation.reservationStatut = ?1) " + " AND (reservation.dateDeadline >= ?2 )")
	Optional<List<Reservation>> findAllByReservationStatutAndDateDeadlineValide(
			ReservationStatut reservationStatut,
			LocalDate localDate);
	
	@Query("select reservation from Reservation reservation where (reservation.reservationStatut = ?1) " + " AND (reservation.dateDeadline < ?2 )")
	Optional<List<Reservation>> findAllByReservationStatutAndDateDeadlineDechue(
			ReservationStatut reservationStatut,
			LocalDate localDate);
			
	
	Optional<List<Reservation>> findAllByLivreAndReservationStatutOrReservationStatut(
			Livre livre, 
			ReservationStatut enregistree,
			ReservationStatut notifiee);
	
	Optional<List<Reservation>> findAllByLivreAndReservationStatut(Livre livre, 
			ReservationStatut enregistree);
	
	Optional<List<Reservation>> findAllByUserAndLivreAndReservationStatut(
			User user, 
			Livre livre, 
			ReservationStatut reservationStatut); 
	
	@Query("select reservation.user.idUser from Reservation reservation where (reservation.livre = ?1) " + " AND (reservation.reservationStatut = ?2 "+" or reservation.reservationStatut = ?3)" + " GROUP BY reservation.user.idUser")
	Optional<List<Reservation>> findAllByLivreGroupByUserAndReservationStatutOrReservationStatut(
			Livre livre, 
			ReservationStatut reservationStatut1, 
			ReservationStatut reservationStatut2);
	
	@Query("select reservation.livre.numLivre from Reservation reservation where (reservation.livre = ?1) " + " AND (reservation.reservationStatut = ?2)" + " GROUP BY reservation.livre.numLivre")
	Optional<List<Reservation>> findAllByLivreGroupByUserAndReservationStatut(
			Livre livre, 
			ReservationStatut reservationStatut1);
	
	Optional<List<Reservation>> findAllByUserAndLivre(
			User user, 
			Livre livre);
	
	@Query("select reservation from Reservation reservation where (reservation.user = ?1) " + "AND (reservation.livre =?2)"+" AND (reservation.reservationStatut= ?3 "+" or reservation.reservationStatut = ?4)")
	Optional<List<Reservation>> findAllByUserAndLivreAndReservationStatutOrReservationStatut(
			User user,
			Livre livre,
			ReservationStatut reservationStatut1,
			ReservationStatut reservationStatut2);
	
	@Query("select reservation from Reservation reservation where (reservation.user = ?1) " + "AND (reservation.livre =?2)"+" AND (reservation.reservationStatut= ?3 "+" or reservation.reservationStatut = ?4)")
	Optional<Reservation> findByUserAndLivreAndStatutReservationOrStatutReservation(User user, Livre livre, ReservationStatut reservationStatut1, ReservationStatut reservationStatut2);
	
	
}
