package biblioWebServiceRest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import biblioWebServiceRest.entities.RoleEnum;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import biblioWebServiceRest.CustomFilter;


/**
 * Cette classe met en place la sécurité. Elle assure que seuls les utilisateurs authentifiés peuvent accéder.
 * @EnableWebSecurity Cette annotation permet d'utiliser Spring Security et fournit l'intégration Spring MVC.
 * @author jeanl
 *
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer{
    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private MyBasicAuthenticationEntryPoint authenticationEntryPoint;
    
    @Value("${prop.swagger.enabled}")
    private boolean enableSwagger;
    
    /**
     * Création du configurateur Swagger
     * @return
     */
    @Bean
    public Docket SwaggerConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enableSwagger)
                .select()
                .apis(RequestHandlerSelectors.basePackage("${prop.swagger.basepackage}"))
                .paths(PathSelectors.any())
                .build();
    }
    /**
     * Methode de WebMvcConfigurer qui permet d'accéder à SWAGGER avec SprinSecurity
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        if (enableSwagger)  
            web.ignoring().antMatchers("/v2/api-docs",
                                   "/configuration/ui",
                                   "/swagger-resources/**",
                                   "/configuration/security",
                                   "/swagger-ui.html",
                                   "/webjars/**");
    }
    /**
     * Methode de WebMvcConfigurer qui permet d'accéder à SWAGGER
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (enableSwagger) {
            registry.addResourceHandler("swagger-ui.html").addResourceLocations("${prop.swagger.resource.location}");
            registry.addResourceHandler("/webjars/**").addResourceLocations("${prop.webjars.resource.location}");
        }
      }
    
    

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
        	.authorizeRequests()
        	.antMatchers(HttpMethod.POST, "biblio/users/login" ).permitAll()
        	.antMatchers(HttpMethod.GET, "/biblio/**").hasAnyAuthority(RoleEnum.ADMIN.toString(),RoleEnum.USER.toString())
        	.antMatchers(HttpMethod.PUT, "/biblio/**").hasAnyAuthority(RoleEnum.ADMIN.toString(),RoleEnum.USER.toString())
            .antMatchers(HttpMethod.POST, "/biblio/**").hasAnyAuthority(RoleEnum.ADMIN.toString(),RoleEnum.USER.toString())
            .antMatchers(HttpMethod.DELETE, "/biblio/**").hasAnyAuthority(RoleEnum.ADMIN.toString(), RoleEnum.USER.toString())
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