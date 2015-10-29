package controllers;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

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
	public Result create(Context context, @JSR303Validation Project project, Validation validation, @Params("pictureName") FileItem uploadedfile[]){
		if(validation.hasViolations()){
			flashError(context,project);
			return Results.redirect("project/new");
		}
		
			//TODO: do something with the project
//		context.getFlashScope().put("success", "Project created successfully.");
		return Results.redirect("/project/new");
	}
	
	@FileProvider(DiskFileItemProvider.class)
	public Result upload(Context context, @Params("pictureName") FileItem uploadedfile[]){
		if(context.isMultipart()){
			System.out.println(uploadedfile.length);
			if(uploadedfile.length==0){
				return Results.status(400);
			}
			for(FileItem fi:uploadedfile){
				if(!fi.getFileName().isEmpty()){
				   if (fi.getContentType().equals("image/jpeg") || fi.getContentType().equals("image/jpg") || fi.getContentType().equals("image/png")) {
						String uuid=UUID.randomUUID().toString();
						try {
							FileUtils.moveFile(fi.getFile(), new File("../hbjpa/src/main/java/assets/image/"+uuid+".jpg"));
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
	
}
