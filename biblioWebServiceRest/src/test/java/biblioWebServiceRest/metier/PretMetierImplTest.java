package biblioWebServiceRest.metier;

import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.context.WebApplicationContext;

import biblioWebServiceRest.BiblioWebServiceRestApplication;
import biblioWebServiceRest.configurations.ApplicationPropertiesConfiguration;
import biblioWebServiceRest.dao.ILivreRepository;
import biblioWebServiceRest.dao.IPretRepository;
import biblioWebServiceRest.dao.IRoleRepository;
import biblioWebServiceRest.dao.IUserRepository;
import biblioWebServiceRest.dto.LivreDTO;
import biblioWebServiceRest.dto.PretDTO;
import biblioWebServiceRest.entities.Categorie;
import biblioWebServiceRest.entities.Livre;
import biblioWebServiceRest.entities.Pret;
import biblioWebServiceRest.entities.PretStatut;
import biblioWebServiceRest.entities.User;
import biblioWebServiceRest.exceptions.BookNotAvailableException;
import biblioWebServiceRest.exceptions.EntityNotFoundException;
import biblioWebServiceRest.mapper.LivreMapper;
import biblioWebServiceRest.mapper.PretMapper;

@SpringBootTest (classes = BiblioWebServiceRestApplication.class)
@TestPropertySource(locations="classpath:application.properties")
public class PretMetierImplTest extends BiblioWebServiceRestMetierTests{

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@TestConfiguration
    static class PretMetierImplTestContextConfiguration {
 
        @Bean
        public IPretMetier pretMetier() {
            return new PretMetierImpl();
        }
    }

    @Autowired
    private IPretMetier pretMetier;
    
    @MockBean
	private IPretRepository pretRepository;
	@MockBean
	private ILivreRepository livreRepository;
	@MockBean
	private IUserRepository userRepository;
	@MockBean
	private IRoleRepository roleRepository;
	@Autowired
	ApplicationPropertiesConfiguration appProperties;
	@Autowired
	private PretMapper pretMapper;
	@Autowired
	private LivreMapper livreMapper; 
	@MockBean
	private ILivreMetier livreMetier;
    
    @Before
    public void setup() {
    	/*
    	 * Jeu de données pour la création d'un prêt
    	 */
    	Categorie categorie1 = new Categorie((long) 1,"Categorie1");
	    
		Livre livre1 = new Livre((long) 1,"titre1", "auteur1", 1,1,categorie1);
		Livre livre2 = new Livre((long) 2,"titre2", "auteur2", 1, 0, categorie1);
		
		User user1 = new User((long)1, "user1");
    	
		LivreDTO livre1DTO = new LivreDTO("titre1", "auteur1", 1, (long) 1 );
		PretDTO pret1Livre1DTO = new PretDTO((long)1, livre1.getNumLivre());
	    
	    Mockito.when(livreRepository.findById(livre1.getNumLivre())).thenReturn(Optional.of(livre1));
	    Mockito.when(livreRepository.findById(livre2.getNumLivre())).thenReturn(Optional.of(livre2));
	    Mockito.when(userRepository.findById(pret1Livre1DTO.getIdUser())).thenReturn(Optional.of(user1));
    	

    }
    
    @Test
    public void testCreatePret_whenSansException() {
    	Categorie categorie1 = new Categorie((long) 1,"Categorie1");
    	Livre livre1 = new Livre((long) 1,"titre1", "auteur1", 1,1,categorie1);
		User user1 = new User((long)1, "user1");
    	Pret pretTest1 = new Pret(LocalDate.now().minusDays(10), LocalDate.now().plusDays(18), PretStatut.ENCOURS, user1, livre1);
    	PretDTO pretDTOTest1 = new PretDTO((long)1, livre1.getNumLivre());
    	Mockito.when(pretRepository.save(any(Pret.class))).thenReturn(pretTest1);
    	
    	Assertions.assertDoesNotThrow(() -> {
    		Pret pretCreated = pretMetier.createPret(pretDTOTest1);
    			
	            }, "Le test unitaire sur la méthode createPret() ne s'est pas correctement déroulé");		
    }
    
    @Test
	public void testCreatePret_whenExceptionNbExemplairesDispo0() {
	   
    	Categorie categorie1 = new Categorie((long) 1,"Categorie1");
    	Livre livre2 = new Livre((long) 2,"titre2", "auteur2", 1,0,categorie1);
		User user1 = new User((long)2, "user1");
    	Pret pretTest2 = new Pret(LocalDate.now().minusDays(10), LocalDate.now().plusDays(18), PretStatut.ENCOURS, user1, livre2 );
    	PretDTO pretDTOTest2 = new PretDTO((long)2, livre2.getNumLivre());
		
		 Assertions.assertThrows(BookNotAvailableException.class, () -> {
			 Pret pretCreated = pretMetier.createPret(pretDTOTest2);
         });
	}
	
	@Test
	public void testCreatePret_whenExceptionLivreAlreadyExists() {
		Categorie categorie1 = new Categorie((long) 1,"Categorie1");
    	Livre livre1 = new Livre((long) 1,"titre1", "auteur1", 1,1,categorie1);
		User user1 = new User((long)1, "user1");
    	Pret pretTest1 = new Pret(LocalDate.now().minusDays(10), LocalDate.now().plusDays(18), PretStatut.ENCOURS, user1, livre1 );
    	PretDTO pretDTOTest1 = new PretDTO((long)3, livre1.getNumLivre());
		
		
		 Assertions.assertThrows(EntityNotFoundException.class, () -> {
			 Pret pretCreated = pretMetier.createPret(pretDTOTest1);
         });
	}
	
}
