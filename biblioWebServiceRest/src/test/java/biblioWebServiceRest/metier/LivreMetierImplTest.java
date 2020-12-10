package biblioWebServiceRest.metier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import biblioWebServiceRest.criteria.CategorieCriteria;
import biblioWebServiceRest.criteria.LivreCriteria;
import biblioWebServiceRest.dao.ICategorieRepository;
import biblioWebServiceRest.dao.ILivreRepository;
import biblioWebServiceRest.dao.IPretRepository;
import biblioWebServiceRest.dao.IReservationRepository;
import biblioWebServiceRest.dao.specs.CategorieSpecification;
import biblioWebServiceRest.dao.specs.LivreSpecification;
import biblioWebServiceRest.dto.LivreDTO;
import biblioWebServiceRest.entities.Categorie;
import biblioWebServiceRest.entities.Livre;
import biblioWebServiceRest.entities.Pret;
import biblioWebServiceRest.entities.PretStatut;
import biblioWebServiceRest.entities.Reservation;
import biblioWebServiceRest.entities.ReservationStatut;
import biblioWebServiceRest.entities.User;
import biblioWebServiceRest.exceptions.EntityAlreadyExistsException;
import biblioWebServiceRest.exceptions.EntityNotDeletableException;
import biblioWebServiceRest.exceptions.EntityNotFoundException;
import biblioWebServiceRest.exceptions.WrongNumberException;
import biblioWebServiceRest.mapper.CategorieMapper;
import biblioWebServiceRest.mapper.LivreMapper;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LivreMetierImplTest  {
	
	@Mock
	private ICategorieRepository categorieRepository;
	
	@Mock
	private ILivreRepository livreRepository;
	
	@Mock
	private IPretRepository pretRepository;
	
	@Mock
	private IReservationRepository reservationRepository;
	
	@Mock
	private CategorieMapper categorieMapper;
	
	@Mock
	private LivreMapper livreMapper;
	
	@InjectMocks
	private LivreMetierImpl livreMetier;
	
	@Before 
	public void setup() {
		
		MockitoAnnotations.initMocks(this);
		
	}
	
	
	@Test
	public void testMiseAjourLivres_withoutDateRetourPlusProche_AndWithoutNbReservationsEnCours_AndWithoutNbREservataires() {
		
		Categorie categorie1 = new Categorie((long) 1,"Categorie1");
    	User user1 = new User((long)1, "user1");
		Livre livre2 = new Livre((long) 2,"titre2", "auteur2", 1, 0,categorie1);
		
		List<Livre> livreList2 = Arrays.asList(livre2);
		
		Mockito.when(livreRepository.findAllByNbExemplairesDisponibles(0)).thenReturn(Optional.of(livreList2));
		Mockito.when(livreRepository.save(any(Livre.class))).thenReturn(livre2);
		
		when(pretRepository.findAllByLivreAndNotPretStatutOrderByDateRetourPrevueAfterThisDate(
				livre2, 
				PretStatut.CLOTURE, 
				LocalDate.now())).thenReturn(Optional.ofNullable(null));

		when(reservationRepository.findAllByLivreAndReservationStatutOrReservationStatut(
				livre2, 
				ReservationStatut.ENREGISTREE, 
				ReservationStatut.NOTIFIEE)).thenReturn(Optional.ofNullable(null));

		when(reservationRepository.findAllByLivreGroupByUserAndReservationStatutOrReservationStatut(
				livre2, 
				ReservationStatut.ENREGISTREE,
				ReservationStatut.NOTIFIEE)).thenReturn(Optional.ofNullable(null));
		
		livreMetier.miseAJourLivres();
		
		verify(livreRepository, times(1)).findAllByNbExemplairesDisponibles(0);
		verify(pretRepository, times(1)).findAllByLivreAndNotPretStatutOrderByDateRetourPrevueAfterThisDate(
				any(Livre.class), 
				any(PretStatut.class), 
				any(LocalDate.class));
		verify(reservationRepository, times(1)).findAllByLivreAndReservationStatutOrReservationStatut(
				any(Livre.class), 
				any(ReservationStatut.class), 
				any(ReservationStatut.class));
		verify(reservationRepository, times(1)).findAllByLivreGroupByUserAndReservationStatutOrReservationStatut(
				any(Livre.class), 
				any(ReservationStatut.class), 
				any(ReservationStatut.class));
		verify(livreRepository).save(any(Livre.class));
	}
	
	@Test
	public void testMiseAjourLivres_OnEachItem() {
		
		Categorie categorie1 = new Categorie((long) 1,"Categorie1");
    	User user1 = new User((long)1, "user1");
    	User user2 = new User((long)2, "user2");
		Livre livre1 = new Livre((long) 1,"titre1", "auteur1", 1, 0,categorie1);
		
		List<Livre> livreList1 = Arrays.asList(livre1);
		
		List<Pret> pretListUser1Livre1 = new ArrayList<Pret>();
		Pret pret1User1Livre1 = new Pret(LocalDate.now().minusDays(10), LocalDate.now().plusDays(18), PretStatut.ENCOURS, user1, livre1 );
		pretListUser1Livre1.add(pret1User1Livre1);
		livre1.setPrets(pretListUser1Livre1);
		
		List<Reservation> reservationListLivre1 = new ArrayList<Reservation>();
		Reservation reservation1 = new Reservation((long)1, LocalDate.now(), null, null, null, ReservationStatut.ENREGISTREE, user2, livre1);
		reservationListLivre1.add(reservation1);
		livre1.setReservations(reservationListLivre1);
		
		Mockito.when(livreRepository.findAllByNbExemplairesDisponibles(0)).thenReturn(Optional.of(livreList1));
		Mockito.when(livreRepository.save(any(Livre.class))).thenReturn(livre1);
		when(pretRepository.findAllByLivreAndNotPretStatutOrderByDateRetourPrevueAfterThisDate(
				livre1, 
				PretStatut.CLOTURE, 
				LocalDate.now())).thenReturn(Optional.of(pretListUser1Livre1));

		when(reservationRepository.findAllByLivreAndReservationStatutOrReservationStatut(
				livre1, 
				ReservationStatut.ENREGISTREE, 
				ReservationStatut.NOTIFIEE)).thenReturn(Optional.of(reservationListLivre1));

		when(reservationRepository.findAllByLivreGroupByUserAndReservationStatutOrReservationStatut(
				livre1, 
				ReservationStatut.ENREGISTREE,
				ReservationStatut.NOTIFIEE)).thenReturn(Optional.of(reservationListLivre1));
		
		livreMetier.miseAJourLivres();
		
		verify(livreRepository, times(1)).findAllByNbExemplairesDisponibles(0);
		verify(pretRepository, times(1)).findAllByLivreAndNotPretStatutOrderByDateRetourPrevueAfterThisDate(
				any(Livre.class), 
				any(PretStatut.class), 
				any(LocalDate.class));
		verify(reservationRepository, times(1)).findAllByLivreAndReservationStatutOrReservationStatut(
				any(Livre.class), 
				any(ReservationStatut.class), 
				any(ReservationStatut.class));
		verify(reservationRepository, times(1)).findAllByLivreGroupByUserAndReservationStatutOrReservationStatut(
				any(Livre.class), 
				any(ReservationStatut.class), 
				any(ReservationStatut.class));
		verify(livreRepository).save(any(Livre.class));
	}
	
	@Test
	public void testSearchByLivreCriteria() {
		
		LivreCriteria livreCriteria = new LivreCriteria();
		Pageable pageable = PageRequest.of(0,6);
		
		Page<Livre> livrePageTest = livreMetier.searchByLivreCriteria(livreCriteria, pageable);
		verify(livreRepository, times(1)).findAll(any(LivreSpecification.class), any(Pageable.class));
		
	}
	
	@Test
	public void testCreateLivre_whenEntityNotFoundException() {
		
		LivreDTO livreDTO1 = new LivreDTO();
		livreDTO1.setAuteur("auteur1");
		livreDTO1.setTitre("titre1");
		livreDTO1.setNbExemplaires(1);
		livreDTO1.setNumCategorie((long) 1);
		
		when(categorieRepository.findById((long)1)).thenReturn(Optional.ofNullable(null));
		
		try {
			Livre livreToCreate = livreMetier.createLivre(livreDTO1);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
			 .hasMessage("Le livre ne peut pas etre enregistre car la categorie saisie n'existe pas");
		}
		
	}
	
	@Test
	public void testCreateLivre_whenEntityAlreadyExistsException() {
		
		Categorie categorie1 = new Categorie((long)1, "categorie1");
		LivreDTO livreDTO1 = new LivreDTO();
		livreDTO1.setAuteur("auteur1");
		livreDTO1.setTitre("titre1");
		livreDTO1.setNbExemplaires(1);
		livreDTO1.setNumCategorie((long) 1);
		Livre livre1 = new Livre ((long)1, "titre1", "auteur1", 1,0, categorie1);
		
		when(categorieRepository.findById((long)1)).thenReturn(Optional.ofNullable(categorie1));
		when(livreRepository.findByTitreAndAuteur("titre1", "auteur1")).thenReturn(Optional.of(livre1));
		
		try {
			Livre livreToCreate = livreMetier.createLivre(livreDTO1);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityAlreadyExistsException.class)
			 .hasMessage("Ce livre a déjà été référencé");
		}
	}
	
	@Test
	public void testCreateLivre_withoutException() {
		
		Categorie categorie1 = new Categorie((long)1, "categorie1");
		LivreDTO livreDTO1 = new LivreDTO();
		livreDTO1.setAuteur("auteur1");
		livreDTO1.setTitre("titre1");
		livreDTO1.setNbExemplaires(1);
		livreDTO1.setNumCategorie((long) 1);
		Livre livre1 = new Livre ((long)1, "titre1", "auteur1", 1,0, categorie1);
		
		when(categorieRepository.findById((long)1)).thenReturn(Optional.ofNullable(categorie1));
		when(livreRepository.findByTitreAndAuteur("titre1", "auteur1")).thenReturn(Optional.ofNullable(null));
		when(livreMapper.livreDTOToLivre(livreDTO1)).thenReturn(livre1);
		when(livreRepository.save(any(Livre.class))).thenReturn(livre1);
		
			
			try {
				Livre livreToCreate = livreMetier.createLivre(livreDTO1);
				verify(livreRepository, times(1)).save(any(Livre.class));
			} catch (EntityAlreadyExistsException e) {
				assertThat(e).isInstanceOf(EntityNotFoundException.class)
				 .hasMessage("Le livre ne peut pas etre enregistre car la categorie saisie n'existe pas");
			} catch (EntityNotFoundException e) {
				assertThat(e).isInstanceOf(EntityAlreadyExistsException.class)
				 .hasMessage("Ce livre a déjà été référencé");
			}
		
	}
	
	@Test
	public void testUpdateLivre_whenUnknownIdAndEntityNotFoundException() {
		Categorie categorie1 = new Categorie((long)1, "categorie1");
		LivreDTO livreDTO2 = new LivreDTO();
		livreDTO2.setAuteur("auteur2");
		livreDTO2.setTitre("titre2");
		livreDTO2.setNbExemplaires(2);
		livreDTO2.setNumCategorie((long) 2);
		Livre livre1 = new Livre ((long)1, "titre1", "auteur1", 1,0, categorie1);
		
		when(livreRepository.findById((long)1)).thenReturn(Optional.ofNullable(null));
		
		try {
			livreMetier.updateLivre((long)1, livreDTO2);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
			 .hasMessage("Le livre à mettre à jour n'existe pas");
		}
	}
		
	@Test
	public void testUpdateLivre_whenEntityAlreadyExistsException() {
		
		Categorie categorie1 = new Categorie((long)1, "categorie1");
		LivreDTO livreDTO2 = new LivreDTO();
		livreDTO2.setAuteur("auteur2");
		livreDTO2.setTitre("titre2");
		livreDTO2.setNbExemplaires(1);
		livreDTO2.setNumCategorie((long) 1);
		Livre livre1 = new Livre ((long)1, "titre1", "auteur1", 1,0, categorie1);
		Livre livre2 = new Livre ((long)2, "titre2", "auteur2", 1,0, categorie1);
		
		when(livreRepository.findById((long)1)).thenReturn(Optional.ofNullable(livre1));
		when(livreRepository.findById((long)2)).thenReturn(Optional.ofNullable(livre2));
		when(livreRepository.findByTitreAndAuteur("titre2", "auteur2")).thenReturn(Optional.ofNullable(livre2));
		
		try {
			livreMetier.updateLivre((long)1, livreDTO2);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityAlreadyExistsException.class)
			 .hasMessage("Ce livre a déjà été référencé");
		}
	}
		
	@Test
	public void testUpdateLivre_whenUnknownCategoryAndEntityNotFoundException() {
		
		Categorie categorie1 = new Categorie((long)1, "categorie1");
		LivreDTO livreDTO2 = new LivreDTO();
		livreDTO2.setAuteur("auteur2");
		livreDTO2.setTitre("titre2");
		livreDTO2.setNbExemplaires(1);
		livreDTO2.setNumCategorie((long) 2);
		Livre livre1 = new Livre ((long)1, "titre1", "auteur1", 1,0, categorie1);
		
		when(livreRepository.findById((long)1)).thenReturn(Optional.ofNullable(livre1));
		when(livreRepository.findByTitreAndAuteur("titre2", "auteur2")).thenReturn(Optional.ofNullable(null));
		when(categorieRepository.findById((long)2)).thenReturn(Optional.ofNullable(null));
		
		try {
			livreMetier.updateLivre((long)1, livreDTO2);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
			 .hasMessage("Le changement de categorie est impossible car la categorie saisie n'existe pas");
		}
	}
	
	@Test
	public void testUpdateLivre_whenNbExemplairesLessZeroAndWrongNumberException() {
		
		Categorie categorie1 = new Categorie((long)1, "categorie1");
		LivreDTO livreDTO2 = new LivreDTO();
		livreDTO2.setAuteur("auteur2");
		livreDTO2.setTitre("titre2");
		livreDTO2.setNbExemplaires(-1);
		livreDTO2.setNumCategorie((long) 1);
		Livre livre1 = new Livre ((long)1, "titre1", "auteur1", 1,0, categorie1);
		Livre livre2 = new Livre ((long)2, "titre2", "auteur2", -1,0, categorie1);
		
		when(livreRepository.findById((long)1)).thenReturn(Optional.ofNullable(livre1));
		when(livreRepository.findByTitreAndAuteur("titre2", "auteur2")).thenReturn(Optional.ofNullable(null));
		when(categorieRepository.findById((long)1)).thenReturn(Optional.ofNullable(categorie1));
		when(livreMapper.livreDTOToLivre(livreDTO2)).thenReturn(livre2);
		
		try {
			livreMetier.updateLivre((long)1, livreDTO2);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(WrongNumberException.class)
			 .hasMessage("Le nombre total d'exemplaires de la référence de livre à mettre à jour doit au moins être égale à 0");
		}
	}
	
	@Test
	public void testUpdateLivre_whenNbExemplairesUpdatedLessThanNbExemplairesDisponiblesAndWrongNumberException() {
		
		Categorie categorie1 = new Categorie((long)1, "categorie1");
		LivreDTO livreDTO2 = new LivreDTO();
		livreDTO2.setAuteur("auteur2");
		livreDTO2.setTitre("titre2");
		livreDTO2.setNbExemplaires(1);
		livreDTO2.setNumCategorie((long) 1);
		Livre livre1 = new Livre ((long)1, "titre1", "auteur1", 3,3, categorie1);
		Livre livre2 = new Livre ((long)2, "titre2", "auteur2", 1,3, categorie1);
		
		when(livreRepository.findById((long)1)).thenReturn(Optional.ofNullable(livre1));
		when(livreRepository.findByTitreAndAuteur("titre2", "auteur2")).thenReturn(Optional.ofNullable(null));
		when(categorieRepository.findById((long)1)).thenReturn(Optional.ofNullable(categorie1));
		when(livreMapper.livreDTOToLivre(livreDTO2)).thenReturn(livre2);
		
		try {
			livreMetier.updateLivre((long)1, livreDTO2);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(WrongNumberException.class)
			 .hasMessage("Le nombre total d'exemplaires ne peut pas être inférieur au nombre de livres actuellement en cours de prêt"); 
		}
	}
	
	@Test
	public void testUpdateLivre_withoutException() {
		
		Categorie categorie1 = new Categorie((long)1, "categorie1");
		LivreDTO livreDTO2 = new LivreDTO();
		livreDTO2.setAuteur("auteur2");
		livreDTO2.setTitre("titre2");
		livreDTO2.setNbExemplaires(4);
		livreDTO2.setNumCategorie((long) 1);
		Livre livre1 = new Livre ((long)1, "titre1", "auteur1", 3,3, categorie1);
		Livre livre2 = new Livre ((long)2, "titre2", "auteur2", 4,3, categorie1);
		
		when(livreRepository.findById((long)1)).thenReturn(Optional.ofNullable(livre1));
		when(livreRepository.findByTitreAndAuteur("titre2", "auteur2")).thenReturn(Optional.ofNullable(null));
		when(categorieRepository.findById((long)1)).thenReturn(Optional.ofNullable(categorie1));
		when(livreMapper.livreDTOToLivre(livreDTO2)).thenReturn(livre2);
		when(livreRepository.save(any(Livre.class))).thenReturn(any(Livre.class));
		
		try {
			Livre livreToUpdate = livreMetier.updateLivre((long)1, livreDTO2);
			verify(livreRepository, times(1)).save(any(Livre.class));
		} catch (EntityNotFoundException e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class);
		} catch (EntityAlreadyExistsException e) {
			assertThat(e).isInstanceOf(EntityAlreadyExistsException.class);
		} catch (WrongNumberException e) {
			assertThat(e).isInstanceOf(WrongNumberException.class);
		}
	}
	
	@Test
	public void testDeleteLivre_whenEntityNotFoundException() {
		Categorie categorie1 = new Categorie((long)1, "categorie1");
		Livre livre1 = new Livre ((long)1, "titre1", "auteur1", 1,0, categorie1);
		
		when(livreRepository.findById((long)1)).thenReturn(Optional.ofNullable(null));
		
		try {
			livreMetier.deleteLivre((long)1);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
			 .hasMessage("Le livre que vous voulez supprimer n'existe pas");
		}
	}
		
	@Test
	public void testDeleteLivre_whenEntityNotDeletableException() {
		Categorie categorie1 = new Categorie((long)1, "categorie1");
		Livre livre1 = new Livre ((long)1, "titre1", "auteur1", 1,0, categorie1);
		
		when(livreRepository.findById((long)1)).thenReturn(Optional.ofNullable(livre1));
		
		try {
			livreMetier.deleteLivre((long)1);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotDeletableException.class)
			 .hasMessage("Vous ne pouvez pas supprimer ce livre qui a encore des prêts encours");
		}
	}
	
	@Test
	public void testDeleteLivre_withoutException() {
		Categorie categorie1 = new Categorie((long)1, "categorie1");
		Livre livre1 = new Livre ((long)1, "titre1", "auteur1", 1,1, categorie1);
		
		when(livreRepository.findById((long)1)).thenReturn(Optional.ofNullable(livre1));
	
			try {
				livreMetier.deleteLivre((long)1);
				verify(livreRepository, times(1)).deleteById((long)1);
			} catch (EntityNotFoundException e) {
				assertThat(e).isInstanceOf(EntityNotFoundException.class)
				 .hasMessage("Le livre que vous voulez supprimer n'existe pas");
			} catch (EntityNotDeletableException e) {
				assertThat(e).isInstanceOf(EntityNotDeletableException.class)
				 .hasMessage("Vous ne pouvez pas supprimer ce livre qui a encore des prêts encours");
			}
		
	}
	
	
}
