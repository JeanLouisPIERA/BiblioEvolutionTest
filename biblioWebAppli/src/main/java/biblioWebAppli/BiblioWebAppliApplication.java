package biblioWebAppli;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;







@SpringBootApplication
public class BiblioWebAppliApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(BiblioWebAppliApplication.class, args);
	}
	
	/**
	@Bean

    public RestTemplate restTemplate() {

        return new RestTemplate();

    }
	**/
	
	
	@Bean 
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder.basicAuthentication("username", "password").build();
	}
	
    
    
}
