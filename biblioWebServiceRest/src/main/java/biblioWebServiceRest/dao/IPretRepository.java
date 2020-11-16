/**
 * Classe de sérialization JPA des prets pour le Mapping ORM avec une extension Specification de l'API Criteria
 */
package biblioWebServiceRest.dao;


import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import org.springframework.stereotype.Repository;

import biblioWebServiceRest.entities.Livre;
import biblioWebServiceRest.entities.Pret;
import biblioWebServiceRest.entities.User;




@Repository
public interface IPretRepository extends JpaRepository<Pret, Long>, JpaSpecificationExecutor<Pret>{
	/**
	@Query("select pret from Pret pret where pret.pretStatut like :pretStatut")
	   Page<Pret> findByPretStatutNamedParams(@Param("pretStatut")PretStatut pretStatut, Pageable pageable);
**/

	Optional<Pret> findByUserAndLivre(User user, Livre livre);
	
	
}
