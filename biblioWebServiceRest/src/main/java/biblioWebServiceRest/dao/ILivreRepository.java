package biblioWebServiceRest.dao;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


import biblioWebServiceRest.entities.Livre;



@Repository
public interface ILivreRepository extends JpaRepository <Livre, Long>, JpaSpecificationExecutor<Livre>{
	
	List<Livre> findByTitre(String titre);
	Page<Livre> findByTitre(String titre, Pageable pageable);
	
	
	
	long countByTitre(String titre);
	




}
