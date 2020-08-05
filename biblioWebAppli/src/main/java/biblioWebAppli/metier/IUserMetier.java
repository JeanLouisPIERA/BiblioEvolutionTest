/**
 * 
 */
package biblioWebAppli.metier;

import java.io.FileNotFoundException;
import java.io.IOException;

import biblioWebAppli.objets.User;

/**
 * @author jeanl
 *
 */
public interface IUserMetier {
	
	/**
	 * Permet de rechercher l'Utilisateur par son nom
	 * @param username
	 * @return
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 */
	
	
	public User findByUsernameAndPassword(String username, String password) throws FileNotFoundException, IOException;
	
	
}
