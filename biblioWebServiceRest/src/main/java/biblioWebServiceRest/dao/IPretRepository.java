/**
 * Classe de sérialization JPA des prets pour le Mapping ORM avec une extension Specification de l'API Criteria
 */
package biblioWebServiceRest.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import biblioWebServiceRest.entities.Pret;
import biblioWebServiceRest.entities.PretStatut;



@Repository
public interface IPretRepository extends JpaRepository<Pret, Long>, JpaSpecificationExecutor<Pret>{
	
	@Query("select pret from Pret pret where (pret.pretStatut <> ?1)" + "AND (pret.dateRetourPrevue < ?2)")
	   List<Pret> findAllByPretStatutAndDateEcheanceBeforeThisDate(PretStatut pretStatut, LocalDate date);
	
	
	
}
