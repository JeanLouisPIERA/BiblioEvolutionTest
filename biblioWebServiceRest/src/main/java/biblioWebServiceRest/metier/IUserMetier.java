/**
 * Interface de définition des méthodes Métier pour l'entité User
 */
package biblioWebServiceRest.metier;


import biblioWebServiceRest.dto.UserDTO;
import biblioWebServiceRest.entities.User;
import biblioWebServiceRest.exceptions.EntityNotFoundException;

public interface IUserMetier {
	
	
	public User registrateUser(UserDTO userDTO);
	
	public User findByUsernameAndPassword(String username, String password) throws EntityNotFoundException;
	
	
}
