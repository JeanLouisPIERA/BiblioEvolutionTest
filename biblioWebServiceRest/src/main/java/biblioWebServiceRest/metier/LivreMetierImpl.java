package biblioWebServiceRest.metier;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import biblioWebServiceRest.criteria.LivreCriteria;
import biblioWebServiceRest.dao.ICategorieRepository;
import biblioWebServiceRest.dao.ILivreRepository;
import biblioWebServiceRest.dao.specs.LivreSpecification;
import biblioWebServiceRest.dao.specs.SearchCriteria;
import biblioWebServiceRest.dao.specs.SearchOperation;
import biblioWebServiceRest.entities.Categorie;
import biblioWebServiceRest.entities.Livre;
import biblioWebServiceRest.mapper.LivreMapper;






@Service
@Transactional
public class LivreMetierImpl implements ILivreMetier{
	
	@Autowired
	private ILivreRepository livreRepository;
	@Autowired
	private ICategorieRepository categorieRepository;
	
	/**
	 * Cette méthode permet de sélectionner les livres en fonction de leur titre, du nom de leur auteur et de leur catégorie
	 * Si aucun paramètre n'est renseigné, la méthode renvoie la liste de tous les livres enregistrés dans la base
	 * Le titre et le nom de l'auteur doivent simplement matcher
	 * Le nom de la catégorie doit être égal sinon la méthode catche une exception
	 * @param titre
	 * @param auteur
	 * @param categorie
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	/**
	@Override
	public List<Livre> searchByTitreAndAuteurAndCategorie(String titre, String auteur, String nomCategorie){
		List<Livre> livresByTitreAuteurCategorieList = new ArrayList<>() ;
		LivreSpecification lsTitre = new LivreSpecification(); 
		lsTitre.add(new SearchCriteria("titre", titre, SearchOperation.MATCH));
		
		LivreSpecification lsAuteur = new LivreSpecification(); 
		lsAuteur.add(new SearchCriteria("auteur", auteur, SearchOperation.MATCH));
		
				LivreSpecification lsCategorie = new LivreSpecification(); 
				Categorie categorie;
				try {
					categorie = categorieRepository.findByNomCategorie(nomCategorie);
					nomCategorie = categorie.getNomCategorie();	
					lsCategorie.add(new SearchCriteria("categorie",categorie, SearchOperation.EQUAL));
					livresByTitreAuteurCategorieList = livreRepository.findAll(Specification.where(lsTitre).and(lsAuteur).and(lsCategorie));
					}
				catch (Exception e){
					if(nomCategorie.isEmpty()){
						livresByTitreAuteurCategorieList = livreRepository.findAll(Specification.where(lsTitre).and(lsAuteur));
					}else throw new RuntimeException("erreur sur la saisie du nom de catégorie");		
				}
				return livresByTitreAuteurCategorieList;
				}
	**/

	/**
	 * @param livreCriteria
	 * @param page
	 * @param size
	 * @return
	 */
	@Override
	public Page<Livre> searchByCriteria(LivreCriteria livreCriteria, int page, int size) {
		Specification<Livre> livreSpecification = new LivreSpecification(livreCriteria);
		
		return livreRepository.findAll(livreSpecification, PageRequest.of(page, size));
	}


}
	
	


	

	



