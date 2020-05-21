package biblioWebServiceRest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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
	 * Si aucun paramètre n'est renseigné, la méthode renvoie la liste de tous les livres enregistrés dans la base
	 * Le titre et le nom de l'auteur doivent simplement matcher
	 * Le nom de la catégorie doit être égal sinon la méthode Méthode catche une exception
	 * @param titre
	 * @param auteur
	 * @param nomCategorie
	 * @return
	 * @see biblioWebServiceRest.metier.ILivreMetier#searchByTitreAndAuteurAndCategorie(java.lang.String, java.lang.String, java.lang.String)
	 */
	@GetMapping(value="/livres")	
	public List<Livre> searchByTitreAndAuteurAndCategorie(@RequestParam String titre, @RequestParam String nomCategorie, @RequestParam String auteur)  {
		return livreMetier.searchByTitreAndAuteurAndCategorie(titre, auteur, nomCategorie);
	}
	
}
