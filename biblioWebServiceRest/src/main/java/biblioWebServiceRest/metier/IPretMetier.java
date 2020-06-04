/**
 * Interface de définition des méthodes Métier pour l'entité Pret
 */
package biblioWebServiceRest.metier;

import java.util.List;

import biblioWebServiceRest.criteria.PretCriteria;
import biblioWebServiceRest.dto.PretCriteriaDTO;
import biblioWebServiceRest.dto.PretDTO;
import biblioWebServiceRest.entities.Pret;

public interface IPretMetier {
		
	/**
	 * CRUD : CREATE Créer le prêt de l'exemplaire disponible d'un livre
	 * @param titre
	 * @param username
	 * @return
	 * @throws Exception
	 */
	PretDTO createPret(Long numLivre, Long idUser) throws Exception;
	
	/**
	 * CRUD : UPDATE prolonger la durée d'un prêt encours ou échu 
	 * @param numPret
	 * @return
	 */
	PretDTO prolongerPret(Long numPret)throws Exception;

	/**
	 * CRUD : UPDATE clôturer un prêt 
	 * @param numPret
	 * @return
	 * @throws Exception
	 */
	PretDTO cloturerPret(Long numPret) throws Exception;
	

// AFFICHER LES PRETS ENCOURS ***************************************************************************
	
	 /**
	  * AFFICHER LES PRETS : recherche les prêts par PretCriteria
	  * @param pretCriteria
	  * @return
	  */
	 List<PretDTO> searchByCriteria(PretCriteriaDTO pretCriteriaDTO);
	 
		
		
}
