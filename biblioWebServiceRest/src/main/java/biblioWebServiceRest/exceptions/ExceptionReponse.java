/**
 * Ce package permet une gestion globale des exceptions
 */
package biblioWebServiceRest.exceptions;

import java.time.LocalDate;

/**
 * Cette classe permet de personnaliser l'affichage des messages d'erreurs 
 * @author jeanl
 *
 */
public class ExceptionReponse {
	
	private LocalDate localDate; 
	private String messageErreur;
	private int statusCode;
 
	
 
	/**
	 * @return the localDate
	 */
	public LocalDate getLocalDate() {
		return localDate;
	}

	/**
	 * @param localDate the localDate to set
	 */
	public void setLocalDate(LocalDate localDate) {
		this.localDate = localDate;
	}

	/**
	 * @return messageErreur
	 */
	public String getMessageErreur() {
		return messageErreur;
	}

	/**
	 * @param messageErreur messageErreur à présenter
	 */
	public void setMessageErreur(String messageErreur) {
		this.messageErreur = messageErreur;
	}
	
	/**
	 * 
	 * @return statusCode présentation du Code Status de l'exception
	 */
	public int getStatusCode() {
		return statusCode;
	}
 
	/**
	 * 	
	 * @param statusCode établissement du Code Status de l'exception
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

}
