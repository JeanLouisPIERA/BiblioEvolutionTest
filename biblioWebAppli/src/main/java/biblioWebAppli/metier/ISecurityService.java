package biblioWebAppli.metier;

import biblioWebAppli.objets.User;

public interface ISecurityService {
	
	String findLoggedInUsername();
	
	User findLoggedInUser();
	
	void autologin(String username, String password);
	
	

}
