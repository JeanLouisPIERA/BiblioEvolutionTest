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
	private Integer nbExemplairesDisponibles;
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
	public String getNomCategorie() {
		return nomCategorie;
	}
	public void setNomCategorie(String nomCategorie) {
		this.nomCategorie = nomCategorie;
	}
	public Integer getNbExemplairesDisponibles() {
		return nbExemplairesDisponibles;
	}
	public void setNbExemplairesDisponibles(Integer nbExemplairesDisponibles) {
		this.nbExemplairesDisponibles = nbExemplairesDisponibles;
	} 
	
	
	
}
