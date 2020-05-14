package biblioWebServiceRest.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biblioWebServiceRest.entities.Livre;
import biblioWebServiceRest.entities.Pret;
import biblioWebServiceRest.entities.PretStatut;
import biblioWebServiceRest.entities.User;

@Repository
public interface IPretRepository extends JpaRepository<Pret, Long>{
	
	List<Pret> findByPretStatut(PretStatut pretStatut);
	Page<Pret> findByPretStatut(PretStatut pretStatut, Pageable pageable);
	
	List<Pret> findByPretStatutAndUser(PretStatut pretStatut, User user);
	Page<Pret> findByPretStatutAndUser(PretStatut pretStatut, User user, Pageable pageable);
	
	List<Pret> findByPretStatutAndLivre(PretStatut pretStatut, Livre livre);
	Page<Pret> findByPretStatutAndLivre(PretStatut pretStatut, Livre livre, Pageable pageable);

}
