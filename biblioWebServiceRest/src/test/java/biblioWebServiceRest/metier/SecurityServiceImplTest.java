package biblioWebServiceRest.metier;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collection;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;

import biblioWebServiceRest.dao.IUserRepository;
import biblioWebServiceRest.entities.Role;
import biblioWebServiceRest.entities.RoleEnum;
import biblioWebServiceRest.entities.User;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SecurityServiceImplTest {
	
	@Mock
	private AuthenticationManager authenticationManager;
	@Mock
	private UserDetailsService userDetailsService;
	@Mock
	private IUserRepository userRepository;
	@Mock
	private SecurityContextHolder securityContextHolder;
	
	
	@InjectMocks
	private SecurityServiceImpl securityService;
	
	@Before 
	public void setup() {
		
		MockitoAnnotations.initMocks(this);
		
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
	}

	
	@Test
	public void testFindLoggedInUsername_whenSuccess() {
		String username = "username";
		String password = "password";
		String adresseMail = "adresseMail";
		Role role1 = new Role();
		role1.setName(RoleEnum.USER);
		
		User user1 = new User(username, password, adresseMail, role1);
		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(role1.getName().toString()));
		
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(
				user1.getUsername(), 
				user1.getPassword(), 
				grantedAuthorities);
		
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
	
		Mockito.when((SecurityContextHolder.getContext().getAuthentication().getDetails())).thenReturn(userDetails);
		
		String loggedInUsername = securityService.findLoggedInUsername();
		
		Assert.assertTrue(loggedInUsername.equals(username));
		
	}
	
	@Test
	public void testFindLoggedInUsername_withoutSuccess() {
		        
        Object userDetails = new Object();
        
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
	
		Mockito.when((SecurityContextHolder.getContext().getAuthentication().getDetails())).thenReturn(userDetails);
		
		String loggedInUsername = securityService.findLoggedInUsername();
	
		Assert.assertTrue(loggedInUsername==null);
		
	}
	
	@Test
	public void testFindLoggedInUser_whenSuccess() {
		
		String username = "username";
		String password = "password";
		String adresseMail = "adresseMail";
		Role role1 = new Role();
		role1.setName(RoleEnum.USER);
		
		User user1 = new User(username, password, adresseMail, role1);
		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(role1.getName().toString()));
		
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(
				user1.getUsername(), 
				user1.getPassword(), 
				grantedAuthorities);
		
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		
		Mockito.when((SecurityContextHolder.getContext().getAuthentication().getDetails())).thenReturn(userDetails);
		
		Mockito.when(userRepository.findByUsername(((UserDetails)userDetails).getUsername())).thenReturn(Optional.of(user1));
		
		User loggedInUser = securityService.findLoggedInUser();
		
		Assert.assertTrue(loggedInUser.equals(user1));
		
	}
	
	@Test
	public void testFindLoggedInUser_withoutSuccess() {
		
		Object userDetails = new Object();
		
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		
		Mockito.when((SecurityContextHolder.getContext().getAuthentication().getDetails())).thenReturn(userDetails);
		
		User loggedInUser = securityService.findLoggedInUser();
		
		Assert.assertTrue(loggedInUser==null);
		
	}
	
	
	@Test
	public void testAutologin_withAuthentication() {
		String username = "username";
		String password = "password";
		String adresseMail = "adresseMail";
		Role role1 = new Role();
		role1.setName(RoleEnum.USER);
		
		User user1 = new User(username, password, adresseMail, role1);
		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(role1.getName().toString()));
		
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(
				user1.getUsername(), 
				user1.getPassword(), 
				grantedAuthorities);
		
		Mockito.when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
		
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken 
	    = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
		
		Mockito.when(authenticationManager.authenticate(usernamePasswordAuthenticationToken )).thenReturn(usernamePasswordAuthenticationToken);
		
		usernamePasswordAuthenticationToken.isAuthenticated();
		
		Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user1));
		
		User user = securityService.autologin(username, password);
		
		verify(userRepository, times(1)).findByUsername(username);
		Assert.assertTrue(user.equals(user1));
		
	}
	
	@Test
	public void testAutologin_withoutAuthentication() {
		
		String username = "username";
		String password = "password";
		String adresseMail = "adresseMail";
		Role role1 = new Role();
		role1.setName(RoleEnum.USER);
		
		User user1 = new User(username, password, adresseMail, role1);
		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(role1.getName().toString()));
		
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(
				user1.getUsername(), 
				user1.getPassword(), 
				grantedAuthorities);
		
		Mockito.when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
		
		
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken 
	    = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
		
		Authentication authenticated = Mockito.mock(Authentication.class);
		authenticated.setAuthenticated(false);
		
		Mockito.when(authenticationManager.authenticate(usernamePasswordAuthenticationToken )).thenReturn(authenticated);
		
		User user = securityService.autologin(username, password);
		
		Assert.assertTrue(user==null);
	}
	
}
