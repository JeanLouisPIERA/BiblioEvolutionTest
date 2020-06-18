/**
 * Cette classe gère l'envoi d'une exception métier personnalisée 
 */
package biblioWebServiceRest.exceptions;

/**
 * Cette classe permet d'envoyer une exception quand l'entité nécessaire au traitement de la requête n'existe pas 
 * @author jeanl
 *
 */
public class EntityNotFoundException extends BiblioException {
	
	
	 
	public EntityNotFoundException() {
		super();
	}
 
	public EntityNotFoundException(String message) {
		super(message);
	}

}
