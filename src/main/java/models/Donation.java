package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Donation extends BaseDomain{
	@NotEmpty
	@Size(min=7)
	private String title;
	@NotNull
	private int amount;
	@NotEmpty
	private String address;
	@Temporal(TemporalType.DATE)
	private Date date;
	@Transient
	private String tempDate;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTempDate() {
		return tempDate;
	}
	public void setTempDate(String tempDate) {
		this.tempDate = tempDate;
	}
	
	
}
