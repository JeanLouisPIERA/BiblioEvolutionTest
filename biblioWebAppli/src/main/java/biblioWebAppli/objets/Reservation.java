package biblioWebAppli.objets;

import java.time.LocalDate;

public class Reservation {
	
	private Long numReservation;
	private LocalDate dateReservation;
	private LocalDate dateNotification; 
	private LocalDate dateDeadline;
	private LocalDate dateSuppression;
	private ReservationStatut reservationStatut;
	private Integer rangReservation;
	private User user;
	private Livre livre;
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
	public LocalDate getDateSuppression() {
		return dateSuppression;
	}
	public void setDateSuppression(LocalDate dateSuppression) {
		this.dateSuppression = dateSuppression;
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
	public Integer getRangReservation() {
		return rangReservation;
	}
	public void setRangReservation(Integer rangReservation) {
		this.rangReservation = rangReservation;
	}
	
	

}