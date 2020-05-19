package biblioWebServiceRest.metier;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import biblioWebServiceRest.dao.ICategorieRepository;
import biblioWebServiceRest.dao.ILivreRepository;
import biblioWebServiceRest.dao.specs.LivreSpecification;
import biblioWebServiceRest.dao.specs.SearchCriteria;
import biblioWebServiceRest.dao.specs.SearchOperation;
import biblioWebServiceRest.entities.Categorie;
import biblioWebServiceRest.entities.Livre;






@Service
@Transactional
public class LivreMetierImpl implements ILivreMetier{
	
	@Autowired
	private ILivreRepository livreRepository;
	@Autowired
	private ICategorieRepository categorieRepository;
	
	
	@Override
	public List<Livre> searchAllLivres() {
		List<Livre> livres = livreRepository.findAll();
		return livres;
	}


	/**
	 * Cette méthode permet de sélectionner les livres en fonction de leur titre, du nom de leur auteur et de leur catégorie
	 * @param titre
	 * @param auteur
	 * @param categorie
	 * @return
	 */
	@Override
	public List<Livre> searchByTitreAndAuteurAndCategorie(String titre, String auteur, String nomCategorie) {
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
					catch (Exception e) {
					if(!nomCategorie.equals("All")) {
						System.out.println("4"+nomCategorie);
					}else {
					System.out.println("2"+"true");
					livresByTitreAuteurCategorieList = livreRepository.findAll(Specification.where(lsTitre).and(lsAuteur));
					}							
				}
				return livresByTitreAuteurCategorieList;
				}
			}

	
	


	

	



