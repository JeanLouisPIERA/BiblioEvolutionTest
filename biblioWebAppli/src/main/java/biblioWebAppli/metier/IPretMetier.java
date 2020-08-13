/**
 * 
 */
package biblioWebAppli.metier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import biblioWebAppli.criteria.PretCriteria;
import biblioWebAppli.dto.PretDTO;
import biblioWebAppli.objets.Pret;



/**
 * @author jeanl
 *
 */
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
	Pret createPret(PretDTO pretDTO);
	
	/**
	 * CRUD : UPDATE prolonger la durée d'un prêt encours ou échu 
	 * @param numPret
	 * @return
	 * @throws EntityNotFoundException 
	 * @throws BookNotAvailableException 
	 */
	Pret prolongerPret(Long numPret);


// AFFICHER LES PRETS ENCOURS ***************************************************************************
	
	 /**
	  * AFFICHER LES PRETS : recherche les prêts par PretCriteria
	  * @param pretCriteria
	  * @return
	  */
	 Page<Pret> searchByCriteria(PretCriteria pretCriteria, Pageable pageable);
	 

}
