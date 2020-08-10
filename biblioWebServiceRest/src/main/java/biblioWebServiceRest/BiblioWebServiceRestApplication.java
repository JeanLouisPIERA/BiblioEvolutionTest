package biblioWebServiceRest;






import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;







@SpringBootApplication
(exclude=DataSourceAutoConfiguration.class)
@EnableWebSecurity
public class BiblioWebServiceRestApplication  {
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(BiblioWebServiceRestApplication.class, args);
	}
	
	@Bean(name = "dataSource")
	public DriverManagerDataSource dataSource() {
		//initialisation du driver Postgresql
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName("org.postgresql.Driver");
	    dataSource.setUrl("jdbc:postgresql://localhost:5432/biblio");
	    dataSource.setUsername("postgres");
	    dataSource.setPassword("postgres");

	    // initialiation de la base
	    Resource initSchema = new ClassPathResource("schema.sql");
	    Resource initData = new ClassPathResource("data.sql");
	    DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema, initData);
	    DatabasePopulatorUtils.execute(databasePopulator, dataSource);

	    return dataSource;
	}
	
	
	
}
