/**
 * 
 */
package biblioWebAppli.metier;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import biblioWebAppli.dto.CategorieDTO;
import biblioWebAppli.dto.UserDTO;
import biblioWebAppli.objets.Categorie;
import biblioWebAppli.objets.User;

/**
 * @author jeanl
 *
 */
@Service
public class UserMetierImpl implements IUserMetier{
	
	@Autowired
    private RestTemplate restTemplate;
    
    @Value("${application.uRLUser}")
	private String uRL;
	

	/**
	 * Permet de rechercher l'utilisateur par son nom 
	 * @param username
	 * @return
	 */
	@Override
	public User findUser(UserDTO userDTO) {
		HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_JSON);

    	HttpEntity<UserDTO> requestEntity = 
    	     new HttpEntity<>(userDTO, headers);
    	ResponseEntity<User> response = restTemplate.exchange(uRL, HttpMethod.POST, requestEntity, 
			              User.class);
		System.out.println(response.getStatusCodeValue());
		User userToValidate = response.getBody();
	
    	return userToValidate;
		
	}


	/**
	 * @param username
	 * @return
	 */
	@Override
	public User findByUsername(String username, String password) {
		HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	
    	String url = uRL+"/login";
    	
    	UserDTO userDTO = new UserDTO(); 
    	userDTO.setUsername(username);   	
    	userDTO.setPassword(password);
    	
    	HttpEntity<UserDTO> requestEntity = 
    	     new HttpEntity<>(userDTO, headers);
    	ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, 
			              User.class);
		System.out.println(response.getStatusCodeValue());
		User userToValidate = response.getBody();
	
    	return userToValidate;
	}
	
	

}
