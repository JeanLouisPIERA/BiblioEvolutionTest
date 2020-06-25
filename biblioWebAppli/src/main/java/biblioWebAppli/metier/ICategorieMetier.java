/**
 * Interface définissant les méthodes métier de l'appli Web 
 */
package biblioWebAppli.metier;

import org.springframework.data.domain.Page;

import biblioWebAppli.criteria.CategorieCriteria;
import biblioWebAppli.dto.CategorieDTO;

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
	public Page<CategorieDTO> searchByCriteria (CategorieCriteria categorieCriteria, int page, int size);
	
	
	/**
	 * Permet de créer une nouvelle catégorie de livre
	 * @param categorieDTO
	 * @return
	 */
	public CategorieDTO createCategorie(CategorieDTO categorieDTO);
	
	/**
	 * Permet de supprimer une categorie de livre
	 * @param numCategorie
	 */
	public void delete(Long numCategorie);
}
