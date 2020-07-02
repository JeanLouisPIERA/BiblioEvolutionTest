/**
 * Interface définissant les méthodes métier de l'appli Web 
 */
package biblioWebAppli.metier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import biblioWebAppli.criteria.CategorieCriteria;
import biblioWebAppli.dto.CategorieDTO;
import biblioWebAppli.entities.Categorie;






/**
 * @author jeanl
 *
 */
public interface ICategorieMetier {
		
	/**
	 * Permet d'afficher les catégories existantes de livre
	 * @param categorieCriteria
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Categorie> searchByCriteria(CategorieCriteria categorieCriteria, int page, int size);
	
	
	/**
	 * Permet de créer une nouvelle catégorie de livre
	 * @param categorieDTO
	 * @return
	 */
	public Categorie createCategorie(CategorieDTO categorieDTO);
	
	/**
	 * Permet de supprimer une categorie de livre
	 * @param numCategorie
	 */
	public void delete(Long numCategorie);
}
