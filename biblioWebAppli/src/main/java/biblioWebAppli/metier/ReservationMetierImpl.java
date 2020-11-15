package biblioWebAppli.metier;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import biblioWebAppli.criteria.ReservationCriteria;
import biblioWebAppli.dto.ReservationDTO;
import biblioWebAppli.objets.Pret;
import biblioWebAppli.objets.Reservation;

@Service
public class ReservationMetierImpl implements IReservationMetier {
	
	@Autowired
    private RestTemplate restTemplate;
	@Autowired
    private HttpHeadersFactory httpHeadersFactory; 
    
    
    @Value("${application.username}")
	private String username;
	@Value("${application.password}")
	private String password;
	@Value("${application.idUser}")
	private Long idUser;
	
    
    @Value("${application.uRLReservation}")
	private String uRL;

	@Override
	public Reservation createReservation(ReservationDTO reservationDTO) {
		HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_JSON);

    	HttpEntity<ReservationDTO> requestEntity = new HttpEntity<>(reservationDTO, headers);
    	ResponseEntity<Reservation> response = restTemplate.exchange(uRL, HttpMethod.POST, requestEntity, Reservation.class);
			System.out.println(response.getStatusCodeValue());
			
    	return response.getBody();
	}

	@Override
	public Reservation notifierReservation(Long numReservation) {
		HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	
    	HttpEntity<?> requestEntity = 
       	     new HttpEntity<>(headers);
		
		String url = uRL+"/notification/"+numReservation;
    	
		ResponseEntity<Reservation> response = restTemplate.exchange(url , HttpMethod.PUT, requestEntity, Reservation.class);
		
		return response.getBody(); 
	}

	@Override
	public Reservation livrerReservationAndCreerPret(Long numReservation) {
		HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	
    	HttpEntity<?> requestEntity = 
       	     new HttpEntity<>(headers);
		
		String url = uRL+"/livraison/"+numReservation;
    	
		ResponseEntity<Reservation> response = restTemplate.exchange(url , HttpMethod.PUT, requestEntity, Reservation.class);
		
		return response.getBody(); 
	}

	@Override
	public Reservation suppressReservation(Long numReservation) {
		HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	
    	HttpEntity<?> requestEntity = 
       	     new HttpEntity<>(headers);
		
		String url = uRL+"/suppression/"+numReservation;
    	
		ResponseEntity<Reservation> response = restTemplate.exchange(url , HttpMethod.PUT, requestEntity, Reservation.class);
		
		return response.getBody(); 
	}

	@Override
	public Page<Reservation> searchAllReservationsByCriteria(ReservationCriteria reservationCriteria,
			Pageable pageable) {
		HttpHeaders headers = httpHeadersFactory.createHeaders(username,password);
		
    	headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    	
    	UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uRL)
    	        .queryParam("numReservation", reservationCriteria.getNumReservation())
    	        .queryParam("username", username)
    	        //.queryParam("userId", pretCriteria.getUserId())
    	        .queryParam("numLivre", reservationCriteria.getNumLivre())
    	        .queryParam("titre", reservationCriteria.getTitre())
    	        .queryParam("auteur", reservationCriteria.getAuteur())
    	        .queryParam("nomCategorieLivre", reservationCriteria.getNomCategorieLivre())
    	        .queryParam("page", pageable.getPageNumber())
    	        .queryParam("size", pageable.getPageSize());
    	
    	HttpEntity<?> entity = new HttpEntity<>(headers);
    	
    	ResponseEntity<RestResponsePage<Reservation>> reservations = restTemplate.exchange
    			(builder.toUriString(), 
				HttpMethod.GET,
				entity,
    			new ParameterizedTypeReference<RestResponsePage<Reservation>>(){});
        Page<Reservation> pageReservation = reservations.getBody();
        
            	
        return pageReservation;
	}

	@Override
	public Page<Reservation> searchMyReservations(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Reservation> searchAndNotifierReservations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Reservation> searchAndUpdateReservationsAnnulées() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
