/**
 * Cette classe permet de réaliser ne recherche multicritères sur les prets en sélectionnant certaines propriétés 
 */
package biblioWebAppli.criteria;




/**
 * @author jeanl
 *
 */
public class PretCriteria {
	
	private Long numPret;
	private String username;
	private Long userId; 
	private Long numLivre; 
	private String titre; 
	private String auteur;
	private String nomCategorieLivre;
	public Long getNumPret() {
		return numPret;
	}
	public void setNumPret(Long numPret) {
		this.numPret = numPret;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getNumLivre() {
		return numLivre;
	}
	public void setNumLivre(Long numLivre) {
		this.numLivre = numLivre;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getAuteur() {
		return auteur;
	}
	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}
	public String getNomCategorieLivre() {
		return nomCategorieLivre;
	}
	public void setNomCategorieLivre(String nomCategorieLivre) {
		this.nomCategorieLivre = nomCategorieLivre;
	}
	
	
	
	

	
	
	
}
