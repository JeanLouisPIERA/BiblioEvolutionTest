/**
 * 
 */
package biblioWebServiceRest.dto;



import biblioWebServiceRest.entities.PretStatut;


/**
 * @author jeanl
 *
 */
public class PretCriteriaDTO {

	private Long numPret;
	private PretStatut pretStatut;
	private UserDTO user;
	private LivreDTO livre;
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
	public UserDTO getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(UserDTO user) {
		this.user = user;
	}
	/**
	 * @return the livre
	 */
	public LivreDTO getLivre() {
		return livre;
	}
	/**
	 * @param livre the livre to set
	 */
	public void setLivre(LivreDTO livre) {
		this.livre = livre;
	}
	
	
	

}
