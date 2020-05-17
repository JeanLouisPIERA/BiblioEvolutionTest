package biblioWebServiceRest.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import biblioWebServiceRest.entities.Livre;
import biblioWebServiceRest.entities.LivreStatut;

@Repository
public interface ILivreRepository extends JpaRepository <Livre, Long>, JpaSpecificationExecutor<Livre>{
	
	List<Livre> findByTitre(String titre);
	Page<Livre> findByTitre(String titre, Pageable pageable);
	
	List<Livre> findByTitreAndLivreStatut(String titre, LivreStatut livreStatut);
	Page<Livre> findByTitreAndLivreStatut(String titre, LivreStatut livreStatut, Pageable pageable);
	
	long countByTitre(String titre);
	
	long countByTitreAndLivreStatut(String titre, LivreStatut livreStatut);
	

}
