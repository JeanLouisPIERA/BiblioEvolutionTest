package biblioWebServiceRest.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;



@Entity
public class Livre implements Serializable{
	@Id @GeneratedValue(strategy = GenerationType.TABLE)
	private Long numLivre; 
	private String titre; 
	private String auteur; 
	private LivreStatut livreStatut;
	@ManyToOne()
	@JoinColumn(name="USER")
	private User user;
	@OneToMany(mappedBy="livre", fetch=FetchType.LAZY)
	private Collection<Pret> prets; 
	
	
	
	public Livre(String titre, String auteur, LivreStatut livreStatut) {
		super();
		this.titre = titre;
		this.auteur = auteur;
		this.livreStatut = livreStatut;
	}

	public Livre(String titre, String auteur, LivreStatut livreStatut, User user) {
		super();
		this.titre = titre;
		this.auteur = auteur;
		this.livreStatut = livreStatut;
		this.user = user;
	}

	public Livre() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Livre(Long numLivre, String titre, String auteur, LivreStatut livreStatut, User user,
			Collection<Pret> prets) {
		super();
		this.numLivre = numLivre;
		this.titre = titre;
		this.auteur = auteur;
		this.livreStatut = livreStatut;
		this.user = user;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Collection<Pret> getPrets() {
		return prets;
	}

	public void setPrets(Collection<Pret> prets) {
		this.prets = prets;
	}

	
	
	
	
	
	
	
	

}
