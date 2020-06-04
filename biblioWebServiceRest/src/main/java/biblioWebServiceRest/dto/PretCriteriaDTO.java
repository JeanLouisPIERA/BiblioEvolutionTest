/**
 * Classe de sérialization des critères de recherche des prêts pour le Mapping DTO 
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
	private LivreCriteriaDTO livre;
	
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
	public LivreCriteriaDTO getLivre() {
		return livre;
	}
	/**
	 * @param livre the livre to set
	 */
	public void setLivre(LivreCriteriaDTO livre) {
		this.livre = livre;
	}
	
	
	

}
