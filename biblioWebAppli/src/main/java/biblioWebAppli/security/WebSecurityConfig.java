package biblioWebAppli.security;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import biblioWebAppli.metier.IUserMetier;
import biblioWebAppli.objets.RoleEnum;




/**
 * Cette classe met en place la sécurité. Elle assure que seuls les utilisateurs authentifiés peuvent accéder.
 * @EnableWebSecurity Cette annotation permet d'utiliser Spring Security et fournit l'intégration Spring MVC.
 * @author jeanl
 *
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired private RestAuthenticationEntryPoint authenticationEntryPoint;
	
	
	@Value("${application.username}")
	private String applicationUsername;
	@Value("${application.password}")
	private String applicationPassword;
	
	
	
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
          .inMemoryAuthentication()
          .withUser(applicationUsername)
          .password(passwordEncoder().encode(applicationPassword))
          .authorities("ROLE_USER");
    }
    
	

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
          .authorizeRequests()
          .antMatchers("/resources/**", "/registration", "/login","/","/login?logout", "/css/**", "/webjars/**", "/bootstrap/**").permitAll()
          .anyRequest()
          .authenticated()
          .and()
          .httpBasic()
          //.authenticationEntryPoint(authenticationEntryPoint)
          .and()
          .logout()
              .permitAll();
          
          
          
          
          
         
       http.addFilterAfter(new CustomFilter(), BasicAuthenticationFilter.class);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	
}
	

    
	
    
    
    
    
    
    
    
