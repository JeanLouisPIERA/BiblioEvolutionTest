/**
 * Classe service REST qui gère les requêtes URI sur les prêts de livres (création, prolongation, archivage,
 * affichage et recherche multicritères) 
 */
package biblioWebServiceRest.services;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import biblioWebServiceRest.criteria.PretCriteria;

import biblioWebServiceRest.dto.PretDTO;
import biblioWebServiceRest.entities.Pret;
import biblioWebServiceRest.exceptions.BookNotAvailableException;
import biblioWebServiceRest.exceptions.EntityNotFoundException;
import biblioWebServiceRest.mapper.PretMapper;
import biblioWebServiceRest.metier.IPretMetier;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;
/**
 * @author jeanl
 *
 */

@RestController
@Api(value="Gestion des prêts de livres")
public class PretRestService {
	
	@Autowired
	IPretMetier pretMetier;
	@Autowired
	PretMapper pretMapper;
	
	
	/**
	 * Cette Requête permet de créer un prêt 
	 * La durée du pret est une constante déclarée dans application.properties et gérées dans le package Configurations
	 * Exceptions gérées en cas de paramètres inconnus en base de données ou d'absence d'exemplaire disponible 
	 * Gestion DTO 
	 * @param nomLivre
	 * @param nomEmprunteur
	 * @return
	 * @throws BookNotAvailableException 
	 * @throws EntityNotFoundException 
	 * @throws BadRequestException 
	 * @throws Exception
	 */
	@ApiOperation(value = "Enregistrement d'un nouveau prêt", response = Pret.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 201, message = "Le prêt a été créé"),
	        @ApiResponse(code = 404, message = "Ressource inexistante"),
	})
	@PostMapping(value="/prets", produces = "application/json", consumes = "application/json")
	public ResponseEntity<Pret> createPret(@Valid @RequestBody PretDTO pretDTO) throws EntityNotFoundException, BookNotAvailableException{
		Pret newPret = pretMetier.createPret(pretDTO);
		return new ResponseEntity<Pret>(newPret, HttpStatus.CREATED);
	}
	
	
	/**
	 * Cette méthode permet de prolonger la durée d'un pret et de mettre à jour son statut
	 * La durée de prolongation est une constante déclarée dans application.properties et gérée dans le package Configurations 
	 * Exceptions gérées si le statut du prêt n'est pas ENCOURS 
	 * Gestion DTO
	 * @param numPret
	 * @return
	 * @throws BookNotAvailableException 
	 * @throws EntityNotFoundException 
	 * @throws Exception
	 * @see biblioWebServiceRest.metier.IPretMetier#prolongerPret(java.lang.Long)
	 */
	@ApiOperation(value = "Prolongation de la durée d'un prêt en cours (exclusion des prets déjà prolongés ou échus non prolongés)", response = Pret.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 202, message = "Le prêt a été prolongé"),
	        @ApiResponse(code = 404, message = "Ressource inexistante"),
	})
	@PatchMapping(value="/prets/{numPret}", produces="application/json")
	public ResponseEntity<Pret> prolongerPret(@PathVariable Long numPret) throws EntityNotFoundException, BookNotAvailableException {
		
		Pret prolongationPret = pretMetier.prolongerPret(numPret);
		return new ResponseEntity<Pret>(prolongationPret, HttpStatus.ACCEPTED);
	
	}
	
	
	
	/**
	 * CRUD : UPDATE clôturer un prêt à la date de transaction de restitution de l'ouvrage 
	 * Le pret passe en statut CLOTURE mais n'est pas supprimé en base de données
	 * Le nombre d'exemplaires de l'ouvrage disponibles au pret est augmenté de +1 
	 * @param numPret
	 * @return
	 * @throws EntityNotFoundException 
	 * @see biblioWebServiceRest.metier.IPretMetier#cloturerPret(java.lang.Long)
	 */
	@ApiOperation(value = "Cloture d'un prêt à la restitution de l'ouvrage", response = Pret.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 202, message = "Le prêt a été cloturé"),
	        @ApiResponse(code = 404, message = "Ressource inexistante"),
	})
	@PutMapping(value="/prets/{numPret}", produces="application/json")
	public ResponseEntity<Pret> cloturerPret(@PathVariable Long numPret) throws EntityNotFoundException {
		
		Pret cloturePret = pretMetier.cloturerPret(numPret);
		return new ResponseEntity<Pret>(cloturePret, HttpStatus.ACCEPTED);
		
	}
	
	
	/**
	 * Recherche Multicritères des prêts enregistrés 
	 * Gestion de la serialization DTO 
	 * @param pretCriteriaDTO
	 * @param page
	 * @param size
	 * @return
	 */
	@ApiOperation(value = "Recherche multi-critères d'un ou plusieurs prets", response = Pret.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "La recherche a été réalisée avec succés"),
	        @ApiResponse(code = 404, message = "Ressource inexistante"),
	})
	@GetMapping(value="/prets", produces="application/json")
	public ResponseEntity<Page<Pret>> searchByPretCriteria(@PathParam("pretCriteria")PretCriteria pretCriteria, @RequestParam int page, @RequestParam int size ) {
		Page<Pret> prets = pretMetier.searchByCriteria(pretCriteria, PageRequest.of(page, size));
		return new ResponseEntity<Page<Pret>>(prets, HttpStatus.OK);
	}

}
