/**
 * Copyright (C) 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import models.Donation;
import models.Login;
import models.Picture;
import models.Project;
import models.User;
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
import ninja.utils.NinjaProperties;
import ninja.validation.FieldViolation;
import ninja.validation.JSR303Validation;
import ninja.validation.Validation;

import org.apache.commons.io.FileUtils;
import org.mindrot.jbcrypt.BCrypt;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import dao.DonationDao;
import dao.PictureDao;
import dao.ProjectDao;
import dao.UserDao;
import dto.UserDto;
import etc.FilePathHelper;
import etc.Validator;

@Singleton
public class ApplicationController {
	Logger log=Logger.getLogger(ApplicationController.class.getName());
	
	List<String> imageNameList=new ArrayList<>();
    @Inject
    ProjectDao projectDao;
    @Inject
    PictureDao pictureDao;
    @Inject
    DonationDao donationDao;
    @Inject
    UserDao userDao;

    @Inject
	Picture picture;
    private int id;
    @Inject
    User user;
    @Inject
    Login login;
    
    @Inject
    FilePathHelper filePath;
    
    int changePasswordUserId=0;
    
    public ApplicationController() {

    }
    
    @FilterWith(SecureFilter.class)
    public Result settings(Session session) {
        return Results.html();
    }
    
    @FilterWith(SecureFilter.class)
    public Result newUser(){
    	return Results.html();
    }

    @FilterWith(SecureFilter.class)
    public Result create(Context context, @JSR303Validation User user, Validation validation){
    	if(validation.hasViolations()){
    		flashError(context, user);
    		return Results.redirect("/settings/user/new");
    	}
    	
    	boolean validateName = validateName(user.getName(), context, user);
    	boolean validateContactNumber = validateContactNumber(user.getContactNumber(), context,user);
    	boolean validateDesignation = validateDesignation(user.getDesignation(), context,user);
    	
    	if(validateName && validateContactNumber && validateDesignation){
    		if(user.getContactNumber().trim().isEmpty()){
    			user.setContactNumber("");
    		}
    		if(user.getDesignation().trim().isEmpty()){
    			user.setDesignation("");
    		}
    		Picture picture=new Picture();
    		userDao.save(user);
    		picture.setUser(user);
    		if(imageNameList.size()>0){
    			picture.setPictureName(imageNameList.get(0));    			
    			pictureDao.save(picture);
    			imageNameList.clear();
    		}
    		context.getFlashScope().put("success", "user.create.success");
    	}
    	
    	return Results.redirect("/settings/user/new");
    }
    
    private boolean validateDesignation(String designation, Context context,User user) {
    	if(!user.getDesignation().trim().isEmpty()){
	    	if(!Validator.isAlphabetOnly(user.getDesignation())){
	    		context.getFlashScope().put("invalidDesignation", "invalid.designation");
	    		flashError(context, user);
	    		return false;
	    	}
    	}
		return true;
	}

	private boolean validateContactNumber(String contactNumber,Context context, User user) {
		if(!user.getContactNumber().trim().isEmpty()){
	    	if(user.getContactNumber().trim().length()<7 || !Validator.isValidPhoneNumber(user.getContactNumber())){
				context.getFlashScope().put("invalidContactNumber", "invalid.contact.number");
				flashError(context, user);
				return false;
	    	}
		}
		return true;
	}

	private boolean validateName(String name, Context context, User user) {
    	if(!Validator.isStrictAlphabetAndSpace(name)){
    		context.getFlashScope().put("invalidName", "invalid.name");
    		flashError(context, user);	
    		return false;
    	}
    	return true;
    }
    private void flashError(Context context, User user){
		context.getFlashScope().put("error", "missing.required");
		if(user.getName().trim().length()<3){
			context.getFlashScope().put("invalidName", "name.short");			
		}
		context.getFlashScope().put("name", user.getName());
		context.getFlashScope().put("email", user.getEmail());
		context.getFlashScope().put("address", user.getAddress());
		context.getFlashScope().put("contactNumber", user.getContactNumber());
		context.getFlashScope().put("designation", user.getDesignation());
//		return Results.redirect("/settings/user/new");
	}
    
    @Inject
    NinjaProperties np;
    
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
						   context.getFlashScope().put("invalidFileSize", "invalid.file.size");
						   return Results.status(400);
					   }
						String fileName=UUID.randomUUID().toString()+fi.getFileName();
						try {
							FileUtils.moveFile(fi.getFile(), new File(filePath.getFilePath()+fileName));
							imageNameList.add(fileName);
						} catch (IOException e) {
							e.printStackTrace();
						}
				   }else{
					   context.getFlashScope().put("noFileSelected", "report.file.error");
					   break;
				   }
				   
				}
			}
		}
		System.out.println("request path : "+context.getRequestPath());
		return Results.redirect("/settings/user/new");
    }
    
    public Result index() {
    	Project project = projectDao.getLatestProjectFrontPage();
    	Picture picture = pictureDao.getLatestProjectPictureFrontPage(project);
    	Project newsEvent=projectDao.getLatestNewsEventFrontPage();
    	Picture newsPicture = pictureDao.getLatestProjectPictureFrontPage(newsEvent);
    	List<Donation> donationList=donationDao.getLatestDonationFrontPage();
    	List<Picture> imageList=pictureDao.findAll();
    	
    	if(project!=null){
    		String desc=project.getDescription();
    		if(desc.length()>500){
    			desc=desc.substring(0, 500);
    			project.setDescription(desc);
    		}
    	}
        
        return Results.html().render("frontProject", project).render("picture", picture)
        		.render("newsEvent",newsEvent).render("newsPicture", newsPicture).render("imageList", imageList)
        		.render("donationList", donationList);

    }
    
    @FilterWith(SecureFilter.class)
    public Result dashboard(){
		long projectCount=projectDao.countTotalProject();
		long donationSum=donationDao.sumTotalDonation();
		long userCount=userDao.countTotalUser();
		
    	return Results.html().render("projectCount", projectCount).render("donationSum",donationSum).render("userCount", userCount);    		
    }
    
    public Result memberList(){
    	List<User> userList=userDao.findAll();
    	return Results.html().render("userList", userList);
    }
    
    @FilterWith(SecureFilter.class)
    public Result editUser(@PathParam("id") int userId){
    	User user=userDao.getUser(userId);
    	id=userId;
    	Picture picture=pictureDao.getUserPicture(user);
    	this.picture=picture;
    	if(picture!=null){
			return Results.html().render("user",user).render("picture",picture);			
		}else{
			return Results.html().render("user",user);
		}
    }
    
    @FilterWith(SecureFilter.class)
    public Result update(Context context, @JSR303Validation User user, Validation validation, Session session){
    	if(validation.hasViolations()){
    		flashError(context, user);
    		return Results.redirect("/settings/user/"+this.id);
    	}
    	boolean validateName = validateName(user.getName(), context, user);
    	boolean validateContactNumber = validateContactNumber(user.getContactNumber(), context,user);
    	boolean validateDesignation = validateDesignation(user.getDesignation(), context,user);
    	
    	if(validateName && validateContactNumber && validateDesignation){
    		if(user.getContactNumber().trim().isEmpty()){
    			user.setContactNumber("");
    		}
    		if(user.getDesignation().trim().isEmpty()){
    			user.setDesignation("");
    		}
    		
    		if(this.picture!=null){
    			log.info("Updating picture for existing picture for user "+this.id);
    			pictureDao.delete(this.picture);
	    		user.setId(this.id);
	    		user.setUpdatedBy(session.get("username"));
	    		if(imageNameList.size()>0){
	    			this.picture.setPictureName(imageNameList.get(0));    			
	    			imageNameList.clear();
	    		}
	    		this.picture.setUser(user);
	    		user.setPicture(this.picture);
    		}else{
    			log.info("Picture didn't exist so creating new");
    			Picture picture=new Picture();
	    		user.setId(this.id);
	    		user.setUpdatedBy(session.get("username"));
	    		if(imageNameList.size()>0){
	    			picture.setPictureName(imageNameList.get(0));    			
	    			imageNameList.clear();
	    		}
	    		picture.setUser(user);
	    		user.setPicture(picture);
    			
    		}
    		userDao.saveOrUpdate(user);
    		context.getFlashScope().put("success", "user.update.success");
    	}
    	
    	return Results.redirect("/settings/user/"+this.id);
    }
    
    List<User> listUser=new ArrayList<User>();
    
    @FilterWith(SecureFilter.class)
    public Result newLoginCredentials(){
    	listUser=userDao.findUserWithoutLoginCredentials();
    	return Results.html().render("listUser",listUser);
    }
    
    @FilterWith(SecureFilter.class)
    public Result createLoginCredentials(Context context, @JSR303Validation UserDto userDto, Validation validation){
    	int userId = 0;
    	if(validation.hasViolations()){
    		context.getFlashScope().put("error", "missing.required");
    		if(userDto.getPassword().trim().isEmpty() || userDto.getPassword().trim().length()<8){
    			context.getFlashScope().put("invalidPassword", "invalid.password.length");
    		}
    		if(userDto.getRetypePassword().trim().isEmpty() || userDto.getRetypePassword().trim().length()<8){
    			context.getFlashScope().put("invalidRetypePassword", "invalid.password.length");
    		}
    		
    		return Results.redirect("/settings/user/loginCredentials");
    	}
    	
    	if(!userDto.getPassword().equals(userDto.getRetypePassword())){
    		context.getFlashScope().put("invalidRetypePassword", "password.mismatch");
    		return Results.redirect("/settings/user/loginCredentials");
    	}
    	
    	String name="";
    	for(User user:listUser){
    		if(userDto.getEmail().equals(user.getEmail())){
    			userId=user.getId();
    			name=user.getName();
    			break;
    		}
    	}
    	
    	user.setId(userId);
    	login.setUpdatedBy(context.getSession().get("username"));
    	login.setPassword(BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt(13)));
    	login.setUser(user);
    	if(userDto.isAdmin()){
    		login.setAdmin(userDto.isAdmin());
    	}
    	boolean isLoginCreated = userDao.newLoginCredentials(login);
    	if(isLoginCreated){
    		user.setHasLoginCredentials(true);
    		user.setEmail(userDto.getEmail());
    		user.setName(name);
    		userDao.updateLoginCredentialsToUser(user);
    	}
    	context.getFlashScope().put("success", "login.credentials.Success");
    	return Results.redirect("/settings/user/loginCredentials");
    }
    
    @FilterWith(SecureFilter.class)
    public Result newChangePassword(@PathParam("id") int userId, Context context, Session session){
    	changePasswordUserId=userId;
    	if(userId==Integer.parseInt(session.get("userId"))){
    		return Results.html();
    	}else{
    		context.getFlashScope().put("error", "invalid.user");
    		return Results.redirect("/settings");
    	}
    	
    }
    
    @FilterWith(AuthenticityFilter.class)
    public Result changePassword(Context context, @JSR303Validation UserDto userDto, Validation validation){
    	if(validation.hasViolations()){
    		if(userDto.getPassword().trim().isEmpty() || userDto.getPassword().trim().length()<8){
    			context.getFlashScope().put("invalidPassword", "invalid.password.length");
    		}
    		if(userDto.getRetypePassword().trim().isEmpty() || userDto.getRetypePassword().trim().length()<8){
    			context.getFlashScope().put("invalidRetypePassword", "invalid.password.length");
    		}

    		List<FieldViolation> fv=validation.getBeanViolations();
    		for(FieldViolation vio:fv){
    			if(!vio.field.equals("email")){
    				context.getFlashScope().put("error", "missing.required");
    				return Results.redirect("/settings/user/changepassword/"+changePasswordUserId);
    			}
    		}

    		if(!userDto.getPassword().equals(userDto.getRetypePassword())){
        		context.getFlashScope().put("invalidRetypePassword", "password.mismatch");
        		return Results.redirect("/settings/user/changepassword/"+changePasswordUserId);
        	}
    		
    	}
    	
    	String password=BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt(13));
    	login.setPassword(password);
    	user.setId(changePasswordUserId);
    	login.setUser(user);
    	login.setAdmin(true);
    	login.setUpdatedBy(context.getSession().get("username"));
    	login.setId(Integer.parseInt(context.getSession().get("loginId")));
    	userDao.updatePassword(login);
    	
    	context.getFlashScope().put("success", "password.change.success");
    	return Results.redirect("/settings");
    }
    
    public Result about(){
    	return Results.html();
    }
}
