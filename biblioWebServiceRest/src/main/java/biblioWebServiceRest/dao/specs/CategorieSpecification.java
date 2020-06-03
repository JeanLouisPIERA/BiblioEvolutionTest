/**
 * Cette classe permet de créer les specifications necessaires à la recherche par Criteria 
 */
package biblioWebServiceRest.dao.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import biblioWebServiceRest.criteria.CategorieCriteria;
import biblioWebServiceRest.entities.Categorie;

/**
 * @author jeanl
 *
 */
public class CategorieSpecification implements Specification<Categorie>{
	
private CategorieCriteria categorieCriteria;
	
    public CategorieSpecification(CategorieCriteria categorieCriteria) {
        this.categorieCriteria = categorieCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Categorie> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        //create a new predicate list
        Predicate predicates = builder.conjunction();

        //add add criteria to predicates
        
            if (categorieCriteria.getNomCategorie()!= null) {
            	predicates.getExpressions().add(builder.like(root.get("nomCategorie"), "%" + categorieCriteria.getNomCategorie() + "%"));			
            }
            if (categorieCriteria.getNumCategorie()!= null) {
            	predicates.getExpressions().add(builder.equal(root.get("numCategorie"), categorieCriteria.getNumCategorie()));			
            }
            
        return builder.and(predicates);
    }

}
