package biblioWebServiceRest.metier;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import biblioWebServiceRest.dao.IUserRepository;
import biblioWebServiceRest.entities.Role;
import biblioWebServiceRest.entities.RoleEnum;
import biblioWebServiceRest.entities.User;


@SpringBootTest
@RunWith(SpringRunner.class)
public class UserDetailsServiceImplTest {

	 @Mock
	 private IUserRepository userRepository;
	
	@InjectMocks
	private UserDetailsServiceImpl userDetailsService;
	
	@Before 
	public void setup() {
		
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testLoadUserByUsername_whenUsernameNotFoundException() {
		
		String username = "username";
		
		Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.ofNullable(null));
		
		
		try {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(org.springframework.security.core.userdetails.UsernameNotFoundException.class)
			 .hasMessage("User not found");
		} 
	}
	
	
	@Test
	public void testLoadUserByUsername_withoutException() throws Exception {
		
		String username = "username";
		String password = "password";
		String adresseMail = "adresseMail";
		Role role1 = new Role();
		role1.setName(RoleEnum.USER);
		
		User user1 = new User(username, password, adresseMail, role1);
		
		Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user1));

		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		Assert.assertTrue(userDetails.getPassword().contentEquals("password"));
		Assert.assertTrue(userDetails.getUsername().contentEquals("username"));
	}
}
