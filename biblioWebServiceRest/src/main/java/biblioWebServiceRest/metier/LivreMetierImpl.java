package biblioWebServiceRest.metier;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import biblioWebServiceRest.dao.ILivreRepository;
import biblioWebServiceRest.dao.specs.LivreSpecification;
import biblioWebServiceRest.dao.specs.SearchCriteria;
import biblioWebServiceRest.dao.specs.SearchOperation;
import biblioWebServiceRest.entities.Categorie;
import biblioWebServiceRest.entities.Livre;
import biblioWebServiceRest.entities.LivreStatut;






@Service
@Transactional
public class LivreMetierImpl implements ILivreMetier{
	
	@Autowired
	private ILivreRepository livreRepository;
	
	
	/**
	 * Méthode pour sélectionner les livres par titre, auteur ou catégorie
	 * @param titre
	 * @param auteur
	 * @param categorie
	 * @return
	 */
	@Override
	public List<Livre> searchByTitreAndAuteurAndCategorie(String titre, String auteur, Categorie categorie) {
		LivreSpecification lsTitreAuteurCategorie = new LivreSpecification();
        lsTitreAuteurCategorie.add(new SearchCriteria("titre", titre, SearchOperation.MATCH));
        lsTitreAuteurCategorie.add(new SearchCriteria("auteur", auteur, SearchOperation.MATCH));
        lsTitreAuteurCategorie.add(new SearchCriteria("categorie", categorie, SearchOperation.EQUAL));
        List<Livre> lsTitreAuteurCategorieList = livreRepository.findAll(lsTitreAuteurCategorie);
		return lsTitreAuteurCategorieList;
	}
	
	/**
	 * Méthode pour sélectionner les livres disponibles par titre, nombre d'exemplaires et nombre d'exemplaires disponibles
	 * @param titre
	 * @param livreStatut
	 * @param nbExemplaires
	 * @param nbExemplairesDisponibles
	 * @return
	 */
	@Override
	public List<Livre> searchByTitreAndStatutDisponibleAndNbExemplairesAndNbExemplairesDisponibles(String titre,
			LivreStatut livreStatut, Integer nbExemplaires, Integer nbExemplairesDisponibles) {
		LivreSpecification lsTitreDisponibleNbExemplairesTotalEtDisponible = new LivreSpecification();
		lsTitreDisponibleNbExemplairesTotalEtDisponible.add(new SearchCriteria("titre", titre, SearchOperation.MATCH));
		lsTitreDisponibleNbExemplairesTotalEtDisponible.add(new SearchCriteria("livreStatut", LivreStatut.DIS, SearchOperation.MATCH));
		lsTitreDisponibleNbExemplairesTotalEtDisponible.add(new SearchCriteria("nbExemplaires", nbExemplaires, SearchOperation.EQUAL));
		lsTitreDisponibleNbExemplairesTotalEtDisponible.add(new SearchCriteria("nbExemplairesDisponibles", nbExemplairesDisponibles, SearchOperation.EQUAL));
		List<Livre> lsTitreDisponibleNbExemplairesTotalEtDisponibleList = livreRepository.findAll(lsTitreDisponibleNbExemplairesTotalEtDisponible);
		return lsTitreDisponibleNbExemplairesTotalEtDisponibleList;
	}
	
	/**
	 * Méthode pour sélectionner les livres disponibles par titre, auteur, catégorie, nombre d'exemplaires et nombre d'exemplaires disponibles
	 * @param titre
	 * @param auteur
	 * @param categorie
	 * @param livreStatut
	 * @param nbExemplaires
	 * @param nbExemplairesDisponibles
	 * @return
	 */
	@Override
	public List<Livre> searchLivresDisponiblesByAttributesAndNbExemplaires(String titre, String auteur, Categorie categorie, LivreStatut livreStatut, Integer nbExemplaires, 
		Integer nbExemplairesDisponibles){
		LivreSpecification lsTitreAuteurCategorie = new LivreSpecification();
	        lsTitreAuteurCategorie.add(new SearchCriteria("titre", titre, SearchOperation.MATCH));
	        lsTitreAuteurCategorie.add(new SearchCriteria("auteur", auteur, SearchOperation.MATCH));
	        lsTitreAuteurCategorie.add(new SearchCriteria("categorie", categorie, SearchOperation.EQUAL));
        LivreSpecification lsTitreDisponibleNbExemplairesTotalEtDisponible = new LivreSpecification();
			lsTitreDisponibleNbExemplairesTotalEtDisponible.add(new SearchCriteria("titre", titre, SearchOperation.MATCH));
			lsTitreDisponibleNbExemplairesTotalEtDisponible.add(new SearchCriteria("livreStatut", LivreStatut.DIS, SearchOperation.MATCH));
			lsTitreDisponibleNbExemplairesTotalEtDisponible.add(new SearchCriteria("nbExemplaires", nbExemplaires, SearchOperation.EQUAL));
			lsTitreDisponibleNbExemplairesTotalEtDisponible.add(new SearchCriteria("nbExemplairesDisponibles", nbExemplairesDisponibles, SearchOperation.EQUAL));
		List<Livre> selectionLivresDisponibles = livreRepository.findAll(Specification.where(lsTitreAuteurCategorie).and(lsTitreDisponibleNbExemplairesTotalEtDisponible));
		return selectionLivresDisponibles;
				
				
		
	}
		


}
