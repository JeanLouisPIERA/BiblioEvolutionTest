/**
 * 
 */
package biblioWebServiceRest.dto;

import java.time.LocalDate;

import biblioWebServiceRest.entities.PretStatut;

/**
import biblioWebServiceRest.entities.PretStatut;
**/


/**
 * @author jeanl
 *
 */


public class PretDTO {
	
	private Long refPret;
	private LocalDate datePret;
	private LocalDate dateRetourPrevue;
	private LocalDate dateRetourEffectif; 
	private PretStatut pretStatut;
	private UserDTO user;
	private LivreDTO livre;
	
	
	
	/**
	 * @return the refPret
	 */
	public Long getRefPret() {
		return refPret;
	}
	/**
	 * @param refPret the refPret to set
	 */
	public void setRefPret(Long refPret) {
		this.refPret = refPret;
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
	public UserDTO getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(UserDTO user) {
		this.user = user;
	}
	/**
	 * @return the livre
	 */
	public LivreDTO getLivre() {
		return livre;
	}
	/**
	 * @param livre the livre to set
	 */
	public void setLivre(LivreDTO livre) {
		this.livre = livre;
	}

	
	
	
	
	
	
	
	
}
