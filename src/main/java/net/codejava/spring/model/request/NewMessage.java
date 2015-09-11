package net.codejava.spring.model.request;

public class NewMessage {

	private String to_user;
	
	private String subject;
	
	private String message;

	
	public String getTo_user() {
		return to_user;
	}

	public void setTo_user(String to_user) {
		this.to_user = to_user;
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
