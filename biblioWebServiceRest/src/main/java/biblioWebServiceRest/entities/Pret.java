/**
 * L'entité Pret gère le prêt d'un livre à un utilisateur, sa date de retour prévue et l'archivage de la confirmation de sa restitution
 */
package biblioWebServiceRest.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;


@Entity
@Table(name="pret")
public class Pret implements Serializable, Comparable<Pret>{
	
	@Id
	@GeneratedValue
	@ApiModelProperty(notes = "ID du pret generee dans la base de donnees")
	private Long numPret;
	@ApiModelProperty(notes= "Date du prêt initial ou date de renouvellement du pret lorsqu'il y a prolongation")
	private LocalDate datePret;
	@ApiModelProperty(notes= "Date de fin du prêt initial ou prolonge calculee automatiquement en fonction de la constante parametree dans l'application ")
	private LocalDate dateRetourPrevue;
	@ApiModelProperty(notes= "Date enregistree au moment de la restitution à la bibliotheque de l'ouvrage emprunte")
	private LocalDate dateRetourEffectif; 
	@Enumerated
	@ApiModelProperty(notes= "Statut du pret qui peut être EN COURS, ECHU si la date de retour est dépassée sans restitution, PROLONGE ou RESTITUE si le livre est restitue")
	private PretStatut pretStatut;
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	@ManyToOne
	@JoinColumn(name="livre_num_livre")
	private Livre livre;
	
	public Pret() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Pret(LocalDate datePret, LocalDate dateRetourPrevue, PretStatut pretStatut, User user, Livre livre) {
		super();
		this.datePret = datePret;
		this.dateRetourPrevue = dateRetourPrevue;
		this.pretStatut = pretStatut;
		this.user = user;
		this.livre = livre;
	}
	
	

	public Pret(Long numPret, LocalDate datePret, LocalDate dateRetourPrevue, LocalDate dateRetourEffectif,
			PretStatut pretStatut, User user, Livre livre) {
		super();
		this.numPret = numPret;
		this.datePret = datePret;
		this.dateRetourPrevue = dateRetourPrevue;
		this.dateRetourEffectif = dateRetourEffectif;
		this.pretStatut = pretStatut;
		this.user = user;
		this.livre = livre;
	}

	public Pret(LocalDate datePret, LocalDate dateRetourPrevue, LocalDate dateRetourEffectif, PretStatut pretStatut,
			User user, Livre livre) {
		super();
		this.datePret = datePret;
		this.dateRetourPrevue = dateRetourPrevue;
		this.dateRetourEffectif = dateRetourEffectif;
		this.pretStatut = pretStatut;
		this.user = user;
		this.livre = livre;
	}

	public Long getNumPret() {
		return numPret;
	}

	public void setNumPret(Long numPret) {
		this.numPret = numPret;
	}

	public LocalDate getDatePret() {
		return datePret;
	}

	public void setDatePret(LocalDate datePret) {
		this.datePret = datePret;
	}

	public LocalDate getDateRetourPrevue() {
		return dateRetourPrevue;
	}

	public void setDateRetourPrevue(LocalDate dateRetourPrevue) {
		this.dateRetourPrevue = dateRetourPrevue;
	}

	public LocalDate getDateRetourEffectif() {
		return dateRetourEffectif;
	}

	public void setDateRetourEffectif(LocalDate dateRetourEffectif) {
		this.dateRetourEffectif = dateRetourEffectif;
	}

	public PretStatut getPretStatut() {
		return pretStatut;
	}

	public void setPretStatut(PretStatut pretStatut) {
		this.pretStatut = pretStatut;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	

	public Livre getLivre() {
		return livre;
	}

	public void setLivre(Livre livre) {
		this.livre = livre;
	}

	@Override
	public int compareTo(Pret pret) {
		return (this.dateRetourPrevue.compareTo(pret.dateRetourPrevue));
	}

}


