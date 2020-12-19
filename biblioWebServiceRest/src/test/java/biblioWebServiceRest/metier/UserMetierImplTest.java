package biblioWebServiceRest.metier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import biblioWebServiceRest.dao.IRoleRepository;
import biblioWebServiceRest.dao.IUserRepository;
import biblioWebServiceRest.dto.UserDTO;
import biblioWebServiceRest.entities.Reservation;
import biblioWebServiceRest.entities.Role;
import biblioWebServiceRest.entities.RoleEnum;
import biblioWebServiceRest.entities.User;
import biblioWebServiceRest.exceptions.EntityNotFoundException;
import biblioWebServiceRest.mapper.UserMapper;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMetierImplTest {
	
	@Mock
	private IUserRepository userRepository;
	@Mock
	private IRoleRepository roleRepository;
	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Mock
	private SecurityServiceImpl securityService;
	@Mock
	private UserMapper userMapper;
	
	@InjectMocks
	private UserMetierImpl userMetier;
	
	@Before 
	public void setup() {
		
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testRegistrateUser( ) {
		
		UserDTO userDTO = new UserDTO();
		userDTO.setPassword("password");
		
		User user1 = new User();
		Role role1 = new Role();
		
		Mockito.when(userMapper.userDTOToUser(userDTO)).thenReturn(user1);
		Mockito.when(bCryptPasswordEncoder.encode("password")).thenReturn("password") ;
		Mockito.when(roleRepository.findByName(RoleEnum.USER)).thenReturn(role1);
		Mockito.when(userRepository.save(any(User.class))).thenReturn(user1);
		
		User registratedUser = userMetier.registrateUser(userDTO);
		Assert.assertTrue(registratedUser.getPassword().contentEquals("password"));
		Assert.assertTrue(registratedUser.getRole().equals(role1));
		verify(userRepository, times(1)).save(any(User.class));
	}
	
	@Test
	public void testFindbyUsernameAndPassWord() throws Exception {
		
		String username = "username";
		String password = "password";
		
		User user1 = new User();
		user1.setPassword(password);
		user1.setUsername(username);
		
		Mockito.when(securityService.autologin(username, password)).thenReturn(user1);
		
		User userFound = userMetier.findByUsernameAndPassword(username, password);
		Assert.assertTrue(userFound.getPassword().contentEquals("password"));
		Assert.assertTrue(userFound.getUsername().equals("username"));
	}
}
