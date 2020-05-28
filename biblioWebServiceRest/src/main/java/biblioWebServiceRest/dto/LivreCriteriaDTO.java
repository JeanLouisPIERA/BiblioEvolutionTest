/**
 * 
 */
package biblioWebServiceRest.dto;


/**
 * @author jeanl
 *
 */
public class LivreCriteriaDTO {
	
	private Long numLivre; 
	private String nomLivre; 
	private String auteur;
	private CategorieDTO categorie;
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
	
	/**
	 * @return the nomLivre
	 */
	public String getNomLivre() {
		return nomLivre;
	}
	/**
	 * @param nomLivre the nomLivre to set
	 */
	public void setNomLivre(String nomLivre) {
		this.nomLivre = nomLivre;
	}
	/**
	 * @return the auteur
	 */
	public String getAuteur() {
		return auteur;
	}
	/**
	 * @param auteur the auteur to set
	 */
	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}
	/**
	 * @return the categorie
	 */
	public CategorieDTO getCategorie() {
		return categorie;
	}
	/**
	 * @param categorie the categorie to set
	 */
	public void setCategorie(CategorieDTO categorie) {
		this.categorie = categorie;
	}
	
	
	

}
