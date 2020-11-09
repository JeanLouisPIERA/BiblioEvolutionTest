/**
 * L'entité Livre permet d'instancier un objet unique référencé dans la bibliothèque et de gérer plusieurs exemplaires 
 */
package biblioWebServiceRest.entities;

import java.io.Serializable;
import java.util.Collection;

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
	@ApiModelProperty(notes= "Categorie de l'ouvrage")
	@ManyToOne 
	@JoinColumn(name="num_categorie")
	private Categorie categorie;
	@JsonIgnore
	@OneToMany(mappedBy="livre", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	private Collection<Pret> prets;
	@JsonIgnore
	@OneToMany(mappedBy="livre", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	private Collection<Reservation> reservations;
	
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
			Categorie categorie, Collection<Pret> prets) {
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
			Categorie categorie, Collection<Pret> prets, Collection<Reservation> reservations) {
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

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public Collection<Pret> getPrets() {
		return prets;
	}

	public void setPrets(Collection<Pret> prets) {
		this.prets = prets;
	}

	public Collection<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(Collection<Reservation> reservations) {
		this.reservations = reservations;
	}

	
	

}
