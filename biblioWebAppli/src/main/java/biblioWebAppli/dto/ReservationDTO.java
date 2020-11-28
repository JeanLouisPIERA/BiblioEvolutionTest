package biblioWebAppli.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ReservationDTO {
	
	@NotNull
	@Positive
	private Long idUser;
	@NotNull
	@Positive
	private Long numLivre;
	public Long getIdUser() {
		return idUser;
	}
	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}
	public Long getNumLivre() {
		return numLivre;
	}
	public void setNumLivre(Long numLivre) {
		this.numLivre = numLivre;
	}
	
	

}
