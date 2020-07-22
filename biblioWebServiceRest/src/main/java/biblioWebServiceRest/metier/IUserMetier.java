/**
 * Interface de définition des méthodes Métier pour l'entité User
 */
package biblioWebServiceRest.metier;

import java.util.Optional;

import biblioWebServiceRest.entities.User;
import biblioWebServiceRest.exceptions.EntityNotFoundException;

public interface IUserMetier {
	
	
	public void save(User user);
	
	public User findByUsername(String username) throws EntityNotFoundException;
	
	public User findByUsernameAndPassword(String username, String password) throws EntityNotFoundException;
	
	public User createUser(String username);
	
	public User createAdmin(String username);


}
