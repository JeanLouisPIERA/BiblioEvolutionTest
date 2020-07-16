/**
 * Interface définissant les méthodes métier de l'appli Web 
 */
package biblioWebAppli.metier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import biblioWebAppli.criteria.LivreCriteria;
import biblioWebAppli.dto.LivreDTO;
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
	
	
	/**
	 * Permet d'enregistrer un nouveau livre
	 * @param livreDTO
	 * @return
	 * @throws EntityNotFoundException 
	 * @throws EntityAlreadyExistsException 
	 * @throws Exception 
	 */
	public Livre createLivre(LivreDTO livreDTO);
	
	/**
	 * Mise à jour des attributs d'un livre déjà référencé
	 * @param numLivre
	 * @param livreDTO
	 * @return
	 */
	public Livre updateLivre(Livre livre); 
	
	
	/**
	 * Permet de supprimer une référence de livre enregistré
	 * @param numLivre
	 * @throws EntityNotDeletableException 
	 * @throws EntityNotFoundException 
	 */
	public String delete(Long numLivre);
}
