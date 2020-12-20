/**
 * 
 */
package biblioWebServiceRest.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import biblioWebServiceRest.dao.IUserRepository;
import biblioWebServiceRest.dto.UserDTO;
import biblioWebServiceRest.entities.User;
import biblioWebServiceRest.exceptions.EntityNotFoundException;
import biblioWebServiceRest.metier.ISecurityService;
import biblioWebServiceRest.metier.IUserMetier;

/**
 * @author jeanl
 *
 */
@RestController
@RequestMapping("/biblio")
public class UserRestService {
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private IUserMetier userMetier;
	@Autowired
	ISecurityService securityService;

	
	  /**
	   * Permet d'obtenir la liste de tous les utilisateurs
	   *
	   * @return the list
	   */
	  @GetMapping("/users")
	  public ResponseEntity<List<User>> getAllUsers() {
	    return new ResponseEntity<List<User>>(userRepository.findAll(), HttpStatus.OK);
	  }
	  
	  /**
	   * Creation d'un utilisateur
	   *
	   * @param user the user
	   * @return the user
	   */
	  @PostMapping("/users")
	  public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO) {
	    return new ResponseEntity<User>(userMetier.registrateUser(userDTO), HttpStatus.OK);
	  }
	  
	  /**
	   * Permet d'authentifier un utilisateur à partir de son nom et de son mot de passe
	   * @param userDTO
	   * @return
	   * @throws EntityNotFoundException
	   */
	  @PostMapping(value = "/users/login")
		public ResponseEntity<User> Authentication(@RequestBody UserDTO userDTO) throws EntityNotFoundException {
			
		    User userAuthenticated = userMetier.findByUsernameAndPassword(userDTO.getUsername(), userDTO.getPassword());
		    return new ResponseEntity<User>(userAuthenticated, HttpStatus.OK);
		  
		}
	 
	}
	

