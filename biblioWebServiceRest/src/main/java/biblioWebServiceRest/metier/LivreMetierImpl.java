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
		if(!livreCriteriaList.isEmpty()) 
			throw new InternalServerErrorException("Ce livre a déjà été référencé");
		
		Optional<Categorie> categorie = categorieRepository.findById(numCategorie); 
		if(!categorie.isPresent()) 
			throw new NotFoundException("Le livre ne peut pas etre enregistre car la categorie saisie n'existe pas");
		
		Livre newLivre = new Livre();
		newLivre.setAuteur(auteur);
		newLivre.setTitre(titre);
		newLivre.setCategorie(categorie.get());
		newLivre.setNbExemplaires(1);
		newLivre.setNbExemplairesDisponibles(1);
		
		return livreRepository.save(newLivre);
	}


	/**
	 * Enregistrement d'un ou plusieurs nouveaux exemplaires pour une reference de livre déjà enregistree
	 * @param numLivre
	 * @param nombreNouveauxExemplaires
	 * @return
	 */
	@Override
	public Livre createExemplaire(Long numLivre, Integer nombreNouveauxExemplaires) throws Exception {
		Optional<Livre> livreUpdate = livreRepository.findById(numLivre);
		if(!livreUpdate.isPresent()) 
			throw new NotFoundException("La référence de livre saisie n'existe pas");
		livreUpdate.get().setNbExemplaires(livreUpdate.get().getNbExemplaires() + nombreNouveauxExemplaires);
		livreUpdate.get().setNbExemplairesDisponibles(livreUpdate.get().getNbExemplairesDisponibles() + nombreNouveauxExemplaires);
		return livreUpdate.get();
	}


	/**
	 * @param numLivre
	 * @param nombreExemplairesASupprimer
	 * @return
	 */
	@Override
	public Livre deleteExemplaire(Long numLivre, Integer nombreExemplairesASupprimer) throws Exception {
		Optional<Livre> livreUpdate = livreRepository.findById(numLivre);
		if(!livreUpdate.isPresent()) 
			throw new NotFoundException("La référence de livre saisie n'existe pas");
		if(livreUpdate.get().getNbExemplaires() < nombreExemplairesASupprimer) 
			throw new InternalServerErrorException("Transaction impossible : le nombre d'exemplaires à supprimer est supérieur au nombre total d'exemplaires enregistrés pour cet ouvrage");
		if(livreUpdate.get().getNbExemplairesDisponibles() < nombreExemplairesASupprimer) 
			throw new InternalServerErrorException("Transaction impossible : le nombre d'exemplaires à supprimer est supérieur au nombre total d'exemplaires disponibles pour cet ouvrage");
		livreUpdate.get().setNbExemplaires(livreUpdate.get().getNbExemplaires() - nombreExemplairesASupprimer);
		livreUpdate.get().setNbExemplairesDisponibles(livreUpdate.get().getNbExemplairesDisponibles() - nombreExemplairesASupprimer);
		return livreUpdate.get();
	}


	/**
	 * Cette méthode permet de changer un livre de categorie en cas de modification de l'organisation des categories de livres
	 * Par exemple suppression d'une categorie ou nouvelle categorie plus adaptee
	 * @param numLivre
	 * @param nomCategorie
	 * @return
	 */
	@Override
	public Livre changeCategorie(Long numLivre, Long numCategorie) throws Exception {
		Optional<Livre> livreUpdate = livreRepository.findById(numLivre);
		if(!livreUpdate.isPresent()) 
			throw new NotFoundException("La référence de livre saisie n'existe pas");
		
		Optional<Categorie> newCategorie = categorieRepository.findById(numCategorie); 
		if(!newCategorie.isPresent()) 
			throw new NotFoundException("Le livre ne peut pas etre enregistre car la categorie saisie n'existe pas");
		
		livreUpdate.get().setCategorie(newCategorie.get());
		return livreUpdate.get();
	}


	/**
	 * Suppression d'un ou plusieurs exemplaires pour une reference de livre déjà enregistrée
	 * La méthode envoie une exception si la référence du livre à supprimer n'existe pas
	 * La méthode envoie une exception s'il existe encore des prets encours 
	 * Pour simplifier l'envoi de l'exception, on compare le nombre de livres disponibles et le nombre total de livres référencés
	 * Si le nombre de livres disponibles est inférieur au nombre total de livres, c'est qu'il existe encore des prêts en cours
	 * @param numLivre
	 * @exception
	 */
	@Override
	public void deleteLivre(Long numLivre) throws Exception {
		Optional<Livre> livreToDelete = livreRepository.findById(numLivre);
		if(!livreToDelete.isPresent()) 
			throw new NotFoundException("Le livre que vous voulez supprimer n'existe pas"); 
		if(livreToDelete.get().getNbExemplairesDisponibles()!=livreToDelete.get().getNbExemplaires()) 
			throw new InternalServerErrorException("Vous ne pouvez pas supprimer ce livre qui a encore des prêts encours"); 
		livreRepository.deleteById(numLivre);
		
	}



}
	
	


	

	



