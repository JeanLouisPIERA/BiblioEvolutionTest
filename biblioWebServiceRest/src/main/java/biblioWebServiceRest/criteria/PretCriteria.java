/**
 * Cette classe permet de réaliser ne recherche multicritères sur les prets en sélectionnant certaines propriétés 
 */
package biblioWebServiceRest.criteria;


/**
 * @author jeanl
 *
 */
public class PretCriteria {
	
	private Long numPret;
	private String username;
	private Integer userId; 
	private Integer numLivre; 
	private String titre; 
	private String auteur;
	private String nomCategorieLivre;
	private String code; 
	/**
	 * @return the numPret
	 */
	public Long getNumPret() {
		return numPret;
	}
	
	/**
	 * @param numPret the numPret to set
	 */
	public void setNumPret(Long numPret) {
		this.numPret = numPret;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getNumLivre() {
		return numLivre;
	}

	public void setNumLivre(Integer numLivre) {
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
	 * @return the nomCategorieLivre
	 */
	public String getNomCategorieLivre() {
		return nomCategorieLivre;
	}

	/**
	 * @param nomCategorieLivre the nomCategorieLivre to set
	 */
	public void setNomCategorieLivre(String nomCategorieLivre) {
		this.nomCategorieLivre = nomCategorieLivre;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	

	
	

	
	
	
	
	
	

	
	
	
}
