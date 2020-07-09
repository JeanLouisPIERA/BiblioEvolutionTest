/**
 * Classe de sérialization des livres pour le Mapping DTO de l'API REST
 */

package biblioWebAppli.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * @author jeanl
 *
 */
public class LivreDTO {
	
	@NotEmpty
	public String titre; 
	@NotEmpty
	private String auteur;
	@NotNull
	@Positive
	private Integer nbExemplaires;
	@Positive
	private Long numCategorie;
	
	
	/**
	 * @return the nbExemplaires
	 */
	public Integer getNbExemplaires() {
		return nbExemplaires;
	}
	/**
	 * @param nbExemplaires the nbExemplaires to set
	 */
	public void setNbExemplaires(Integer nbExemplaires) {
		this.nbExemplaires = nbExemplaires;
	}
	/**
	 * @return the titre
	 */
	public String getTitre() {
		return titre;
	}
	/**
	 * @param titre the titre to set
	 */
	public void setTitre(String titre) {
		this.titre = titre;
	}
	/**
	 * @return the auteur
	 */
	public String getAuteur() {
		return auteur;
	}
	/**
	 * @param auteur the auteur to set
	 */
	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}
	/**
	 * @return the numCategorie
	 */
	public Long getNumCategorie() {
		return numCategorie;
	}
	/**
	 * @param numCategorie the numCategorie to set
	 */
	public void setNumCategorie(Long numCategorie) {
		this.numCategorie = numCategorie;
	}
	
	
	
	

}
