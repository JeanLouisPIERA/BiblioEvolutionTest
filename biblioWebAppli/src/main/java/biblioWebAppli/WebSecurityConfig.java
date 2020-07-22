package biblioWebAppli;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
    
	/**@Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;
    **/
    @Autowired
    private IUserMetier userMetier;

    /**
     * Création d'un mot de passe crypté.
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configuration des autorisations par les rôles.
     * Elle définit quels URLS sont sécurisés ou pas (comme / )
     */
    
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/resources/**", "/registration", "/login", "/login?logout", "/", "/css/**", "/webjars/**", "/bootstrap/**").permitAll()
                .antMatchers("/user/**").hasAnyAuthority(RoleEnum.ADMIN.toString(),RoleEnum.USER.toString())
                .antMatchers("/admin/**").hasAuthority(RoleEnum.ADMIN.toString())
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }
   
    /**
     * Méthode qui permet de vérifier les credentials 
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager customAuthenticationManager(String username, String password) throws Exception {
    	
    	authenticationManager().authenticate();
        return authenticationManager();
    }
    
    
    /**
     * Création du Manager d'Authentification 
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	auth
    	//userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    	.inMemoryAuthentication()
    	.withUser(userMetier.findByUsername(username, password)); 
    	
       
    }
    
    
}