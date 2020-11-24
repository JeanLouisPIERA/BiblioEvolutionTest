package biblioWebAppli.metier;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import biblioWebAppli.criteria.ReservationCriteria;
import biblioWebAppli.dto.ReservationDTO;
import biblioWebAppli.objets.Reservation;
import biblioWebAppli.objets.ReservationStatut;
import biblioWebAppli.objets.User;

@Service
public class ReservationMetierImpl implements IReservationMetier {
	
	@Autowired
    private RestTemplate restTemplate;
	@Autowired
    private HttpHeadersFactory httpHeadersFactory; 
	@Autowired
	private IUserMetier userMetier;
    
    
    @Value("${application.username1}")
	private String username1;
    @Value("${application.username2}")
	private String username2;
	@Value("${application.password}")
	private String password;
	@Value("${application.idUser}")
	private Long idUser;
	
    @Value("${application.uRLReservation}")
	private String uRL;
    
  

	@Override
	public Reservation createReservation(Long numLivre) throws FileNotFoundException, IOException  {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		HttpHeaders headers = httpHeadersFactory.createHeaders(username,password);
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	
    	ReservationDTO reservationDTO = new ReservationDTO();
    	
    	reservationDTO.setNumLivre(numLivre);
    	
    	User user = userMetier.findByUsernameAndPassword(username, password);
    	reservationDTO.setIdUser(user.getIdUser());
    	System.out.println(user.getUsername());
    	System.out.println(user.getIdUser());
    	
    	HttpEntity<ReservationDTO> requestEntity = new HttpEntity<>(reservationDTO, headers);
    	ResponseEntity<Reservation> response = restTemplate.exchange(uRL, HttpMethod.POST, requestEntity, Reservation.class);
			System.out.println(response.getStatusCodeValue());
			
    	return response.getBody();
	}

	
	@Override
	public Reservation suppressReservation(Long numReservation) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		HttpHeaders headers = httpHeadersFactory.createHeaders(username,password);
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	
    	HttpEntity<?> requestEntity = 
       	     new HttpEntity<>(headers);
		
		String url = uRL+"/suppression/"+numReservation;
    	
		ResponseEntity<Reservation> response = restTemplate.exchange(url , HttpMethod.PUT, requestEntity, Reservation.class);
		
		return response.getBody(); 
	}
	
	@Override
	public Page<Reservation> searchAllReservationsByCriteria(ReservationCriteria reservationCriteria, Pageable pageable) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		HttpHeaders headers = httpHeadersFactory.createHeaders(username,password);
		
    	headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    	
    	UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uRL)
    	        .queryParam("numReservation", reservationCriteria.getNumReservation())
    	        .queryParam("username", username)
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
	public Page<Reservation> searchAllReservationsByCriteriaAndReservationStatut(ReservationCriteria reservationCriteria, String reservationStatut, Pageable pageable) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		HttpHeaders headers = httpHeadersFactory.createHeaders(username,password);
		
    	headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    	
    	
        	reservationCriteria.setReservationStatut(ReservationStatut.fromValueText(reservationStatut));
        	
        	
        	System.out.println("reservation statut :" + reservationCriteria.getReservationStatut());
        	System.out.println("reservation statut :" + reservationStatut);
        	System.out.println("reservation statut :" + reservationCriteria.getReservationStatut().getCode());
        	System.out.println("reservation auteur :" + reservationCriteria.getAuteur());
    	
    	
    	UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uRL)
    	        .queryParam("numReservation", reservationCriteria.getNumReservation())
    	        .queryParam("username", username)
    	        .queryParam("numLivre", reservationCriteria.getNumLivre())
    	        .queryParam("titre", reservationCriteria.getTitre())
    	        .queryParam("auteur", reservationCriteria.getAuteur())
    	        .queryParam("nomCategorieLivre", reservationCriteria.getNomCategorieLivre())
    	        .queryParam("reservationStatutCode", reservationCriteria.getReservationStatut().getCode())
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
	public Reservation readReservation(Long numReservation) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		HttpHeaders headers = httpHeadersFactory.createHeaders(username,password);
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	
    	HttpEntity<?> requestEntity = 
       	     new HttpEntity<>(headers);
		
		String url = uRL+"/" + numReservation;
    	
		ResponseEntity<Reservation> response = restTemplate.exchange(url , HttpMethod.GET, requestEntity, Reservation.class);
		
		return response.getBody(); 
		
	}
	
	
	

}
