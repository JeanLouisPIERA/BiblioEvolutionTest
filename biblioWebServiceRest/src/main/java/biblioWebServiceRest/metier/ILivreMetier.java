package biblioWebServiceRest.metier;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;

import biblioWebServiceRest.criteria.LivreCriteria;
import biblioWebServiceRest.entities.Livre;




public interface ILivreMetier {
	
		
	/**
	 * Méthode pour sélectionner les livres par le titre, le nom de l'auteur et la catégorie
	 * @param titre
	 * @param auteur
	 * @param categorie
	 * @return
	 */
	/**
	List<Livre> searchByTitreAndAuteurAndCategorie(String titre, String auteur, String nomCategorie);
	**/
	
	/**
	 * 
	 * @param livreCriteria
	 * @param page
	 * @param size
	 * @return
	 */
	Page<Livre> searchByCriteria(@RequestBody LivreCriteria livreCriteria, int page, int size); 
}