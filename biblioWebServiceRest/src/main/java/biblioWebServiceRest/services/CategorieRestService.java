/**
 * Classe qui gère les requêtes URI sur les catégories de livres en référencement (création, consultation 
 * et suppression)
 */
package biblioWebServiceRest.services;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import biblioWebServiceRest.criteria.CategorieCriteria;
import biblioWebServiceRest.dto.CategorieDTO;
import biblioWebServiceRest.entities.Categorie;
import biblioWebServiceRest.mapper.CategorieMapper;
import biblioWebServiceRest.metier.ICategorieMetier;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author jeanl
 *
 */


@RestController
@Api(value="Gestion des categories de livres en referencement dans la Bibliotheque")
public class CategorieRestService {
	
	@Autowired
	private ICategorieMetier categorieMetier;
	@Autowired
	private CategorieMapper categorieMapper;
	

	/**
	 * Méthode pour identifier toutes les categories de livres en referencement 
	 * @param categorieCriteria
	 * @return
	 * @see biblioWebServiceRest.metier.ICategorieMetier#searchByCriteria(biblioWebServiceRest.criteria.CategorieCriteria)
	 */
	@ApiOperation(value = "Identification des categories de livres en referencement", response = Categorie.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "La recherche a été réalisée avec succés"),
	        @ApiResponse(code = 201, message = "Code erreur non utilisé"),
	        @ApiResponse(code = 401, message = "Pas d'autorisation pour accéder à cette ressource"),
	        @ApiResponse(code = 403, message = "Accès interdit à cette ressource "),
	        @ApiResponse(code = 404, message = "Ressource inexistante"),
	        @ApiResponse(code = 500, message = "Erreur interne au Serveur")
	})
	@GetMapping(value="/categories", produces = "application/json")
	public Page<Categorie> searchByCriteria(@PathParam("searched by") CategorieCriteria categorieCriteria, @RequestParam int page, @RequestParam int size) {

		List<CategorieDTO> categorieDTOs = categorieMetier.searchByCriteria(categorieCriteria);
		List<Categorie> categories = categorieMapper.categorieDTOsToCategories(categorieDTOs);
		int end = (page + size > categories.size() ? categories.size() : (page + size));
		Page<Categorie> categoriesByPage = new PageImpl<Categorie>(categories.subList(page, end));		
		return categoriesByPage;
	}


	/**
	 * Methode pour creer une nouvelle categorie de livres
	 * @param nomCategorie
	 * @return
	 * @throws Exception
	 * @see biblioWebServiceRest.metier.ICategorieMetier#createCategorie(java.lang.String)
	 */
	@ApiOperation(value = "Enregistrement d'une nouvelle categorie de livres en refencement", response = Categorie.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 201, message = "La reference de cette categorie a été creee"),
	        @ApiResponse(code = 401, message = "Pas d'autorisation pour accéder à cette ressource"),
	        @ApiResponse(code = 403, message = "Accès interdit à cette ressource "),
	        @ApiResponse(code = 404, message = "Ressource inexistante"),
	        @ApiResponse(code = 500, message = "Erreur interne au Serveur")
	})
	@PostMapping(value="/categories/{nomCategorie}/creation", produces = "application/json")
	public ResponseEntity<Categorie> createCategorie(@PathVariable String nomCategorie) throws Exception {
		CategorieDTO newCategorieDTO = categorieMetier.createCategorie(nomCategorie);
		Categorie newCategorie = categorieMapper.categorieDTOToCategorie(newCategorieDTO);
		return new ResponseEntity<Categorie>(newCategorie, HttpStatus.CREATED);
	}
	

	/**
	 * Méthode pour supprimer une catégorie 
	 * La méthode retourne HttpStatus OK si la suppression a été effectuée
	 * La méthode envoie une InternalServerException si la réference de la categorie à supprimer contient au moins un livre
	 * La méthode envoie une NotFoundException si la réference n'existe pas
	 * @param numCategorie
	 * @throws Exception
	 * @return ResponseEntity HttpStatus OK
	 * @see biblioWebServiceRest.metier.ICategorieMetier#deleteCategorie(java.lang.Long)
	 */
	@ApiOperation(value = "Suppression d'une référence de categorie de livres en refencement", response = Categorie.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 201, message = "Code erreur non utilisé"),
	        @ApiResponse(code = 401, message = "Pas d'autorisation pour accéder à cette ressource"),
	        @ApiResponse(code = 403, message = "Accès interdit à cette ressource "),
	        @ApiResponse(code = 404, message = "Ressource inexistante"),
	        @ApiResponse(code = 500, message = "Erreur interne au Serveur")
	})
	@DeleteMapping(value="/categories/{numCategorie}/suppression", produces = "application/text")
	public ResponseEntity<String> deleteCategorie(Long numCategorie) throws Exception {
		categorieMetier.deleteCategorie(numCategorie);
		return new ResponseEntity<String>("La categorie de cette reference a ete supprimee", HttpStatus.OK);
		
	}
	
	
	
	

}
