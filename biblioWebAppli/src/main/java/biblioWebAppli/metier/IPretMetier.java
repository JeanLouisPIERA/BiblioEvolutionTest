/**
 * 
 */
package biblioWebAppli.metier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import biblioWebAppli.criteria.PretCriteria;
import biblioWebAppli.objets.Pret;




/**
 * @author jeanl
 *
 */
public interface IPretMetier {
	
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
