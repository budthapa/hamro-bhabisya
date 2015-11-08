package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ResetPassword extends BaseDomain{
	private String hashToken;
	private String randomToken;
	private Date validTill;
	@Column(name="user_id")
	private int userId;
	
//	@OneToOne
//	private User user;
	
	public String getHashToken() {
		return hashToken;
	}
	public void setHashToken(String hashToken) {
		this.hashToken = hashToken;
	}
	public String getRandomToken() {
		return randomToken;
	}
	public void setRandomToken(String randomToken) {
		this.randomToken = randomToken;
	}
	public Date getValidTill() {
		return validTill;
	}
	public void setValidTill(Date validTill) {
		this.validTill = validTill;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
}
