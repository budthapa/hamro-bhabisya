package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class FinancialReport extends BaseDomain{

	@Size(min=10)
	@NotEmpty
	private String title;
	
	@OneToOne(cascade=CascadeType.ALL, mappedBy="report")
	private Picture picture;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}
	
}
