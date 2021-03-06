/**
 * Classe de sérialization JPA des prets pour le Mapping ORM avec une extension Specification de l'API Criteria
 */
package biblioWebServiceRest.dao;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import biblioWebServiceRest.entities.Livre;
import biblioWebServiceRest.entities.Pret;
import biblioWebServiceRest.entities.PretStatut;
import biblioWebServiceRest.entities.User;




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

	@Query("select pret from Pret pret where (pret.dateRetourPrevue < ?1) " + " AND (pret.pretStatut <> ?2)")
	Optional<List<Pret>> findAllByDateRetourPrevueBeforeAndNotPretStatut(LocalDate dateRetourPrevueMax, PretStatut pretStatut);
	
	@Query("select pret from Pret pret where (pret.livre = ?1) " + " AND (pret.user = ?2)"+ " AND (pret.pretStatut <> ?3 )")
	Optional<List<Pret>> findAllByLivreAndUserAndNotPretStatut(Livre livre, User user, PretStatut pretStatut);
	
	Optional<Pret> findByUserAndLivre(User user, Livre livre);
	
	@Query("select pret from Pret pret where (pret.livre = ?1) " + " AND (pret.pretStatut <> ?2)")
	Optional<List<Pret>> findAllByLivreAndNotPretStatut(Livre livre1, PretStatut pretStatut);
	
	@Query("select pret from Pret pret where (pret.livre = ?1) " + " AND (pret.pretStatut <> ?2)" + "ORDER BY pret.dateRetourPrevue ASC")
	Optional<List<Pret>> findAllByLivreAndNotPretStatutOrderByDateRetourPrevue(Livre livre1, PretStatut pretStatut);
	
	@Query("select pret from Pret pret where (pret.livre = ?1) " + " AND (pret.pretStatut <> ?2)" + " AND (pret.dateRetourPrevue > ?3)"+ "ORDER BY pret.dateRetourPrevue ASC")
	Optional<List<Pret>> findAllByLivreAndNotPretStatutOrderByDateRetourPrevueAfterThisDate(Livre livre1, PretStatut pretStatut, LocalDate localDate);

	Optional<List<Pret>> findAllByLivreAndPretStatut(Livre livre1, PretStatut pretStatut);
	
	@Query("select pret from Pret pret where (pret.livre.numLivre = ?1) " + " AND (pret.user.idUser = ?2)")
	Optional<Pret> findByNumLivreAndUserId(Long numLivre, Long userId);
	
}
