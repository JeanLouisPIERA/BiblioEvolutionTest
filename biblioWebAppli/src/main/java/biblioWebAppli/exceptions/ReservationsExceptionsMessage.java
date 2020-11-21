package biblioWebAppli.exceptions;

import org.springframework.stereotype.Component;

@Component
public class ReservationsExceptionsMessage {
	
	public String convertCodeStatusToExceptionMessage(Integer StatusCode) {
		String message ="";
		
		if (StatusCode ==406) {
			message = "RESERVATION IMPOSSIBLE : vous ne pouvez pas réserver un livre que vous avez déjà en cours de prêt";
		}
		
		if (StatusCode ==423) {
			message = "RESERVATION IMPOSSIBLE : le nombre maximum d'utilisateurs ayant fait une réservation est atteint";
		}
			
		return message;
	
	}
}

