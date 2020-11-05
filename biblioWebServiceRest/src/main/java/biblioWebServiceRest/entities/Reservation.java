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
	@Table(name="reservation")
	public class Reservation implements Serializable{
		
		@Id @GeneratedValue
		@ApiModelProperty(notes = "ID de la réservation generee dans la base de donnees")
		private Long numReservation;
		
		@ApiModelProperty(notes= "Date de la réservation")
		private LocalDate datePret;
		
		@Enumerated
		@ApiModelProperty(notes= "Statut de la réservation peut être "
				+ "ENREGISTREE : si le nombre de réservations en cours est < 2 x le nombre d'emplaires et si pas de prêt en cours pour ce livvre, "
				+ "IMPOSSIBLE : si le nombre de réservations en cours est < 2 x le nombre d'emplaires,"
				+ " REFUSEE : s'il existe un pret de ce livre en cours pour cet utilisateur,"
				+ " NOTIFIEE : quand le livre est disponible,"
				+ " LIVREE : quand le livre est emprunté,"
				+ " ANNULEE : si le livre n'est pas emprunté dans les 48 heures qui suivent sa notification")
		private ReservationStatut reservationStatut;
		
		@ManyToOne
		@JoinColumn(name="user_id")
		private User user;
		
		@ManyToOne
		@JoinColumn(name="livre_num_livre")
		private Livre livre;

		public Reservation(Long numReservation, LocalDate datePret, ReservationStatut reservationStatut, User user,
				Livre livre) {
			super();
			this.numReservation = numReservation;
			this.datePret = datePret;
			this.reservationStatut = reservationStatut;
			this.user = user;
			this.livre = livre;
		}

		public Long getNumReservation() {
			return numReservation;
		}

		public void setNumReservation(Long numReservation) {
			this.numReservation = numReservation;
		}

		public LocalDate getDatePret() {
			return datePret;
		}

		public void setDatePret(LocalDate datePret) {
			this.datePret = datePret;
		}

		public ReservationStatut getReservationStatut() {
			return reservationStatut;
		}

		public void setReservationStatut(ReservationStatut reservationStatut) {
			this.reservationStatut = reservationStatut;
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
