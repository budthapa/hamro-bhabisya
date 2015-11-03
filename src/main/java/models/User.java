package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class User extends BaseDomain{
	@NotEmpty
	@Size(min=3)
	private String name;
	@NotEmpty
	@Email
	@Column(name="email",unique=true)
	private String email;
    
    private String address;
    private String contactNumber;
    private String designation;
    private boolean hasLoginCredentials=false;
    
    @OneToOne(mappedBy="user")
    @Cascade(value=CascadeType.ALL)
    private Picture picture;
    
    @OneToOne(mappedBy="user")
    @Cascade(value=CascadeType.ALL)
    private Login login;
    
    public User() {}
    
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}

	public boolean isHasLoginCredentials() {
		return hasLoginCredentials;
	}

	public void setHasLoginCredentials(boolean hasLoginCredentials) {
		this.hasLoginCredentials = hasLoginCredentials;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

}
