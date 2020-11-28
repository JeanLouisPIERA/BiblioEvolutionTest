/**
  * Interface de définition des méthodes Métier pour l'entité Livre
 */
package biblioWebServiceRest.metier;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import biblioWebServiceRest.criteria.LivreCriteria;
import biblioWebServiceRest.dto.LivreDTO;
import biblioWebServiceRest.entities.Livre;
import biblioWebServiceRest.exceptions.EntityAlreadyExistsException;
import biblioWebServiceRest.exceptions.EntityNotDeletableException;
import biblioWebServiceRest.exceptions.EntityNotFoundException;
import biblioWebServiceRest.exceptions.WrongNumberException;


public interface ILivreMetier {
	/**
	 * Cette méthode est utilisée par les classes métier Livre et Reservation
	 * Elle permet de mettre à jour la date de retour la plus proche pour tous les livres éligibles à la réservation
	 * Elle permet d'identifier le nombre de réservations en cours pour un livre 
	 */
	void miseAJourLivres();
	
	/**
	 * Recherche multicritères des livres enregistrés
	 * @param livreCriteria
	 * @return
	 */
	Page<Livre> searchByLivreCriteria(LivreCriteria livreCriteria, Pageable pageable);
	
	/**
	 * Creation d'un nouveau livre à referencer 
	 * La creation d'une référence se fait sur la base d'un seul exemplaire
	 * L'enregistrement de plusieurs exemplaires présent à la création se fait par simple mise à jour en suivant
	 * @param livreDTO
	 * @param numCategorie
	 * @return
	 * @throws EntityAlreadyExistsException 
	 * @throws EntityNotFoundException 
	 * @throws Exception
	 */
	Livre createLivre(LivreDTO livreDTO) throws EntityAlreadyExistsException, EntityNotFoundException;
	
	/**
	 * Mise à jour des attributs d'un livre déjà référencé
	 * @param numLivre
	 * @param livreDTO
	 * @return
	 * @throws EntityNotFoundException 
	 * @throws EntityAlreadyExistsException 
	 * @throws WrongNumberException 
	 */
	Livre updateLivre(Long numLivre, LivreDTO livreDTO) throws EntityNotFoundException, EntityAlreadyExistsException, WrongNumberException;
	
	
	/**
	 * Suppression d'une reference de livre 
	 * @param numLivre
	 * @throws EntityNotDeletableException 
	 * @throws EntityNotFoundException 
	 */
	void deleteLivre(Long numLivre) throws EntityNotFoundException, EntityNotDeletableException; 
	
}