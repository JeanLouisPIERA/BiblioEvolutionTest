package biblioWebAppli.exceptions;

import org.springframework.stereotype.Component;

@Component
public class ReservationsExceptionsMessage {
	
	public String convertCodeStatusToExceptionMessage(Integer StatusCode) {
		String message ="";
		
		if (StatusCode ==400) {
			message = "SUPPRESSION IMPOSSIBLE = Le statut de cette réservation ne permet pas de la supprimer";
		}
		
		if (StatusCode ==406) {
			message = "RESERVATION IMPOSSIBLE : vous ne pouvez pas réserver un livre que vous avez déjà un prêt en-cours, prolongé ou échu";
		}
		
		if (StatusCode ==409) {
			message = "RESERVATION IMPOSSIBLE : vous ne pouvez pas réserver un livre pour lequel vous avez déjà une réservation en-cours ou notifiée";
		}
		if(StatusCode==417) {
		message ="RESERVATION IMPOSSIBLE = Vous pouvez emprunter immédiatement un exemplaire disponible de ce livre";
		}
		
		if (StatusCode ==423) {
			message = "RESERVATION IMPOSSIBLE : le nombre maximum d'utilisateurs ayant fait une réservation est atteint";
		}
		
		
			
		return message;
	
	}
}

