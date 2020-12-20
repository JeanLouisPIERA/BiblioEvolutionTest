/**
 * L'entité Livre permet d'instancier un objet unique référencé dans l'API REST 
 */
package biblioWebAppli.objets;

public class Livre {
	
	private Long numLivre; 
	
	private String titre; 
	
	private String auteur; 
	
	private Integer nbExemplaires;
	
	private Integer nbExemplairesDisponibles;
	
	private Categorie categorie;
	
	private String dateRetourPrevuePlusProche;
	
	private Integer nbReservationsEnCours;
	
	private Integer nbReservataires;

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
	 * @return the nbExemplaires
	 */
	public Integer getNbExemplaires() {
		return nbExemplaires;
	}

	/**
	 * @param nbExemplaires the nbExemplaires to set
	 */
	public void setNbExemplaires(Integer nbExemplaires) {
		this.nbExemplaires = nbExemplaires;
	}

	/**
	 * @return the nbExemplairesDisponibles
	 */
	public Integer getNbExemplairesDisponibles() {
		return nbExemplairesDisponibles;
	}

	/**
	 * @param nbExemplairesDisponibles the nbExemplairesDisponibles to set
	 */
	public void setNbExemplairesDisponibles(Integer nbExemplairesDisponibles) {
		this.nbExemplairesDisponibles = nbExemplairesDisponibles;
	}

	/**
	 * @return the categorie
	 */
	public Categorie getCategorie() {
		return categorie;
	}

	/**
	 * @param categorie the categorie to set
	 */
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	

	public Integer getNbReservationsEnCours() {
		return nbReservationsEnCours;
	}

	public void setNbReservationsEnCours(Integer nbReservationsEnCours) {
		this.nbReservationsEnCours = nbReservationsEnCours;
	}

	public Integer getNbReservataires() {
		return nbReservataires;
	}

	public void setNbReservataires(Integer nbReservataires) {
		this.nbReservataires = nbReservataires;
	}

	public String getDateRetourPrevuePlusProche() {
		return dateRetourPrevuePlusProche;
	}

	public void setDateRetourPrevuePlusProche(String dateRetourPrevuePlusProche) {
		this.dateRetourPrevuePlusProche = dateRetourPrevuePlusProche;
	}
	
	
	
	
	

}
