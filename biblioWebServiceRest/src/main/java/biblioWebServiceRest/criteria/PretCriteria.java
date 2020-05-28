/**
 * 
 */
package biblioWebServiceRest.criteria;

import biblioWebServiceRest.entities.Livre;
import biblioWebServiceRest.entities.PretStatut;
import biblioWebServiceRest.entities.User;

/**
 * @author jeanl
 *
 */
public class PretCriteria {
	
	private Long numPret;
	private PretStatut pretStatut;
	private User user;
	private Livre livre;
	/**
	 * @return the numPret
	 */
	public Long getNumPret() {
		return numPret;
	}
	/**
	 * @param numPret the numPret to set
	 */
	public void setNumPret(Long numPret) {
		this.numPret = numPret;
	}
	
	/**
	 * @return the pretStatut
	 */
	public PretStatut getPretStatut() {
		return pretStatut;
	}
	/**
	 * @param pretStatut the pretStatut to set
	 */
	public void setPretStatut(PretStatut pretStatut) {
		this.pretStatut = pretStatut;
	}
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @return the livre
	 */
	public Livre getLivre() {
		return livre;
	}
	/**
	 * @param livre the livre to set
	 */
	public void setLivre(Livre livre) {
		this.livre = livre;
	}

	
	
	
}
