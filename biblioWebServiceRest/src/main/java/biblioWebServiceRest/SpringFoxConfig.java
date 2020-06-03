/**
 * 
 */
package biblioWebServiceRest;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;

import springfox.documentation.service.Contact;

import springfox.documentation.spi.DocumentationType;

import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author jeanl
 *
 */
@Configuration
@EnableSwagger2
public class SpringFoxConfig {                                    
	
	
	
	@Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)
        		.groupName("Biblio")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
                
               
    }
	
	 private ApiInfo getApiInfo() {

         Contact contact = new Contact("Jean-Louis PIERA", "https://github.com/JeanLouisPIERA/Biblio", "jeanlouispiera@yahoo.fr");

         return new ApiInfoBuilder()

                 .title("Biblio")

                 .description("Projet 7 Openclassrooms Springboot REST API pour la gestion des prets d'une bibliothèque municipale")

                 .version("1.0.0")

                 .license("Apache 2.0")

                 .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")

                 .contact(contact)

                 .build();

     }
   

}


