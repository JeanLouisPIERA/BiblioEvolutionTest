/**
 * Cette classe gère l'envoi d'une exception métier personnalisée 
 */
package biblioWebServiceRest.exceptions;

/**
 * Cette classe permet de gérer les exceptions serveur de type 500
 * @author jeanl
 *
 */
public class BookAvailableException extends BiblioException {
	
	
	 
	public BookAvailableException() {
		super();
	}
 
	public BookAvailableException(String message) {
		super(message);
		
	}

}
