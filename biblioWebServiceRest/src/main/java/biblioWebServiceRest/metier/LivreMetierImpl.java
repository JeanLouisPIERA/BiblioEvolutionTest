/**
  * Classe d'implémentation des méthodes Métier pour l'entité Livre
 */

package biblioWebServiceRest.metier;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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
import biblioWebServiceRest.exceptions.EntityAlreadyExistsException;
import biblioWebServiceRest.exceptions.EntityNotDeletableException;
import biblioWebServiceRest.exceptions.EntityNotFoundException;
import biblioWebServiceRest.exceptions.WrongNumberException;
import biblioWebServiceRest.mapper.LivreMapper;

@Service
@Transactional
public class LivreMetierImpl implements ILivreMetier{
	
	
	@Autowired
	private ILivreRepository livreRepository;
	@Autowired
	private ICategorieRepository categorieRepository; 
	@Autowired
	private IPretRepository pretRepository;
	@Autowired
	private LivreMapper livreMapper;
	@Autowired
	private IReservationRepository reservationRepository;
	
	
	// TICKET 1 FONCTIONNALITE
	// MISE A JOUR DU DATE DE RETOUR LA PLUS PROCHE D'UN EXEMPLAIRE D'UN LIVRE ET DU NOMBRE D'UTILISATEURS AYANT RESERVE CE LIVRE 
	@Override
	public void miseAJourLivres() {
	//TICKET 1 Fonctionnalité : extraction de tous les livres éligibles à la réservation
			Optional<List<Livre>> livresList = livreRepository.findAllByNbExemplairesDisponibles(0);
			//TICKET 1 Fonctionnalité  : on recherche la date de retour de prêt la plus proche et on la met à jour   
			if(livresList.isPresent()) {
				for(Livre livre : livresList.get()) {
					Optional<List<Pret>> pretsListe = pretRepository.findAllByLivreAndNotPretStatutOrderByDateRetourPrevueAfterThisDate(
							livre, 
							PretStatut.CLOTURE, 
							LocalDate.now());
					if(pretsListe.isPresent()) {
						Pret pretDateRetourPlusProche = pretsListe.get().get(0);
						livre.setDateRetourPrevuePlusProche(pretDateRetourPlusProche.getDateRetourPrevue().toString());
					}else {
						livre.setDateRetourPrevuePlusProche("Aucune date de retour ne peut être indiquée");
					}
			
					 
					//TICKET 1 Fonctionnalité 1 WebAppli : on identifie le nombre de réservations en cours
					// le nombre d'utilisateurs ayant une réservation en cours
					// le rang de la réservation dans la liste des réservations d'un livre
					Optional<List<Reservation>> reservations = reservationRepository.findAllByLivreAndReservationStatutOrReservationStatut(livre, ReservationStatut.ENREGISTREE, ReservationStatut.NOTIFIEE);
						if(reservations.isPresent()) {
							livre.setNbReservationsEnCours(reservations.get().size());
							}
						else {
							livre.setNbReservationsEnCours(0);
						}
					Optional<List<Reservation>> reservationsByUser = reservationRepository.findAllByLivreGroupByUserAndReservationStatutOrReservationStatut(livre, ReservationStatut.ENREGISTREE, ReservationStatut.NOTIFIEE);
						if(reservationsByUser.isPresent()) {
							livre.setNbReservataires(reservationsByUser.get().size());
							System.out.println(livre.getNbReservataires());}
						else {
							livre.setNbReservataires(0);
						}
						
					
					livreRepository.save(livre);
				}
			}
	}
			
	
	
	
	
	/**
	 * Recherche multicritères des livres enregistrés
	 * @param livreCriteria
	 * @return
	 */
	@Override
	public Page<Livre> searchByLivreCriteria(LivreCriteria livreCriteria, Pageable pageable) {
		// TICKET 1 FONCTIONNALITE DE MISE A JOURS DES LIVRES SUR DATE DE RETOUR LA PLUS PROCHE ET TAILLE DE LA LISTE D'ATTENTE
		this.miseAJourLivres();
		//Specification<Livre> livreSpecification = new LivreSpecification(livreCriteria);
		Page<Livre> livres = livreRepository.findAll(new LivreSpecification(livreCriteria), pageable);
		return livres;
	}

	/**
	 * Creation d'un nouveau livre à referencer 
	 * La méthode envoie une exception si une réference existe déjà avec le même titre et le même auteur quelle que soit sa categorie
	 * La méthode envoie une exception si la catégorie dans laquelle doit être enregistré le livre a creer n'existe pas
	 * La méthode envoie une exception si le nombre total d'exemplaires est négatif
	 * Pour les références qui doivent enregistrer plusieurs tomes d'un même titre, il faut enregistrer le numéro du tome dans le titre (exemple Tome 1)
	 * @param livreDTO
	 * @return
	 * @throws EntityAlreadyExistsException,  
	 * @throws EntityNotFoundException 
	 * @throws Exception
	 */
	@Override
	public Livre createLivre(LivreDTO livreDTO) throws EntityAlreadyExistsException, EntityNotFoundException {		
		
		Optional<Categorie> categorie = categorieRepository.findById(livreDTO.getNumCategorie());
		if(!categorie.isPresent()) 
			throw new EntityNotFoundException("Le livre ne peut pas etre enregistre car la categorie saisie n'existe pas");
		
		Optional<Livre> livreAlreadyCreated = livreRepository.findByTitreAndAuteur(livreDTO.getTitre(), livreDTO.getAuteur());
		if(livreAlreadyCreated.isPresent()) 
			throw new EntityAlreadyExistsException("Ce livre a déjà été référencé");
		
		Livre livreToCreate = livreMapper.livreDTOToLivre(livreDTO); 
		
		livreToCreate.setCategorie(categorie.get());
		livreToCreate.setNbExemplairesDisponibles(livreDTO.getNbExemplaires());
		
		return livreRepository.save(livreToCreate);
		
	}

	/**
	 * Mise à jour des attributs d'un livre déjà referencé 
	 * La méthode envoie une exception si une réference existe déjà avec le même titre et le même auteur quelle que soit sa categorie
	 * La méthode envoie une exception si la catégorie dans laquelle doit être enregistré le livre a creer n'existe pas
	 * La méthode envoie une exception si le nombre total d'exemplaires est négatif ou si le nombre total d'exemplaires est inférieur au nombre total d'exemplaires en cours de pret
	 * Pour les références qui doivent enregistrer plusieurs tomes d'un même titre, il faut enregistrer le numéro du tome dans le titre (exemple Tome 1)
	 * @param numLivre
	 * @param livreDTO
	 * @return
	 * @throws EntityNotFoundException 
	 * @throws EntityAlreadyExistsException 
	 * @throws WrongNumberException 
	 */
	@Override
	public Livre updateLivre(Long numLivre, LivreDTO livreDTO) throws EntityNotFoundException, EntityAlreadyExistsException, WrongNumberException {
		Optional<Livre> livreToUpdate = livreRepository.findById(numLivre);
		if(!livreToUpdate.isPresent()) 
			throw new EntityNotFoundException("Le livre à mettre à jour n'existe pas");
		
		if(livreDTO.getTitre()!=null && livreDTO.getAuteur()!=null) {
		Optional<Livre> livreAfterUpdate = livreRepository.findByTitreAndAuteur(livreDTO.getTitre(), livreDTO.getAuteur()); 
		if(livreAfterUpdate.isPresent()) {
			throw new EntityAlreadyExistsException("Ce livre a déjà été référencé");
		}else {
			//Livre livreUpdates = livreMapper.livreDTOToLivre(livreDTO); 
			livreToUpdate.get().setTitre(livreDTO.getTitre());
			livreToUpdate.get().setAuteur(livreDTO.getAuteur());
		}
		}
		
		if(livreDTO.getNumCategorie()!=null) {
		Optional<Categorie> categorie = categorieRepository.findById(livreDTO.getNumCategorie());
		if(!categorie.isPresent()) 
			throw new EntityNotFoundException("Le changement de categorie est impossible car la categorie saisie n'existe pas");
		livreToUpdate.get().setCategorie(categorie.get());
		}		
		/*
		 * La mise à jour du nombre total d'exemplaires d'une référence de livre est inférieure au nombre des exemplaires
		 * disponibles avant mise à jour soulève une exception car il y a un problème de gestion : on supprime un livre 
		 * qui est encore en circulation. Dans ce cas on rend impossible la gestion des encours de livres disponibles après mise
		 * à jour		
		 */
		Integer nbExemplairesIndisponibles = livreToUpdate.get().getNbExemplaires()-livreToUpdate.get().getNbExemplairesDisponibles();
		if(livreDTO.getNbExemplaires()<0) 
			throw new WrongNumberException("Le nombre total d'exemplaires de la référence de livre à mettre à jour doit au moins être égale à 0");
		if((livreDTO.getNbExemplaires()-nbExemplairesIndisponibles)<0)
			throw new WrongNumberException("Le nombre total d'exemplaires ne peut pas être inférieur au nombre de livres actuellement en cours de prêt"); 
		livreToUpdate.get().setNbExemplaires(livreDTO.getNbExemplaires());
		livreToUpdate.get().setNbExemplairesDisponibles(livreDTO.getNbExemplaires()-nbExemplairesIndisponibles);
		
		return livreRepository.save(livreToUpdate.get());
	}

	/**
	 * @throws EntityNotDeletableException 
	 * @throws EntityNotFoundException 
	 * Suppression d'un ou plusieurs exemplaires pour une reference de livre déjà enregistrée
	 * La méthode envoie une exception si la référence du livre à supprimer n'existe pas
	 * La méthode envoie une exception s'il existe encore des prets encours 
	 * Pour simplifier l'envoi de l'exception, on compare le nombre de livres disponibles et le nombre total de livres référencés
	 * Si le nombre de livres disponibles est inférieur au nombre total de livres, c'est qu'il existe encore des prêts en cours
	 * @param numLivre
	 * @exception
	 */
	@Override
	public void deleteLivre(Long numLivre) throws EntityNotFoundException, EntityNotDeletableException{
		Optional<Livre> livreToDelete = livreRepository.findById(numLivre);
		if(!livreToDelete.isPresent()) 
			throw new EntityNotFoundException("Le livre que vous voulez supprimer n'existe pas"); 
		/*
		 * Comme les prets clotures ne sont pas supprimés, le seul moyen de s'assurer qu'il n'existe pas de pret encours pour un 
		 * livre à supprimer est de vérifier que le nombre total d'exemplaires est égal au nombre d'exemplaires disponibles
		 */
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
		
		Optional<List<Reservation>> reservationsSupprimeesOuAnnuleesOuLivrees = reservationRepository.findAllByLivreAndReservationStatutAndReservationStatutAndReservationStatut(
				livreToDelete.get(),
				ReservationStatut.ANNULEE,
				ReservationStatut.SUPPRIMEE, 
				ReservationStatut.LIVREE);
		if(reservationsSupprimeesOuAnnuleesOuLivrees.isPresent()) {
			for(Reservation reservation : reservationsSupprimeesOuAnnuleesOuLivrees.get()) {
				reservationRepository.deleteById(reservation.getNumReservation());
			}
		}
		livreRepository.deleteById(numLivre);
		
	}


}
	
	


	

	



