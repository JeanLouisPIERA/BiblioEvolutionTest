package biblioWebAppli.security;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;





/**
 * Cette classe met en place la sécurité. Elle assure que seuls les utilisateurs authentifiés peuvent accéder.
 * @EnableWebSecurity Cette annotation permet d'utiliser Spring Security et fournit l'intégration Spring MVC.
 * @author jeanl
 *
 */

//@Configuration
@EnableWebSecurity
public class WebSecurityConfig { //extends WebSecurityConfigurerAdapter {

	@Value("${application.username1}")
	private String applicationUsername1;
	@Value("${application.username2}")
	private String applicationUsername2;
	@Value("${application.username3}")
	private String applicationUsername3;
	@Value("${application.username4}")
	private String applicationUsername4;
	@Value("${application.username5}")
	private String applicationUsername5;
	@Value("${application.password}")
	private String applicationPassword;
	
	
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        
    	auth.inMemoryAuthentication().withUser(applicationUsername1).password(passwordEncoder().encode(applicationPassword))
          .authorities("ROLE_USER");
    	
    	auth.inMemoryAuthentication().withUser(applicationUsername2).password(passwordEncoder().encode(applicationPassword))
        .authorities("ROLE_USER");
    	
    	auth.inMemoryAuthentication().withUser(applicationUsername3).password(passwordEncoder().encode(applicationPassword))
        .authorities("ROLE_USER");
    	
    	auth.inMemoryAuthentication().withUser(applicationUsername4).password(passwordEncoder().encode(applicationPassword))
        .authorities("ROLE_USER");
    	
    	auth.inMemoryAuthentication().withUser(applicationUsername5).password(passwordEncoder().encode(applicationPassword))
        .authorities("ROLE_USER");
    }
    
	

    //@Override
    //protected void configure(HttpSecurity http) throws Exception {
    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
    	protected void configure(HttpSecurity http) throws Exception {
    	http
    	   .antMatcher("/biblio/**")
           .authorizeRequests()
          .anyRequest()
          .authenticated()
          .and()
          .httpBasic();
          
        http
          .addFilterAfter(new CustomFilter(), BasicAuthenticationFilter.class);
    }
   }
    
    
    @Configuration
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
            .csrf()
            .disable()
            .authorizeRequests()
            .antMatchers("/resources/**", "/registration", "/login","/logout","/login?logout", "/css/**", "/webjars/**", "/bootstrap/**").permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .loginPage("/login");
        }


}
    
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	
}
	

    
	
    
    
    
    
    
    
    
