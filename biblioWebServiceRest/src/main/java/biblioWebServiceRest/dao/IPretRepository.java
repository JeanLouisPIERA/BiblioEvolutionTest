/**
 * Classe de sérialization JPA des prets pour le Mapping ORM avec une extension Specification de l'API Criteria
 */
package biblioWebServiceRest.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
	   Optional<List<Pret>> findAllByOtherPretStatutAndDateEcheanceBeforeThisDate(PretStatut pretStatut, LocalDate date);
	
	@Query("select pret from Pret pret where (pret.pretStatut = ?1)")
	   Optional<List<Pret>> findAllByPretStatut(PretStatut pretStatut);
	
	@Query("select pret from Pret pret where (pret.pretStatut = ?1 "+" or pret.pretStatut = ?2)" + "AND (pret.dateRetourPrevue > ?3)")
	   Optional<List<Pret>> findAllByPretStatutAndDateEcheanceAfterThisDate(PretStatut pretStatut1, PretStatut pretSatut2, LocalDate date);
	
	@Query("select pret from Pret pret where (pret.pretStatut = ?1)" + "AND (pret.dateRetourPrevue BETWEEN ?2 AND ?3)")
	   Optional<List<Pret>> findAllByPretStatutAndDateEcheanceBetweenTwoDates(PretStatut pretStatut, LocalDate dateDebut, LocalDate dateFin);
	
}
