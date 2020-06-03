package biblioWebServiceRest.metier;

import java.util.List;

import biblioWebServiceRest.criteria.CategorieCriteria;
import biblioWebServiceRest.entities.Categorie;

public interface ICategorieMetier {
	
	
	/**
	 * Méthode pour identifier toutes les categories de livres en referencement 
	 * @return
	 */
	public List<Categorie> searchByCriteria(CategorieCriteria categorieCriteria);
	
	
	
	/**
	 * Methode pour creer une nouvelle categorie 
	 * @param nomCategorie
	 * @return
	 * @throws Exception
	 */
	public Categorie createCategorie(String nomCategorie) throws Exception;
	
	
	/**
	 * Methode pour supprimer une categorie
	 * @param numCategorie
	 * @throws Exception
	 */
	public void deleteCategorie(Long numCategorie) throws Exception; 
	
}


