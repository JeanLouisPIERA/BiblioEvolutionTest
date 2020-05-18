package biblioWebServiceRest.metier;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import biblioWebServiceRest.entities.Categorie;
import biblioWebServiceRest.entities.Livre;
import biblioWebServiceRest.entities.LivreStatut;



public interface ILivreMetier {

	/**
	 * Méthode pour sélectionner les livres par titre, auteur ou catégorie
	 * @param titre
	 * @param auteur
	 * @param categorie
	 * @return
	 */
	List<Livre> searchByTitreAndAuteurAndCategorie(String titre, String auteur, Categorie categorie);
	
	/**
	 * Méthode pour sélectionner les livres disponibles par titre, nombre d'exemplaires et nombre d'exemplaires disponibles
	 * @param titre
	 * @param livreStatut
	 * @param nbExemplaires
	 * @param nbExemplairesDisponibles
	 * @return
	 */
	List<Livre> searchByTitreAndStatutDisponibleAndNbExemplairesAndNbExemplairesDisponibles(String titre,
			LivreStatut livreStatut, Integer nbExemplaires, Integer nbExemplairesDisponibles);
	
	/**
	 * Méthode pour sélectionner les livres disponibles par titre, auteur, catégorie, nombre d'exemplaires et nombre d'exemplaires disponibles
	 * @param titre
	 * @param auteur
	 * @param categorie
	 * @param livreStatut
	 * @param nbExemplaires
	 * @param nbExemplairesDisponibles
	 * @return
	 */
	public List<Livre> searchLivresDisponiblesByAttributesAndNbExemplaires(String titre, String auteur, 
			Categorie categorie, LivreStatut livreStatut, Integer nbExemplaires, 
			Integer nbExemplairesDisponibles);
}