/**
 * L'entité Livre permet d'instancier un objet unique référencé dans la bibliothèque et de gérer plusieurs exemplaires 
 */
package biblioWebServiceRest.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;



import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;



@Entity
//@Table(name="livre")
@Table(name="livre", uniqueConstraints=
@UniqueConstraint(columnNames = {"titre", "auteur"})) 
public class Livre implements Serializable{
	@Id @GeneratedValue
	@ApiModelProperty(notes = "ID du livre generee dans la base de donnees")
	private Long numLivre; 
	@ApiModelProperty(notes= "Nom du livre")
	@Column(name="titre")
	private String titre; 
	@ApiModelProperty(notes= "Nom de l'auteur du livre")
	@Column(name="auteur")
	private String auteur; 
	@ApiModelProperty(notes= "Nombre d'exemplaires total de l'ouvrage que possède la bibliotheque")
	private Integer nbExemplaires;
	@ApiModelProperty(notes= "Nombre d'exemplaires de l'ouvrage actuellement disponibles au pret")
	private Integer nbExemplairesDisponibles;
	// TICKET 1 : WEB APPLI 
	@ApiModelProperty(notes= "Date de retour prévue la plus proche d'un exemplaire de ce livre")
	private String dateRetourPrevuePlusProche;
	// TICKET 1 : WEB APPLI
	@ApiModelProperty(notes= "Nombre de réservations en cours")
	private Integer nbReservationsEnCours;
	@ApiModelProperty(notes="Nombre d'utilisateurs dans la file d'attente des réservations en cours")
	private Integer nbReservataires;
	@ApiModelProperty(notes= "Categorie de l'ouvrage")
	@ManyToOne 
	@JoinColumn(name="num_categorie")
	private Categorie categorie;
	@JsonIgnore
	@OneToMany(mappedBy="livre", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Pret> prets;
	@JsonIgnore
	@OneToMany(mappedBy="livre", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Reservation> reservations;
	
	public Livre() {
		super();
	}

	public Livre(String titre, String auteur, Integer nbExemplaires, Integer nbExemplairesDisponibles,
			Categorie categorie) {
		super();
		this.titre = titre;
		this.auteur = auteur;
		this.nbExemplaires = nbExemplaires;
		this.nbExemplairesDisponibles = nbExemplairesDisponibles;
		this.categorie = categorie;
	}

	public Livre(Long numLivre, String titre, String auteur, Integer nbExemplaires, Integer nbExemplairesDisponibles,
			Categorie categorie, List<Pret> prets) {
		super();
		this.numLivre = numLivre;
		this.titre = titre;
		this.auteur = auteur;
		this.nbExemplaires = nbExemplaires;
		this.nbExemplairesDisponibles = nbExemplairesDisponibles;
		this.categorie = categorie;
		this.prets = prets;
	}
	
	

	public Livre(Long numLivre, String titre, String auteur, Integer nbExemplaires, Integer nbExemplairesDisponibles,
			Categorie categorie, List<Pret> prets, List<Reservation> reservations) {
		super();
		this.numLivre = numLivre;
		this.titre = titre;
		this.auteur = auteur;
		this.nbExemplaires = nbExemplaires;
		this.nbExemplairesDisponibles = nbExemplairesDisponibles;
		this.categorie = categorie;
		this.prets = prets;
		this.reservations = reservations;
	}
	
	

	public Livre(Long numLivre, String titre, String auteur, Integer nbExemplaires, Integer nbExemplairesDisponibles,
			String dateRetourPrevuePlusProche, Integer nbReservationsEnCours, Categorie categorie, List<Pret> prets,
			List<Reservation> reservations) {
		super();
		this.numLivre = numLivre;
		this.titre = titre;
		this.auteur = auteur;
		this.nbExemplaires = nbExemplaires;
		this.nbExemplairesDisponibles = nbExemplairesDisponibles;
		this.dateRetourPrevuePlusProche = dateRetourPrevuePlusProche;
		this.nbReservationsEnCours = nbReservationsEnCours;
		this.categorie = categorie;
		this.prets = prets;
		this.reservations = reservations;
	}
	
	

	public Livre(Long numLivre, String titre, String auteur, Integer nbExemplaires, Integer nbExemplairesDisponibles,
			String dateRetourPrevuePlusProche, Integer nbReservationsEnCours, Integer nbReservataires,
			Categorie categorie, List<Pret> prets, List<Reservation> reservations) {
		super();
		this.numLivre = numLivre;
		this.titre = titre;
		this.auteur = auteur;
		this.nbExemplaires = nbExemplaires;
		this.nbExemplairesDisponibles = nbExemplairesDisponibles;
		this.dateRetourPrevuePlusProche = dateRetourPrevuePlusProche;
		this.nbReservationsEnCours = nbReservationsEnCours;
		this.nbReservataires = nbReservataires;
		this.categorie = categorie;
		this.prets = prets;
		this.reservations = reservations;
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

	public Integer getNbExemplaires() {
		return nbExemplaires;
	}

	public void setNbExemplaires(Integer nbExemplaires) {
		this.nbExemplaires = nbExemplaires;
	}

	public Integer getNbExemplairesDisponibles() {
		return nbExemplairesDisponibles;
	}

	public void setNbExemplairesDisponibles(Integer nbExemplairesDisponibles) {
		this.nbExemplairesDisponibles = nbExemplairesDisponibles;
	}

	public Integer getNbReservationsEnCours() {
		return nbReservationsEnCours;
	}

	public void setNbReservationsEnCours(Integer nbReservationsEnCours) {
		this.nbReservationsEnCours = nbReservationsEnCours;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public List<Pret> getPrets() {
		return prets;
	}

	public void setPrets(List<Pret> prets) {
		this.prets = prets;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	

	public String getDateRetourPrevuePlusProche() {
		return dateRetourPrevuePlusProche;
	}

	public void setDateRetourPrevuePlusProche(String dateRetourPrevuePlusProche) {
		this.dateRetourPrevuePlusProche = dateRetourPrevuePlusProche;
	}

	public Integer getNbReservataires() {
		return nbReservataires;
	}

	public void setNbReservataires(Integer nbReservataires) {
		this.nbReservataires = nbReservataires;
	}

	

	

}
