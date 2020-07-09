/**
 * Interface définissant les méthodes métier de l'appli Web 
 */
package biblioWebAppli.metier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import biblioWebAppli.criteria.LivreCriteria;
import biblioWebAppli.dto.LivreDTO;
import biblioWebAppli.entities.Livre;
import biblioWebAppli.exceptions.EntityAlreadyExistsException;
import biblioWebAppli.exceptions.EntityNotDeletableException;
import biblioWebAppli.exceptions.EntityNotFoundException;

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
	public Page<Livre> searchByCriteria(LivreCriteria livreCriteria, Pageable pageable, int page, int size);
	
	
	/**
	 * Permet d'enregistrer un nouveau livre
	 * @param livreDTO
	 * @return
	 * @throws EntityNotFoundException 
	 * @throws EntityAlreadyExistsException 
	 */
	public Livre createLivre(LivreDTO livreDTO) throws EntityNotFoundException, EntityAlreadyExistsException;
	
	/**
	 * Mise à jour des attributs d'un livre déjà référencé
	 * @param numLivre
	 * @param livreDTO
	 * @return
	 */
	public Livre updateLivre(Long numLivre, LivreDTO livreDTO); 
	
	
	/**
	 * Permet de supprimer une référence de livre enregistré
	 * @param numLivre
	 * @throws EntityNotDeletableException 
	 * @throws EntityNotFoundException 
	 */
	public void delete(Long numLivre) throws EntityNotFoundException, EntityNotDeletableException;
}
