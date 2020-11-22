package biblioWebServiceRest.criteria;

import biblioWebServiceRest.entities.ReservationStatut;

public class ReservationCriteria {
	
	private Long numReservation;
	private String username;
	private Integer userId; 
	private Integer numLivre; 
	private String titre; 
	private String auteur;
	private String nomCategorieLivre;
	private String reservationStatutCode;
	
	
	
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
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getNumLivre() {
		return numLivre;
	}
	public void setNumLivre(Integer numLivre) {
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
	public String getReservationStatutCode() {
		return reservationStatutCode;
	}
	public void setReservationStatutCode(String reservationStatutCode) {
		this.reservationStatutCode = reservationStatutCode;
	}
	
	
	
	
	
	
	
	
	

}
