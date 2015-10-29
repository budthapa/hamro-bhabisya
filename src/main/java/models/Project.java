package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Project extends BaseDomain{
	@Size(min=15, max=100)
	@NotEmpty
	private String title;
	@Column(length=10000)
	@NotEmpty
	@Size(min=30)
	private String description;
	@OneToMany(cascade=CascadeType.MERGE, mappedBy="project")
	private List<Picture> picture=new ArrayList<>();
	
	@Column(name="project_category")
	private String projectCategory;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Picture> getPicture() {
		return picture;
	}
	public void setPicture(List<Picture> picture) {
		this.picture = picture;
	}
	public String getProjectCategory() {
		return projectCategory;
	}
	public void setProjectCategory(String projectCategory) {
		this.projectCategory = projectCategory;
	}
	
}
