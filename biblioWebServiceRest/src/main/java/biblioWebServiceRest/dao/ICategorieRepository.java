/**
 * Classe de sérialization JPA des catégories pour le Mapping ORM avec une extension Specification de l'API Criteria
 */
package biblioWebServiceRest.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import biblioWebServiceRest.entities.Categorie;


@Repository
public interface ICategorieRepository extends JpaRepository<Categorie, Long>, JpaSpecificationExecutor<Categorie> {

	@Query("select c from Categorie c where c.nomCategorie like %?1")
	Optional<Categorie> findByNomCategorie(String nomCategorie);
	
	
	
}
