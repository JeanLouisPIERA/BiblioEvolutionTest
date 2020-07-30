/**
 * Classe qui gère les requêtes URI sur les catégories de livres en référencement (création, consultation 
 * et suppression)
 */
package biblioWebServiceRest.services;


import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import biblioWebServiceRest.criteria.CategorieCriteria;
import biblioWebServiceRest.dto.CategorieDTO;
import biblioWebServiceRest.entities.Categorie;
import biblioWebServiceRest.exceptions.EntityAlreadyExistsException;
import biblioWebServiceRest.exceptions.EntityNotDeletableException;
import biblioWebServiceRest.exceptions.EntityNotFoundException;
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
@RequestMapping("/biblio")
@Api(value="Gestion des categories de livres en referencement dans la Bibliotheque")
public class CategorieRestService {
	
	@Autowired
	private ICategorieMetier categorieMetier;
	
	

	/**
	 * Méthode pour identifier toutes les categories de livres en referencement 
	 * @param categorieCriteria
	 * @return
	 * @see biblioWebServiceRest.metier.ICategorieMetier#searchByCriteria(biblioWebServiceRest.criteria.CategorieCriteria)
	 */
	@ApiOperation(value = "Identification des categories de livres en referencement", response = Categorie.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "La recherche a été réalisée avec succés")
	})
	@GetMapping(value="/categories", produces = "application/json")
	public ResponseEntity<Page<Categorie>> searchByCriteria(@PathParam(value = "categorieCriteria") CategorieCriteria categorieCriteria, @RequestParam(name="page", defaultValue = "0") int page, @RequestParam (name="size", defaultValue = "3")int size) {
		Page<Categorie> pageCategories = categorieMetier.searchByCriteria(categorieCriteria, PageRequest.of(page, size));
		return new ResponseEntity<Page<Categorie>>(pageCategories, HttpStatus.OK);
	}


	/**
	 * Methode pour creer une nouvelle categorie de livres
	 * @param nomCategorie
	 * @return
	 * @throws EntityAlreadyExistsException 
	 * @throws Exception
	 * @see biblioWebServiceRest.metier.ICategorieMetier#createCategorie(java.lang.String)
	 */
	@ApiOperation(value = "Enregistrement d'une nouvelle categorie de livres en refencement", response = Categorie.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 201, message = "La reference de cette categorie a été creee"),
	        @ApiResponse(code = 400, message = "Les termes de la requête de création n'ont pas été validés : "
	        		+ "la saisie ne doit pas être nulle ou vide et elle doit comprendre entre 5 et 25 caractères alphabétiques."),
	        @ApiResponse(code = 404, message = "Ressource inexistante"),
	        @ApiResponse(code = 409, message = "La reference de cette categorie existe déjà")
	})
	@PostMapping(value="/categories", produces = "application/json", consumes="application/json")
	public ResponseEntity<Categorie> createCategorie(@Valid @RequestBody CategorieDTO categorieDTO) throws EntityAlreadyExistsException {
		return new ResponseEntity<Categorie>(categorieMetier.createCategorie(categorieDTO), HttpStatus.CREATED);
	}
	

	/**
	 * Méthode pour supprimer une catégorie 
	 * La méthode retourne HttpStatus OK si la suppression a été effectuée
	 * La méthode envoie une InternalServerException si la réference de la categorie à supprimer contient au moins un livre
	 * La méthode envoie une NotFoundException si la réference n'existe pas
	 * @param numCategorie
	 * @throws Exception
	 * @return ResponseEntity HttpStatus OK
	 * @throws EntityNotDeletableException 
	 * @throws EntityNotFoundException 
	 * @see biblioWebServiceRest.metier.ICategorieMetier#deleteCategorie(java.lang.Long)
	 */
	@ApiOperation(value = "Suppression d'une référence de categorie de livres en refencement", response = Categorie.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "La demande de suppression de cette catégorie a été correctement effectuée"),
	        @ApiResponse(code = 404, message = "Ressource inexistante")
	})
	@DeleteMapping(value="/categories/{numCategorie}", produces = "application/text")
	public ResponseEntity<String> deleteCategorie(@PathVariable (value="numCategorie", required=true) Long numCategorie) throws EntityNotFoundException, EntityNotDeletableException {
		categorieMetier.deleteCategorie(numCategorie);
		return new ResponseEntity<String>("La categorie de cette reference a ete supprimee", HttpStatus.OK);
		
	}
	
	
	
	

}
