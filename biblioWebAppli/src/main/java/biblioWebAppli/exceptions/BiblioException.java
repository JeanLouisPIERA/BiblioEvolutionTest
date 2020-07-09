/**
 * Classe Mère de la gestion personnalisée des exceptions métier de l'API
 */
package biblioWebAppli.exceptions;

/**
 * @author jeanl
 *
 */
public class BiblioException extends Exception{
	
	private String message;

	public BiblioException() {
		super();
	}

	public BiblioException(String message) {
		super(message);
	}
	
	
	

}
