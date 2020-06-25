/**
 * Copie à l'identique du DTO de l'API REST
 */
package biblioWebAppli.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author jeanl
 *
 */
public class CategorieDTO {
	
	@NotEmpty
	@Size(min = 5, max = 25)
	private String nomCategorie;

	/**
	 * @return the nomCategorie
	 */
	public String getNomCategorie() {
		return nomCategorie;
	}

	/**
	 * @param nomCategorie the nomCategorie to set
	 */
	public void setNomCategorie(String nomCategorie) {
		this.nomCategorie = nomCategorie;
	}
	
	

}
