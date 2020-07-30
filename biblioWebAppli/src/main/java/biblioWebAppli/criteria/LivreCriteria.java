/**
 * Copie à l'identique du Criteria Livre de l'API REST utilisée pour la recherche multicritères
 */
package biblioWebAppli.criteria;





/**
 * @author jeanl
 *
 */
public class LivreCriteria {

	private Long numLivre; 
	private String titre; 
	private String auteur;
	private String nomCategorie;
	
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
