/**
 * 
 */
package biblioWebServiceRest.exceptions;

/**
 * @author jeanl
 *
 */
public class ExceptionReponse {
	
	private String messageErreur;
	private int statusCode;
 
	
 
	/**
	 * @return the messageErreur
	 */
	public String getMessageErreur() {
		return messageErreur;
	}

	/**
	 * @param messageErreur the messageErreur to set
	 */
	public void setMessageErreur(String messageErreur) {
		this.messageErreur = messageErreur;
	}

	public int getStatusCode() {
		return statusCode;
	}
 
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

}
