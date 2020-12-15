package biblioWebServiceRest.metier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import biblioWebServiceRest.configurations.ApplicationPropertiesConfiguration;
import biblioWebServiceRest.criteria.LivreCriteria;
import biblioWebServiceRest.criteria.PretCriteria;
import biblioWebServiceRest.dao.ILivreRepository;
import biblioWebServiceRest.dao.IPretRepository;
import biblioWebServiceRest.dao.IRoleRepository;
import biblioWebServiceRest.dao.IUserRepository;
import biblioWebServiceRest.dao.specs.LivreSpecification;
import biblioWebServiceRest.dao.specs.PretSpecification;
import biblioWebServiceRest.dto.PretDTO;
import biblioWebServiceRest.entities.Categorie;
import biblioWebServiceRest.entities.Livre;
import biblioWebServiceRest.entities.Pret;
import biblioWebServiceRest.entities.PretStatut;
import biblioWebServiceRest.entities.User;
import biblioWebServiceRest.exceptions.BookNotAvailableException;
import biblioWebServiceRest.exceptions.EntityNotFoundException;
import biblioWebServiceRest.exceptions.WrongNumberException;
import biblioWebServiceRest.mapper.LivreMapper;
import biblioWebServiceRest.mapper.PretMapper;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PretMetierImplTest extends BiblioWebServiceRestMetierTests{

	@Mock
	private IPretRepository pretRepository;
	@Mock
	private ILivreRepository livreRepository;
	@Mock
	private IUserRepository userRepository;
	@Mock
	private IRoleRepository roleRepository;
	@Mock
	private ApplicationPropertiesConfiguration appProperties;
	@Mock
	private PretMapper pretMapper;
	@Mock
	LivreMapper livreMapper; 
	@Mock
	private ILivreMetier livreMetier;
	
	@InjectMocks
	private PretMetierImpl pretMetier;
	
	@Before 
	public void setup() {
		
		MockitoAnnotations.initMocks(this);
		
	}
	
	@Test
	public void testCreatePret_whenLivreEntityNotFoundexception() {
		
		Categorie categorie1 = new Categorie((long) 1,"Categorie1");
    	User user1 = new User((long)1, "user1");
		Livre livre1 = new Livre((long) 1,"titre1", "auteur1", 1, 0,categorie1);
			
		PretDTO pretDTO1 = new PretDTO();
		pretDTO1.setIdUser((long)1);
		pretDTO1.setNumLivre((long)1);
		
		Mockito.when(livreRepository.findById((long)1)).thenReturn(Optional.empty());
				//.ofNullable(null));
		
		try {
			Pret newPret = pretMetier.createPret(pretDTO1);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
			 .hasMessage("Aucun enregistrement de livre ne correspond à votre demande");
		}
	}
	
	@Test
	public void testCreatePret_whenBookNotAvailableException() {
		
		Categorie categorie1 = new Categorie((long) 1,"Categorie1");
    	User user1 = new User((long)1, "user1");
		Livre livre1 = new Livre((long) 1,"titre1", "auteur1", 1, 0,categorie1);
		
		PretDTO pretDTO1 = new PretDTO();
		pretDTO1.setIdUser((long)1);
		pretDTO1.setNumLivre((long)1);
		
		Mockito.when(livreRepository.findById((long)1)).thenReturn(Optional.ofNullable(livre1));
		
		try {
			Pret newPret = pretMetier.createPret(pretDTO1);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(BookNotAvailableException.class)
			 .hasMessage("Il n'y a plus d'exemplaire disponible de ce livre");
		}
	}
	
	@Test
	public void testCreatePret_whenUserEntityNotFoundException() {
		
		Categorie categorie1 = new Categorie((long) 1,"Categorie1");
    	User user1 = new User((long)1, "user1");
		Livre livre1 = new Livre((long) 1,"titre1", "auteur1", 1, 1,categorie1);
		
		PretDTO pretDTO1 = new PretDTO();
		pretDTO1.setIdUser((long)1);
		pretDTO1.setNumLivre((long)1);
		
		Mockito.when(livreRepository.findById((long)1)).thenReturn(Optional.ofNullable(livre1));
		Mockito.when(userRepository.findById((long)1)).thenReturn(Optional.ofNullable(null));
		
		try {
			Pret newPret = pretMetier.createPret(pretDTO1);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
			 .hasMessage("Aucun utilisateur ne correspond à votre identification de l'emprunteur ");
		}
	}
	
	@Test
	public void testCreatePret_withoutException() throws Exception {
		
		Categorie categorie1 = new Categorie((long) 1,"Categorie1");
    	User user1 = new User((long)1, "user1");
		Livre livre1 = new Livre((long) 1,"titre1", "auteur1", 1, 1,categorie1);
		Pret pret1User1Livre1 = new Pret(LocalDate.now().minusDays(10), LocalDate.now().plusDays(18), PretStatut.ENCOURS, user1, livre1 );
		
		PretDTO pretDTO1 = new PretDTO();
		pretDTO1.setIdUser((long)1);
		pretDTO1.setNumLivre((long)1);
		
		Mockito.when(livreRepository.findById((long)1)).thenReturn(Optional.of(livre1));
		Mockito.when(userRepository.findById((long)1)).thenReturn(Optional.of(user1));
		Mockito.when(pretRepository.save(any(Pret.class))).thenReturn(pret1User1Livre1);
		
		
			Pret newPret = pretMetier.createPret(pretDTO1);
			Assert.assertTrue(newPret.equals(pret1User1Livre1));
			verify(pretRepository, times(1)).save(any(Pret.class));
		
	}
	
	@Test
	public void testProlongerPret_whenEntityNotFoundException() {
		
		Mockito.when(pretRepository.findById((long)1)).thenReturn(Optional.ofNullable(null));
		
		try {
			Pret prolongePret = pretMetier.prolongerPret((long)1);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
			 .hasMessage("Aucun prêt enregistré ne correspond à votre demande");
		}
	}	
	
	@Test
	public void testProlongerPret_whenBookNotAvailableception() {
		
		Categorie categorie1 = new Categorie((long) 1,"Categorie1");
    	User user1 = new User((long)1, "user1");
    	User user2 = new User((long)2, "user2");
    	User user3 = new User((long)3, "user3");
		Livre livre1 = new Livre((long) 1,"titre1", "auteur1", 3, 0,categorie1);
		Pret pret1User1Livre1 = new Pret(LocalDate.now().minusDays(10), LocalDate.now().minusDays(18), PretStatut.ECHU, user1, livre1 );
		Pret pret2User2Livre1 = new Pret(LocalDate.now().minusDays(10), LocalDate.now().plusDays(18), PretStatut.CLOTURE, user2, livre1 );
		Pret pret3User3Livre1 = new Pret(LocalDate.now().minusDays(10), LocalDate.now().plusDays(18), PretStatut.PROLONGE, user3, livre1 );
		Mockito.when(pretRepository.findById((long)1)).thenReturn(Optional.ofNullable(pret1User1Livre1));
		Mockito.when(pretRepository.findById((long)2)).thenReturn(Optional.ofNullable(pret2User2Livre1 ));
		Mockito.when(pretRepository.findById((long)3)).thenReturn(Optional.ofNullable(pret3User3Livre1));
		
		try {
			Pret prolongePret = pretMetier.prolongerPret((long)1);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(BookNotAvailableException.class)
			 .hasMessage("Le statut de ce pret de livre ne permet pas sa prolongation");
		}
		
		try {
			Pret prolongePret = pretMetier.prolongerPret((long)2);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(BookNotAvailableException.class)
			 .hasMessage("Le statut de ce pret de livre ne permet pas sa prolongation");
		}
		
		try {
			Pret prolongePret = pretMetier.prolongerPret((long)3);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(BookNotAvailableException.class)
			 .hasMessage("Le statut de ce pret de livre ne permet pas sa prolongation");
		}
	}	
	
	@Test
	public void testProlongerPret_whenWrongNumberException() {
		
		Categorie categorie1 = new Categorie((long) 1,"Categorie1");
    	User user1 = new User((long)1, "user1");
    	User user2 = new User((long)2, "user2");
		Livre livre1 = new Livre((long) 1,"titre1", "auteur1", 3, 0,categorie1);
		Pret pret1User1Livre1 = new Pret(LocalDate.now().minusDays(10), LocalDate.now().minusDays(18), PretStatut.ENCOURS, user1, livre1 );
		Pret pret2User2Livre1 = new Pret(LocalDate.now().minusDays(10), LocalDate.now().minusDays(18), PretStatut.AECHOIR, user2, livre1 );
		Mockito.when(pretRepository.findById((long)1)).thenReturn(Optional.ofNullable(pret1User1Livre1));
		Mockito.when(pretRepository.findById((long)2)).thenReturn(Optional.ofNullable(pret2User2Livre1 ));
		
		try {
			Pret prolongePret = pretMetier.prolongerPret((long)1);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(WrongNumberException.class)
			 .hasMessage("La date limite pour prolonger votre prêt est dépassée");
		}
		
		try {
			Pret prolongePret = pretMetier.prolongerPret((long)2);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(WrongNumberException.class)
			 .hasMessage("La date limite pour prolonger votre prêt est dépassée");
		}
	}	
	
	@Test
	public void testProlongerPret_withoutException_withStatutEncours() {
		
		Categorie categorie1 = new Categorie((long) 1,"Categorie1");
    	User user1 = new User((long)1, "user1");
		Livre livre1 = new Livre((long) 1,"titre1", "auteur1", 3, 0,categorie1);
		Pret pret1User1Livre1 = new Pret(LocalDate.now().minusDays(10), LocalDate.now().plusDays(18), PretStatut.ENCOURS, user1, livre1 );
		Mockito.when(pretRepository.findById((long)1)).thenReturn(Optional.ofNullable(pret1User1Livre1));
		Mockito.when(pretRepository.save(any(Pret.class))).thenReturn(pret1User1Livre1);
		
			try {
				Pret prolongePret = pretMetier.prolongerPret((long)1);
				verify(pretRepository, times(1)).save(any(Pret.class));
			} catch (EntityNotFoundException e) {
				assertThat(e).isInstanceOf(EntityNotFoundException.class)
				 .hasMessage("Aucun prêt enregistré ne correspond à votre demande");
			} catch (BookNotAvailableException e) {
				assertThat(e).isInstanceOf(BookNotAvailableException.class)
				 .hasMessage("Le statut de ce pret de livre ne permet pas sa prolongation");
			} catch (WrongNumberException e) {
				assertThat(e).isInstanceOf(WrongNumberException.class)
				 .hasMessage("La date limite pour prolonger votre prêt est dépassée");
			}
	}	
	
	@Test
	public void testProlongerPret_withoutException_withStatutAEchoir() {
		
		Categorie categorie1 = new Categorie((long) 1,"Categorie1");
    	User user2 = new User((long)2, "user2");
		Livre livre1 = new Livre((long) 1,"titre1", "auteur1", 3, 0,categorie1);
		Pret pret2User2Livre1 = new Pret(LocalDate.now().minusDays(10), LocalDate.now().plusDays(4), PretStatut.AECHOIR, user2, livre1 );
		Mockito.when(pretRepository.findById((long)2)).thenReturn(Optional.ofNullable(pret2User2Livre1 ));
		Mockito.when(pretRepository.save(any(Pret.class))).thenReturn(pret2User2Livre1);
			
			try {
				Pret prolongePret = pretMetier.prolongerPret((long)2);
				verify(pretRepository, times(1)).save(any(Pret.class));
			} catch (EntityNotFoundException e) {
				assertThat(e).isInstanceOf(EntityNotFoundException.class)
				 .hasMessage("Aucun prêt enregistré ne correspond à votre demande");
			} catch (BookNotAvailableException e) {
				assertThat(e).isInstanceOf(BookNotAvailableException.class)
				 .hasMessage("Le statut de ce pret de livre ne permet pas sa prolongation");
			} catch (WrongNumberException e) {
				assertThat(e).isInstanceOf(WrongNumberException.class)
				 .hasMessage("La date limite pour prolonger votre prêt est dépassée");
			}
	}	
	
	@Test
	public void testCloturerPret_whenEntityNotFoundException() {
		
		Mockito.when(pretRepository.findById((long)1)).thenReturn(Optional.ofNullable(null));
		
		try {
			Pret cloturePret = pretMetier.cloturerPret((long)1);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
			 .hasMessage("Aucun prêt enregistré ne correspond à votre demande");
		}
		
	}
	
	@Test
	public void testcloturerPret_withoutException_whenBookNotAvailableException() {
		
		Categorie categorie1 = new Categorie((long) 1,"Categorie1");
    	User user1 = new User((long)1, "user1");
		Livre livre1 = new Livre((long) 1,"titre1", "auteur1", 3, 0,categorie1);
		Pret pret1User1Livre1 = new Pret(LocalDate.now().minusDays(10), LocalDate.now().minusDays(5), PretStatut.CLOTURE, user1, livre1 );
		Mockito.when(pretRepository.findById((long)1)).thenReturn(Optional.of(pret1User1Livre1));
			
					try {
						Pret cloturePret = pretMetier.cloturerPret((long)1);
						verify(pretRepository, times(1)).save(any(Pret.class));
					} catch (Exception e) {
						assertThat(e).isInstanceOf(BookNotAvailableException.class)
						 .hasMessage("Le statut de ce pret de livre ne permet pas sa clôture");
					}
	}	
	
	
	@Test
	public void testcloturerPret_withoutException_withStatutEncours() {
		
		Categorie categorie1 = new Categorie((long) 1,"Categorie1");
    	User user1 = new User((long)1, "user1");
		Livre livre1 = new Livre((long) 1,"titre1", "auteur1", 3, 0,categorie1);
		Pret pret1User1Livre1 = new Pret(LocalDate.now().minusDays(10), LocalDate.now().plusDays(18), PretStatut.ENCOURS, user1, livre1 );
		Mockito.when(pretRepository.findById((long)1)).thenReturn(Optional.of(pret1User1Livre1));
		Mockito.when(pretRepository.save(any(Pret.class))).thenReturn(pret1User1Livre1);
				
					try {
						Pret cloturePret = pretMetier.cloturerPret((long)1);
						verify(pretRepository, times(1)).save(any(Pret.class));
					} catch (EntityNotFoundException | BookNotAvailableException e) {
						
					}
		
	}	
	
	@Test
	public void testcloturerPret_withoutException_withStatutAEchoir() {
		
		Categorie categorie1 = new Categorie((long) 1,"Categorie1");
    	User user1 = new User((long)1, "user1");
		Livre livre1 = new Livre((long) 1,"titre1", "auteur1", 3, 0,categorie1);
		Pret pret1User1Livre1 = new Pret(LocalDate.now().minusDays(10), LocalDate.now().plusDays(4), PretStatut.AECHOIR, user1, livre1 );
		Mockito.when(pretRepository.findById((long)1)).thenReturn(Optional.of(pret1User1Livre1));
		Mockito.when(pretRepository.save(any(Pret.class))).thenReturn(pret1User1Livre1);
				
					try {
						Pret cloturePret = pretMetier.cloturerPret((long)1);
						verify(pretRepository, times(1)).save(any(Pret.class));
					} catch (EntityNotFoundException | BookNotAvailableException e) {
						
					}
		
	}	
	
	@Test
	public void testcloturerPret_withoutException_withStatutEchu() {
		
		Categorie categorie1 = new Categorie((long) 1,"Categorie1");
    	User user1 = new User((long)1, "user1");
		Livre livre1 = new Livre((long) 1,"titre1", "auteur1", 3, 0,categorie1);
		Pret pret1User1Livre1 = new Pret(LocalDate.now().minusDays(10), LocalDate.now().minusDays(4), PretStatut.ECHU, user1, livre1 );
		Mockito.when(pretRepository.findById((long)1)).thenReturn(Optional.of(pret1User1Livre1));
		Mockito.when(pretRepository.save(any(Pret.class))).thenReturn(pret1User1Livre1);
				
					try {
						Pret cloturePret = pretMetier.cloturerPret((long)1);
						verify(pretRepository, times(1)).save(any(Pret.class));
					} catch (EntityNotFoundException | BookNotAvailableException e) {
						
					}
		
	}	
	
	@Test
	public void testcloturerPret_withoutException_withStatutProlonge() {
		
		Categorie categorie1 = new Categorie((long) 1,"Categorie1");
    	User user1 = new User((long)1, "user1");
		Livre livre1 = new Livre((long) 1,"titre1", "auteur1", 3, 0,categorie1);
		Pret pret1User1Livre1 = new Pret(LocalDate.now().minusDays(10), LocalDate.now().plusDays(36), PretStatut.PROLONGE, user1, livre1 );
		Mockito.when(pretRepository.findById((long)1)).thenReturn(Optional.of(pret1User1Livre1));
		Mockito.when(pretRepository.save(any(Pret.class))).thenReturn(pret1User1Livre1);
				
						try {
							Pret cloturePret = pretMetier.cloturerPret((long)1);
							verify(pretRepository, times(1)).save(any(Pret.class));
						} catch (EntityNotFoundException | BookNotAvailableException e) {
							
						}
	}	
	
	
	@Test
	public void testSearchByCriteria() {
		
		PretCriteria pretCriteria = new PretCriteria();
		Pageable pageable = PageRequest.of(0,6);
		
		Page<Pret> pretPageTest = pretMetier.searchByCriteria(pretCriteria, pageable);
		verify(pretRepository, times(1)).findAll(any(PretSpecification.class), any(Pageable.class));
		
	}
	
	@Test
	public void testSearchAndUpdatePretsEchus_returnEmptyList() {
		
		List<Pret> listPretsEchus = Arrays.asList();
		
		Mockito.when(pretRepository.findAllByOtherPretStatutAndDateEcheanceBeforeThisDate(
				PretStatut.CLOTURE, 
				LocalDate.now())).thenReturn(Optional.ofNullable(null)); 
		
		List<Pret> returnedList = pretMetier.searchAndUpdatePretsEchus();
		Assert.assertTrue(returnedList.isEmpty());
		verify(pretRepository, times(1)).findAllByOtherPretStatutAndDateEcheanceBeforeThisDate(PretStatut.CLOTURE, LocalDate.now());	
	
	}
	
	@Test
	public void testSearchAndUpdatePretsEchus_withNotEmptyList() {
		
		Categorie categorie1 = new Categorie((long) 1,"Categorie1");
    	User user1 = new User((long)1, "user1");
    	User user2 = new User((long)2, "user2");
    	User user3 = new User((long)3, "user3");
		Livre livre1 = new Livre((long) 1,"titre1", "auteur1", 3, 0,categorie1);
		Pret pret1User1Livre1 = new Pret(LocalDate.now().minusDays(10), LocalDate.now().minusDays(4), PretStatut.AECHOIR, user1, livre1 );
		Pret pret2User2Livre1 = new Pret(LocalDate.now().minusDays(10), LocalDate.now().minusDays(6), PretStatut.ENCOURS, user2, livre1 );
		Pret pret3User3Livre1 = new Pret(LocalDate.now().minusDays(50), LocalDate.now().minusDays(10), PretStatut.PROLONGE, user3, livre1 );
		List<Pret> listPretsEchus = Arrays.asList(pret1User1Livre1,pret2User2Livre1, pret3User3Livre1);
		
		Mockito.when(pretRepository.findById((long)1)).thenReturn(Optional.ofNullable(pret1User1Livre1));
		Mockito.when(pretRepository.findById((long)2)).thenReturn(Optional.ofNullable(pret2User2Livre1 ));
		Mockito.when(pretRepository.findById((long)3)).thenReturn(Optional.ofNullable(pret3User3Livre1));
		
		Mockito.when(pretRepository.findAllByOtherPretStatutAndDateEcheanceBeforeThisDate(
				PretStatut.CLOTURE, 
				LocalDate.now())).thenReturn(Optional.of(listPretsEchus)); 
		
		List<Pret> returnedList = pretMetier.searchAndUpdatePretsEchus();
		Assert.assertTrue(returnedList.equals(listPretsEchus));
		verify(pretRepository, times(1)).findAllByOtherPretStatutAndDateEcheanceBeforeThisDate(PretStatut.CLOTURE, LocalDate.now());	
	
	}
	
	
	@Test
	public void testSearchAndUpdatePretsAEchoir_whenOnlyPretsAEchoir() {
		
		Categorie categorie1 = new Categorie((long) 1,"Categorie1");
    	User user1 = new User((long)1, "user1");
		Livre livre1 = new Livre((long) 1,"titre1", "auteur1", 1, 0,categorie1);
		Pret pret1User1Livre1 = new Pret(LocalDate.now().minusDays(10), LocalDate.now().plusDays(1), PretStatut.AECHOIR, user1, livre1 );
		
		List<Pret> listPretsAlreadyAEchoir = Arrays.asList(pret1User1Livre1);
		
		Mockito.when(pretRepository.findAllByPretStatut(PretStatut.AECHOIR)).thenReturn(Optional.ofNullable(listPretsAlreadyAEchoir));
		
		List<Pret> returnedList = pretMetier.searchAndUpdatePretsAEchoir();
		verify(pretRepository, times(1)).findAllByPretStatut(PretStatut.AECHOIR);
		Assert.assertTrue(returnedList.equals(listPretsAlreadyAEchoir));
		
	}
	
	
	@Test
	public void testSearchAndUpdatePretsAEchoir_whenOnlyPretsEncours() {
		
		Categorie categorie1 = new Categorie((long) 1,"Categorie1");
    	User user2 = new User((long)2, "user2");
    	User user3 = new User((long)3, "user3");
		Livre livre1 = new Livre((long) 1,"titre1", "auteur1", 3, 0,categorie1);
		Pret pret2User2Livre1 = new Pret(LocalDate.now().minusDays(10), LocalDate.now().plusDays(2), PretStatut.ENCOURS, user2, livre1 );
		
		List<Pret> listPretsProlongesOuEncoursAEchoir = new ArrayList<Pret>();
		listPretsProlongesOuEncoursAEchoir.add(pret2User2Livre1);
		
	
		Mockito.when(appProperties.getDureeAEchoir()).thenReturn(3);
		Mockito.when(pretRepository.findById((long)2)).thenReturn(Optional.ofNullable(pret2User2Livre1 ));
		Mockito.when(pretRepository.save(any(Pret.class))).thenReturn(pret2User2Livre1);
		
		Mockito.when(pretRepository.findAllByPretStatutAndDateEcheanceAfterThisDate(
				PretStatut.PROLONGE, 
				PretStatut.ENCOURS,
				LocalDate.now())).thenReturn(Optional.of(listPretsProlongesOuEncoursAEchoir)); 
		
		List<Pret> returnedList = pretMetier.searchAndUpdatePretsAEchoir();
		Assert.assertTrue(returnedList.equals(listPretsProlongesOuEncoursAEchoir));
		verify(pretRepository, times(1)).findAllByPretStatutAndDateEcheanceAfterThisDate(
				PretStatut.PROLONGE, 
				PretStatut.ENCOURS,
				LocalDate.now());
		
	}
	
	
	@Test
	public void testSearchAndUpdatePretsAEchoir_whenOnlyPretsProlonges() {
		
		Categorie categorie1 = new Categorie((long) 1,"Categorie1");
    	User user2 = new User((long)2, "user2");
    	User user3 = new User((long)3, "user3");
		Livre livre1 = new Livre((long) 1,"titre1", "auteur1", 3, 0,categorie1);
		Pret pret3User3Livre1 = new Pret(LocalDate.now().minusDays(50), LocalDate.now().plusDays(2), PretStatut.PROLONGE, user3, livre1 );
		
		List<Pret> listPretsProlongesOuEncoursAEchoir = new ArrayList<Pret>();
		listPretsProlongesOuEncoursAEchoir.add(pret3User3Livre1);
		
	
		Mockito.when(appProperties.getDureeAEchoir()).thenReturn(3);
		Mockito.when(pretRepository.findById((long)3)).thenReturn(Optional.ofNullable(pret3User3Livre1));
		Mockito.when(pretRepository.save(any(Pret.class))).thenReturn(pret3User3Livre1);
		
		Mockito.when(pretRepository.findAllByPretStatutAndDateEcheanceAfterThisDate(
				PretStatut.PROLONGE, 
				PretStatut.ENCOURS,
				LocalDate.now())).thenReturn(Optional.of(listPretsProlongesOuEncoursAEchoir)); 
		
		List<Pret> returnedList = pretMetier.searchAndUpdatePretsAEchoir();
		Assert.assertTrue(returnedList.equals(listPretsProlongesOuEncoursAEchoir));
		verify(pretRepository, times(1)).findAllByPretStatutAndDateEcheanceAfterThisDate(
				PretStatut.PROLONGE, 
				PretStatut.ENCOURS,
				LocalDate.now());
		
	}
	
	@Test
	public void testReadPret_whenEntityNotFoundException() {
		
		Mockito.when(pretRepository.findById((long)0)).thenReturn(Optional.ofNullable(null));
		
		try {
			pretMetier.readPret((long)0);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
			 .hasMessage("Aucun prêt enregistré ne correspond à votre demande");
		}
		
	}
	
	@Test
	public void testReadPret_withoutException() {
		
		Categorie categorie1 = new Categorie((long) 1,"Categorie1");
    	User user2 = new User((long)2, "user2");
		Livre livre1 = new Livre((long) 1,"titre1", "auteur1", 1, 0,categorie1);
		Pret pret2User2Livre1 = new Pret(LocalDate.now().minusDays(10), LocalDate.now().plusDays(2), PretStatut.ENCOURS, user2, livre1 );
		pret2User2Livre1.setNumPret((long)2);
		
		Mockito.when(pretRepository.findById((long)2)).thenReturn(Optional.ofNullable(pret2User2Livre1));
		
		try {
			Pret readPret = pretMetier.readPret((long)2);
			verify(pretRepository, times(1)).findById((long)2);
			Assert.assertTrue(readPret.equals(pret2User2Livre1));
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
			 .hasMessage("Aucun prêt enregistré ne correspond à votre demande");
		}
	}
	
	
	
}
	
	
	
