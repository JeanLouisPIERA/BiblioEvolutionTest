/**
 * 
 */
package biblioWebServiceRest.dto;



/**
 * @author jeanl
 *
 */
public class LivreDTO {
	
	private Long numLivre; 
	private String titre; 
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
	 * @return the titre
	 */
	public String getTitre() {
		return titre;
	}
	/**
	 * @param titre the titre to set
	 */
	public void setTitre(String titre) {
		this.titre = titre;
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
