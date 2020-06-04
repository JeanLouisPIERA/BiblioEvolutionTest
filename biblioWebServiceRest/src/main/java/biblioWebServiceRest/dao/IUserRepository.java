/**
 * Classe de sérialization JPA des utilisateurs pour le Mapping ORM avec une extension Specification de l'API Criteria
 */
package biblioWebServiceRest.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import biblioWebServiceRest.entities.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long>{
	
	@Query("select u from User u where u.username like %?1")
	Optional<User> findByUsername(String username);

	
}
