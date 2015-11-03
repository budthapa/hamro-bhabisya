package dto;

import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class UserDto {
	@NotEmpty
	@Email
	private String email;
	@NotEmpty
	@Size(min=8)
	private String password;
	@Transient
	@NotEmpty
	@Size(min=8)
	private String retypePassword;
    private boolean isAdmin=false;
    private boolean isActive=true;
    private boolean hasLoginCredentials;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRetypePassword() {
		return retypePassword;
	}
	public void setRetypePassword(String retypePassword) {
		this.retypePassword = retypePassword;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public boolean isHasLoginCredentials() {
		return hasLoginCredentials;
	}
	public void setHasLoginCredentials(boolean hasLoginCredentials) {
		this.hasLoginCredentials = hasLoginCredentials;
	}
    
}
