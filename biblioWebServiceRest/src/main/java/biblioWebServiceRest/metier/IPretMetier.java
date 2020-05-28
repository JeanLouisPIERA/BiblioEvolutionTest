package biblioWebServiceRest.metier;

import java.util.List;

import biblioWebServiceRest.criteria.PretCriteria;
import biblioWebServiceRest.entities.Pret;

public interface IPretMetier {
		
	/**
	 * CRUD : CREATE Créer le prêt de l'exemplaire disponible d'un livre
	 * @param titre
	 * @param username
	 * @return
	 * @throws Exception
	 */
	Pret createPret(String titre, String username) throws Exception;
	
	/**
	 * CRUD : UPDATE prolonger la durée d'un prêt encours ou échu 
	 * @param numPret
	 * @return
	 */
	Pret prolongerPret(Long numPret)throws Exception;

	/**
	 * CRUD : UPDATE clôturer un prêt 
	 * @param numPret
	 * @return
	 * @throws Exception
	 */
	Pret cloturerPret(Long numPret) throws Exception;
	

// AFFICHER LES PRETS ENCOURS ***************************************************************************
	
	 /**
	  * AFFICHER LES PRETS : recherche les prêts par PretCriteria
	  * @param pretCriteria
	  * @return
	  */
	 List<Pret> searchByCriteria(PretCriteria pretCriteria);
	 
		
		
}
