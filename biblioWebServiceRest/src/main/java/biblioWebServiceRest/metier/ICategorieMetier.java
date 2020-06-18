/**
 * Interface de définition des méthodes Métier pour l'entité Catégorie
 */
package biblioWebServiceRest.metier;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import biblioWebServiceRest.criteria.CategorieCriteria;
import biblioWebServiceRest.dto.CategorieDTO;
import biblioWebServiceRest.entities.Categorie;
import biblioWebServiceRest.exceptions.EntityAlreadyExistsException;
import biblioWebServiceRest.exceptions.EntityNotDeletableException;
import biblioWebServiceRest.exceptions.EntityNotFoundException;


public interface ICategorieMetier {
	
	
	
	/**
	 * Méthode pour identifier toutes les categories de livres en referencement 
	 * @return
	 */
	public Page<Categorie> searchByCriteria(CategorieCriteria categorieCriteria, Pageable pageable);
	
	
	
	/**
	 * Methode pour creer une nouvelle categorie 
	 * @param nomCategorie
	 * @return
	 * @throws Exception
	 */
	public Categorie createCategorie(CategorieDTO categorieDTO) throws EntityAlreadyExistsException;
	
	
	/**
	 * Methode pour supprimer une categorie
	 * @param categorieDTO
	 * @throws EntityNotFoundException
	 * @throws EntityNotDeletableException
	 */
	public void deleteCategorie(Long numCategorie) throws EntityNotFoundException, EntityNotDeletableException; 
	
}


