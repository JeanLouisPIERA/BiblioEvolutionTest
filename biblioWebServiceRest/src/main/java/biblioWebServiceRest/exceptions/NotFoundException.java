/**
 * 
 */
package biblioWebServiceRest.exceptions;

/**
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
