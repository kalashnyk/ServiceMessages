package net.codejava.spring.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "MESSAGES")
public class Message {

	@Id
	@Column(name = "MESSAGE_ID")
	@SequenceGenerator(name = "pk_sequence", sequenceName = "messages_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "FROM_USER")
	private User from_user;

	@ManyToOne
	@JoinColumn(name = "TO_USER")
	private User to_user;

	@Column(name = "DATE_TIME")
	private Date date_time;

	@Column(name = "SUBJECT")
	private String subject;

	@Column(name = "MESSAGE")
	private String message;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getFrom_user() {
		return from_user;
	}

	public void setFrom_user(User from_user) {
		this.from_user = from_user;
	}

	public User getTo_user() {
		return to_user;
	}

	public void setTo_user(User to_user) {
		this.to_user = to_user;
	}

	public Date getDate_time() {
		return date_time;
	}

	public void setDate_time(Date date_time) {
		this.date_time = date_time;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
