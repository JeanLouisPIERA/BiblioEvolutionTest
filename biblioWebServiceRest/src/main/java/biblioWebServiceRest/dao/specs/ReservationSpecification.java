package biblioWebServiceRest.dao.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import biblioWebServiceRest.criteria.ReservationCriteria;
import biblioWebServiceRest.entities.Reservation;
import biblioWebServiceRest.entities.ReservationStatut;

public class ReservationSpecification implements Specification<Reservation>{
	
	private ReservationCriteria reservationCriteria;
	
	public ReservationSpecification (ReservationCriteria reservationCriteria) {
		this.reservationCriteria = reservationCriteria;
	}
	
	@Override
	public Predicate toPredicate(Root<Reservation> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		//create a new predicate list
        Predicate predicates = builder.conjunction();

        //add add criteria to predicates
 
	        if (reservationCriteria.getNumReservation()!= null) {
	        	predicates.getExpressions().add(builder.equal(root.get("numReservation"), reservationCriteria.getNumReservation()));			
	        }
       
        	if (reservationCriteria.getUsername()!= null) {
            	predicates.getExpressions().add(builder.like(root.get("user").get("username"), "%" + reservationCriteria.getUsername() + "%"));		
            }
        	
        	if (reservationCriteria.getUserId()!= null) {
            	predicates.getExpressions().add(builder.equal(root.get("user").get("idUser"), reservationCriteria.getUserId()));		
            }
        	
            if (reservationCriteria.getTitre()!= null) {
            	predicates.getExpressions().add(builder.like(root.get("livre").get("titre"), "%" + reservationCriteria.getTitre() + "%"));	  	            
            }
            
            if (reservationCriteria.getAuteur()!= null) {
            	predicates.getExpressions().add(builder.like(root.get("livre").get("auteur"), "%" + reservationCriteria.getAuteur() + "%"));	
            }
            
            if (reservationCriteria.getNomCategorieLivre()!= null) {
            	predicates.getExpressions().add(builder.like(root.get("livre").get("categorie").get("nomCategorie"), "%" + reservationCriteria.getNomCategorieLivre() + "%"));	
            }
            
            if (reservationCriteria.getNumLivre()!= null) {
            	predicates.getExpressions().add(builder.equal(root.get("livre").get("numLivre"), reservationCriteria.getNumLivre()));	
            }
            
            if (reservationCriteria.getReservationStatutCode() != null) {
            	
            	predicates.getExpressions().add(builder.equal(root.get("reservationStatut"), ReservationStatut.fromValueCode(reservationCriteria.getReservationStatutCode())));			
            	
            }
         
       
        return builder.and(predicates);
		
	}
	
}
