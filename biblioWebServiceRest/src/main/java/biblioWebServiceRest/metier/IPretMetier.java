/**
 * Interface de définition des méthodes Métier pour l'entité Pret
 */
package biblioWebServiceRest.metier;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import biblioWebServiceRest.criteria.PretCriteria;
import biblioWebServiceRest.dto.PretCriteriaDTO;
import biblioWebServiceRest.dto.PretDTO;
import biblioWebServiceRest.entities.Pret;
import biblioWebServiceRest.exceptions.BadRequestException;
import biblioWebServiceRest.exceptions.BookNotAvailableException;
import biblioWebServiceRest.exceptions.EntityNotFoundException;

public interface IPretMetier {
		
	/**
	 * CRUD : CREATE Créer le prêt de l'exemplaire disponible d'un livre
	 * @param titre
	 * @param username
	 * @return
	 * @throws BadRequestException 
	 * @throws BookNotAvailableException 
	 * @throws EntityNotFoundException 
	 * @throws Exception
	 */
	Pret createPret(PretDTO pretDTO) throws BadRequestException, EntityNotFoundException, BookNotAvailableException;
	
	/**
	 * CRUD : UPDATE prolonger la durée d'un prêt encours ou échu 
	 * @param numPret
	 * @return
	 * @throws EntityNotFoundException 
	 * @throws BookNotAvailableException 
	 */
	Pret prolongerPret(Long numPret) throws EntityNotFoundException, BookNotAvailableException;

	/**
	 * CRUD : UPDATE clôturer un prêt 
	 * @param numPret
	 * @return
	 * @throws EntityNotFoundException 
	 * @throws Exception
	 */
	Pret cloturerPret(Long numPret) throws EntityNotFoundException;
	

// AFFICHER LES PRETS ENCOURS ***************************************************************************
	
	 /**
	  * AFFICHER LES PRETS : recherche les prêts par PretCriteria
	  * @param pretCriteria
	  * @return
	  */
	 Page<Pret> searchByCriteria(PretCriteria pretCriteria, Pageable pageable);
	 
		
		
}
