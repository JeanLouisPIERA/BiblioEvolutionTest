/**
 * 
 */
package biblioWebAppli.metier;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import biblioWebAppli.criteria.LivreCriteria;

import biblioWebAppli.dto.LivreDTO;

import biblioWebAppli.entities.Livre;

/**
 * @author jeanl
 *
 */
@Service
public class LivreMetierImpl implements ILivreMetier {
	
	@Autowired
    private RestTemplate restTemplate;
    
    public final String uRL = "http://localhost:8080/livres";
	
    
	/**
	 * @param livreCriteria
	 * @param page
	 * @param size
	 * @return
	 */
	@Override
	public Page<Livre> searchByCriteria(LivreCriteria livreCriteria, Pageable pageable, int page, int size) {
		HttpHeaders headers = new HttpHeaders();
    	headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

    	UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uRL)
    	        .queryParam("numLivre", livreCriteria.getNumLivre())
    	        .queryParam("titre", livreCriteria.getTitre())
    	        .queryParam("auteur", livreCriteria.getAuteur())
    	        .queryParam("nomCategorie", livreCriteria.getNomCategorie())
    	        .queryParam("page", page)
    	        .queryParam("size", size);
    	

    	HttpEntity<?> entity = new HttpEntity<>(headers);
    	
    	ResponseEntity<RestResponsePage<Livre>> livres = restTemplate.exchange
    			(builder.toUriString(), 
				HttpMethod.GET,
				entity,
    			new ParameterizedTypeReference<RestResponsePage<Livre>>(){});
        Page<Livre> pageLivre = livres.getBody();
        
            	
        return pageLivre;
	}

	/**
	 * @throws biblioWebAppli.exceptions.EntityNotFoundException,  
	 * @throws  
	 * @param livreDTO
	 * @return
	 * @throws biblioWebAppli.exceptions.EntityAlreadyExistsException 
	
	 
	 */
	@Override
	public Livre createLivre(LivreDTO livreDTO) throws biblioWebAppli.exceptions.EntityNotFoundException, biblioWebAppli.exceptions.EntityAlreadyExistsException {
		HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_JSON);

    	Livre livreToCreate = new Livre();
    	
    	HttpEntity<LivreDTO> requestEntity = 
    	     new HttpEntity<>(livreDTO, headers);
    	ResponseEntity<Livre> response;
		try {
			response = restTemplate.exchange(uRL, HttpMethod.POST, requestEntity, 
			              Livre.class);
			System.out.println(response.getStatusCodeValue());
			livreToCreate = response.getBody();
			System.out.println(livreToCreate);
		} catch (HttpClientErrorException exception) {
			if(exception.getStatusCode() == HttpStatus.NOT_FOUND) 
				throw new biblioWebAppli.exceptions.EntityNotFoundException("Le livre ne peut pas etre enregistre car la categorie saisie n'existe pas");
			if(exception.getStatusCode() == HttpStatus.CONFLICT) 
				throw new biblioWebAppli.exceptions.EntityAlreadyExistsException("Ce livre a déjà été référencé");
		}
        
    	return livreToCreate;
	}

	/**
	 * @param numLivre
	 * @param livreDTO
	 * @return
	 */
	@Override
	public Livre updateLivre(Long numLivre, LivreDTO livreDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param numLivre
	 * @throws biblioWebAppli.exceptions.EntityNotFoundException,  
	 * @throws biblioWebAppli.exceptions.EntityNotDeletableException,
	 */
	@Override
	public void delete(Long numLivre) throws biblioWebAppli.exceptions.EntityNotFoundException, biblioWebAppli.exceptions.EntityNotDeletableException {
		 try {
				restTemplate.delete(uRL+"/"+numLivre);
			} catch (HttpClientErrorException exception) {
				if(exception.getStatusCode() == HttpStatus.NOT_FOUND) 
					throw new biblioWebAppli.exceptions.EntityNotFoundException("Le livre que vous voulez supprimer n'existe pas"); 
				if(exception.getStatusCode() == HttpStatus.PRECONDITION_FAILED) 
					throw new biblioWebAppli.exceptions.EntityNotDeletableException("Vous ne pouvez pas supprimer ce livre qui a encore des prêts encours"); 
				
			}
		
	}

}
