/**
 * Ce package permet une gestion globale des exceptions
 */
package biblioWebServiceRest.exceptions;

/**
 * Cette classe permet de gérer les exceptions clients de type 404
 * @author jeanl
 *
 */
public class NotFoundException extends RuntimeException {
	
	String message;
	 
	public NotFoundException() {
		super();
	}
 
	public NotFoundException(String message) {
		super(message);
		this.message = message;
	}

}
