package biblioWebServiceRest.metier;

import java.util.List;


import biblioWebServiceRest.entities.Livre;




public interface ILivreMetier {
	
	/**
	 * Méthode pour sélectionner tous les livres
	 */
	List<Livre> searchAllLivres();

		
	/**
	 * Méthode pour sélectionner un livre par le titre, le nom de l'auteur et la catégorie
	 * @param titre
	 * @param auteur
	 * @param categorie
	 * @return
	 */
	List<Livre> searchByTitreAndAuteurAndCategorie(String titre, String auteur, String nomCategorie);
	
}