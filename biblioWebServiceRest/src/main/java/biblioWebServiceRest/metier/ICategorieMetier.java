package biblioWebServiceRest.metier;

import java.util.List;

import biblioWebServiceRest.entities.Categorie;

public interface ICategorieMetier {
	
	
	/**
	 * Méthode pour sélectionner toutes les catégories
	 * @return
	 */
	public List<Categorie> searchAllCategories();
	
	/**
	 * Méthode pour sélection les catégories par leur nom
	 * @param nomCategorie
	 * @return
	 */
	public Categorie searchByNomCategorie(String nomCategorie);

}


