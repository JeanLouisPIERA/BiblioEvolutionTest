/**
 * 
 */
package biblioWebServiceRest;

import java.util.Arrays;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author jeanl
 *
 */
@Configuration
@EnableSwagger2
@EnableWebSecurity
public class SpringFoxConfig extends WebSecurityConfigurerAdapter{                                    
	
	@Autowired
    private BasicAuthenticationPoint basicAuthenticationPoint;
	
	@Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)
        		.groupName("Biblio")
                .select()
                .apis(RequestHandlerSelectors.basePackage("biblioWebServiceRest.services"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo())
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(basicAuthScheme()));
               
    }
	
	 private ApiInfo getApiInfo() {

         Contact contact = new Contact("Jean-Louis PIERA", "https://github.com/JeanLouisPIERA/Biblio", "jeanlouispiera@yahoo.fr");

         return new ApiInfoBuilder()

                 .title("Biblio")

                 .description("Projet 7 Openclassrooms")

                 .version("1.0.0")

                 .license("Apache 2.0")

                 .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")

                 .contact(contact)

                 .build();

     }
    
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(Arrays.asList(basicAuthReference()))
                .forPaths(PathSelectors.any())
                .build();
    }

    private SecurityScheme basicAuthScheme() {
        return new BasicAuth("basicAuth");
    }

    private SecurityReference basicAuthReference() {
        return new SecurityReference("basicAuth", new AuthorizationScope[0]);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
          .withUser("user1").password(passwordEncoder().encode("user1Pass"))
          .authorities("ROLE_USER");
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {

            http
            	.authorizeRequests()
            	.antMatchers(
                        "/", "/csrf", 
                        "/v2/api-docs", 
                        "/swagger-resources/**",
                        "/swagger-ui.html#/**",
                        "/webjars/**"
                        ).permitAll()
            	.anyRequest().authenticated()
            	.and()
            	.httpBasic()
            	.authenticationEntryPoint(basicAuthenticationPoint);
            
            http
            	.addFilterAfter(new CustomFilter(), BasicAuthenticationFilter.class );        	
    }

}


