package biblioWebServiceRest.metier;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import biblioWebServiceRest.entities.Livre;
import biblioWebServiceRest.entities.Pret;
import biblioWebServiceRest.entities.PretStatut;
import biblioWebServiceRest.entities.User;

public interface IPretMetier {
	
/*
 * Gérer les prêts CRUD : créer un nouveau prêt	
 */
	Pret createPret(Pret pret);
	
/*
 * Gérer les prêts CRUD : consulter un prêt 
 * A l'issue statut du prêt = pretStatut.ENCOURS
 */
	
	Pret readPret(long numPret);
	
/*
 * Gérer les prêts CRUD : update pour prolonger la durée d'un prêt 
 * A l'entrée : pretStatut.ENCOURS ou pretStatut.ECHU
 * A l'issue : pretStatut.POLONGE
 */

	Pret prolongerPret(Pret pret);
	
/*
 * Gérer les prêts CRUD : update pour clôturer un prêt 
 * A l'entrée : pretStatutENCOURS ou pretStatutECHU ou pretStatutPROLONGE 
 * A l'issue : pretStatut.CLOTURE
 */
	
	Pret cloturerPret(Pret pret);
	
/*
 * Afficher la liste de tous les prets 
 */
	
	List<Pret> displayAllPrets();
	
	Page<Pret> displayAllPrets(Pageable pageable);
	
// AFFICHER LES PRETS ENCOURS ***************************************************************************
	
	/*
	 * Afficher la liste de tous les prêts ENCOURS
	 */
		
		List<Pret> displayPretsByStatutEncours(PretStatut pretStatut);
		
		Page<Pret> displayPretsByStatutEncours(PretStatut pretStatut, Pageable pageable);
		
	/*
	 * Afficher la liste de tous les prêts ENCOURS par Utilisateur
	 */
		
		List<Pret> displayPretsByStatutEncoursAndByUser(PretStatut pretStatut, User user);
		
		Page<Pret> displayPretsByStatutEncoursAndByUser(PretStatut pretStatut, User user, Pageable pageable);
		
	/*
	 * Afficher la liste de tous les prêts ENCOURS pour un Ouvrage
	 */
		
		List<Pret> displayPretsByStatutEncoursAndByTitreLivre(PretStatut pretStatut, Livre livre);
		
		Page<Pret> displayPretsByStatutEncoursAndByTitreLivre(PretStatut pretStatut, Livre livre, Pageable pageable);
		
// AFFICHER LES PRETS PROLONGES ***************************************************************************
	
	/*
	 * Afficher la liste de tous les prêts PROLONGES
	 */
		
		List<Pret> displayPretsByStatutProlonge(PretStatut pretStatut);
		
		Page<Pret> displayPretsByStatutProlonge(PretStatut pretStatut, Pageable pageable);
		
	/*
	 * Afficher la liste de tous les prêts PROLONGE par Utilisateur
	 */
		
		List<Pret> displayPretsByStatutProlongeAndByUser(PretStatut pretStatut, User user);
		
		Page<Pret> displayPretsByStatutProlongeAndByUser(PretStatut pretStatut, User user, Pageable pageable);
		
	/*
	 * Afficher la liste de tous les prêts PROLONGE pour un Ouvrage
	 */
		
		List<Pret> displayPretsByStatutProlongeAndByTitreLivre(PretStatut pretStatut, Livre livre);
		
		Page<Pret> displayPretsByStatutProlongeAndByTitreLivre(PretStatut pretStatut, Livre livre, Pageable pageable);

		
// AFFICHER LES PRETS ECHUS NON RENDUS ***************************************************************************
		
	/*
	 * Afficher la liste de tous les prêts ECHUS NON RENDUS
	 */
		
		List<Pret> displayPretsByStatutEchu(PretStatut pretStatut);
		
		Page<Pret> displayPretsByStatutEchu(PretStatut pretStatut, Pageable pageable);
		
	/*
	 * Afficher la liste de tous les prêts PROLONGE par Utilisateur
	 */
		
		List<Pret> displayPretsByStatutEchuAndByUser(PretStatut pretStatut, User user);
		
		Page<Pret> displayPretsByStatutEchuAndByUser(PretStatut pretStatut, User user, Pageable pageable);
		
	/*
	 * Afficher la liste de tous les prêts PROLONGE pour un Ouvrage
	 */
		
		List<Pret> displayPretsByStatutEchuAndByTitreLivre(PretStatut pretStatut, Livre livre);
		
		Page<Pret> displayPretsByStatutEchuAndByTitreLivre(PretStatut pretStatut, Livre livre, Pageable pageable);
		
		
}
