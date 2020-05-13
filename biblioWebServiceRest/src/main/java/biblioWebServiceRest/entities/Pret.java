package biblioWebServiceRest.entities;

import java.io.Serializable;
import java.time.LocalDate;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Pret implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long numPret;
	private LocalDate datePret;
	private LocalDate dateRetour;
	private PretStatut pretStatut;
	@ManyToOne()
	@JoinColumn(name="USER")
	private User user;
	@ManyToOne()
	@JoinColumn(name="LIVRE")
	private Livre livre;
	
	public Pret() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Pret(LocalDate datePret, LocalDate dateRetour, PretStatut pretStatut, User user, Livre livre) {
		super();
		this.datePret = datePret;
		this.dateRetour = dateRetour;
		this.pretStatut = pretStatut;
		this.user = user;
		this.livre = livre;
	}
	public Pret(Long numPret, LocalDate datePret, LocalDate dateRetour, PretStatut pretStatut, User user, Livre livre) {
		super();
		this.numPret = numPret;
		this.datePret = datePret;
		this.dateRetour = dateRetour;
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
	public LocalDate getDateRetour() {
		return dateRetour;
	}
	public void setDateRetour(LocalDate dateRetour) {
		this.dateRetour = dateRetour;
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
	
	

}


