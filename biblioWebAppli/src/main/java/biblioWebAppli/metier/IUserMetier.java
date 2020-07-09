/**
 * 
 */
package biblioWebAppli.metier;

import biblioWebAppli.entities.User;

/**
 * @author jeanl
 *
 */
public interface IUserMetier {
	
	/**
	 * Permet de rechercher l'Utilisateur par son nom
	 * @param username
	 * @return
	 */
	public User findUser(String username);

}
