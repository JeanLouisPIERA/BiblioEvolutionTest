package biblioWebServiceRest.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import biblioWebServiceRest.entities.Reservation;
import biblioWebServiceRest.entities.ReservationStatut;

public interface IReservationRepository extends JpaRepository<Reservation, Long>, JpaSpecificationExecutor<Reservation>{
	
	Optional<List<Reservation>> findAllByReservationStatut(ReservationStatut reservationStatut);
	
	@Query("select reservation from Reservation reservation where (reservation.reservationStatut = ?1) " + " AND (reservation.livre.nbExemplairesDisponibles = ?2 "+" or reservation.livre.nbExemplairesDisponibles > ?2)")
	Optional<List<Reservation>> findAllByReservationStatutAndLivreNombreExemplairesDisponiblesNamedParams(
			ReservationStatut reservationStatut,
			int nbExemplairesDisponibles);
	
}
