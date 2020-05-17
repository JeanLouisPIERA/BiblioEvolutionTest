package biblioWebServiceRest.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name="pret")
public class Pret implements Serializable{
	
	@Id
	@GeneratedValue
	private Long numPret;
	private LocalDate datePret;
	private LocalDate dateRetourPrevue;
	private LocalDate dateRetourEffectif; 
	@Enumerated
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
	
	

	
	

}


