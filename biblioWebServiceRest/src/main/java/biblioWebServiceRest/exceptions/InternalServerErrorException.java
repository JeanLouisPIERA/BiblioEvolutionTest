/**
 * Ce package permet une gestion globale des exceptions
 */
package biblioWebServiceRest.exceptions;

/**
 * Cette classe permet de gérer les exceptions serveur de type 500
 * @author jeanl
 *
 */
public class InternalServerErrorException extends RuntimeException {
	
	String message;
	 
	public InternalServerErrorException() {
		super();
	}
 
	public InternalServerErrorException(String message) {
		super(message);
		this.message = message;
	}

}
