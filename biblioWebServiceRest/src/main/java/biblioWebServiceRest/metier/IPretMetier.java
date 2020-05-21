package biblioWebServiceRest.metier;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import biblioWebServiceRest.entities.Pret;
import biblioWebServiceRest.entities.PretStatut;
import biblioWebServiceRest.entities.User;

public interface IPretMetier {
	
	/**
	 * CRUD : CREATE Créer le prêt de l'exemplaire disponible d'un livre
	 * @param pret
	 * @return
	 */

	Pret createPret(String titre, String username);
	

	/**
	 * CRUD : READ consulter un prêt 
	 * @param numPret
	 * @return
	 */
	/**
	Pret readPret(long numPret);
	**/
	 
	/**
	 * CRUD : READ consulter un prêt 
	 * @param titre
	 * @param username
	 * @param datePret
	 * @return
	 */
	Pret readPret(String titre, String username, String datePret);
	

	/**
	 * CRUD : UPDATE prolonger la durée d'un prêt encours ou échu 
	 * @param pret
	 * @return
	 */
	Pret prolongerPret(Pret pret);
	

	/**
	 * CRUD : UPDATE clôturer un prêt 
	 * @return
	 */
	Pret cloturerPret(Pret pret);
	

// AFFICHER LES PRETS ENCOURS ***************************************************************************
	
	
		/**
		 * Afficher tous les prêts
		 * @return
		 */
		List<Pret> searchAllPrets();
		
		/**
		 * Afficher et paginer tous les prêts
		 * @param pageable
		 * @return
		 */
		
		Page<Pret> searchAllPrets(Pageable pageable);
		
		
	
		/**
		 * Sélectionner les prêts par leur date de retour prévue, leur statut et l'emprunteur
		 * @param pretStatut
		 * @param user
		 * @return
		 */
		List<Pret> searchPretsByStatutEncoursAndByUser(LocalDate dateRetour, PretStatut pretStatut, User user);
		
		Page<Pret> displayPretsByDateRetourAndStatutAndByUser(LocalDate dateRetour, PretStatut pretStatut, User user, Pageable pageable);
		
	
		
		
}
