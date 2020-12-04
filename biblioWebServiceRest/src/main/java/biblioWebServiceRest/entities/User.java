/**
 * L'entité User permet de créer un compte utilisateur (nom, adresse mail), son profil d'autorisation et son password 
 * et de gérer et d'archiver les prêts réalisés par un utilisateur sur ce compte
 */
package biblioWebServiceRest.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;



@Entity
@Table(name="utilisateur")
public class User implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "ID de l'utilisateur generee dans la base de donnees")
	private Long idUser;
	@ApiModelProperty(notes= "Nom de l'utilisateur")
	private String username;
	@JsonIgnore
	private String password;
	@Transient
	@JsonIgnore
	private String passwordConfirm;
	@ApiModelProperty(notes= "Adresse mail de l'utilisateur")
	private String adresseMail;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="role_id")
    private Role role;
	@JsonIgnore
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	Collection<Pret> prets;
	
	
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param username
	 */
	public User(String username) {
		super();
		this.username = username;
	}

	

	public User(Long idUser, String username) {
		super();
		this.idUser = idUser;
		this.username = username;
	}

	public User(String username, String adresseMail) {
		super();
		this.username = username;
		this.adresseMail = adresseMail;
	}



	public User(String username, String adresseMail, Role role) {
		super();
		this.username = username;
		this.adresseMail = adresseMail;
		this.role = role;
	}



	public User(String username, String password, String passwordConfirm, String adresseMail,
			Role role) {
		super();
		this.username = username;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
		this.adresseMail = adresseMail;
		this.role = role;
	}
	
	

	public User(String username, String password, String adresseMail, Role role) {
		super();
		this.username = username;
		this.password = password;
		this.adresseMail = adresseMail;
		this.role = role;
	}

	

	public User(Long idUser, String username, String password, String passwordConfirm, String adresseMail, Role role,
			Collection<Pret> prets) {
		super();
		this.idUser = idUser;
		this.username = username;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
		this.adresseMail = adresseMail;
		this.role = role;
		this.prets = prets;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
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

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getAdresseMail() {
		return adresseMail;
	}

	public void setAdresseMail(String adresseMail) {
		this.adresseMail = adresseMail;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}


	public Collection<Pret> getPrets() {
		return prets;
	}

	public void setPrets(Collection<Pret> prets) {
		this.prets = prets;
	}
	
	
	
	

}
