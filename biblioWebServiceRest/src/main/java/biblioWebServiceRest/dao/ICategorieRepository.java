package biblioWebServiceRest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import biblioWebServiceRest.entities.Categorie;


@Repository
public interface ICategorieRepository extends JpaRepository<Categorie, Long>, JpaSpecificationExecutor<Categorie> {

}
