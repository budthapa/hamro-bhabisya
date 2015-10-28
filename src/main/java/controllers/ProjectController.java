package controllers;

import java.io.File;
import java.io.IOException;

import models.Project;
import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.SecureFilter;
import ninja.params.Params;
import ninja.uploads.DiskFileItemProvider;
import ninja.uploads.FileItem;
import ninja.uploads.FileProvider;
import ninja.validation.JSR303Validation;
import ninja.validation.Validation;

import org.apache.commons.io.FileUtils;

import com.google.inject.Singleton;

@Singleton
public class ProjectController {
	
	@FilterWith(SecureFilter.class)
	public Result newProject(){
		return Results.html();
	}
	
//	@FilterWith(AuthenticityFilter.class)
//	@FileProvider(DiskFileItemProvider.class)
	public Result create(Context context, @JSR303Validation Project project, Validation validation){
		if(validation.hasViolations()){
			flashError(context,project);
			return Results.redirect("project/new");
		}
		
		/*if(context.isMultipart()){
			for(FileItem fi:uploadedfile){
//			   if (fi.getContentType().equals("image/jpeg") || fi.getContentType().equals("image/jpg") || fi.getContentType().equals("image/png")) {
				System.out.println("FileName "+fi.getFileName());
				if(!fi.getFileName().isEmpty()){
					
				try {
					   FileUtils.moveFile(fi.getFile(), new File("../hbjpa/src/main/java/assets/image/"+fi.getFileName()));
				   } catch (IOException e) {
					   context.getFlashScope().put("alreadyExists", "File with name "+fi.getFileName()+" already exists");
				   }
				}
//			   } else{
//				   context.getFlashScope().put("noFileSelected", "Not a valid file or no file(s) selected");
//				   break;
//			   }
			}
		}*/
		
		//TODO: do something with the project
//		context.getFlashScope().put("success", "Project created successfully.");
		return Results.redirect("/project/new");
	}
	
	@FileProvider(DiskFileItemProvider.class)
	public Result upload(Context context, @Params("pictureName") FileItem uploadedfile[]){
		if(context.isMultipart()){
			System.out.println(uploadedfile.length);
			if(uploadedfile.length==0){
				return Results.noContent();
			}
			for(FileItem fi:uploadedfile){
			   if (fi.getContentType().equals("image/jpeg") || fi.getContentType().equals("image/jpg") || fi.getContentType().equals("image/png")) {
				   try {
					   FileUtils.moveFile(fi.getFile(), new File("../hbjpa/src/main/java/assets/image/"+fi.getFileName()));
				   } catch (IOException e) {
					   context.getFlashScope().put("alreadyExists", "File with name "+fi.getFileName()+" already exists");
				   }
			   } else{
				   context.getFlashScope().put("noFileSelected", "Not a valid file or no file(s) selected");
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
	
}
