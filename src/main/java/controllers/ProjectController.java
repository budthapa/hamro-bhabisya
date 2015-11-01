package controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import models.Picture;
import models.Project;
import ninja.AuthenticityFilter;
import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.SecureFilter;
import ninja.params.Params;
import ninja.params.PathParam;
import ninja.session.Session;
import ninja.uploads.DiskFileItemProvider;
import ninja.uploads.FileItem;
import ninja.uploads.FileProvider;
import ninja.validation.JSR303Validation;
import ninja.validation.Validation;

import org.apache.commons.io.FileUtils;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import dao.PictureDao;
import dao.ProjectDao;

@Singleton
public class ProjectController {
	List<String> imageNameList=new ArrayList<>();
	
	@Inject
	ProjectDao projectDao;
	@Inject
	PictureDao pictureDao;
	@Inject
	Picture picture;

	private int id;
	
	public Result index(){
		List<Project> projectList=projectDao.findAllProject();
		return Results.html().render("projectList",projectList);
	}
	
	@FilterWith(SecureFilter.class)
	public Result newProject(){
		return Results.html();
	}
	
	@FilterWith(AuthenticityFilter.class)
	public Result create(Context context, @JSR303Validation Project project, Validation validation, 
			@Params("pictureName") FileItem uploadedfile[], Session session){
		if(validation.hasViolations()){
			flashError(context,project);
			return Results.redirect("project/new");
		}
		
		project.setCreatedBy(session.get("username"));
		projectDao.save(project);
		
		picture.setProject(project);
		//need to work on this later for multiple images
//		for(String name:imageNameList){
//		}
		if(imageNameList.size()>0){
			picture.setPictureName(imageNameList.get(0));
			pictureDao.save(picture);
			imageNameList.clear();			
		}
		context.getFlashScope().put("success", "Project created successfully.");
		return Results.redirect("/project/new");
	}
	
	@FileProvider(DiskFileItemProvider.class)
	public Result upload(Context context, @Params("pictureName") FileItem uploadedfile[]){
		if(context.isMultipart()){
			if(uploadedfile.length==0){
				return Results.status(400);
			}
			for(FileItem fi:uploadedfile){
				if(!fi.getFileName().isEmpty()){
				   if (fi.getContentType().equals("image/jpeg") || fi.getContentType().equals("image/jpg") || fi.getContentType().equals("image/png")) {
					   long fileSize=fi.getFile().length();
					   if((fileSize/1024)>200){
						   return Results.status(400);
					   }
					   //if()
						String uuid=UUID.randomUUID().toString();
						try {
							FileUtils.moveFile(fi.getFile(), new File("../hbjpa/src/main/java/assets/image/"+uuid+".jpg"));
							imageNameList.add(uuid);
						} catch (IOException e) {
							e.printStackTrace();
						}
				   }else{
					   context.getFlashScope().put("noFileSelected", "Not a valid file or no file(s) selected");
					   break;
				   }
				   
				}
			}
		}
		return Results.redirect("/project/new");
	}
	
	private Result flashError(Context context, Project project){
		context.getFlashScope().put("error", "Missing required fields.");
		if(project.getTitle().trim().length()<20){
			context.getFlashScope().put("invalidName", "Title too short.");			
		}
		context.getFlashScope().put("name", project.getTitle());
		context.getFlashScope().put("invalidMessage", "Message too short.");
		if(project.getDescription().trim().length()<100){
			context.getFlashScope().put("message", project.getDescription());
		}
		return Results.redirect("project/new");
	}
	
	public Result showProject(@PathParam("id") int projectId, Session session){
		Project project=projectDao.getProject(projectId);
		Picture picture = pictureDao.getLatestProjectPictureFrontPage(project);
		
		if(session.get("username")!=null){
			return Results.redirect("/project/edit/"+projectId);
		}else{
			if(picture!=null){
				return Results.html().render(project).render(picture);			
			}else{
				return Results.html().render(project);
			}			
		}
		
	}
	
	@FilterWith(SecureFilter.class)
	public Result editProject(@PathParam("id") int projectId) {
		Project project=projectDao.getProject(projectId);
		Picture picture = pictureDao.getLatestProjectPictureFrontPage(project);
		id=projectId;
		if(picture!=null){
			return Results.html().render(project).render(picture);			
		}else{
			return Results.html().render(project);
		}
	}
	
	public Result update(Context context, @JSR303Validation Project project, Validation validation, 
			@Params("pictureName") FileItem uploadedfile[], Session session){
		
		project.setId(id);
//		if(project.getButtonName().equals("Update")){
			if(validation.hasViolations()){
				flashError(context,project);
				return Results.redirect("/project/edit/"+this.id);
			}
			project.setUpdatedBy(session.get("username"));
			projectDao.saveOrUpdate(project);
			
			picture.setProject(project);
			//need to work on this later for multiple images
	//		for(String name:imageNameList){
	//		}
			if(imageNameList.size()>0){
				picture.setPictureName(imageNameList.get(0));
				pictureDao.saveOrUpdate(picture);
				imageNameList.clear();			
			}
			context.getFlashScope().put("success", "Project updated successfully.");
//		}else{
//			projectDao.delete(project);
//			context.getFlashScope().put("success", "Project deleted successfully.");
//		}
		if(project.getProjectCategory().equals("News & Events")){
			return Results.redirect("/project/events");
		}
		return Results.redirect("/project");
	}

	public Result showNewsEvent(){
		List<Project> newsList=projectDao.findAllNewsEvent();
		return Results.html().render("newsList", newsList);
	}
	
}
