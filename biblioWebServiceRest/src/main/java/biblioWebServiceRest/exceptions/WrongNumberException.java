/**
 * Cette classe gère l'envoi d'une exception métier personnalisée 
 */
package biblioWebServiceRest.exceptions;

/**
 * Cette classe permet de gérer les exceptions serveur de type 500
 * @author jeanl
 *
 */
public class WrongNumberException extends BiblioException {
	
	
	 
	public WrongNumberException() {
		super();
	}
 
	public WrongNumberException(String message) {
		super(message);
		
	}

}
