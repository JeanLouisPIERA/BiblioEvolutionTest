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
import biblioWebAppli.httpheaders.RestTemplateFactory;
import biblioWebAppli.objets.Categorie;
import biblioWebAppli.objets.User;
import biblioWebAppli.objets.UserAuth;

/**
 * @author jeanl
 *
 */
@Service
public class UserMetierImpl implements IUserMetier{
	
	@Autowired
    private RestTemplate restTemplate;
	@Autowired
    private RestTemplateFactory restTemplateFactory;
	@Autowired
	private HttpHeadersFactory httpHeadersFactory;
	
    
    @Value("${application.uRLUser}")
	private String uRL;
	

	/**
	 * Permet une recherche multicritère de l'utilisateur
	 * @param username
	 * @return
	 */
    /**
	@Override
	public User findUser(UserDTO userDTO) {
		//HttpHeaders headers = new HttpHeaders();
		HttpHeaders headers = httpHeadersFactory.createHeaders(userDTO.getUsername(), userDTO.getPassword());
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
	**/

	/**
	 * @param username
	 * @return
	 */
	@Override
	public User findByUsernameAndPassword(String username, String password) {
		//HttpHeaders headers = new HttpHeaders();
		
		HttpHeaders headers = httpHeadersFactory.createHeaders(username, password);
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	
    	//String url = uRL+"/login";
    	
    	
    	
    	UserAuth userAuth = new UserAuth(); 
    	userAuth.setUsername(username);   	
    	userAuth.setPassword(password);
    	
    	
    	HttpEntity<UserAuth> requestEntity = 
    	     new HttpEntity<>(userAuth, headers);
    	/**
    	restTemplateFactory.afterPropertiesSet();
    	
    	ResponseEntity<User> response = restTemplateFactory.getObject().exchange(url, HttpMethod.POST, requestEntity, 
			              User.class);
		System.out.println(response.getStatusCodeValue());
		**/
    	ResponseEntity<User> response = restTemplate.exchange(uRL, HttpMethod.POST, requestEntity, 
	              User.class);
    	System.out.println(response.getStatusCodeValue());
    	
		User userToValidate = response.getBody();
		System.out.println(userToValidate.getUsername());
	
    	return userToValidate;
	}
	
	/**
	 * @param username
	 * @return
	 */
	/**
	@Override
	public User findByUsername(String username) {
		HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	
    	String url = uRL+"/login";
    	
    	UserDTO userDTO = new UserDTO(); 
    	userDTO.setUsername(username);   	
    	
    	
    	HttpEntity<UserDTO> requestEntity = 
    	     new HttpEntity<>(userDTO, headers);
    	ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, 
			              User.class);
		System.out.println(response.getStatusCodeValue());
		User userToValidate = response.getBody();
	
    	return userToValidate;
	}
**/
}
