package net.codejava.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class User {

	@Id
	@Column(name = "USER_ID")
	@SequenceGenerator(name = "pk_sequence", sequenceName = "users_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	private Integer id;

	@Column(name = "FIO")
	private String fio;

	@Column(name = "LOGIN")
	private String login;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "ENABLED")
	private boolean enabled;
	
	@Column(name = "ROLE")
	private String role;
	
//	public User() {
//	}
//
//	public User(String login, String email, String password, boolean enabled) {
//		this.login = login;
//		this.email = email;
//		this.password = password;
//		this.enabled = enabled;
//	}
//
//	public User(String login, String email, String password, boolean enabled, String role) {
//		this.login = login;
//		this.email = email;
//		this.password = password;
//		this.enabled = enabled;
//		this.role = role;
//	}
//	
//	public User(Integer user_id, String fio, String login, String email, String password, boolean enabled, String role) {
//		this.id = user_id;
//		this.fio = fio;
//		this.login = login;
//		this.email = email;
//		this.password = password;
//		this.enabled = enabled;
//		this.role = role;
//	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFio() {
		return fio;
	}

	public void setFio(String fio) {
		this.fio = fio;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public boolean isAdmin(){
		if(this.role.equals("ROLE_ADMIN")){
			return true;
		}
		return false;
	}
}
