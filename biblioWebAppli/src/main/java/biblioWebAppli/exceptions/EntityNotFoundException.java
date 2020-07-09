/**
 * Cette classe gère l'envoi d'une exception métier personnalisée 
 */
package biblioWebAppli.exceptions;

/**
 * Cette classe permet d'envoyer une exception quand la suppression de l'entité ne respecte pas les propriétés ACID
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
