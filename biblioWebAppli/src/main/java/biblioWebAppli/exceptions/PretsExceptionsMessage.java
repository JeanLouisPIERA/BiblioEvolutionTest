package biblioWebAppli.exceptions;

import org.springframework.stereotype.Component;

@Component
public class PretsExceptionsMessage {
	
	public String convertCodeStatusToExceptionMessage(Integer StatusCode) {
		String message ="";
		
		if (StatusCode ==423) {
			message =  "PROLONGATION IMPOSSIBLE = Le statut de ce prêt ne permet pas de le prolonger";
			}
		
		
			
		return message;
	
	}
}

