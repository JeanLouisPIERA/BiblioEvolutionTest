package biblioWebServiceRest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import biblioWebServiceRest.entities.Livre;
import biblioWebServiceRest.metier.ICategorieMetier;
import biblioWebServiceRest.metier.ILivreMetier;

@RestController
public class LivreRestService {
	@Autowired
	private ILivreMetier livreMetier;
	
	@Autowired
	private ICategorieMetier categorieMetier;

	/**
	 * Cette requête permet d'affihier tous les livres
	 * @return
	 */

	@GetMapping(value="/livres")	
	public List<Livre> searchAllLivres() {
		return livreMetier.searchAllLivres();
	}
	

	/**
	 * Cette méthode permet de faire une requête multicritères sur les livres 
	 * @param titre
	 * @param auteur
	 * @param nomCategorie
	 * @return
	 * @see biblioWebServiceRest.metier.ILivreMetier#searchByTitreAndAuteurAndCategorie(java.lang.String, java.lang.String, java.lang.String)
	 */
	@GetMapping(value="/livres/{titre}/{auteur}/{nomCategorie}")	
	public List<Livre> searchByTitreAndAuteurAndCategorie(@PathVariable String titre, @PathVariable String auteur, @PathVariable String nomCategorie) {
		return livreMetier.searchByTitreAndAuteurAndCategorie(titre, auteur, nomCategorie);
	}
	
}
