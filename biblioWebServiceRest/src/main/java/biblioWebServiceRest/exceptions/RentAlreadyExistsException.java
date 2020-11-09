/**
 * Cette classe gère l'envoi d'une exception métier personnalisée 
 */
package biblioWebServiceRest.exceptions;

/**
 * Cette classe permet d'envoyer une exception métier si l'entité à construire ou à modifier existe déjà
 * @author jeanl
 *
 */
public class RentAlreadyExistsException extends BiblioException {

	public RentAlreadyExistsException() {
		super();
		
	}

	public RentAlreadyExistsException(String message) {
		super(message);
		
	}
	
	
	
	

}
