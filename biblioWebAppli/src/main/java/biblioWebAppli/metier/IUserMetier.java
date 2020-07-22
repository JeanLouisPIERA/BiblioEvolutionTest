/**
 * 
 */
package biblioWebAppli.metier;

import biblioWebAppli.dto.UserDTO;
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
	 */
	public User findUser(UserDTO userDTO);
	
	public User findByUsername(String username, String password);
	

}
