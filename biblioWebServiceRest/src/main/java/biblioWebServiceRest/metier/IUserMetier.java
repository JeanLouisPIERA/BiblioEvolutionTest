/**
 * Interface de définition des méthodes Métier pour l'entité User
 */
package biblioWebServiceRest.metier;

import biblioWebServiceRest.entities.User;

public interface IUserMetier {
	
	
	public void save(User user);
	
	public User findByUsername(String username);
	
	public User createUser(String username);
	
	public User createAdmin(String username);


}
