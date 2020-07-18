/**
 * 
 */
package biblioWebServiceRest.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import biblioWebServiceRest.dao.IUserRepository;
import biblioWebServiceRest.entities.User;
import biblioWebServiceRest.exceptions.EntityNotFoundException;
import biblioWebServiceRest.metier.IUserMetier;

/**
 * @author jeanl
 *
 */
@RestController
public class UserRestService {
	@Autowired
	private IUserRepository userRepository;

	
	  /**
	   * Get all users list.
	   *
	   * @return the list
	   */
	  @GetMapping("/users")
	  public List<User> getAllUsers() {
	    return userRepository.findAll();
	  }
	  /**
	   * Gets users by id.
	   *
	   * @param userId the user id
	   * @return the users by id
	   * @throws ResourceNotFoundException the resource not found exception
	   */
	  @GetMapping("/users/{id}")
	  public ResponseEntity<User> getUsersById(@PathVariable(value = "id") Long userId)
	      throws EntityNotFoundException {
	    User user =
	        userRepository
	            .findById(userId)
	            .orElseThrow(() -> new EntityNotFoundException("User not found on :: " + userId));
	    return ResponseEntity.ok().body(user);
	  }
	  /**
	   * Create user user.
	   *
	   * @param user the user
	   * @return the user
	   */
	  @PostMapping("/users")
	  public User createUser(@Valid @RequestBody User user) {
	    return userRepository.save(user);
	  }
	  /**
	   * Update user response entity.
	   *
	   * @param userId the user id
	   * @param userDetails the user details
	   * @return the response entity
	   * @throws ResourceNotFoundException the resource not found exception
	   */
	  @PutMapping("/users/{id}")
	  public ResponseEntity<User> updateUser(
	      @PathVariable(value = "id") Long userId, @Valid @RequestBody User userDetails)
	      throws EntityNotFoundException {
	    User user =
	        userRepository
	            .findById(userId)
	            .orElseThrow(() -> new EntityNotFoundException("User not found on :: " + userId));
	    user.setAdresseMail(userDetails.getAdresseMail());
	    user.setUsername(userDetails.getUsername());
	    user.setPassword(userDetails.getPassword());
	    final User updatedUser = userRepository.save(user);
	    return ResponseEntity.ok(updatedUser);
	  }
	  /**
	   * Delete user map.
	   *
	   * @param userId the user id
	   * @return the map
	   * @throws Exception the exception
	   */
	  @DeleteMapping("/users/{id}")
	  public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) throws Exception {
	    User user =
	        userRepository
	            .findById(userId)
	            .orElseThrow(() -> new EntityNotFoundException("User not found on :: " + userId));
	    userRepository.delete(user);
	    Map<String, Boolean> response = new HashMap<>();
	    response.put("deleted", Boolean.TRUE);
	    return response;
	  }
	}
	

