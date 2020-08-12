/**
 * 
 */
package biblioBatch.config;



import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;


/**
 * @author jeanl
 *
 */
@Configuration
public class ThymeleafTemplateConfig implements ApplicationContextAware, EnvironmentAware {
	
	public static final String EMAIL_TEMPLATE_ENCODING = "UTF-8";
	
	@Value("${application.prefix}")
	private String prefix;
	
	private ApplicationContext applicationContext;
    private Environment environment;
	
	/**
	 * @param environment
	 */
	@Override
	public void setEnvironment(final Environment environment) {
		this.environment = environment;
		
	}

	/**
	 * @param applicationContext
	 * @throws BeansException
	 */
	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		
	}
	
	/**
	 * Ce bean permet de générer le template spécialement configuré pour envoyer des mails
	 * @return
	 */
	
	@Bean
	public SpringTemplateEngine thymeleafTemplateEngine() {
	    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
	    templateEngine.addDialect(new Java8TimeDialect());
	    templateEngine.setTemplateResolver(thymeleafTemplateResolver());
	    return templateEngine;
	}
    
    /**
     * Ce bean permet d'indiquer à la dépendance Thymeleaf où se trouve le template HTML 
     * @return
     */
    @Bean
    public SpringResourceTemplateResolver thymeleafTemplateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix(prefix);
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }
}