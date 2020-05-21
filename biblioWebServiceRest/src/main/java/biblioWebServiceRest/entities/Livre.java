package biblioWebServiceRest.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name="livre", 
uniqueConstraints= {
		@UniqueConstraint(columnNames="titre")
})
public class Livre implements Serializable{
	@Id @GeneratedValue
	private Long numLivre; 
	private String titre; 
	private String auteur; 
	private Integer nbExemplaires;
	private Integer nbExemplairesDisponibles;
	@ManyToOne
	@JoinColumn(name="num_categorie")
	private Categorie categorie;
	@JsonIgnore
	@OneToMany(mappedBy="livre", fetch=FetchType.LAZY)
	private Collection<Pret> prets;
	
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

	
	

}
