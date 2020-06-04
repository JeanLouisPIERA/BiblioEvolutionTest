/**
 * Classe d'implémentation des méthodes Métier pour l'entité Catégorie
 */
package biblioWebServiceRest.metier;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import biblioWebServiceRest.criteria.CategorieCriteria;
import biblioWebServiceRest.dao.ICategorieRepository;
import biblioWebServiceRest.dao.specs.CategorieSpecification;
import biblioWebServiceRest.dto.CategorieDTO;
import biblioWebServiceRest.entities.Categorie;
import biblioWebServiceRest.exceptions.InternalServerErrorException;
import biblioWebServiceRest.exceptions.NotFoundException;
import biblioWebServiceRest.mapper.CategorieMapper;


@Service
@Transactional
public class CategorieMetierImpl implements ICategorieMetier{
	
	@Autowired
	private ICategorieRepository categorieRepository;
	@Autowired
	private CategorieMapper categorieMapper;

		
		/**
		 * Methode pour creer une nouvelle categorie de livres
		 * La méthode envoie une exception si le nom de la categorie à créer existe déjà pour éviter les doublons
		 * @param nomCategorie
		 * @return
		 * @throws Exception
		 */
		@Override
		public CategorieDTO createCategorie(String nomCategorie) throws Exception {
				
			Optional<Categorie> testNewCategorie = categorieRepository.findByNomCategorie(nomCategorie);
			if(testNewCategorie.isPresent()) 
				throw new InternalServerErrorException("La categorie que vous souhaitez creer existe deja");
			Categorie newCategorie = new Categorie();
			newCategorie.setNomCategorie(nomCategorie);
			categorieRepository.save(newCategorie);
			CategorieDTO newCategorieDTO = categorieMapper.categorieToCategorieDTO(newCategorie);
			return newCategorieDTO;
			
			
		}
		/**
		 * Méthode pour supprimer une catégorie 
		 * La méthode retourne true si la suppression a été effectuée
		 * La méthode envoie une InternalServerException si la réference de la categorie à supprimer contient au moins un livre
		 * La méthode envoie une NotFoundException si la réference n'existe pas
		 * @param numCategorie
		 * @throws Exception
		 */
		@Override
		public void deleteCategorie(Long numCategorie) throws Exception {
			Optional<Categorie> categorieToDelete = categorieRepository.findById(numCategorie);
			if(!categorieToDelete.isPresent()) 
				throw new NotFoundException("La categorie que vous voulez supprimer n'existe pas"); 
			if(!categorieToDelete.get().getLivres().isEmpty()) 
				throw new InternalServerErrorException("Vous ne pouvez pas supprimer cette categorie qui contient des livres"); 
			categorieRepository.deleteById(numCategorie);
		}
		/**
		 * Méthode pour identifier toutes les categories de livres en referencement 
		 * @param categorieCriteria
		 * @return
		 */
		@Override
		public List<CategorieDTO> searchByCriteria(CategorieCriteria categorieCriteria) {
			
			Specification<Categorie> categorieSpecification = new CategorieSpecification(categorieCriteria);
			List<Categorie> categories = categorieRepository.findAll(categorieSpecification);
			List<CategorieDTO> categorieDTOs = categorieMapper.
					categoriesToCategorieDTOs(categories);
			return categorieDTOs;
			
			//Specification<Categorie> categorieSpecification = new CategorieSpecification(categorieCriteria);
			//return categorieRepository.findAll(categorieSpecification);
		
		}
		
		
	
}
