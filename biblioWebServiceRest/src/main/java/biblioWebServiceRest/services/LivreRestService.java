package biblioWebServiceRest.services;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import biblioWebServiceRest.criteria.LivreCriteria;
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
	 * @param livreCriteria
	 * @param page
	 * @param size
	 * @return
	 * @see biblioWebServiceRest.metier.ILivreMetier#searchByCriteria(biblioWebServiceRest.criteria.LivreCriteria, int, int)
	 */
	@GetMapping(value="/livres")
	public Page<Livre> searchByCriteria(@PathParam("searched by") LivreCriteria livreCriteria, @RequestParam int page, @RequestParam int size) {
		return livreMetier.searchByCriteria(livreCriteria, page, size);
	}

	

	
	
	
}
