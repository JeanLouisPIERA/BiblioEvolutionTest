/**
 * 
 */
package biblioWebAppli.metier;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import biblioWebAppli.objets.User;
import biblioWebAppli.objets.UserAuth;
import biblioWebAppli.security.RestTemplateFactory;

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
	 * @param username
	 * @return
	 * @throws IOException 
	 */
	@Override
	public User findByUsernameAndPassword(String username, String password) throws IOException {
		
		String url = uRL + "/login";
		
		HttpHeaders headers = httpHeadersFactory.createHeaders(username, password);
		
		System.out.println("headers"+headers.toString());
		
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	
    	UserAuth userAuth = new UserAuth(); 
    	userAuth.setUsername(username);   	
    	userAuth.setPassword(password);
    	
    	HttpEntity<UserAuth> requestEntity = 
    	     new HttpEntity<>(userAuth, headers);
    	
    	ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, User.class);
    	System.out.println(response.getStatusCodeValue());
    	
		User userAuthenticated = response.getBody();
		System.out.println(userAuthenticated.getUsername());
		System.out.println(password);
		System.out.println(userAuthenticated.getIdUser());
		
		//SecurityContextHolder.getContext().getAuthentication().getName();
    	System.out.println("securityNom"+SecurityContextHolder.getContext().getAuthentication().getName());
		
    	if(response.getStatusCodeValue()==200) {
		Properties properties = new Properties();
		//InputStream input = new  FileInputStream("src/main/resources/application.properties");
		properties.load(new  FileInputStream("src/main/resources/application.properties")); 
		properties.setProperty("usernameLoggedIn", username); 
		properties.setProperty("password", password);
		properties.setProperty("idUserLoggedIn", userAuthenticated.getIdUser().toString());
		properties.store(new FileOutputStream("src/main/resources/application.properties"), "");
		}
    	return userAuthenticated;
	}
	
}
