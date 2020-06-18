/**
 * Cette classe gère l'envoi d'une exception métier personnalisée
 */
package biblioWebServiceRest.exceptions;

/**
 * Cette classe permet d'envoyer une exception métier si un des champs exigés du RequestBody est vide
 * @author jeanl
 *
 */
public class BadRequestException extends  BiblioException {

	public BadRequestException() {
		super();
	}

	public BadRequestException(String message) {
		super(message);
	}
}
