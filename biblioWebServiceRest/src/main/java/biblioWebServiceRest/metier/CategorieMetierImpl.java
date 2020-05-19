package biblioWebServiceRest.metier;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biblioWebServiceRest.dao.ICategorieRepository;
import biblioWebServiceRest.entities.Categorie;


@Service
@Transactional
public class CategorieMetierImpl implements ICategorieMetier{
	
	@Autowired
	private ICategorieRepository categorieRepository;

		/**
		 * Méthode pour sélectionner toutes les catégories
		 * @return
		 */
		@Override
		public List<Categorie> searchAllCategories() {
			List<Categorie> categories = categorieRepository.findAll();
			return categories;
		}
		/**
		 * Méthode pour sélection les catégories par leur nom
		 * @param nomCategorie
		 * @return
		 */
		@Override
		public Categorie searchByNomCategorie(String nomCategorie) {
			Categorie categorie = categorieRepository.findByNomCategorie(nomCategorie);
			return categorie;
			
		}
	
}
