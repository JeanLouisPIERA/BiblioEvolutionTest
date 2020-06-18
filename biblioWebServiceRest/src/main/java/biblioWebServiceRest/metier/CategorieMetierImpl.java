/**
 * Classe d'implémentation des méthodes Métier pour l'entité Catégorie
 */
package biblioWebServiceRest.metier;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import biblioWebServiceRest.criteria.CategorieCriteria;
import biblioWebServiceRest.dao.ICategorieRepository;
import biblioWebServiceRest.dao.specs.CategorieSpecification;
import biblioWebServiceRest.dto.CategorieDTO;
import biblioWebServiceRest.entities.Categorie;
import biblioWebServiceRest.exceptions.BiblioException;
import biblioWebServiceRest.exceptions.BookNotAvailableException;
import biblioWebServiceRest.exceptions.EntityAlreadyExistsException;
import biblioWebServiceRest.exceptions.EntityNotDeletableException;
import biblioWebServiceRest.exceptions.EntityNotFoundException;
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
		 * @param categorieDTO
		 * @return
		 * @throws EntityAlreadyExistsException
		 */
		@Override
		public Categorie createCategorie(CategorieDTO categorieDTO) throws EntityAlreadyExistsException {
			
			Categorie newCategorie = categorieMapper.categorieDTOToCategorie(categorieDTO);
			Optional<Categorie> testNewCategorie = categorieRepository.findByNomCategorie(newCategorie.getNomCategorie());
			if(testNewCategorie.isPresent()) 
				throw new EntityAlreadyExistsException("La categorie que vous souhaitez creer existe deja");
			categorieRepository.save(newCategorie);
			return newCategorie;
			
			
		}
		/**
		 * Méthode pour supprimer une catégorie 
		 * La méthode retourne true si la suppression a été effectuée
		 * La méthode envoie une InternalServerException si la réference de la categorie à supprimer contient au moins un livre
		 * La méthode envoie une NotFoundException si la réference n'existe pas
		 * @param numCategorie
		 * @throws EntityNotFoundException 
		 * @throws EntityNotDeletableException 
		 * @throws Exception
		 */
		@Override
		public void deleteCategorie(Long numCategorie) throws EntityNotFoundException, EntityNotDeletableException  {
			Optional<Categorie> categorieToDelete = categorieRepository.findById(numCategorie);
			if(!categorieToDelete.isPresent()) 
				throw new EntityNotFoundException("La categorie que vous voulez supprimer n'existe pas"); 
			if(!categorieToDelete.get().getLivres().isEmpty()) 
				throw new EntityNotDeletableException("Vous ne pouvez pas supprimer cette categorie qui contient des livres"); 
			categorieRepository.deleteById(categorieToDelete.get().getNumCategorie());
		}
		
		
		/**
		 * Méthode pour identifier toutes les categories de livres en referencement 
		 * @param categorieCriteria
		 * @return
		 */
		@Override
		public Page<Categorie> searchByCriteria(CategorieCriteria categorieCriteria, Pageable pageable) {
			
			Specification<Categorie> categorieSpecification = new CategorieSpecification(categorieCriteria);
			Page<Categorie> categories = categorieRepository.findAll(categorieSpecification, pageable);
			return categories;
			
		
		}
		
		
		
	
}
