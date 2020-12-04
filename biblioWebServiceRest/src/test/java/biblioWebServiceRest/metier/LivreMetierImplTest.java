package biblioWebServiceRest.metier;



import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
//import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import biblioWebServiceRest.BiblioWebServiceRestApplication;
import biblioWebServiceRest.criteria.LivreCriteria;
import biblioWebServiceRest.dao.ICategorieRepository;
import biblioWebServiceRest.dao.ILivreRepository;
import biblioWebServiceRest.dao.IPretRepository;
import biblioWebServiceRest.dao.IReservationRepository;
import biblioWebServiceRest.dao.specs.LivreSpecification;
import biblioWebServiceRest.dto.LivreDTO;
import biblioWebServiceRest.entities.Categorie;
import biblioWebServiceRest.entities.Livre;
import biblioWebServiceRest.entities.Pret;
import biblioWebServiceRest.entities.PretStatut;
import biblioWebServiceRest.entities.Reservation;
import biblioWebServiceRest.entities.ReservationStatut;
import biblioWebServiceRest.entities.User;
import biblioWebServiceRest.exceptions.BiblioException;
import biblioWebServiceRest.exceptions.EntityAlreadyExistsException;
import biblioWebServiceRest.exceptions.EntityNotFoundException;
import biblioWebServiceRest.mapper.LivreMapper;


@SpringBootTest (classes = BiblioWebServiceRestApplication.class)
@TestPropertySource(locations="classpath:application.properties")
public class LivreMetierImplTest  extends BiblioWebServiceRestMetierTests{
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@TestConfiguration
    static class LivreMetierImplTestContextConfiguration {
 
        @Bean
        public ILivreMetier livreMetier() {
            return new LivreMetierImpl();
        }
    }

    @Autowired
    private ILivreMetier livreMetier;
    
    @MockBean
    private ILivreRepository livreRepository;
	@MockBean
	private ICategorieRepository categorieRepository; 
	@MockBean
	private IPretRepository pretRepository;
	@Autowired
	private LivreMapper livreMapper;
	@MockBean
	private IReservationRepository reservationRepository;
//**************************************************************************************************************************	
	@Before
	public void setUp() {
		/*
		 * Jeu de données pour le test de la création d'un livre***********************************************************
		 */
		Categorie categorie1 = new Categorie((long) 1,"Categorie1");
		
		Optional<Categorie> optionalCategorie1 = Optional.of(categorie1);
	    
		Livre livre1 = new Livre((long) 1,"titre1", "auteur1", 1,1,categorie1);
		Livre livre2 = new Livre((long) 2,"titre2", "auteur2", 1, 1, categorie1);
		
		LivreDTO livre1DTO = new LivreDTO("titre1", "auteur1", 1, categorie1.getNumCategorie());
	    
	    Mockito.when(categorieRepository.findById(livre1DTO.getNumCategorie())).thenReturn(optionalCategorie1);
	    
	    Mockito.when(livreRepository.findByTitreAndAuteur(livre1DTO.getTitre(), livre1DTO.getAuteur())).thenReturn(Optional.of(livre1));
	    
	    
	    /*
	     * Jeu de données pour la mise à Jour ********************************************************************************
	     */
	   
	   /*
	    // 1- MAINSTREAM private MiseAJour()
	    User user3 = new User("user3");
	    List<Livre> livreList3 = new ArrayList<Livre>();
	    List<Pret> pretList3 = new ArrayList<Pret>();
	    
	    List<Reservation> reservationList3 = new ArrayList<Reservation>();
	    Livre livre3 = new Livre((long) 3,"titre3", "auteur3", 1, 0, null, 2,2, categorie1, pretList3, reservationList3);
	    livreList3.add(livre3);
	    Pret pret3Livre3 = new Pret(LocalDate.now(), LocalDate.now().plusDays(28), PretStatut.ENCOURS, user3, livre3);
	    pretList3.add(pret3Livre3);
	    
	    Reservation reservation3 = new Reservation((long)3, LocalDate.now(), null, null, null, ReservationStatut.ENREGISTREE, user3, livre3);
	    reservationList3.add(reservation3);
	    
	    Mockito.when(livreRepository.findAllByNbExemplairesDisponibles(0)).thenReturn(Optional.of(livreList3));
	    
	    Mockito.when(pretRepository.findAllByLivreAndNotPretStatutOrderByDateRetourPrevueAfterThisDate(
				livre3, 
				PretStatut.CLOTURE, 
				LocalDate.now())).thenReturn(Optional.of(pretList3));
	   
	    Mockito.when(reservationRepository.findAllByLivreAndReservationStatutOrReservationStatut(livre3, ReservationStatut.ENREGISTREE, ReservationStatut.NOTIFIEE)).thenReturn(Optional.of(reservationList3));
	    
	    Mockito.when(reservationRepository.findAllByLivreGroupByUserAndReservationStatutOrReservationStatut(livre3, ReservationStatut.ENREGISTREE, ReservationStatut.NOTIFIEE)).thenReturn(Optional.of(reservationList3));
	    
	    // 2- else 1
	    User user4 = new User("user4");
	    List<Livre> livreList4 = new ArrayList<Livre>();
	    List<Pret> pretList4 = new ArrayList<Pret>();
	    
	    List<Reservation> reservationList4 = new ArrayList<Reservation>();
	    Livre livre4 = new Livre((long) 3,"titre3", "auteur3", 1, 0, null, 2,2, categorie1, pretList4, reservationList4);
	    livreList4.add(livre4);
	    Pret pret4Livre4 = new Pret(LocalDate.now(), LocalDate.now().minusDays(28), PretStatut.ENCOURS, user4, livre4);
	    pretList4.add(pret4Livre4);
	    
	    Reservation reservation4 = new Reservation((long)3, LocalDate.now(), null, null, null, ReservationStatut.ENREGISTREE, user4, livre4);
	    reservationList4.add(reservation4);
	    
	    Mockito.when(livreRepository.findAllByNbExemplairesDisponibles(0)).thenReturn(Optional.of(livreList4));
	    
	    Mockito.when(pretRepository.findAllByLivreAndNotPretStatutOrderByDateRetourPrevueAfterThisDate(
				livre3, 
				PretStatut.CLOTURE, 
				LocalDate.now())).thenReturn(Optional.of(pretList3));
	   
	    Mockito.when(reservationRepository.findAllByLivreAndReservationStatutOrReservationStatut(livre4, ReservationStatut.ENREGISTREE, ReservationStatut.NOTIFIEE)).thenReturn(Optional.of(reservationList4));
	    
	    Mockito.when(reservationRepository.findAllByLivreGroupByUserAndReservationStatutOrReservationStatut(livre4, ReservationStatut.ENREGISTREE, ReservationStatut.NOTIFIEE)).thenReturn(Optional.of(reservationList4));
	    
	    */

//*****************************************************************************************************************************
	    /*
	     * Jeu de données pour méthode SearchLivreByCriteria
	     */
			 // 1- MAINSTREAM private MiseAJour()
			    User user5 = new User("user5");
			    List<Livre> livreList5 = new ArrayList<Livre>();
			    List<Pret> pretList5 = new ArrayList<Pret>();
			    
			    List<Reservation> reservationList5 = new ArrayList<Reservation>();
			    Livre livre5 = new Livre((long) 5,"titre5", "auteur5", 1, 0, null, 2,2, categorie1, pretList5, reservationList5);
			    livreList5.add(livre5);
			    Pret pret5Livre5 = new Pret(LocalDate.now(), LocalDate.now().plusDays(28), PretStatut.ENCOURS, user5, livre5);
			    pretList5.add(pret5Livre5);
			    
			    Reservation reservation3 = new Reservation((long)5, LocalDate.now(), null, null, null, ReservationStatut.ENREGISTREE, user5, livre5);
			    reservationList5.add(reservation3);
			    
			    LivreCriteria livreCriteria5 = new LivreCriteria((long) 5, "titre5", "auteur5", null, 0);
			    Page<Livre> livrePage5 = new PageImpl<Livre>(livreList5, PageRequest.of(0, 6), livreList5.size());
			    Pageable pageable = PageRequest.of(0, 6);
			    LivreSpecification livreSpecification5 = new LivreSpecification(livreCriteria5);
			    
			    //
			    Mockito.when(livreRepository.findAllByNbExemplairesDisponibles(0)).thenReturn(Optional.of(livreList5));
			    
			    Mockito.when(pretRepository.findAllByLivreAndNotPretStatutOrderByDateRetourPrevueAfterThisDate(
						livre5, 
						PretStatut.CLOTURE, 
						LocalDate.now())).thenReturn(Optional.of(pretList5));
			   
			    Mockito.when(reservationRepository.findAllByLivreAndReservationStatutOrReservationStatut(livre5, ReservationStatut.ENREGISTREE, ReservationStatut.NOTIFIEE)).thenReturn(Optional.of(reservationList5));
			    
			    Mockito.when(reservationRepository.findAllByLivreGroupByUserAndReservationStatutOrReservationStatut(livre5, ReservationStatut.ENREGISTREE, ReservationStatut.NOTIFIEE)).thenReturn(Optional.of(reservationList5));
			    //
			    
			    Mockito.when(livreRepository.findAll(livreSpecification5, pageable)).thenReturn(livrePage5);
			    
			    
	}
	

	@Test
	public void testCreateLivre_whenSansExceptions() {
	   
		Categorie categorie1 = new Categorie((long) 1,"Categorie1");
		LivreDTO livre2DTO = new LivreDTO("titre2", "auteur2", 1, (long) 1);
		Livre livre2 = new Livre((long) 2,"titre2", "auteur2", 1,null,categorie1);
		Mockito.when(livreRepository.save(any(Livre.class))).thenReturn(livre2);
		
		 Assertions.assertDoesNotThrow(() -> {
			 Livre livreTest = livreMetier.createLivre(livre2DTO);
	            }, "Le test unitaire sur la méthode createLivre() ne s'est pas correctement déroulé");		 
	}
	
	@Test
	public void testCreateLivre_whenExceptionSansCategorie() {
	   
		LivreDTO livre2DTO = new LivreDTO("titre2", "auteur2", 1, null);
		Livre livre2 = new Livre((long) 2,"titre2", "auteur2", 1,null,null);
		
		
		 Assertions.assertThrows(EntityNotFoundException.class, () -> {
			 Livre livreTest = livreMetier.createLivre(livre2DTO);
         });
	}
	
	@Test
	public void testCreateLivre_whenExceptionLivreAlreadyExists() {
	   
		Categorie categorie1 = new Categorie((long) 1,"Categorie1");
		LivreDTO livre1DTO = new LivreDTO("titre1", "auteur1", 1, categorie1.getNumCategorie());
		Livre livre1 = new Livre((long) 1,"titre1", "auteur1", 1,1,categorie1);
		
		 Assertions.assertThrows(EntityAlreadyExistsException.class, () -> {
			 Livre livreTest = livreMetier.createLivre(livre1DTO);
         });
	}
	/*
	@Test
	public void testMiseAJourLivre_whenSansException() {
			Categorie categorie1 = new Categorie((long) 1,"Categorie1");
			User user3 = new User("user3");
		    List<Livre> livreList3 = new ArrayList<Livre>();
		    List<Pret> pretList3 = new ArrayList<Pret>();
		    
		    List<Reservation> reservationList3 = new ArrayList<Reservation>();
		    Livre livre3 = new Livre((long) 3,"titre3", "auteur3", 1, 0, null, 2,2, categorie1, pretList3, reservationList3);
		    livreList3.add(livre3);
		    Pret pret3Livre3 = new Pret(LocalDate.now(), LocalDate.now().plusDays(28), PretStatut.ENCOURS, user3, livre3);
		    pretList3.add(pret3Livre3);
		    
		    Reservation reservation3 = new Reservation((long)3, LocalDate.now(), null, null, null, ReservationStatut.ENREGISTREE, user3, livre3);
		    reservationList3.add(reservation3);
		    
		    Mockito.when(livreRepository.save(any(Livre.class))).thenReturn(livre3);
			
		    livreMetier.miseAJourLivres();
		    
		    
		    System.out.println(livre3.getDateRetourPrevuePlusProche());
		    System.out.println(pret3Livre3.getDateRetourPrevue());
		    
		    Assertions.assertTrue(livre3.getDateRetourPrevuePlusProche().toString().equals(pret3Livre3.getDateRetourPrevue().toString()));	
	}
	
	
	@Test
	public void testMiseAJourLivre_whenDateRetourPlusProcheNonIndiquée() {
		Categorie categorie1 = new Categorie((long) 1,"Categorie1");
		User user4 = new User("user4");
	    List<Livre> livreList4 = new ArrayList<Livre>();
	    List<Pret> pretList4 = new ArrayList<Pret>();
	    
	    List<Reservation> reservationList4 = new ArrayList<Reservation>();
	    Livre livre4 = new Livre((long) 3,"titre3", "auteur3", 1, 0, null, 2,2, categorie1, pretList4, reservationList4);
	    livreList4.add(livre4);
	    Pret pret4Livre4 = new Pret(LocalDate.now().minusDays(28), LocalDate.now().minusDays(18), PretStatut.ENCOURS, user4, livre4);
	    pretList4.add(pret4Livre4);
	    
	    Reservation reservation4 = new Reservation((long)3, LocalDate.now(), null, null, null, ReservationStatut.ENREGISTREE, user4, livre4);
	    reservationList4.add(reservation4);
		
	    livreMetier.miseAJourLivres();
	    
	    System.out.println(livre4.getDateRetourPrevuePlusProche());
	    
	    
	    Assertions.assertTrue(livre4.getDateRetourPrevuePlusProche().toString().equals("Aucune date de retour ne peut être indiquée"));	
	}
	*/
	
	@Test
	public void testSearchByLivreCriteria_withMiseAjour() {
		Categorie categorie1 = new Categorie((long) 1,"Categorie1");
		User user5 = new User("user5");
	    List<Livre> livreList5 = new ArrayList<Livre>();
	    List<Pret> pretList5 = new ArrayList<Pret>();
	    
	    List<Reservation> reservationList5 = new ArrayList<Reservation>();
	    Livre livre5 = new Livre((long) 5,"titre5", "auteur5", 1, 0, LocalDate.now().plusDays(28).toString(), 2,2, categorie1, pretList5, reservationList5);
	    livreList5.add(livre5);
	    Pret pret5Livre5 = new Pret(LocalDate.now(), LocalDate.now().plusDays(28), PretStatut.ENCOURS, user5, livre5);
	    pretList5.add(pret5Livre5);
	    
	    Reservation reservation3 = new Reservation((long)5, LocalDate.now(), null, null, null, ReservationStatut.ENREGISTREE, user5, livre5);
	    reservationList5.add(reservation3);
	    
	    LivreCriteria livreCriteria5 = new LivreCriteria((long) 5, "titre5", "auteur5", null, 0);
	    Page<Livre> livrePage5 = new PageImpl<Livre>(livreList5, PageRequest.of(0, 6), livreList5.size());
	    
	    
	    Pageable pageable = PageRequest.of(0, 6);
	    
	    Mockito.when(livreRepository.findAll(new LivreSpecification(livreCriteria5), pageable)).thenReturn(livrePage5);
	    
	    Page<Livre> livrePageResult = livreMetier.searchByLivreCriteria(livreCriteria5, PageRequest.of(0, 6));
	    Assertions.assertTrue(livrePageResult.hasContent());
	    Livre livreResult = (Livre) livrePageResult.getContent().get(0);
	    
	    //Vérification du contenu du retour du searchLivreByCriteria()
	    Assertions.assertTrue(livreResult.equals(livre5));
	    
	    //Vérification de la mise à jour
	    Assertions.assertTrue(livreResult.getDateRetourPrevuePlusProche().toString().equals(pret5Livre5.getDateRetourPrevue().toString()));	
	    
	   
	    
	}
	
}
