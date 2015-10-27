package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class ContactUs extends BaseDomain{
	@Size(min=5, max=50)
	@NotEmpty
	String name;
	@Email
	String email;
	@Size(min=20)
	@NotEmpty
	@Column(length=5000)
	String message;
	
	public ContactUs(){}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
