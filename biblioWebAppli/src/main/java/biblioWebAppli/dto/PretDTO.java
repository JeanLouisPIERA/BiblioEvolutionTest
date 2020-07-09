/**
 * Classe de sérialization des prets pour le Mapping DTO 
 */
package biblioWebAppli.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import biblioWebServiceRest.entities.PretStatut;



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
