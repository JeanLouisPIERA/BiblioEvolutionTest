/**
 * Cette classe permet de réaliser ne recherche multicritères sur les livres en referencement en sélectionnant certaines propriétés 
 */
package biblioWebServiceRest.criteria;



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
	
	
	
	public LivreCriteria() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public LivreCriteria(Long numLivre, String titre, String auteur, String nomCategorie,
			Integer nbExemplairesDisponibles) {
		super();
		this.numLivre = numLivre;
		this.titre = titre;
		this.auteur = auteur;
		this.nomCategorie = nomCategorie;
		this.nbExemplairesDisponibles = nbExemplairesDisponibles;
	}


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
	public Integer getNbExemplairesDisponibles() {
		return nbExemplairesDisponibles;
	}
	public void setNbExemplairesDisponibles(Integer nbExemplairesDisponibles) {
		this.nbExemplairesDisponibles = nbExemplairesDisponibles;
	}
	
	
}
