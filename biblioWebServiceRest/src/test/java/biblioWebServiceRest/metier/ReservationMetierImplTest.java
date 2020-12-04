package biblioWebServiceRest.metier;

import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import biblioWebServiceRest.dao.IReservationRepository;
import biblioWebServiceRest.dao.IRoleRepository;
import biblioWebServiceRest.dao.IUserRepository;
import biblioWebServiceRest.dto.LivreDTO;
import biblioWebServiceRest.dto.PretDTO;
import biblioWebServiceRest.dto.ReservationDTO;
import biblioWebServiceRest.entities.Categorie;
import biblioWebServiceRest.entities.Livre;
import biblioWebServiceRest.entities.Pret;
import biblioWebServiceRest.entities.PretStatut;
import biblioWebServiceRest.entities.Reservation;
import biblioWebServiceRest.entities.ReservationStatut;
import biblioWebServiceRest.entities.User;
import biblioWebServiceRest.exceptions.BookAvailableException;
import biblioWebServiceRest.exceptions.BookNotAvailableException;
import biblioWebServiceRest.exceptions.EntityAlreadyExistsException;
import biblioWebServiceRest.exceptions.EntityNotFoundException;
import biblioWebServiceRest.exceptions.RentAlreadyExistsException;
import biblioWebServiceRest.mapper.LivreMapper;
import biblioWebServiceRest.mapper.PretMapper;
import biblioWebServiceRest.mapper.ReservationMapper;

@SpringBootTest //(classes = BiblioWebServiceRestApplication.class)
//@TestPropertySource(locations="classpath:application.properties")
public class ReservationMetierImplTest extends BiblioWebServiceRestMetierTests{

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@TestConfiguration
    static class ReservationMetierImplTestContextConfiguration {
 
        @Bean
        public IReservationMetier reservationMetier() {
            return new ReservationMetierImpl();
        }
    }

    @Autowired
    private IReservationMetier reservationMetier;
    
    @MockBean
	private IReservationRepository reservationRepository;
	@MockBean
	private IPretRepository pretRepository;
	@MockBean
	private ILivreRepository livreRepository;
	@MockBean
	private IUserRepository userRepository;
	@MockBean
	private IRoleRepository roleRepository;
	@MockBean
	IPretMetier pretMetier;
	@MockBean
	ILivreMetier livreMetier;
	
    
    @Before
    public void setup() {
    	/*
    	 * Jeu de données de test pour tous les use cases de la création d'une réservation
    	 */
    	Categorie categorie1 = new Categorie((long) 1,"Categorie1");
    	
    	User user1 = new User((long)1, "user1");
    	User user2 = new User((long)2, "user2");
    	User user3 = new User((long)3, "user3");
	
		Livre livre1 = new Livre((long) 1,"titre1", "auteur1", 1,0,categorie1);
		Livre livre2 = new Livre((long) 2,"titre2", "auteur2", 0,0,categorie1);
		Livre livre3 = new Livre((long) 3,"titre3", "auteur3", 1, 1, categorie1);
		Livre livre4 = new Livre((long) 4,"titre4", "auteur4", 1, 0, categorie1);
		Livre livre5 = new Livre((long) 5,"titre5", "auteur5", 1, 0, categorie1);
		Livre livre6 = new Livre((long) 6,"titre6", "auteur6", 1, 0, categorie1);
		
		ReservationDTO reservationDTO1 = new ReservationDTO((long)1, (long)1);
		ReservationDTO reservationDTO2 = new ReservationDTO((long)1, (long)2);
		ReservationDTO reservationDTO3 = new ReservationDTO((long)1, (long)3);
		ReservationDTO reservationDTO4 = new ReservationDTO((long)1, (long)4);
		ReservationDTO reservationDTO5 = new ReservationDTO((long)1, (long)5);
		ReservationDTO reservationDTO6 = new ReservationDTO((long)1, (long)6);
		
		Reservation reservation1 = new Reservation((long)1, LocalDate.now(), null, null, null, ReservationStatut.ENREGISTREE, user1, livre1);
		List<Reservation> reservationList = new ArrayList<Reservation>();
		reservationList.add(reservation1);
		Reservation reservation2 = new Reservation((long)2, LocalDate.now(), null, null, null, ReservationStatut.ENREGISTREE, user1, livre4);
		
		Reservation reservation3 = new Reservation((long)3, LocalDate.now(), null, null, null, ReservationStatut.ENREGISTREE, user2, livre6);
		Reservation reservation4 = new Reservation((long)4, LocalDate.now(), null, null, null, ReservationStatut.ENREGISTREE, user3, livre6);
		List<Reservation> reservationList1 = new ArrayList<Reservation>();
		reservationList1.add(reservation3);
		reservationList1.add(reservation4);
		
		PretDTO pret1Livre1DTO = new PretDTO((long)1, (long) 5);
		List<Pret> pretListUser1Livre5 = new ArrayList<Pret>();
		Pret pret1Livre5 = new Pret(LocalDate.now().minusDays(10), LocalDate.now().plusDays(18), PretStatut.ENCOURS, user1, livre5 );
		pretListUser1Livre5.add(pret1Livre5);
		
	    Mockito.when(userRepository.findById(reservationDTO1.getIdUser())).thenReturn(Optional.of(user1));
	    Mockito.when(livreRepository.findById(reservationDTO1.getNumLivre())).thenReturn(Optional.of(livre1));
	    Mockito.when(livreRepository.findById(reservationDTO2.getNumLivre())).thenReturn(Optional.of(livre2));
	    Mockito.when(livreRepository.findById(reservationDTO3.getNumLivre())).thenReturn(Optional.of(livre3));
	    Mockito.when(livreRepository.findById(reservationDTO4.getNumLivre())).thenReturn(Optional.of(livre4));
	    Mockito.when(livreRepository.findById(reservationDTO5.getNumLivre())).thenReturn(Optional.of(livre5));
	    Mockito.when(livreRepository.findById(reservationDTO6.getNumLivre())).thenReturn(Optional.of(livre6));
	    Mockito.when(reservationRepository.findByUserAndLivreAndStatutReservationOrStatutReservation(user1, livre4, ReservationStatut.ENREGISTREE, ReservationStatut.NOTIFIEE)).thenReturn(Optional.of(reservation2));
	    Mockito.when(pretRepository.findAllByLivreAndUserAndNotPretStatut(livre5, user1, PretStatut.CLOTURE)).thenReturn(Optional.of(pretListUser1Livre5));		
	    Mockito.when(reservationRepository.findAllByLivreGroupByUserAndReservationStatutOrReservationStatut(livre6, ReservationStatut.ENREGISTREE, ReservationStatut.NOTIFIEE)).thenReturn(Optional.of(reservationList1));
	    Mockito.when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation1);		
	    
    }
    
    
    
    @Test
    public void TestCreateReservation_whenSansException() {
    	
    	ReservationDTO reservationDTO1 = new ReservationDTO((long)1, (long)1);
  	
		Assertions.assertDoesNotThrow(() -> {
    		Reservation reservationCreated = reservationMetier.createReservation(reservationDTO1);
    			
	            }, "Le test unitaire sur la méthode createReservation() ne s'est pas correctement déroulé");	
    	
    }
    
    @Test
    public void TestCreateReservation_whenExceptionUserInconnu() {
    	
		ReservationDTO reservationDTO1 = new ReservationDTO((long)0, (long)1);
		
		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			Reservation reservationCreated = reservationMetier.createReservation(reservationDTO1);
        });
    }
	
	@Test
	public void TestCreateReservation_whenExceptionLivreInconnu() {
		
		ReservationDTO reservationDTO1 = new ReservationDTO((long)1, (long)0);
		
		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			Reservation reservationCreated = reservationMetier.createReservation(reservationDTO1);
        });
		
	}
	
	@Test
	public void TestCreateReservation_whenExceptionNbExemplaires0() {
		
		ReservationDTO reservationDTO1 = new ReservationDTO((long)1, (long)2);
		
		Assertions.assertThrows(BookNotAvailableException.class, () -> {
			Reservation reservationCreated = reservationMetier.createReservation(reservationDTO1);
        });
		
	}
	
	@Test
	public void TestCreateReservation_whenExceptionNbExemplairesDisponibles() {
		
		ReservationDTO reservationDTO1 = new ReservationDTO((long)1, (long)3);
		
		Assertions.assertThrows(BookAvailableException.class, () -> {
			Reservation reservationCreated = reservationMetier.createReservation(reservationDTO1);
        });
		
	}
	
	@Test
	public void TestCreateReservation_whenExceptionReservationCeLivreDejaEnCours() {
		
		ReservationDTO reservationDTO1 = new ReservationDTO((long)1, (long)4);
		
		Assertions.assertThrows(EntityAlreadyExistsException.class, () -> {
			Reservation reservationCreated = reservationMetier.createReservation(reservationDTO1);
        });
		
	}
	
	@Test
	public void TestCreateReservation_whenExceptionPretCeLivreDejaEnCours() {
		
		ReservationDTO reservationDTO1 = new ReservationDTO((long)1, (long)5);
		
		Assertions.assertThrows(RentAlreadyExistsException.class, () -> {
			Reservation reservationCreated = reservationMetier.createReservation(reservationDTO1);
        });
		
	}
	
	@Test
	public void TestCreateReservation_whenExceptionFileAttenteMaximum() {
		
		ReservationDTO reservationDTO1 = new ReservationDTO((long)1, (long)6);
		
		Assertions.assertThrows(BookNotAvailableException.class, () -> {
			Reservation reservationCreated = reservationMetier.createReservation(reservationDTO1);
        });
		
	}
  	
    }
	

