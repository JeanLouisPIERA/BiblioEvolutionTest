/**
 * Classe de sérialization des prets pour le Mapping DTO 
 */
package biblioWebServiceRest.dto;



import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;




/**
 * @author jeanl
 *
 */


public class PretDTO {
	@NotNull
	@Positive
	private Long idUser;
	@NotNull
	@Positive
	private Long numLivre;
	
	public PretDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public PretDTO(@NotNull @Positive Long idUser, @NotNull @Positive Long numLivre) {
		super();
		this.idUser = idUser;
		this.numLivre = numLivre;
	}


	/**
	 * @return the idUser
	 */
	public Long getIdUser() {
		return idUser;
	}
	/**
	 * @param idUser the idUser to set
	 */
	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}
	/**
	 * @return the numLivre
	 */
	public Long getNumLivre() {
		return numLivre;
	}
	/**
	 * @param numLivre the numLivre to set
	 */
	public void setNumLivre(Long numLivre) {
		this.numLivre = numLivre;
	}
	
	
	
	
	
	
	
	
}
