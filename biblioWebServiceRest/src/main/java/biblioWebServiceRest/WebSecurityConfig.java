package biblioWebServiceRest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import biblioWebServiceRest.entities.RoleEnum;
import biblioWebServiceRest.CustomFilter;


/**
 * Cette classe met en place la sécurité. Elle assure que seuls les utilisateurs authentifiés peuvent accéder.
 * @EnableWebSecurity Cette annotation permet d'utiliser Spring Security et fournit l'intégration Spring MVC.
 * @author jeanl
 *
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private MyBasicAuthenticationEntryPoint authenticationEntryPoint;
    

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
        	.csrf().disable()
        	//.antMatcher("/api/categories")
        	//.httpBasic()
            //.authenticationEntryPoint(authenticationEntryPoint)
            //.and()
        	.authorizeRequests()
        	.antMatchers(HttpMethod.POST, "biblio/users/login" ).permitAll()
        	.antMatchers(HttpMethod.GET, "/biblio/**").hasAnyAuthority(RoleEnum.ADMIN.toString(),RoleEnum.USER.toString())
        	.antMatchers(HttpMethod.PUT, "/biblio/**").hasAnyAuthority(RoleEnum.ADMIN.toString(),RoleEnum.USER.toString())
            .antMatchers(HttpMethod.POST, "/biblio/**").hasAnyAuthority(RoleEnum.ADMIN.toString(),RoleEnum.USER.toString())
            .antMatchers(HttpMethod.DELETE, "/biblio/**").hasAuthority(RoleEnum.ADMIN.toString())
            .antMatchers("/**").authenticated()
            .anyRequest().authenticated()
            .and()
            .httpBasic()
            .authenticationEntryPoint(authenticationEntryPoint)
            ;
        
       
    		
    	  	//http.addFilterAfter(new CustomFilter(),
            //BasicAuthenticationFilter.class);
            
    }
   
    /**
     * Méthode qui permet de vérifier les credentials 
     * @return
     * @throws Exception
    */ 
    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }
    
    
    /**
     * Création du Manager d'Authentification 
     * @param auth
     * @throws Exception
    */ 
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
    
    
   
    
    
    
}