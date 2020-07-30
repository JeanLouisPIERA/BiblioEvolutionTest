/**
 * Interface définissant les méthodes métier de l'appli Web 
 */
package biblioWebAppli.metier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import biblioWebAppli.criteria.LivreCriteria;
import biblioWebAppli.objets.Livre;

/**
 * @author jeanl
 *
 */
public interface ILivreMetier {
		
	
	/**
	 * Permet d'afficher les livres enregistrés dans l'API
	 * @param livreCriteria
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Livre> searchByCriteria(LivreCriteria livreCriteria, Pageable pageable);
	
	
	
}
