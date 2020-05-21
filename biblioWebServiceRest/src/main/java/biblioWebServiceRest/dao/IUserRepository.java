package biblioWebServiceRest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import biblioWebServiceRest.entities.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long>{
	
	@Query("select u from User u where u.username like %?1")
	User findByUsername(String username);

	
}
