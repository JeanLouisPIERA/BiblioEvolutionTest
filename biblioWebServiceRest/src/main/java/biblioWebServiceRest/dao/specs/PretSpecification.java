/**
 * Cette classe permet de créer les specifications necessaires à la recherche par Criteria 
 */
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
       
        	if (pretCriteria.getUsername()!= null) {
            	predicates.getExpressions().add(builder.like(root.get("user").get("username"), "%" + pretCriteria.getUsername()+ "%"));		
            }
        	
        	if (pretCriteria.getUserId()!= null) {
            	predicates.getExpressions().add(builder.equal(root.get("user").get("idUser"), pretCriteria.getUserId()));		
            }
        	
            if (pretCriteria.getTitre()!= null) {
            	predicates.getExpressions().add(builder.like(root.get("livre").get("titre"), "%" + pretCriteria.getTitre() + "%"));	  	            
            }
            
            if (pretCriteria.getAuteur()!= null) {
            	predicates.getExpressions().add(builder.like(root.get("livre").get("auteur"), "%" + pretCriteria.getAuteur() + "%"));	
            }
            
            if (pretCriteria.getNomCategorieLivre()!= null) {
            	predicates.getExpressions().add(builder.like(root.get("livre").get("categorie").get("nomCategorie"), "%" + pretCriteria.getNomCategorieLivre() + "%"));	
            }
            
            if (pretCriteria.getNumLivre()!= null) {
            	predicates.getExpressions().add(builder.equal(root.get("livre").get("numLivre"), pretCriteria.getNumLivre()));	
            }
            if (pretCriteria.getPretStatut()!= null) {
            	predicates.getExpressions().add(builder.like(root.get("pretStatut").get("code"),"%" + pretCriteria.getPretStatut() + "%"));			
            	
            }
	            
        	
        /**	
            if (pretCriteria.getPretStatut().getCode()!= null) {
            	
            	predicates.getExpressions().add(builder.equal(root.get("pretStatut").get("text"), pretCriteria.getPretStatut().getText()));			
            	
            }
        **/
        return builder.and(predicates);
		
	}
	
	
	}
