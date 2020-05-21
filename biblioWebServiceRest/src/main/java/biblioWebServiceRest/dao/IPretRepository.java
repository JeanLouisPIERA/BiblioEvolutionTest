package biblioWebServiceRest.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


import biblioWebServiceRest.entities.Pret;


@Repository
public interface IPretRepository extends JpaRepository<Pret, Long>, JpaSpecificationExecutor<Pret>{
	
	

}
