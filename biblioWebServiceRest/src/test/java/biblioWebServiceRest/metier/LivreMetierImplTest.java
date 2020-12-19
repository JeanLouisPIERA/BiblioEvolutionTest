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
import org.springframework.data.domain.PageImpl;
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
		
		Categorie categorie1 = new Categorie((long) 1,"Categorie1");
		Livre livre1 = new Livre((long) 1,"titre1", "auteur1", 1, 0,categorie1);
		Livre livre2 = new Livre((long) 2,"titre2", "auteur2", 1, 0,categorie1);
		
		List<Livre> livreList = Arrays.asList(livre1, livre2);
		
		Page<Livre> livrePage = new PageImpl<Livre>(livreList);
		
		Mockito.when(livreRepository.findAll(any(LivreSpecification.class), any(Pageable.class))).thenReturn(livrePage);
		
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
				LocalDate.now())).thenReturn(Optional.empty());

		when(reservationRepository.findAllByLivreAndReservationStatutOrReservationStatut(
				livre2, 
				ReservationStatut.ENREGISTREE, 
				ReservationStatut.NOTIFIEE)).thenReturn(Optional.empty());

		when(reservationRepository.findAllByLivreGroupByUserAndReservationStatutOrReservationStatut(
				livre2, 
				ReservationStatut.ENREGISTREE,
				ReservationStatut.NOTIFIEE)).thenReturn(Optional.empty());
		
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
		Assert.assertTrue(livre2.getDateRetourPrevuePlusProche().contentEquals("Aucune date de retour ne peut être indiquée"));
		Assert.assertTrue(livre2.getNbReservationsEnCours()==0);
		Assert.assertTrue(livre2.getNbReservataires()==0);
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
		Assert.assertTrue(livre1.getDateRetourPrevuePlusProche().equals(LocalDate.now().plusDays(18).toString()));
		Assert.assertTrue(livre1.getNbReservationsEnCours()==1);
		Assert.assertTrue(livre1.getNbReservataires()==1);
	}
	
	
	
	
	@Test
	public void testSearchByLivreCriteria() {
				
		LivreCriteria livreCriteria = new LivreCriteria();
		Pageable pageable = PageRequest.of(0,6);
		
		Page<Livre> livrePageTest = livreMetier.searchByLivreCriteria(livreCriteria, pageable);
		verify(livreRepository, times(1)).findAll(any(LivreSpecification.class), any(Pageable.class));
		Assertions.assertTrue(livrePageTest.getNumberOfElements()==2);
		Assertions.assertTrue(livrePageTest.getTotalPages()==1);
		Assertions.assertTrue(livrePageTest.getContent().get(0).getNumLivre()== (long)1);
		Assertions.assertTrue(livrePageTest.getContent().get(1).getNumLivre()==(long)2);
		
	}
	
	@Test
	public void testCreateLivre_whenEntityNotFoundException() {
		
		LivreDTO livreDTO1 = new LivreDTO();
		livreDTO1.setAuteur("auteur1");
		livreDTO1.setTitre("titre1");
		livreDTO1.setNbExemplaires(1);
		livreDTO1.setNumCategorie((long) 1);
		
		when(categorieRepository.findById((long)1)).thenReturn(Optional.empty());
		
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
		
		when(categorieRepository.findById((long)1)).thenReturn(Optional.of(categorie1));
		when(livreRepository.findByTitreAndAuteur("titre1", "auteur1")).thenReturn(Optional.of(livre1));
		
		try {
			Livre livreToCreate = livreMetier.createLivre(livreDTO1);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityAlreadyExistsException.class)
			 .hasMessage("Ce livre a déjà été référencé");
		}
	}
	
	@Test
	public void testCreateLivre_withoutException() throws Exception {
		
		Categorie categorie1 = new Categorie((long)1, "categorie1");
		LivreDTO livreDTO1 = new LivreDTO();
		livreDTO1.setAuteur("auteur1");
		livreDTO1.setTitre("titre1");
		livreDTO1.setNbExemplaires(1);
		livreDTO1.setNumCategorie((long) 1);
		Livre livre1 = new Livre ((long)1, "titre1", "auteur1", 1,0, categorie1);
		
		when(categorieRepository.findById((long)1)).thenReturn(Optional.of(categorie1));
		when(livreRepository.findByTitreAndAuteur("titre1", "auteur1")).thenReturn(Optional.empty());
		when(livreMapper.livreDTOToLivre(livreDTO1)).thenReturn(livre1);
		when(livreRepository.save(any(Livre.class))).thenReturn(livre1);
			
		Livre livreToCreate = livreMetier.createLivre(livreDTO1);
		verify(livreRepository, times(1)).save(any(Livre.class));
		Assert.assertTrue(livreToCreate.equals(livre1));
		
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
		
		when(livreRepository.findById((long)1)).thenReturn(Optional.of(livre1));
		when(livreRepository.findById((long)2)).thenReturn(Optional.of(livre2));
		when(livreRepository.findByTitreAndAuteur("titre2", "auteur2")).thenReturn(Optional.of(livre2));
		
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
		
		when(livreRepository.findById((long)1)).thenReturn(Optional.of(livre1));
		when(livreRepository.findByTitreAndAuteur("titre2", "auteur2")).thenReturn(Optional.empty());
		when(categorieRepository.findById((long) livreDTO2.getNumCategorie())).thenReturn(Optional.empty());
		
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
		
		when(livreRepository.findById((long)1)).thenReturn(Optional.of(livre1));
		when(livreRepository.findByTitreAndAuteur("titre2", "auteur2")).thenReturn(Optional.empty());
		when(categorieRepository.findById((long)1)).thenReturn(Optional.of(categorie1));
		when(livreMapper.livreDTOToLivre(livreDTO2)).thenReturn(livre2);
		
		try {
			livreMetier.updateLivre((long)1, livreDTO2);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(WrongNumberException.class)
			 .hasMessage("Le nombre total d'exemplaires de la référence de livre à mettre à jour doit au moins être égale à 0");
		}
	}
	
	@Test
	public void testMiseAJourLivre_whenNbExemlairesIsWrongNumberException() {
		
		Categorie categorie1 = new Categorie((long)1, "categorie1");
		LivreDTO livreDTO2 = new LivreDTO();
		livreDTO2.setAuteur("auteur2");
		livreDTO2.setTitre("titre2");
		livreDTO2.setNbExemplaires(1);
		livreDTO2.setNumCategorie((long) 1);
		Livre livre1 = new Livre ((long)1, "titre1", "auteur1", 3,1, categorie1);
		Livre livre1Update = new Livre ((long)1, "titre2", "auteur2", 1,1, categorie1);
		
		when(livreRepository.findById((long)1)).thenReturn(Optional.of(livre1));
		when(livreRepository.findByTitreAndAuteur("titre2", "auteur2")).thenReturn(Optional.empty());
		when(categorieRepository.findById((long)1)).thenReturn(Optional.of(categorie1));
		when(livreMapper.livreDTOToLivre(livreDTO2)).thenReturn(livre1Update);
		when(livreRepository.save(any(Livre.class))).thenReturn(livre1Update);

		try {
			Livre livreToUpdate = livreMetier.updateLivre((long)1, livreDTO2);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(WrongNumberException.class)
			 .hasMessage("Le nombre total d'exemplaires ne peut pas être inférieur au nombre de livres actuellement en cours de prêt"); 
		}
	}
	
	@Test
	public void testUpdateLivre_withoutException() throws Exception {
		
		Categorie categorie1 = new Categorie((long)1, "categorie1");
		LivreDTO livreDTO2 = new LivreDTO();
		livreDTO2.setAuteur("auteur2");
		livreDTO2.setTitre("titre2");
		livreDTO2.setNbExemplaires(4);
		livreDTO2.setNumCategorie((long) 1);
		Livre livre1 = new Livre ((long)1, "titre1", "auteur1", 3,3, categorie1);
		Livre livre1Update = new Livre ((long)1, "titre2", "auteur2", 4,3, categorie1);
		
		when(livreRepository.findById((long)1)).thenReturn(Optional.of(livre1));
		when(livreRepository.findByTitreAndAuteur("titre2", "auteur2")).thenReturn(Optional.empty());
		when(categorieRepository.findById((long)1)).thenReturn(Optional.of(categorie1));
		when(livreMapper.livreDTOToLivre(livreDTO2)).thenReturn(livre1Update);
		when(livreRepository.save(any(Livre.class))).thenReturn(livre1Update);

		Livre livreToUpdate = livreMetier.updateLivre((long)1, livreDTO2);
		verify(livreRepository, times(1)).save(any(Livre.class));
		Assert.assertTrue(livreToUpdate.equals(livre1Update));
		
	}
	
	@Test
	public void testDeleteLivre_whenEntityNotFoundException() {
		Categorie categorie1 = new Categorie((long)1, "categorie1");
		Livre livre1 = new Livre ((long)1, "titre1", "auteur1", 1,0, categorie1);
		
		when(livreRepository.findById((long)1)).thenReturn(Optional.empty());
		
		try {
			livreMetier.deleteLivre((long)1);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
			 .hasMessage("Le livre que vous voulez supprimer n'existe pas");
		}
	}
	
	/*
	@Override
	public void deleteLivre(Long numLivre) throws EntityNotFoundException, EntityNotDeletableException{
		Optional<Livre> livreToDelete = livreRepository.findById(numLivre);
		if(!livreToDelete.isPresent()) 
			throw new EntityNotFoundException("Le livre que vous voulez supprimer n'existe pas"); 
		
		 * Comme les prets clotures ne sont pas supprimés, le seul moyen de s'assurer qu'il n'existe pas de pret encours pour un 
		 * livre à supprimer est de vérifier que le nombre total d'exemplaires est égal au nombre d'exemplaires disponibles
		 
		Optional<List<Pret>> pretsNonClotures = pretRepository.findAllByLivreAndNotPretStatut(livreToDelete.get(), PretStatut.CLOTURE);
		Optional<List<Reservation>> reservationsNonSupprimeesAndNonAnnuleesAndNonLivrees = reservationRepository.findAllByLivreAndNotReservationStatutAndNotReservationStatutAndNotReservationStatut(livreToDelete.get(), ReservationStatut.ANNULEE, ReservationStatut.SUPPRIMEE, ReservationStatut.LIVREE);		
		if(pretsNonClotures.isPresent() || reservationsNonSupprimeesAndNonAnnuleesAndNonLivrees.isPresent()) 
			throw new EntityNotDeletableException("Vous ne pouvez pas supprimer ce livre qui a encore des prêts ou des réservations encours"); 
		
		Optional<List<Pret>> pretsClotures = pretRepository.findAllByLivreAndPretStatut(livreToDelete.get(), PretStatut.CLOTURE);
		if(pretsClotures.isPresent()) {
			for(Pret pret : pretsClotures.get()) {
				pretRepository.deleteById(pret.getNumPret());
			}
		}
		
		Optional<List<Reservation>> reservationsSupprimeesAndAnnulees = reservationRepository.findAllByLivreAndReservationStatutAndReservationStatutAndReservationStatut(
				livreToDelete.get(),
				ReservationStatut.ANNULEE,
				ReservationStatut.SUPPRIMEE, 
				ReservationStatut.LIVREE);
		if(reservationsSupprimeesAndAnnulees.isPresent()) {
			for(Reservation reservation : reservationsSupprimeesAndAnnulees.get()) {
				reservationRepository.deleteById(reservation.getNumReservation());
			}
		}
		livreRepository.deleteById(numLivre);
		
	}
*/
	
	
		
	@Test
	public void testDeleteLivre_whenEntityNotDeletableException_withPretsNonClotures() {
		Categorie categorie1 = new Categorie((long)1, "categorie1");
		Livre livre1 = new Livre ((long)1, "titre1", "auteur1", 1,0, categorie1);
		List<Pret> pretsNonCloturesList = new ArrayList<Pret>();
		when(livreRepository.findById((long)1)).thenReturn(Optional.of(livre1));
		when(pretRepository.findAllByLivreAndNotPretStatut(livre1, PretStatut.CLOTURE)).thenReturn(Optional.of(pretsNonCloturesList));
		
		try {
			livreMetier.deleteLivre((long)1);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotDeletableException.class)
			 .hasMessage("Vous ne pouvez pas supprimer ce livre qui a encore des prêts ou des réservations encours");
		}
	}
	
	@Test
	public void testDeleteLivre_whenEntityNotDeletableException_withReservationsNonSupprimeesOuNonAnnuleesOuNonLivrees() {
		Categorie categorie1 = new Categorie((long)1, "categorie1");
		Livre livre1 = new Livre ((long)1, "titre1", "auteur1", 1,0, categorie1);
		List<Reservation> reservationsNonSupprimeesAndNonAnnuleesAndNonLivrees = new ArrayList<Reservation>();
		when(livreRepository.findById((long)1)).thenReturn(Optional.of(livre1));
		when(reservationRepository.findAllByLivreAndNotReservationStatutAndNotReservationStatutAndNotReservationStatut(
				livre1, ReservationStatut.ANNULEE, ReservationStatut.SUPPRIMEE, ReservationStatut.LIVREE))
				.thenReturn(Optional.of(reservationsNonSupprimeesAndNonAnnuleesAndNonLivrees));

		try {
			livreMetier.deleteLivre((long)1);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotDeletableException.class)
			 .hasMessage("Vous ne pouvez pas supprimer ce livre qui a encore des prêts ou des réservations encours");
		}
	}
	
		
	@Test
	public void testDeleteLivre_withoutException() throws Exception {
		Categorie categorie1 = new Categorie((long)1, "categorie1");
		Livre livre1 = new Livre ((long)1, "titre1", "auteur1", 1,1, categorie1);
		
		List<Pret> pretsCloturesList = new ArrayList<Pret>();
		Pret pret1 = new Pret();
		pret1.setNumPret((long)1);
		pretsCloturesList.add(pret1);
		
		List<Reservation> reservationsSupprimmeesOuAnnuleesOuLivreesList = new ArrayList<Reservation>();
		Reservation reservation1 = new Reservation();
		reservation1.setNumReservation((long)1);
		reservationsSupprimmeesOuAnnuleesOuLivreesList.add(reservation1);
		
		when(livreRepository.findById((long)1)).thenReturn(Optional.of(livre1));
		when(pretRepository.findAllByLivreAndPretStatut(livre1, PretStatut.CLOTURE)).thenReturn(Optional.of(pretsCloturesList));
		when(reservationRepository.findAllByLivreAndReservationStatutAndReservationStatutAndReservationStatut(
				livre1,
				ReservationStatut.ANNULEE,
				ReservationStatut.SUPPRIMEE, 
				ReservationStatut.LIVREE))
				.thenReturn(Optional.of(reservationsSupprimmeesOuAnnuleesOuLivreesList));
		
		
		livreMetier.deleteLivre((long)1);
		verify(livreRepository, times(1)).deleteById((long)1);
			
	}
	
	
}
