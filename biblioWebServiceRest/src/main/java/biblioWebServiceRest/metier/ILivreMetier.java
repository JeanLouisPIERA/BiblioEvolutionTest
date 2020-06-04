/**
  * Interface de définition des méthodes Métier pour l'entité Livre
 */
package biblioWebServiceRest.metier;

import java.util.List;

import biblioWebServiceRest.criteria.LivreCriteria;
import biblioWebServiceRest.dto.LivreCriteriaDTO;
import biblioWebServiceRest.dto.LivreDTO;
import biblioWebServiceRest.entities.Livre;


public interface ILivreMetier {
	
	/**
	 * Recherche multicritères des livres enregistrés
	 * @param livreCriteria
	 * @return
	 */
	List<LivreDTO> searchByCriteria(LivreCriteriaDTO livreCriteriaDTO);
	
	/**
	 * Creation d'un nouveau livre à referencer 
	 * La creation d'une référence se fait sur la base d'un seul exemplaire
	 * L'enregistrement de plusieurs exemplaires présent à la création se fait par simple mise à jour en suivant
	 * @param titre
	 * @param auteur
	 * @param numCategorie
	 * @return
	 * @throws Exception
	 */
	LivreDTO createLivre(String titre, String auteur, Long numCategorie) throws Exception;
	
	/**
	 * Enregistrement d'un ou plusieurs nouveaux exemplaires pour une reference de livre déjà enregistree
	 * @param numLivre
	 * @param nombreNouveauxExemplaires
	 * @return
	 */
	LivreDTO createExemplaire(Long numLivre, Integer nombreNouveauxExemplaires) throws Exception;
	
	/**
	 * Suppression d'un ou plusieurs exemplaires pour une reference de livre déjà enregistrée
	 * @param numLivre
	 * @param nombreExemplairesASupprimer
	 * @return
	 */
	LivreDTO deleteExemplaire(Long numLivre, Integer nombreExemplairesASupprimer)throws Exception;
	
	/**
	 * Cette méthode permet de changer un livre de categorie en cas de modification de l'organisation des categories de livres
	 * Par exemple suppression d'une categorie ou nouvelle categorie plus adaptee
	 * @param numLivre
	 * @param nomCategorie
	 * @return
	 */
	LivreDTO changeCategorie(Long numLivre, Long numCategorie) throws Exception;
	
	/**
	 * Suppression d'une reference de livre 
	 * @param numLivre
	 */
	void deleteLivre(Long numLivre) throws Exception; 
	
}