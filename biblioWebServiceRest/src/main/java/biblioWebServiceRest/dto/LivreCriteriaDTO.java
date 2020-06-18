/**
 * Classe de sérialization des critères de recherche pour les livres pour le Mapping DTO 
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
	private Integer numCategorie;
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
	 * @return the numCategorie
	 */
	public Integer getNumCategorie() {
		return numCategorie;
	}
	/**
	 * @param numCategorie the numCategorie to set
	 */
	public void setNumCategorie(Integer numCategorie) {
		this.numCategorie = numCategorie;
	}
	
	
	
	
	

}
