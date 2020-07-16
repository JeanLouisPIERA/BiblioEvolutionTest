/**
 * L'entité Pret gère le prêt d'un livre à un utilisateur, sa date de retour prévue et l'archivage de la confirmation de sa restitution
 */
package biblioWebAppli.objets;


import java.time.LocalDate;


	public class Pret {
	
	private Long numPret;
	private LocalDate datePret;
	private LocalDate dateRetourPrevue;
	private LocalDate dateRetourEffectif; 
	private PretStatut pretStatut;
	private User user;
	private Livre livre;
	/**
	 * @return the numPret
	 */
	public Long getNumPret() {
		return numPret;
	}
	/**
	 * @param numPret the numPret to set
	 */
	public void setNumPret(Long numPret) {
		this.numPret = numPret;
	}
	/**
	 * @return the datePret
	 */
	public LocalDate getDatePret() {
		return datePret;
	}
	/**
	 * @param datePret the datePret to set
	 */
	public void setDatePret(LocalDate datePret) {
		this.datePret = datePret;
	}
	/**
	 * @return the dateRetourPrevue
	 */
	public LocalDate getDateRetourPrevue() {
		return dateRetourPrevue;
	}
	/**
	 * @param dateRetourPrevue the dateRetourPrevue to set
	 */
	public void setDateRetourPrevue(LocalDate dateRetourPrevue) {
		this.dateRetourPrevue = dateRetourPrevue;
	}
	/**
	 * @return the dateRetourEffectif
	 */
	public LocalDate getDateRetourEffectif() {
		return dateRetourEffectif;
	}
	/**
	 * @param dateRetourEffectif the dateRetourEffectif to set
	 */
	public void setDateRetourEffectif(LocalDate dateRetourEffectif) {
		this.dateRetourEffectif = dateRetourEffectif;
	}
	/**
	 * @return the pretStatut
	 */
	public PretStatut getPretStatut() {
		return pretStatut;
	}
	/**
	 * @param pretStatut the pretStatut to set
	 */
	public void setPretStatut(PretStatut pretStatut) {
		this.pretStatut = pretStatut;
	}
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @return the livre
	 */
	public Livre getLivre() {
		return livre;
	}
	/**
	 * @param livre the livre to set
	 */
	public void setLivre(Livre livre) {
		this.livre = livre;
	}
	
	

}


