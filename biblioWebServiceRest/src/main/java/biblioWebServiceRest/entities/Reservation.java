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
	public class Reservation implements Serializable, Comparable<Reservation>{
		
		@Id @GeneratedValue
		@ApiModelProperty(notes = "ID de la réservation generee dans la base de donnees")
		private Long numReservation;
		
		@ApiModelProperty(notes= "Date de la réservation")
		private LocalDate dateReservation;
		
		@ApiModelProperty(notes= "Date de notification")
		private LocalDate dateNotification;
		
		@ApiModelProperty(notes= "Date de fin de notification")
		private LocalDate dateDeadline;
		
		@Enumerated
		@ApiModelProperty(notes= "Statut de la réservation peut être "
				+ " ENREGISTREE : si le nombre de réservations en cours est < 2 x le nombre d'emplaires et si pas de prêt en cours pour ce livvre, "
				+ " SUPPRIMEE : si l'utilisateur renonce à emprunter le livre,"
				+ " NOTIFIEE : quand le livre est disponible,"
				+ " LIVREE : quand le livre est emprunté,"
				+ " ANNULEE : si le livre n'est pas emprunté dans les 48 heures qui suivent sa notification"
				+ " IMPOSSIBLE : si les conditions d'enregistrement ne sont pas remplies ****** C'est une exception, pas un statut******")
		private ReservationStatut reservationStatut;
		
		@ManyToOne
		@JoinColumn(name="user_id")
		private User user;
		
		@ManyToOne
		@JoinColumn(name="livre_num_livre")
		private Livre livre;

		public Reservation(Long numReservation, LocalDate dateReservation, LocalDate dateNotification,
				LocalDate dateDeadline, ReservationStatut reservationStatut, User user, Livre livre) {
			super();
			this.numReservation = numReservation;
			this.dateReservation = dateReservation;
			this.dateNotification = dateNotification;
			this.dateDeadline = dateDeadline;
			this.reservationStatut = reservationStatut;
			this.user = user;
			this.livre = livre;
		}

		public Reservation() {
			super();
		}



		public Long getNumReservation() {
			return numReservation;
		}

		public void setNumReservation(Long numReservation) {
			this.numReservation = numReservation;
		}


		public LocalDate getDateReservation() {
			return dateReservation;
		}


		public void setDateReservation(LocalDate dateReservation) {
			this.dateReservation = dateReservation;
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
		
		public LocalDate getDateNotification() {
			return dateNotification;
		}

		public void setDateNotification(LocalDate dateNotification) {
			this.dateNotification = dateNotification;
		}

		public LocalDate getDateDeadline() {
			return dateDeadline;
		}

		public void setDateDeadline(LocalDate dateDeadline) {
			this.dateDeadline = dateDeadline;
		}

		@Override
		public int compareTo(Reservation reservation) {
			return (this.numReservation.intValue() - reservation.numReservation.intValue());
			
		}
		
	

}
