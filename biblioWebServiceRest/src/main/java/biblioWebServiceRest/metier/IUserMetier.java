package biblioWebServiceRest.metier;

import biblioWebServiceRest.entities.User;

public interface IUserMetier {
	
	public User findByUsername(String username);

}
