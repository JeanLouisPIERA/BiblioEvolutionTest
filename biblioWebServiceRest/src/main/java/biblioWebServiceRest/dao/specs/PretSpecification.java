package biblioWebServiceRest.dao.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import biblioWebServiceRest.criteria.PretCriteria;
import biblioWebServiceRest.entities.Pret;

public class PretSpecification implements Specification<Pret> {
	
	private PretCriteria pretCriteria;
	
	public PretSpecification (PretCriteria pretCriteria) {
		this.pretCriteria = pretCriteria;
	}

	/**
	 * @param root
	 * @param query
	 * @param criteriaBuilder
	 * @return
	 */
	@Override
	public Predicate toPredicate(Root<Pret> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		//create a new predicate list
        Predicate predicates = builder.conjunction();

        //add add criteria to predicates
	       
	        if (pretCriteria.getNumPret()!= null) {
	        	predicates.getExpressions().add(builder.equal(root.get("numPret"), pretCriteria.getNumPret()));			
	        }
        
        	if (pretCriteria.getUser()!= null) {
            	predicates.getExpressions().add(builder.like(root.get("user").get("username"), "%" + pretCriteria.getUser().getUsername()+ "%"));		
            }
        	
        	if(pretCriteria.getLivre()!=null) {
	            if (pretCriteria.getLivre().getTitre()!= null) {
	            	predicates.getExpressions().add(builder.like(root.get("livre").get("titre"), "%" + pretCriteria.getLivre().getTitre() + "%"));	  	            
	            }
	            
	            if (pretCriteria.getLivre().getAuteur()!= null) {
	            	predicates.getExpressions().add(builder.like(root.get("livre").get("auteur"), "%" + pretCriteria.getLivre().getAuteur() + "%"));	
	            }
	            
	            if (pretCriteria.getLivre().getCategorie()!= null) {
	            	predicates.getExpressions().add(builder.like(root.get("livre").get("categorie").get("nomCategorie"), "%" + pretCriteria.getLivre().getCategorie().getNomCategorie() + "%"));	
	            }
	            
	            if (pretCriteria.getLivre().getNumLivre()!= null) {
	            	predicates.getExpressions().add(builder.equal(root.get("livre").get("numLivre"), pretCriteria.getLivre().getNumLivre()));	
	            }
        	}
        	
            if (pretCriteria.getPretStatut().getCode()!= null) {
            	
            	predicates.getExpressions().add(builder.equal(root.get("pretStatut").get("text"), pretCriteria.getPretStatut().getText()));			
            	
            }
        
        return builder.and(predicates);
		
	}
	
	
	}
