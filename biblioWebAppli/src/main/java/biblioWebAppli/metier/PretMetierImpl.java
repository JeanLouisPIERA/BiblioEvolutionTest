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

import biblioWebAppli.criteria.PretCriteria;
import biblioWebAppli.dto.LivreDTO;
import biblioWebAppli.dto.PretDTO;
import biblioWebAppli.entities.Livre;
import biblioWebAppli.entities.Pret;
import biblioWebAppli.exceptions.BookNotAvailableException;
import biblioWebAppli.exceptions.EntityNotFoundException;


/**
 * @author jeanl
 *
 */
@Service
public class PretMetierImpl implements IPretMetier{
	
	@Autowired
    private RestTemplate restTemplate;
    
    public final String uRL = "http://localhost:8080/prets";

	/**
	 * @param pretDTO
	 * @return
	 * @throws EntityNotFoundException
	 * @throws BookNotAvailableException
	 */
	@Override
	public Pret createPret(PretDTO pretDTO) throws EntityNotFoundException, BookNotAvailableException {
		HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_JSON);

    	Pret pretToCreate = new Pret();
    	
    	HttpEntity<PretDTO> requestEntity = 
    	     new HttpEntity<>(pretDTO, headers);
    	ResponseEntity<Pret> response;
		try {
			response = restTemplate.exchange(uRL, HttpMethod.POST, requestEntity, 
			              Pret.class);
			System.out.println(response.getStatusCodeValue());
			pretToCreate = response.getBody();
			System.out.println(pretToCreate);
		} catch (HttpClientErrorException exception) {
			if(exception.getStatusCode() == HttpStatus.NOT_FOUND) 
				throw new biblioWebAppli.exceptions.EntityNotFoundException("Aucun enregistrement ne correspond au livre ou à l'utilisateur saisi");
			if(exception.getStatusCode() == HttpStatus.LOCKED) 
				throw new BookNotAvailableException ("Il n'y a plus d'exemplaire disponible de ce livre");
		}
        
    	return pretToCreate;
	}

	/**
	 * @param numPret
	 * @return
	 * @throws EntityNotFoundException
	 * @throws BookNotAvailableException
	 */
	@Override
	public Pret prolongerPret(Long numPret) throws EntityNotFoundException, BookNotAvailableException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param numPret
	 * @return
	 * @throws EntityNotFoundException
	 */
	@Override
	public Pret cloturerPret(Long numPret) throws EntityNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param pretCriteria
	 * @param pageable
	 * @return
	 */
	@Override
	public Page<Pret> searchByCriteria(PretCriteria pretCriteria, Pageable pageable, int page, int size) {
		HttpHeaders headers = new HttpHeaders();
    	headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    	
    	UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uRL)
    	        .queryParam("numPret", pretCriteria.getNumPret())
    	        .queryParam("username", pretCriteria.getUsername())
    	        .queryParam("userId", pretCriteria.getUserId())
    	        .queryParam("titre", pretCriteria.getTitre())
    	        .queryParam("auteur", pretCriteria.getAuteur())
    	        .queryParam("nomCategorieLivre", pretCriteria.getNomCategorieLivre())
    	        .queryParam("page", page)
    	        .queryParam("size", size);
    	
    	HttpEntity<?> entity = new HttpEntity<>(headers);
    	
    	ResponseEntity<RestResponsePage<Pret>> prets = restTemplate.exchange
    			(builder.toUriString(), 
				HttpMethod.GET,
				entity,
    			new ParameterizedTypeReference<RestResponsePage<Pret>>(){});
        Page<Pret> pagePret = prets.getBody();
        
            	
        return pagePret;
	}
	
	

}
