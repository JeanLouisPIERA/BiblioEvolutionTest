package biblioWebServiceRest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import biblioWebServiceRest.entities.Reservation;

public interface IReservationRepository extends JpaRepository<Reservation, Long>, JpaSpecificationExecutor<Reservation>{

}
