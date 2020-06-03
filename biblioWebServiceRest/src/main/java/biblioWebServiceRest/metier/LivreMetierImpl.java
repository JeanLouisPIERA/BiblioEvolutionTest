package biblioWebServiceRest.metier;


import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import biblioWebServiceRest.criteria.LivreCriteria;
import biblioWebServiceRest.dao.ICategorieRepository;
import biblioWebServiceRest.dao.ILivreRepository;
import biblioWebServiceRest.dao.specs.LivreSpecification;
import biblioWebServiceRest.entities.Categorie;
import biblioWebServiceRest.entities.Livre;
import biblioWebServiceRest.exceptions.InternalServerErrorException;
import biblioWebServiceRest.exceptions.NotFoundException;

@Service
@Transactional
public class LivreMetierImpl implements ILivreMetier{

	@Autowired
	private ILivreRepository livreRepository;
	@Autowired
	private ICategorieRepository categorieRepository; 
	
	
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


	/**
	 * Creation d'un nouveau livre à referencer 
	 * La creation d'une référence se fait sur la base d'un seul exemplaire
	 * L'enregistrement de plusieurs exemplaires présent à la création se fait par simple mise à jour en suivant
	 * La méthode envoie une exception si une réference existe déjà avec le même titre et le même auteur quelle que soit sa categorie
	 * La méthode envoie une exception si la catégorie dans laquelle doit être enregistrer le livre a creer n'existe pas
	 * Pour les références qui doivent enregistrer plusieurs tomes d'un même titre, il faut enregistrer le numéro du tome dans le titre (exemple Tome 1)
	 * @param titre
	 * @param auteur
	 * @return
	 * @throws Exception
	 */
	@Override
	public Livre createLivre(String titre, String auteur, Long numCategorie) throws Exception {
		LivreCriteria livreCriteria = new LivreCriteria(); 
		livreCriteria.setTitre(titre);
		livreCriteria.setAuteur(auteur);
		List<Livre> livreCriteriaList = this.searchByCriteria(livreCriteria);
		if(!livreCriteriaList.isEmpty()) throw new InternalServerErrorException("Ce livre a déjà été référencé");
		Livre newLivre = new Livre();
		newLivre.setAuteur(auteur);
		newLivre.setTitre(titre);
		Optional<Categorie> categorie = categorieRepository.findById(numCategorie); 
		if(!categorie.isPresent()) throw new NotFoundException("Le livre ne peut pas etre enregistre car la categorie saisie n'existe pas");
		newLivre.setCategorie(categorie.get());
		newLivre.setNbExemplaires(1);
		newLivre.setNbExemplairesDisponibles(1);
		return livreRepository.save(newLivre);
	}


	/**
	 * @param numLivre
	 * @param nombreNouveauxExemplaires
	 * @return
	 */
	@Override
	public Livre createExemplaire(Long numLivre, Integer nombreNouveauxExemplaires) {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * @param numLivre
	 * @param nombreExemplairesASupprimer
	 * @return
	 */
	@Override
	public Livre deleteExemplaire(Long numLivre, Integer nombreExemplairesASupprimer) {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * @param numLivre
	 * @param nomCategorie
	 * @return
	 */
	@Override
	public Livre changeCategorie(Long numLivre, String nomCategorie) {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * @param numLivre
	 */
	@Override
	public void deleteLivre(Long numLivre) {
		// TODO Auto-generated method stub
		
	}



}
	
	


	

	



