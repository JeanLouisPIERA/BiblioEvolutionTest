package biblioWebServiceRest.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ValueMapping;

import biblioWebServiceRest.dto.LivreDTO;
import biblioWebServiceRest.dto.PretDTO;
import biblioWebServiceRest.dto.ReservationDTO;
import biblioWebServiceRest.dto.UserDTO;
import biblioWebServiceRest.entities.Livre;
import biblioWebServiceRest.entities.Pret;
import biblioWebServiceRest.entities.Reservation;
import biblioWebServiceRest.entities.User;



@Mapper(componentModel="spring")
public interface ReservationMapper {
	
	@ValueMapping(source = "ReservationStatut.ANNULEE", target = "ReservationStatut.ANNULEE")
	@ValueMapping(source = "ReservationStatut.ENREGISTREE", target = "ReservationStatut.ENREGISTREE")
	@ValueMapping(source = "ReservationStatut.IMPOSSIBLE", target = "ReservationStatut.IMPOSSIBLE")
	@ValueMapping(source = "ReservationStatut.LIVREE", target = "ReservationStatut.LIVREE")
	@ValueMapping(source = "ReservationStatut.NOTIFIEE", target = "ReservationStatut.NOTIFIEEE")
	@ValueMapping(source = "ReservationStatut.REFUSEE", target = "ReservationStatut.REFUSEE")
	ReservationDTO reservationToReservationDTO(Reservation entity);
	
	Reservation reservationDTOToReservation(ReservationDTO dto);
	Pret pretDTOToPret(PretDTO dto);
	
	LivreDTO livreToLivreDTO(Livre entity);
	Livre livreDTOToLivre(LivreDTO dto);

	UserDTO userToUserDTO(User entity);
	User userDTOToUser(UserDTO dto);
	
	
	List<ReservationDTO> reservationsToReservationsDTOs(List<Reservation> reservations);
	List<Reservation> reservationDTOsToReseration(List<ReservationDTO> reservationDTOs);

}
