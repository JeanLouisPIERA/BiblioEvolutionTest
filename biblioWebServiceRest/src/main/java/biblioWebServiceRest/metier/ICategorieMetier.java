/**
 * Interface de définition des méthodes Métier pour l'entité Catégorie
 */
package biblioWebServiceRest.metier;

import java.util.List;

import biblioWebServiceRest.criteria.CategorieCriteria;
import biblioWebServiceRest.dto.CategorieDTO;


public interface ICategorieMetier {
	
	
	/**
	 * Méthode pour identifier toutes les categories de livres en referencement 
	 * @return
	 */
	public List<CategorieDTO> searchByCriteria(CategorieCriteria categorieCriteria);
	
	
	
	/**
	 * Methode pour creer une nouvelle categorie 
	 * @param nomCategorie
	 * @return
	 * @throws Exception
	 */
	public CategorieDTO createCategorie(String nomCategorie) throws Exception;
	
	
	/**
	 * Methode pour supprimer une categorie
	 * @param numCategorie
	 * @throws Exception
	 */
	public void deleteCategorie(Long numCategorie) throws Exception; 
	
}


