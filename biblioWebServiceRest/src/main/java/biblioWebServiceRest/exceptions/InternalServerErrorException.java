/**
 * 
 */
package biblioWebServiceRest.exceptions;

/**
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
