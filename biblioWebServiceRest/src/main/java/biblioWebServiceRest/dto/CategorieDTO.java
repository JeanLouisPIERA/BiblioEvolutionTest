/**
 * Classe de sérialization des catégories pour le Mapping DTO 
 */
package biblioWebServiceRest.dto;

/**
 * @author jeanl
 *
 */
public class CategorieDTO {
	
	private Long numCategorie;
	
	private String nomCategorie;
	
	/**
	 * @return the numCategorie
	 */
	public Long getNumCategorie() {
		return numCategorie;
	}
	/**
	 * @param numCategorie the numCategorie to set
	 */
	public void setNumCategorie(Long numCategorie) {
		this.numCategorie = numCategorie;
	}
	
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
