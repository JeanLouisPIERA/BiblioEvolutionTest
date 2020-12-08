package biblioWebServiceRest.metier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import biblioWebServiceRest.configurations.ApplicationPropertiesConfiguration;
import biblioWebServiceRest.criteria.ReservationCriteria;
import biblioWebServiceRest.dao.ILivreRepository;
import biblioWebServiceRest.dao.IPretRepository;
import biblioWebServiceRest.dao.IReservationRepository;
import biblioWebServiceRest.dao.IRoleRepository;
import biblioWebServiceRest.dao.IUserRepository;
import biblioWebServiceRest.dao.specs.ReservationSpecification;
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
import biblioWebServiceRest.exceptions.WrongNumberException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ReservationMetierImplTest extends BiblioWebServiceRestMetierTests{
 
    @Mock
	private IReservationRepository reservationRepository;
	@Mock
	private IPretRepository pretRepository;
	@Mock
	private ILivreRepository livreRepository;
	@Mock
	private IUserRepository userRepository;
	@Mock
	private IRoleRepository roleRepository;
	@Mock
	IPretMetier pretMetier;
	@Mock
	ILivreMetier livreMetier;
	@Mock
	ApplicationPropertiesConfiguration appProperties;
	
	@InjectMocks
    private ReservationMetierImpl reservationMetier;
	
    @Before
    public void setup() {
    	/*
    	 * Jeu de données pour les test CREATE
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
		Livre livre8 = new Livre((long) 8,"titre8", "auteur8", 1, 0, categorie1);
		
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
		
		List<Reservation> reservationList1 = Arrays.asList(reservation3, reservation4);
		
		PretDTO pret1Livre1DTO = new PretDTO((long)1, (long) 5);
		List<Pret> pretListUser1Livre5 = new ArrayList<Pret>();
		Pret pret1Livre5 = new Pret(LocalDate.now().minusDays(10), LocalDate.now().plusDays(18), PretStatut.ENCOURS, user1, livre5 );
		pretListUser1Livre5.add(pret1Livre5);
		
		Mockito.when(appProperties.getMultiplicateur()).thenReturn(2);
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
	    
	    /*
	     * Jeu de données pour les use cases READ
	     */
	    Page<Reservation> reservationPage1 = new PageImpl<Reservation>(reservationList1);
	    Mockito.when(reservationRepository.findAll(any(ReservationSpecification.class), any(Pageable.class))).thenReturn(reservationPage1);
	
	    Mockito.when(reservationRepository.findById((long)1)).thenReturn(Optional.of(reservation1));
	    
	    /*
	     * Jeu de données pour les use cases UPDATE
	     */
	    Reservation reservation5 = new Reservation((long)5, LocalDate.now().minusDays(5), LocalDate.now().minusDays(3), LocalDate.now().minusDays(1), null, ReservationStatut.NOTIFIEE, user3, livre6);
	    Mockito.when(reservationRepository.findById((long)5)).thenReturn(Optional.of(reservation5));
	    Reservation reservation6 = new Reservation((long)6, LocalDate.now(), null, null, null, ReservationStatut.ENREGISTREE, 2,user3, livre6);
	    Mockito.when(reservationRepository.findById((long)6)).thenReturn(Optional.of(reservation6));
	    Reservation reservation7 = new Reservation((long)7, LocalDate.now().minusDays(5), LocalDate.now().minusDays(1), LocalDate.now().plusDays(1), null, ReservationStatut.NOTIFIEE, 1, user3, livre6);
	    Mockito.when(reservationRepository.findById((long)7)).thenReturn(Optional.of(reservation7));
	    List<Reservation> reservationListToUpdate = Arrays.asList(reservation6);
	    Mockito.when(reservationRepository.findAllByLivreAndReservationStatut(livre6, ReservationStatut.ENREGISTREE)).thenReturn(Optional.of(reservationListToUpdate));
	    
	    /*
	     * Jeu de données pour les use cases DELETE
	     */
	    Reservation reservation11 = new Reservation((long)11, LocalDate.now().minusDays(10), LocalDate.now().minusDays(8), LocalDate.now().minusDays(6),LocalDate.now().minusDays(2) , ReservationStatut.LIVREE, 1, user3, livre8);
		Reservation reservation12 = new Reservation((long)12, LocalDate.now().minusDays(10), LocalDate.now().minusDays(8), LocalDate.now().minusDays(6),LocalDate.now().minusDays(2) , ReservationStatut.ANNULEE, 1, user3, livre8);
		Reservation reservation13 = new Reservation((long)13, LocalDate.now().minusDays(10), LocalDate.now().minusDays(8), LocalDate.now().minusDays(6),LocalDate.now().minusDays(2) , ReservationStatut.SUPPRIMEE, 1, user3, livre8);
		Mockito.when(reservationRepository.findById((long)11)).thenReturn(Optional.of(reservation11));
		Mockito.when(reservationRepository.findById((long)12)).thenReturn(Optional.of(reservation12));
		Mockito.when(reservationRepository.findById((long)13)).thenReturn(Optional.of(reservation13));
		
    }
    
   //****************************************** TESTS CREATE ********************************************************************
    
    @Test
    public void testCreateReservation_whenExceptionUserInconnu() {
    	
		ReservationDTO reservationDTO1 = new ReservationDTO((long)0, (long)1);
		
		try {
			Reservation reservationCreated = reservationMetier.createReservation(reservationDTO1);
		} catch (EntityNotFoundException | BookNotAvailableException | BookAvailableException | EntityAlreadyExistsException | RentAlreadyExistsException e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
			 .hasMessage("UTILISATEUR INCONNU = Aucun utilisateur ne correspond à votre identification de l'emprunteur ");
		} 
	}
	
	@Test
	public void testCreateReservation_whenExceptionLivreInconnu() {
		
		ReservationDTO reservationDTO1 = new ReservationDTO((long)1, (long)0);
		
		try {
			Reservation reservationCreated = reservationMetier.createReservation(reservationDTO1);
		} catch (EntityNotFoundException | BookNotAvailableException | BookAvailableException
				| EntityAlreadyExistsException | RentAlreadyExistsException e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
			 .hasMessage("OUVRAGE INCONNU = Aucun enregistrement de livre ne correspond à votre demande");
		}
	}
	
	@Test
	public void testCreateReservation_whenExceptionNbExemplaires0() {
		
		ReservationDTO reservationDTO1 = new ReservationDTO((long)1, (long)2);
		
		try {
			Reservation reservationCreated = reservationMetier.createReservation(reservationDTO1);
		} catch (EntityNotFoundException | BookNotAvailableException | BookAvailableException
				| EntityAlreadyExistsException | RentAlreadyExistsException e) {
			assertThat(e).isInstanceOf(BookNotAvailableException.class)
			 .hasMessage("RESERVATION IMPOSSIBLE = Il n'y a aucun exemplaire à emprunter pour cette référence de livre");
		}
	}
	
	@Test
	public void testCreateReservation_whenExceptionNbExemplairesDisponibles() {
		
		ReservationDTO reservationDTO1 = new ReservationDTO((long)1, (long)3);
		
		try {
			Reservation reservationCreated = reservationMetier.createReservation(reservationDTO1);
		} catch (EntityNotFoundException | BookNotAvailableException | BookAvailableException
				| EntityAlreadyExistsException | RentAlreadyExistsException e) {
			assertThat(e).isInstanceOf(BookAvailableException.class)
			 .hasMessage("RESERVATION IMPOSSIBLE = Vous pouvez emprunter immédiatement un exemplaire disponible de ce livre");
		}	
	}
	
	@Test
	public void testCreateReservation_whenExceptionReservationCeLivreDejaEnCours() {
		
		ReservationDTO reservationDTO1 = new ReservationDTO((long)1, (long)4);
		
		try {
			Reservation reservationCreated = reservationMetier.createReservation(reservationDTO1);
		} catch (EntityNotFoundException | BookNotAvailableException | BookAvailableException
				| EntityAlreadyExistsException | RentAlreadyExistsException e) {
			assertThat(e).isInstanceOf(EntityAlreadyExistsException.class)
			 .hasMessage("RESERVATION IMPOSSIBLE : vous ne pouvez pas réserver un livre pour lequel vous avez déjà une réservation en cours");
		}
	}
	
	@Test
	public void testCreateReservation_whenExceptionPretCeLivreDejaEnCours() {
		
		ReservationDTO reservationDTO1 = new ReservationDTO((long)1, (long)5);
		
		try {
			Reservation reservationCreated = reservationMetier.createReservation(reservationDTO1);
		} catch (EntityNotFoundException | BookNotAvailableException | BookAvailableException
				| EntityAlreadyExistsException | RentAlreadyExistsException e) {
			assertThat(e).isInstanceOf(RentAlreadyExistsException.class)
			 .hasMessage("RESERVATION IMPOSSIBLE : vous ne pouvez pas réserver un livre que vous avez déjà en cours de prêt");
		}
	}
	
	@Test
	public void testCreateReservation_whenExceptionFileAttenteMaximum() {
		
		ReservationDTO reservationDTO1 = new ReservationDTO((long)1, (long)6);
		
		try {
			Reservation reservationCreated = reservationMetier.createReservation(reservationDTO1);
		} catch (EntityNotFoundException | BookNotAvailableException | BookAvailableException
				| EntityAlreadyExistsException | RentAlreadyExistsException e) {
			assertThat(e).isInstanceOf(BookNotAvailableException.class)
			 .hasMessage("RESERVATION IMPOSSIBLE : le nombre maximum d'utilisateurs ayant fait une réservation est atteint");
		}	
	}
	
	@Test
    public void testCreateReservation_withoutExceptionAtFirstRank() {
    	Categorie categorie1 = new Categorie((long) 1,"Categorie1");
    	User user1 = new User((long)1, "user1");
    	Livre livre1 = new Livre((long) 1,"titre100", "auteur100", 1,0,categorie1);
    	Reservation reservation1 = new Reservation((long)1, LocalDate.now(), null, null, null, ReservationStatut.ENREGISTREE, user1, livre1);
    	
    	ReservationDTO reservationDTO1 = new ReservationDTO((long)1, (long)1);
    	
    	Mockito.when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation1);
   	
			try {
				Reservation reservationCreated = reservationMetier.createReservation(reservationDTO1);
				Assertions.assertTrue(reservationCreated.compareTo(reservation1) == 0);
			} catch (EntityNotFoundException e) {
				assertThat(e).isInstanceOf(EntityNotFoundException.class)
				 .hasMessage("UTILISATEUR INCONNU = Aucun utilisateur ne correspond à votre identification de l'emprunteur ");
			} catch (BookNotAvailableException e) {
				assertThat(e).isInstanceOf(EntityNotFoundException.class)
				 .hasMessage("OUVRAGE INCONNU = Aucun enregistrement de livre ne correspond à votre demande");
			} catch (BookAvailableException e) {
				assertThat(e).isInstanceOf(BookNotAvailableException.class);
			} catch (EntityAlreadyExistsException e) {
				assertThat(e).isInstanceOf(BookAvailableException.class)
				 .hasMessage("RESERVATION IMPOSSIBLE = Vous pouvez emprunter immédiatement un exemplaire disponible de ce livre");			
			} catch (RentAlreadyExistsException e) {
				assertThat(e).isInstanceOf(RentAlreadyExistsException.class)
				 .hasMessage("RESERVATION IMPOSSIBLE : vous ne pouvez pas réserver un livre que vous avez déjà en cours de prêt");
			}
	}
	
	@Test
    public void testCreateReservation_withoutExceptionWithARankSupTo1() {
    	Categorie categorie1 = new Categorie((long) 1,"Categorie1");
    	User user1 = new User((long)1, "user1");
    	User user20 = new User((long)20, "user20");
    	User user30 = new User((long)30, "user30");
    	Livre livre10 = new Livre((long) 10,"titre10", "auteur10", 2,0,categorie1);
    	Reservation reservation1 = new Reservation((long)1, LocalDate.now(), null, null, null, ReservationStatut.ENREGISTREE, user1, livre10);
    	Reservation reservation20 = new Reservation((long)2, LocalDate.now().minusDays(10), LocalDate.now().minusDays(1), LocalDate.now().plusDays(1), null, ReservationStatut.NOTIFIEE, user20, livre10);
    	Reservation reservation30 = new Reservation((long)3, LocalDate.now().minusDays(8), null, null, null, ReservationStatut.ENREGISTREE, user30, livre10);
    	List<Reservation> reservationList2 = Arrays.asList(reservation20, reservation30);
    	Mockito.when(reservationRepository.findAllByLivreGroupByUserAndReservationStatutOrReservationStatut(livre10, ReservationStatut.ENREGISTREE, ReservationStatut.NOTIFIEE)).thenReturn(Optional.of(reservationList2));
	    
    	ReservationDTO reservationDTO10 = new ReservationDTO((long)1, (long)10);
    	Mockito.when(livreRepository.findById(reservationDTO10.getNumLivre())).thenReturn(Optional.of(livre10));
    	Mockito.when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation1);
   	
			try {
				Reservation reservationCreated = reservationMetier.createReservation(reservationDTO10);
				Assertions.assertTrue(reservationCreated.compareTo(reservation1) == 0);
			} catch (EntityNotFoundException e) {
				assertThat(e).isInstanceOf(EntityNotFoundException.class)
				 .hasMessage("UTILISATEUR INCONNU = Aucun utilisateur ne correspond à votre identification de l'emprunteur ");
			} catch (BookNotAvailableException e) {
				assertThat(e).isInstanceOf(EntityNotFoundException.class)
				 .hasMessage("OUVRAGE INCONNU = Aucun enregistrement de livre ne correspond à votre demande");
			} catch (BookAvailableException e) {
				assertThat(e).isInstanceOf(BookNotAvailableException.class);
			} catch (EntityAlreadyExistsException e) {
				assertThat(e).isInstanceOf(BookAvailableException.class)
				 .hasMessage("RESERVATION IMPOSSIBLE = Vous pouvez emprunter immédiatement un exemplaire disponible de ce livre");			
			} catch (RentAlreadyExistsException e) {
				assertThat(e).isInstanceOf(RentAlreadyExistsException.class)
				 .hasMessage("RESERVATION IMPOSSIBLE : vous ne pouvez pas réserver un livre que vous avez déjà en cours de prêt");
			}
    }
	
	//***************************************** TESTS READ **********************************************************************
	/**
	 * Mock du dao avec JpaSpecification<Reservation> et any(ReservationSPecification.class())
	 * On teste que la méthode toPredicate et le criteria Bilder de la classe ReservationSpecification fonctionnent 
	 * correctement avec tous les attributs = null
	 */
	@Test
	public void testSearchAllReservationsByCriteria_withoutAllAttributesNull() {
		ReservationCriteria reservationCriteria = new ReservationCriteria();
		Pageable pageable = PageRequest.of(0,6);
		
		Page<Reservation> reservationPageTest = reservationMetier.searchAllReservationsByCriteria(reservationCriteria, pageable);
		Assertions.assertTrue(reservationPageTest.getContent().size()==2);
		Assertions.assertTrue(reservationPageTest.getContent().get(0).getNumReservation()==3);
		Assertions.assertTrue(reservationPageTest.getContent().get(1).getNumReservation()==4);
	}
	
	/**
	 * Mock du dao avec JpaSpecification<Reservation> et any(ReservationSPecification.class())
	 * On teste que la méthode toPredicate et le criteria Bilder de la classe ReservationSpecification fonctionnent 
	 * correctement avec tous les attributs non null
	 */
	@Test
	public void testSearchAllReservationsByCriteria_withAllAttributesNotNull() {
		ReservationCriteria reservationCriteria = new ReservationCriteria();
			reservationCriteria.setAuteur("auteur1");
			reservationCriteria.setNomCategorieLivre("categorie1");
			reservationCriteria.setNumLivre(1);
			reservationCriteria.setNumReservation((long) 3);
			reservationCriteria.setReservationStatutCode("ENREGISTREE");
			reservationCriteria.setTitre("titre1") ;
			reservationCriteria.setUserId(1);
			reservationCriteria.setUsername("user1");		
		Pageable pageable = PageRequest.of(0,6);
		
		Page<Reservation> reservationPageTest = reservationMetier.searchAllReservationsByCriteria(reservationCriteria, pageable);
		Assertions.assertTrue(reservationPageTest.getContent().size()==2);
		Assertions.assertTrue(reservationPageTest.getContent().get(0).getNumReservation()==3);
		Assertions.assertTrue(reservationPageTest.getContent().get(1).getNumReservation()==4);
	}
	
	@Test 
	public void testReadReservation_withEntityNotFoundException() {
		
		try {
			Reservation reservation = reservationMetier.readReservation((long) 0);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
			 .hasMessage("Aucune reservation enregistrée ne correspond à votre demande");
		}
	}
	
	@Test
	public void testReadReservation_withoutException() {
		try {
			Reservation reservation = reservationMetier.readReservation((long) 1);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
			 .hasMessage("Aucune reservation enregistrée ne correspond à votre demande");
		}
	}
	
	//************************************** TESTS UPDATE **********************************************************************
	
	@Test
	public void testNotifierReservation_whenEntityNotFoundException() {
		
		try {
			Reservation reservationNotifiee = reservationMetier.notifierReservation((long) 0);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
			 .hasMessage("RESERVATION INCONNUE : Cette réservation n'existe pas");
		} 
	}
	
	@Test
	public void testNotifierReservation_whenReservationStatutNotEnregistree() {
		
		try {
			Reservation reservationNotifiee = reservationMetier.notifierReservation((long)5);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(WrongNumberException.class)
			 .hasMessage("NOTIFICATION IMPOSSIBLE = Le statut de cette réservation ne permet pas de la notifier");
		} 
	}
	
	@Test
	public void testNotifierReservation_withoutException() {
		
		try {
			Reservation reservationNotifiee = reservationMetier.notifierReservation((long)6);
		} catch (EntityNotFoundException e) {
			assertThat(e).hasMessage("RESERVATION INCONNUE : Cette réservation n'existe pas");
		} catch (WrongNumberException e1) {
			assertThat(e1).hasMessage("NOTIFICATION IMPOSSIBLE = Le statut de cette réservation ne permet pas de la notifier");
		}
	}
	
	@Test
	public void testLivrerReservationAndCreerPret_whenEntityNotFoundException() {		
		try {
			reservationMetier.livrerReservationAndCreerPret((long) 0);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
			 .hasMessage("RESERVATION INCONNUE = Cette réservation n'existe pas");
		} 
	}
	
	@Test
	public void testLivrerReservationAndCreerPret_whenStatutReservationNotNotifiee() {	
		try {
			reservationMetier.livrerReservationAndCreerPret((long)6);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(WrongNumberException.class)
			 .hasMessage("LIVRAISON IMPOSSIBLE = Le statut de cette réservation ne permet pas de la notifier");
		} 
	}
	
	@Test
	public void testLivrerReservationAndCreerPret_whenDeadlineDepassee() {
		
		try {
			reservationMetier.livrerReservationAndCreerPret((long)5);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(BookNotAvailableException.class)
			 .hasMessage("LIVRAISON ANNULEE = La date limite de votre réservation pour le pret du livre est dépassée");
		} 
	}
	
	@Test
	public void testMiseAJourRangReservationDansFileAttente() {
		Categorie categorie1 = new Categorie((long) 1,"Categorie1");
    	User user20 = new User((long)20, "user20");
    	User user30 = new User((long)30, "user30");
    	Livre livre20 = new Livre((long) 20,"titre20", "auteur20", 2,0,categorie1);
    	Reservation reservation20 = new Reservation((long)2, LocalDate.now().minusDays(10), LocalDate.now().minusDays(1), LocalDate.now().plusDays(1), null, ReservationStatut.NOTIFIEE, 1,user20, livre20);
    	Reservation reservation30 = new Reservation((long)3, LocalDate.now().minusDays(8), null, null, null, ReservationStatut.ENREGISTREE, 2, user30, livre20);
    	//Reservation reservation20b = new Reservation((long)2, LocalDate.now().minusDays(10), LocalDate.now().minusDays(1), LocalDate.now().plusDays(1), LocalDate.now(), ReservationStatut.LIVREE, null,user20, livre20);
    	Reservation reservation30b = new Reservation((long)3, LocalDate.now().minusDays(8), null, null, null, ReservationStatut.ENREGISTREE, 1, user30, livre20);
    	List<Reservation> reservationList30 = Arrays.asList(reservation30);
    	Mockito.when(reservationRepository.findAllByLivreGroupByUserAndReservationStatutOrReservationStatut(livre20, ReservationStatut.ENREGISTREE, ReservationStatut.NOTIFIEE)).thenReturn(Optional.of(reservationList30));
	    

    	Mockito.when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation30b);
		
		reservationRepository.findAllByLivreAndReservationStatut(livre20, ReservationStatut.ENREGISTREE);
		
		Assert.assertTrue(reservation20.getRangReservation()==1);
	}
	
	
	@Test
	public void testLivrerReservationAndCreerPret_withoutException() {
    	
		try {
			reservationMetier.livrerReservationAndCreerPret((long)7);
		} catch (EntityNotFoundException e) {
			assertThat(e)
			 .hasMessage("RESERVATION INCONNUE = Cette réservation n'existe pas");
		} catch (WrongNumberException e) {
			assertThat(e)
			 .hasMessage("LIVRAISON IMPOSSIBLE = Le statut de cette réservation ne permet pas de la notifier");
		} catch (BookNotAvailableException e) {
			assertThat(e)
			 .hasMessage("LIVRAISON ANNULEE = La date limite de votre réservation pour le pret du livre est dépassée");
		}
		
	}
	
	@Test
	public void testSearchAndNotifierReservations_withNoReservationsANotifier() {

		try {
			List<Reservation> reservationsANotifierList = reservationMetier.searchAndNotifierReservations();
			Assertions.assertTrue(reservationsANotifierList.isEmpty());
		} catch (EntityNotFoundException | WrongNumberException e) {		
		}
	}
		
	@Test
	public void testSearchAndNotifierReservations_withOnlyReservationsLAreadyNotifieesWithDeadlineValideANotifier() {
		  Categorie categorie1 = new Categorie((long) 1,"Categorie1");
		  User user3 = new User((long)3, "user3");
		  Livre livre6 = new Livre((long) 6,"titre6", "auteur6", 1, 0, categorie1);	
		  Reservation reservation7 = new Reservation((long)7, LocalDate.now().minusDays(5), LocalDate.now().minusDays(1), LocalDate.now().plusDays(1), null, ReservationStatut.NOTIFIEE, user3, livre6);
		  Mockito.when(reservationRepository.findById((long)7)).thenReturn(Optional.of(reservation7));
		    
		  List<Reservation> reservationsAlreadyNotifieesDeadlineValideList = Arrays.asList(reservation7);
		  Mockito.when(reservationRepository.findAllByReservationStatutAndDateDeadlineValide(ReservationStatut.NOTIFIEE, LocalDate.now())).thenReturn(Optional.of(reservationsAlreadyNotifieesDeadlineValideList));
		try {
			List<Reservation> reservationsANotifierList = reservationMetier.searchAndNotifierReservations();
			Assertions.assertTrue(reservationsANotifierList.size()==1);
			Assertions.assertTrue(reservationsANotifierList.get(0).getNumReservation()==7);
		} catch (EntityNotFoundException | WrongNumberException e) {
			
		}
	}
	
	@Test
	public void testSearchAndNotifierReservations_withOnlyReservationsLAreadyNotifieesWithDeadlineDechuesANotifier() {
		  Categorie categorie1 = new Categorie((long) 1,"Categorie1");
		  User user3 = new User((long)3, "user3");
		  Livre livre6 = new Livre((long) 6,"titre6", "auteur6", 1, 0, categorie1);	
		  Reservation reservation8 = new Reservation((long)8, LocalDate.now().minusDays(10), LocalDate.now().minusDays(5), LocalDate.now().minusDays(3), null, ReservationStatut.NOTIFIEE, user3, livre6);
		  Mockito.when(reservationRepository.findById((long)8)).thenReturn(Optional.of(reservation8));
		    
		  List<Reservation> reservationsAlreadyNotifieesDeadlineDechueList = Arrays.asList(reservation8);
		  Mockito.when(reservationRepository.findAllByReservationStatutAndDateDeadlineDechue(ReservationStatut.NOTIFIEE, LocalDate.now())).thenReturn(Optional.of(reservationsAlreadyNotifieesDeadlineDechueList));			  
				  
		try {
			List<Reservation> reservationsANotifierList = reservationMetier.searchAndNotifierReservations();
			Assertions.assertTrue(reservationsANotifierList.isEmpty());
		} catch (EntityNotFoundException | WrongNumberException e) {
			
		}
	}
	
	@Test
	public void testSearchAndNotifierReservations_withOnlyReservationsEnregistreesSelectionnees() {
		  Categorie categorie1 = new Categorie((long) 1,"Categorie1");
		  User user3 = new User((long)3, "user3");
		  Livre livre7 = new Livre((long) 7,"titre7", "auteur7", 1, 1, categorie1);	
		  Reservation reservation9 = new Reservation((long)9, LocalDate.now().minusDays(10), null, null, null, ReservationStatut.ENREGISTREE, 1, user3, livre7);
		  Reservation reservation10 = new Reservation((long)10, LocalDate.now().minusDays(10), LocalDate.now(), LocalDate.now().plusDays(2), null, ReservationStatut.NOTIFIEE, 1, user3, livre7);
		  Mockito.when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation10);
		  Mockito.when(reservationRepository.findById((long)9)).thenReturn(Optional.of(reservation9));
		  Mockito.when(reservationRepository.findById((long)10)).thenReturn(Optional.of(reservation10));
		    
		  List<Reservation> reservationsEnregistreesSelectionneesList = Arrays.asList(reservation9);
		  Mockito.when(reservationRepository.findAllByReservationStatutAndNbExemplairesDisponiblesAndRangReservation(ReservationStatut.ENREGISTREE, 1,1)).thenReturn(Optional.of(reservationsEnregistreesSelectionneesList));
		
		  
		  try {
				List<Reservation> reservationsANotifierList = reservationMetier.searchAndNotifierReservations();
				Assertions.assertTrue(reservationsANotifierList.size()==1);
				Assertions.assertTrue(reservationsANotifierList.get(0).getNumReservation()==10);
				} catch (EntityNotFoundException | WrongNumberException e) {
			}	
	}
	
	//***************************************** TETS DELETE *******************************************************************
	
	@Test
	public void testSuppressReservation_whenEntitynotFoundException() {
		
		try {
			Reservation reservationToSuppress = reservationMetier.suppressReservation((long) 0);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
			 .hasMessage("RESERVATION INCONNUE = Cette réservation n'existe pas");
		} 
	}
	
	@Test
	public void testSuppressReservation_whenWrongNumberException_withReservationStatutLivree() {
		try {
			Reservation reservationToSuppress = reservationMetier.suppressReservation((long) 11);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(WrongNumberException.class)
			 .hasMessage("SUPPRESSION IMPOSSIBLE = Le statut de cette réservation ne permet pas de la supprimer");
		} 
	}
	
	@Test
	public void testSuppressReservation_whenReservationStatutAnnulee() {
		try {
			Reservation reservationToSuppress = reservationMetier.suppressReservation((long) 12);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(WrongNumberException.class)
			 .hasMessage("SUPPRESSION IMPOSSIBLE = Le statut de cette réservation ne permet pas de la supprimer");
		} 
	}
	
	@Test
	public void testSuppressReservation_whenWrongNumberException_withReservationStatutSupprimee() {
		try {
			Reservation reservationToSuppress = reservationMetier.suppressReservation((long) 13);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(WrongNumberException.class)
			 .hasMessage("SUPPRESSION IMPOSSIBLE = Le statut de cette réservation ne permet pas de la supprimer");
		} 	
	}
	
	@Test
	public void testSuppressReservation_withoutException_withReservationStatutEnregistree() {
			Categorie categorie1 = new Categorie((long) 1,"Categorie1");
			User user3 = new User((long)3, "user3");
			Livre livre8 = new Livre((long) 7,"titre7", "auteur7", 1, 1, categorie1);	
			Reservation reservation14 = new Reservation((long)14, LocalDate.now().minusDays(10), null, null, null , ReservationStatut.ENREGISTREE, 1, user3, livre8);
			Mockito.when(reservationRepository.findById((long)14)).thenReturn(Optional.of(reservation14));
		    Mockito.when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation14);
		
			try {
				Reservation reservationToSuppress = reservationMetier.suppressReservation((long) 14);
			} catch (EntityNotFoundException e) {
				assertThat(e).isInstanceOf(EntityNotFoundException.class)
				 .hasMessage("RESERVATION INCONNUE = Cette réservation n'existe pas");
			} catch (WrongNumberException e) {
				assertThat(e).isInstanceOf(WrongNumberException.class);
			}
	}
	
	@Test
	public void testSuppressReservation_withoutException_withReservationStatutNotifiee() {
		Categorie categorie1 = new Categorie((long) 1,"Categorie1");
		User user3 = new User((long)3, "user3");
		Livre livre8 = new Livre((long) 7,"titre7", "auteur7", 1, 1, categorie1);	
		Reservation reservation15 = new Reservation((long)15, LocalDate.now().minusDays(10), LocalDate.now().minusDays(1), LocalDate.now().plusDays(1), null , ReservationStatut.NOTIFIEE, 1, user3, livre8);	
		Mockito.when(reservationRepository.findById((long)15)).thenReturn(Optional.of(reservation15));
	    Mockito.when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation15);
	
			try {
				Reservation reservationToSuppress = reservationMetier.suppressReservation((long) 15);
			} catch (EntityNotFoundException e) {
				assertThat(e).isInstanceOf(EntityNotFoundException.class)
				 .hasMessage("RESERVATION INCONNUE = Cette réservation n'existe pas");
			} catch (Exception e) {
				assertThat(e).isInstanceOf(WrongNumberException.class);
			}	
	}
	
	
	
}
	
	

	

