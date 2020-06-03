/**
 * 
 */
package biblioWebServiceRest.services;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import biblioWebServiceRest.criteria.PretCriteria;
import biblioWebServiceRest.dto.LivreDTO;
import biblioWebServiceRest.dto.PretCriteriaDTO;
import biblioWebServiceRest.dto.PretDTO;
import biblioWebServiceRest.dto.UserDTO;
import biblioWebServiceRest.entities.Livre;
import biblioWebServiceRest.entities.Pret;
import biblioWebServiceRest.entities.User;
import biblioWebServiceRest.mapper.LivreMapper;
import biblioWebServiceRest.mapper.PretCriteriaMapper;
import biblioWebServiceRest.mapper.PretMapper;
import biblioWebServiceRest.mapper.UserMapper;
import biblioWebServiceRest.metier.ILivreMetier;
import biblioWebServiceRest.metier.IPretMetier;
import biblioWebServiceRest.metier.IUserMetier;
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
	ILivreMetier livreMetier;
	@Autowired
	IUserMetier userMetier;
	@Autowired
	PretMapper pretMapper; 
	@Autowired
	PretCriteriaMapper pretCriteriaMapper;
	@Autowired
	LivreMapper livreMapper;
	@Autowired
	UserMapper userMapper;

	
	/**
	 * Cette Requête permet de créer un prêt 
	 * La durée du pret est une constante déclarée dans application.properties et gérées dans le package Configurations
	 * Exceptions gérées en cas de paramètres inconnus en base de données ou d'absence d'exemplaire disponible 
	 * Gestion DTO 
	 * @param nomLivre
	 * @param nomEmprunteur
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "Enregistrement d'un nouveau prêt", response = Pret.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 201, message = "Le prêt a été créé"),
	        @ApiResponse(code = 401, message = "Pas d'autorisation pour accéder à cette ressource"),
	        @ApiResponse(code = 403, message = "Accès interdit à cette ressource "),
	        @ApiResponse(code = 404, message = "Ressource inexistante"),
	        @ApiResponse(code = 500, message = "Erreur interne au Serveur")
	})
	@PostMapping(value="/prets/livre/{nomLivre}/user/{nomEmprunteur}", produces = "application/json")
	public ResponseEntity<PretDTO> createPret(@PathVariable String nomLivre, @PathVariable String nomEmprunteur ) throws Exception {
		String titre = nomLivre;
		String username = nomEmprunteur;
		
		Pret newPret = pretMetier.createPret(titre, username);
		Livre livreNewPret = newPret.getLivre();
		User userNewPret = newPret.getUser();
		
		LivreDTO livreNewPretDTO = livreMapper.livreToLivreDTO(livreNewPret);
		UserDTO usernewPretDTO = userMapper.userTouserDTO(userNewPret);
		PretDTO newPretDTO = pretMapper.pretToPretDTO(newPret);
		
		newPretDTO.setLivre(livreNewPretDTO);
		newPretDTO.setUser(usernewPretDTO);
		
		return new ResponseEntity<PretDTO>(newPretDTO, HttpStatus.CREATED);
	}
	
	
	/**
	 * Cette méthode permet de prolonger la durée d'un pret et de mettre à jour son statut
	 * La durée de prolongation est une constante déclarée dans application.properties et gérée dans le package Configurations 
	 * Mise à jour des prets ENCOURS au statut ECHU selon la date de la demande
	 * Exceptions gérées si le statut du prêt n'est pas ENCOURS 
	 * Gestion DTO
	 * @param numPret
	 * @return
	 * @throws Exception
	 * @see biblioWebServiceRest.metier.IPretMetier#prolongerPret(java.lang.Long)
	 */
	@ApiOperation(value = "Prolongation de la durée d'un nouveau prêt", response = Pret.class)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Code erreur non utilisé"),
	        @ApiResponse(code = 202, message = "Le prêt a été prolongé"),
	        @ApiResponse(code = 401, message = "Pas d'autorisation pour accéder à cette ressource"),
	        @ApiResponse(code = 403, message = "Accès interdit à cette ressource "),
	        @ApiResponse(code = 404, message = "Ressource inexistante"),
	        @ApiResponse(code = 500, message = "Erreur interne au Serveur")
	})
	@PutMapping(value="/prets/{refPret}/prolongation", produces="application/json")
	public ResponseEntity<PretDTO> prolongerPret(@PathVariable Long refPret) throws Exception {
		Long numPret = refPret;
		
		Pret pretProlonge = pretMetier.prolongerPret(numPret);
		Livre livrePretProlonge = pretProlonge.getLivre();
		User userPretProlonge = pretProlonge.getUser();
		
		
		UserDTO userPretProlongeDTO = userMapper.userTouserDTO(userPretProlonge);
		LivreDTO livrePretProlongeDTO = livreMapper.livreToLivreDTO(livrePretProlonge);
		PretDTO pretProlongeDTO = pretMapper.pretToPretDTO(pretProlonge);
		
		pretProlongeDTO.setLivre(livrePretProlongeDTO);
		pretProlongeDTO.setUser(userPretProlongeDTO);
		
		return new ResponseEntity<PretDTO>(pretProlongeDTO, HttpStatus.ACCEPTED) ;
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
	        @ApiResponse(code = 201, message = "Code erreur non utilisé"),
	        @ApiResponse(code = 401, message = "Pas d'autorisation pour accéder à cette ressource"),
	        @ApiResponse(code = 403, message = "Accès interdit à cette ressource "),
	        @ApiResponse(code = 404, message = "Ressource inexistante"),
	        @ApiResponse(code = 500, message = "Erreur interne au Serveur")
	})
	@GetMapping(value="/prets", produces="application/json")
	public ResponseEntity<Page<PretDTO>> searchByPretCriteriaDTO(@PathParam("searched by")PretCriteriaDTO pretCriteriaDTO, @RequestParam int page, @RequestParam int size ) {
		PretCriteria pretCriteria = pretCriteriaMapper.pretCriteriaDTOToPretCriteria(pretCriteriaDTO);
		List<Pret> prets = pretMetier.searchByCriteria(pretCriteria);
		List<PretDTO> pretDTOs = pretMapper.pretsToPretsDTOs(prets);
		int end = (page + size > prets.size() ? prets.size() :(page + size));
		Page<PretDTO> pagePretDTO = new PageImpl<PretDTO>(pretDTOs.subList(page, end));
		return new ResponseEntity<Page<PretDTO>>(pagePretDTO, HttpStatus.OK);
	}

}
