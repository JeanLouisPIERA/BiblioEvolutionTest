/**
 * Classe REST Service qui gère les requêtes URI sur les livres en référencement (création, gestion du nombre 
 * d'exemplaires, de la catégorie, suppression de la référence, affichage et recherche multicritères)
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import biblioWebServiceRest.criteria.LivreCriteria;
import biblioWebServiceRest.dto.LivreDTO;
import biblioWebServiceRest.entities.Livre;
import biblioWebServiceRest.entities.Pret;
import biblioWebServiceRest.exceptions.EntityAlreadyExistsException;
import biblioWebServiceRest.exceptions.EntityNotDeletableException;
import biblioWebServiceRest.exceptions.EntityNotFoundException;
import biblioWebServiceRest.exceptions.WrongNumberException;
import biblioWebServiceRest.metier.ILivreMetier;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@Api(value="Gestion des livres en referencement dans la Bibliotheque")
public class LivreRestService {
	
	
	@Autowired
	private ILivreMetier livreMetier;

	/**
	 * Si aucun paramètre n'est renseigné, la méthode renvoie la liste de tous les livres enregistrés dans la base
	 * Le titre et le nom de l'auteur doivent simplement matcher
	 * Le nom de la catégorie doit être égal sinon la méthode Méthode catche une exception
	 * Gestion de la serialization DTO
	 * @param livreCriteriaDTO
	 * @param page
	 * @param size
	 * @return
	 * @see biblioWebServiceRest.metier.ILivreMetier#searchByCriteria(biblioWebServiceRest.criteria.LivreCriteria, int, int)
	 */
	@ApiOperation(value = "Recherche multi-critères d'un ou plusieurs livres", response = Pret.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "La recherche a été réalisée avec succés"),
	        @ApiResponse(code = 404, message = "Ressource inexistante")
	})
	@GetMapping(value="/livres", produces = "application/json")
	public ResponseEntity<Page<Livre>> searchByLivreCriteria(@PathParam(value = "livreCriteria") LivreCriteria livreCriteria, @RequestParam(name="page", defaultValue = "0") int page, @RequestParam(name="size", defaultValue = "3") int size) {
		Page<Livre> pageLivres = livreMetier.searchByLivreCriteria(livreCriteria, PageRequest.of(page, size));
		return new ResponseEntity<Page<Livre>>(pageLivres, HttpStatus.OK);
	} 


	/**
	 *Creation d'un nouveau livre à referencer 
	 * La méthode envoie une exception si une réference existe déjà avec le même titre et le même auteur quelle que soit sa categorie
	 * La méthode envoie une exception si la catégorie dans laquelle doit être enregistré le livre a creer n'existe pas
	 * La méthode envoie une exception si le nombre total d'exemplaires est négatif
	 * Pour les références qui doivent enregistrer plusieurs tomes d'un même titre, il faut enregistrer le numéro du tome dans le titre (exemple Tome 1)
	 * @param livreDTO
	 * @return
	 * @throws EntityAlreadyExistsException,  
	 * @throws EntityNotFoundException 
	 * @see biblioWebServiceRest.metier.ILivreMetier#createLivre(java.lang.String, java.lang.String, java.lang.Long)
	 */
	@ApiOperation(value = "Enregistrement d'une nouvelle référence de livre. Si un même titre compte plusieurs tomes, enregistrer la référence du volume dans le titre. Exemple : Fantomas Tome 1)", response = Livre.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 201, message = "Le livre a été créé"),
	        @ApiResponse(code = 400, message = "Les termes de la requête de création n'ont pas été validés : "
    		+ "la saisie ne doit des champs ne doit pas être nulle ou vide, le nom de l'auteur et le titre doivent comprendre entre 5 et 25 caractères alphabétiques "
    		+ "et le nombre d'exemplaires et la référence de la catégorie doivent être des nombres entiers non nuls."),
	        @ApiResponse(code = 404, message = "Ressource inexistante")
	})
	@PostMapping(value="/livres", produces = "application/json")
	public ResponseEntity<Livre> createLivre(@Valid @RequestBody LivreDTO livreDTO) throws EntityNotFoundException, EntityAlreadyExistsException {
		
		Livre newLivre = livreMetier.createLivre(livreDTO);
		return new ResponseEntity<Livre>(newLivre, HttpStatus.CREATED);
		
	}
	
	
	/**
	 * Mise à jour des attributs d'un livre déjà referencé 
	 * La méthode envoie une exception si une réference existe déjà avec le même titre et le même auteur quelle que soit sa categorie
	 * La méthode envoie une exception si la catégorie dans laquelle doit être enregistré le livre a creer n'existe pas
	 * La méthode envoie une exception si le nombre total d'exemplaires est négatif ou si le nombre total d'exemplaires est inférieur au nombre total d'exemplaires en cours de pret
	 * Pour les références qui doivent enregistrer plusieurs tomes d'un même titre, il faut enregistrer le numéro du tome dans le titre (exemple Tome 1)
	 * @param numLivre
	 * @param livreDTO
	 * @return
	 * @throws EntityNotFoundException 
	 * @throws EntityAlreadyExistsException 
	 * @throws WrongNumberException 
	 * @see biblioWebServiceRest.metier.ILivreMetier#updateLivre(java.lang.Long, biblioWebServiceRest.dto.LivreDTO)
	 */
	@ApiOperation(value = "Mise à jour d'une référence de livre exitante (Titre, nom de l'auteur, catégorie, nombre d'exemplaires existants)", response = Livre.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 201, message = "Le livre a été mis à jour"),
	        @ApiResponse(code = 400, message = "Requête incomplete : il faut remplir tous les champs requis"),
	        @ApiResponse(code = 404, message = "Ressource inexistante")
	})
	@PutMapping(value="/livres/{numLivre}", produces = "application/json")
	public ResponseEntity<Livre> updateLivre(@PathVariable Long numLivre, @Valid @RequestBody LivreDTO livreDTO) throws EntityNotFoundException, WrongNumberException, EntityAlreadyExistsException{
	Livre livreToUpdate = livreMetier.updateLivre(numLivre, livreDTO); 
	return new ResponseEntity<Livre>(livreToUpdate, HttpStatus.ACCEPTED); 
	}
	
	


	/**
	 * Suppression d'une reference de livre 
	 * @param numLivre
	 * @throws EntityNotDeletableException 
	 * @throws EntityNotFoundException 
	 * @throws Exception
	 * @see biblioWebServiceRest.metier.ILivreMetier#deleteLivre(java.lang.Long)
	 */
	@ApiOperation(value = "Suppression d'une référence de livre", response = Livre.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "La demande de suppression de ce livre a été correctement effectuée"),
	        @ApiResponse(code = 404, message = "Ressource inexistante"),
	})
	@DeleteMapping(value="/livres/{numLivre}", produces = "application/text")
	public ResponseEntity<String> deleteLivre(@PathVariable Long numLivre) throws EntityNotFoundException, EntityNotDeletableException{
		livreMetier.deleteLivre(numLivre);
		return new ResponseEntity<String>("Le livre de cette référence a été supprimé", HttpStatus.OK);
	}		
		
}
	
	
	

