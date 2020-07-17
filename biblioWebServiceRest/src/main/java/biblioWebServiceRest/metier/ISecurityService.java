/**
 * 
 */
package biblioWebServiceRest.metier;

import biblioWebServiceRest.entities.User;

public interface ISecurityService {
	
		String findLoggedInUsername();
		
		User findLoggedInUser();
		
		void autologin(String username, String password);
		

}
