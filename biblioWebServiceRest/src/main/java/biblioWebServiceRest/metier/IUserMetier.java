/**
 * Interface de définition des méthodes Métier pour l'entité User
 */
package biblioWebServiceRest.metier;

import biblioWebServiceRest.entities.User;

public interface IUserMetier {
	
	public User findByUsername(String username);

}
