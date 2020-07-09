/**
 * Classe de sérialization des catégories pour le Mapping DTO 
 */
package biblioWebServiceRest.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author jeanl
 *
 */
public class CategorieDTO {
	
	@NotEmpty
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
