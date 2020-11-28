package biblioWebAppli.criteria;

import java.util.List;

import biblioWebAppli.objets.ReservationStatut;

public class ReservationCriteria {
	
	private Long numReservation;
	private String username;
	private Long userId; 
	private Long numLivre; 
	private String titre; 
	private String auteur;
	private String nomCategorieLivre;
	private String code;
	private Integer nbExemplairesDisponibles;
	private ReservationStatut reservationStatut;
	
	
	public Long getNumReservation() {
		return numReservation;
	}
	public void setNumReservation(Long numReservation) {
		this.numReservation = numReservation;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
	public String getNomCategorieLivre() {
		return nomCategorieLivre;
	}
	public void setNomCategorieLivre(String nomCategorieLivre) {
		this.nomCategorieLivre = nomCategorieLivre;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getNbExemplairesDisponibles() {
		return nbExemplairesDisponibles;
	}
	public void setNbExemplairesDisponibles(Integer nbExemplairesDisponibles) {
		this.nbExemplairesDisponibles = nbExemplairesDisponibles;
	}
	public ReservationStatut getReservationStatut() {
		return reservationStatut;
	}
	public void setReservationStatut(ReservationStatut reservationStatut) {
		this.reservationStatut = reservationStatut;
	}
	
	
	

}
