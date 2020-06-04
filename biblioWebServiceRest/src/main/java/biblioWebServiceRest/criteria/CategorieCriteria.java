/** 
* Cette classe permet de rechercher par son nom une catégorie de livres en referencement pour vérifier si elle existe 
 */
package biblioWebServiceRest.criteria;



/**
 * @author jeanl
 *
 */
public class CategorieCriteria {
	
	
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
