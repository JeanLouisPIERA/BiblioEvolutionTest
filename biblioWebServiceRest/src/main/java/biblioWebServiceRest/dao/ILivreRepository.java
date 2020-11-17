/**
 * Classe de sérialization JPA des livres pour le Mapping ORM avec une extension Specification de l'API Criteria
 */
package biblioWebServiceRest.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import biblioWebServiceRest.entities.Livre;

@Repository
public interface ILivreRepository extends JpaRepository <Livre, Long>, JpaSpecificationExecutor<Livre>{
	
	@Query("select l from Livre l where l.titre like %?1")
	Optional<Livre> findByTitre(String titre);

	@Query("select livre from Livre livre where livre.nbExemplairesDisponibles = ?1")
	Optional<List<Livre>> findAllByNbExemplairesDisponibles(Integer nbExemplairesDisponibles);

	
	




}
