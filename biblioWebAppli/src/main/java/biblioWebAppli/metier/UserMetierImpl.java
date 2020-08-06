/**
 * 
 */
package biblioWebAppli.metier;


import java.io.IOException;
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
	private HttpHeadersFactory httpHeadersFactory;
	
    
    @Value("${application.uRLUser}")
	private String uRL;
    
	/**
	 * @param username
	 * @return
	 * @throws IOException 
	 */
	@Override
	public User findByUsernameAndPassword(String username, String password) throws IOException {
		
		String url = uRL + "/login";
		
		HttpHeaders headers = httpHeadersFactory.createHeaders(username, password);
		
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	
    	UserAuth userAuth = new UserAuth(); 
    	userAuth.setUsername(username);   	
    	userAuth.setPassword(password);
    	
    	HttpEntity<UserAuth> requestEntity = 
    	     new HttpEntity<>(userAuth, headers);
    	
    	ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, User.class);
    
		User userAuthenticated = response.getBody();
		
    	return userAuthenticated;
	}
	
}
