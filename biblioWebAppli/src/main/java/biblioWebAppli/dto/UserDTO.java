/**
 * Classe de sérialization des utilisateurs pour le Mapping DTO 
 */
package biblioWebAppli.dto;



/**
 * @author jeanl
 *
 */
public class UserDTO {
	
	private String username;
	private String adresseMail;
	private String password; 
	
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the adresseMail
	 */
	public String getAdresseMail() {
		return adresseMail;
	}
	/**
	 * @param adresseMail the adresseMail to set
	 */
	public void setAdresseMail(String adresseMail) {
		this.adresseMail = adresseMail;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
	

	
	
	
	
	

}
