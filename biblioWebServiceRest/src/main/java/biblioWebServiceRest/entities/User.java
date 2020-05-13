package biblioWebServiceRest.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long numUser;
	private String username;
	private String password;
	private String adresseMail;
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY)
	Collection<Livre> livres;
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY)
	Collection<Pret> prets;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String username, String password, String adresseMail) {
		super();
		this.username = username;
		this.password = password;
		this.adresseMail = adresseMail;
	}

	public User(Long numUser, String username, String password, String adresseMail, Collection<Livre> livres,
			Collection<Pret> prets) {
		super();
		this.numUser = numUser;
		this.username = username;
		this.password = password;
		this.adresseMail = adresseMail;
		this.livres = livres;
		this.prets = prets;
	}

	public Long getNumUser() {
		return numUser;
	}

	public void setNumUser(Long numUser) {
		this.numUser = numUser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAdresseMail() {
		return adresseMail;
	}

	public void setAdresseMail(String adresseMail) {
		this.adresseMail = adresseMail;
	}

	public Collection<Livre> getLivres() {
		return livres;
	}

	public void setLivres(Collection<Livre> livres) {
		this.livres = livres;
	}

	public Collection<Pret> getPrets() {
		return prets;
	}

	public void setPrets(Collection<Pret> prets) {
		this.prets = prets;
	}
	
	

}
