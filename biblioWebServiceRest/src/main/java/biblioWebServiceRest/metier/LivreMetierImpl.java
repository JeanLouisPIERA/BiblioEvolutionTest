package biblioWebServiceRest.metier;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import biblioWebServiceRest.criteria.LivreCriteria;
import biblioWebServiceRest.dao.ILivreRepository;
import biblioWebServiceRest.dao.specs.LivreSpecification;
import biblioWebServiceRest.entities.Livre;

@Service
@Transactional
public class LivreMetierImpl implements ILivreMetier{

	@Autowired
	private ILivreRepository livreRepository;
	
	
	/**
	 * Recherche multicritères des livres enregistrés
	 * @param livreCriteria
	 * @return
	 */
	@Override
	public List<Livre> searchByCriteria(LivreCriteria livreCriteria) {
		Specification<Livre> livreSpecification = new LivreSpecification(livreCriteria);
		return livreRepository.findAll(livreSpecification);
	}



}
	
	


	

	



