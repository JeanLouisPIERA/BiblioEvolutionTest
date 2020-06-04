/**
 * Cette classe permet de créer les specifications necessaires à la recherche par Criteria 
 */
package biblioWebServiceRest.dao.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import biblioWebServiceRest.criteria.LivreCriteria;
import biblioWebServiceRest.entities.Livre;

public class LivreSpecification implements Specification<Livre> {
	
	private LivreCriteria livreCriteria;
	
    public LivreSpecification(LivreCriteria livreCriteria) {
        this.livreCriteria = livreCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Livre> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        //create a new predicate list
        Predicate predicates = builder.conjunction();

        //add add criteria to predicates
        
            if (livreCriteria.getAuteur()!= null) {
            	predicates.getExpressions().add(builder.like(root.get("auteur"), "%" + livreCriteria.getAuteur() + "%"));			
            }
            if (livreCriteria.getTitre()!= null) {
            	predicates.getExpressions().add(builder.like(root.get("titre"), "%" + livreCriteria.getTitre() + "%"));			
            }
            if (livreCriteria.getNumLivre() != null) {
            	predicates.getExpressions().add(builder.equal(root.get("numLivre"), livreCriteria.getNumLivre()));			
            }
            
            if (livreCriteria.getCategorie()!= null) {
            	predicates.getExpressions().add(builder.like(root.get("categorie").get("nomCategorie"), "%" + livreCriteria.getCategorie().getNomCategorie() + "%"));	
            }
        
        return builder.and(predicates);
    }

    
}
