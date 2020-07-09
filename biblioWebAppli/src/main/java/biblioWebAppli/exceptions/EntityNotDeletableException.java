/**
 * Cette classe gère l'envoi d'une exception métier personnalisée 
 */
package biblioWebAppli.exceptions;

/**
 * Cette classe permet d'envoyer une exception quand la suppression de l'entité ne respecte pas les propriétés ACID
 * @author jeanl
 *
 */
public class EntityNotDeletableException extends BiblioException {

	public EntityNotDeletableException() {
		super();
	}

	public EntityNotDeletableException(String message) {
		super(message);
	}
	
	

}
