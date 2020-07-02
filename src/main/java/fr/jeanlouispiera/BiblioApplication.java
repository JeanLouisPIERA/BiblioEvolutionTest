package fr.jeanlouispiera;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableConfigurationProperties()
//@EnableJpaRepositories(basePackages = {"fr.jeanlouispiera.biblioWebServiceRest.dao"})
@EntityScan(basePackages= {"fr.jeanlouispiera.biblioWebServiceRest.entities","fr.jeanlouispiera.biblioWebAppli.entities" })
@ComponentScan(basePackages= {"fr.jeanlouispiera.biblioWebServiceRest.dao"})

public class BiblioApplication {

	public static void main(String[] args) {
		SpringApplication.run(BiblioApplication.class, args);
	}

}
