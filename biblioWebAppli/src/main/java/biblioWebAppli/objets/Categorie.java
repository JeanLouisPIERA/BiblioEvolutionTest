/**
 * L'entité Categorie permet d'instancier un objet unique référencé dans l'API REST 
 */
package biblioWebAppli.objets;

/**
 * @author jeanl
 *
 */
public class Categorie {
	
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
