package biblioWebServiceRest.metier;

import java.util.List;

import biblioWebServiceRest.criteria.LivreCriteria;
import biblioWebServiceRest.entities.Livre;


public interface ILivreMetier {
	
	/**
	 * Recherche multicritères des livres enregistrés
	 * @param livreCriteria
	 * @return
	 */
	List<Livre> searchByCriteria(LivreCriteria livreCriteria);
}