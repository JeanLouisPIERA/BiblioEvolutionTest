package biblioWebServiceRest.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name="livre")
public class Livre implements Serializable{
	@Id @GeneratedValue
	private Long numLivre; 
	private String titre; 
	private String auteur; 
	@Enumerated
	private LivreStatut livreStatut;
	@OneToMany(mappedBy="livre", fetch=FetchType.LAZY)
	private Collection<Pret> prets; 
	
	

	public Livre(String titre, String auteur, LivreStatut livreStatut) {
		super();
		this.titre = titre;
		this.auteur = auteur;
		this.livreStatut = livreStatut;
	}

	public Livre() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public Livre(Long numLivre, String titre, String auteur, LivreStatut livreStatut, Collection<Pret> prets) {
		super();
		this.numLivre = numLivre;
		this.titre = titre;
		this.auteur = auteur;
		this.livreStatut = livreStatut;
		this.prets = prets;
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

	public LivreStatut getLivreStatut() {
		return livreStatut;
	}

	public void setLivreStatut(LivreStatut livreStatut) {
		this.livreStatut = livreStatut;
	}


	public Collection<Pret> getPrets() {
		return prets;
	}

	public void setPrets(Collection<Pret> prets) {
		this.prets = prets;
	}

	
	
	
	
	
	
	
	

}
