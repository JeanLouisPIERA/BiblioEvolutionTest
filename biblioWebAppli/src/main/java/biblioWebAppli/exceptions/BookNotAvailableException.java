/**
 * Cette classe gère l'envoi d'une exception métier personnalisée 
 */
package biblioWebAppli.exceptions;

/**
 * Cette classe permet d'envoyer une exception métier si l'entité à construire ou à modifier existe déjà
 * @author jeanl
 *
 */
public class BookNotAvailableException extends BiblioException {

	public BookNotAvailableException() {
		super();
		
	}

	public BookNotAvailableException(String message) {
		super(message);
		
	}
	
	
	
	

}
