package biblioWebServiceRest;

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

import biblioWebServiceRest.entities.RoleEnum;



@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

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
                .antMatchers("/resources/**", "/livres/**", "/pret/**","/registration", "/login", "/login?logout", "/", "/css/**", "/webjars/**", "/bootstrap/**").permitAll()
                .antMatchers("/user/**").hasAnyAuthority(RoleEnum.ADMIN.toString(),RoleEnum.USER.toString())
                .antMatchers("/admin/**").hasAuthority(RoleEnum.ADMIN.toString())
                .anyRequest().permitAll()
                .and()
            .formLogin()
                .loginPage("/")
                .permitAll()
                .and()
            .logout()
                .permitAll()
                .and()
            .csrf().disable();
        		
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
