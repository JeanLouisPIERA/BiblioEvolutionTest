package biblioWebAppli.metier;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import biblioWebAppli.criteria.ReservationCriteria;
import biblioWebAppli.dto.ReservationDTO;
import biblioWebAppli.objets.Reservation;

@Service
public class ReservationMetierImpl implements IReservationMetier {
	
	@Autowired
    private RestTemplate restTemplate;
    
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
		// TODO Auto-generated method stub
		return null;
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
