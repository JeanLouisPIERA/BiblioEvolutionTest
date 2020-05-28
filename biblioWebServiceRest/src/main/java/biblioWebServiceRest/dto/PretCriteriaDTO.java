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

	private Long refPret;
	private PretStatut pretStatut;
	private UserDTO user;
	private LivreDTO livre;
	
	/**
	 * @return the refPret
	 */
	public Long getRefPret() {
		return refPret;
	}
	/**
	 * @param refPret the refPret to set
	 */
	public void setRefPret(Long refPret) {
		this.refPret = refPret;
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
