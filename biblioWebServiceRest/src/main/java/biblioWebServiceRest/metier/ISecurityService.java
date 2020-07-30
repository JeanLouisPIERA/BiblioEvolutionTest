/**
 * 
 */
package biblioWebServiceRest.metier;

import biblioWebServiceRest.entities.User;

public interface ISecurityService {
	
		String findLoggedInUsername();
		
		User findLoggedInUser();
		
		User autologin(String username, String password);
		

}
