package biblioWebServiceRest.metier;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import biblioWebServiceRest.entities.Livre;
import biblioWebServiceRest.entities.LivreStatut;

public interface ILivreMetier {


/*
 * Méthode pour la Lecture de l'enregistrement d'un Livre
 */
	Livre readLivre(long numLivre);


/*
 * Méthode pour afficher la liste de tous les livres
 */
	List<Livre> displayAllLivres();
	
	Page<Livre> displayPagesAllLivres(Pageable pageable);
	
/*
 * Méthode pour afficher la liste de tous les exemplaires d'un même livre
 */
	
	List<Livre> displayByTitre(String titre);
	
	Page<Livre> displayByTitre(String titre, Pageable pageable);
	
/*
 * Méthode pour afficher la liste de tous les exemplaires disponible d'un même livre
 */
	
	List<Livre> displayByTitreAndLivreStatut(String Titre, LivreStatut livreStatut);
	
	Page<Livre> displayByTitreAndLivreStatut(String Titre, LivreStatut livreStatut, Pageable pageable);
	
/*
 * Méthode pour compter le nombre d'exemplaires d'un même livre
 */
	
	long countByTitre(String titre);
	
/*
 * Méthode pour compter le nombre d'exemplaires disponibles d'un même livre
 */
	
	long countByTitreAndByLivreStatut(String Titre, @Param("statut")LivreStatut livreStatut);
	
	
}