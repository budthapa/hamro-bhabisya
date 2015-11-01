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

import java.util.List;

import models.Article;
import models.Donation;
import models.Picture;
import models.Project;
import models.User;
import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.SecureFilter;
import ninja.utils.NinjaProperties;
import ninja.validation.JSR303Validation;
import ninja.validation.Validation;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import dao.ArticleDao;
import dao.DonationDao;
import dao.PictureDao;
import dao.ProjectDao;
import dao.SetupDao;
import dao.UserDao;
import etc.Validator;

@Singleton
public class ApplicationController {

    @Inject
    ArticleDao articleDao;
    @Inject
    ProjectDao projectDao;
    @Inject
    PictureDao pictureDao;
    @Inject
    DonationDao donationDao;
    @Inject
    UserDao userDao;

    @Inject
    SetupDao setupDao;

    public ApplicationController() {

    }
    
    @FilterWith(SecureFilter.class)
    public Result settings() {
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
    		userDao.save(user);
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
    private Result flashError(Context context, User user){
		context.getFlashScope().put("error", "missing.required");
		if(user.getName().trim().length()<3){
			context.getFlashScope().put("invalidName", "name.short");			
		}
		context.getFlashScope().put("name", user.getName());
		context.getFlashScope().put("email", user.getEmail());
		context.getFlashScope().put("address", user.getAddress());
		context.getFlashScope().put("contactNumber", user.getContactNumber());
		context.getFlashScope().put("designation", user.getDesignation());
		return Results.redirect("/settings/user/new");
	}
    
    @Inject
    NinjaProperties np;
    
    public Result index() {
    	Project project = projectDao.getLatestProjectFrontPage();
    	Picture picture = pictureDao.getLatestProjectPictureFrontPage(project);
    	Project newsEvent=projectDao.getLatestNewsEventFrontPage();
    	Picture newsPicture = pictureDao.getLatestProjectPictureFrontPage(newsEvent);
    	List<Donation> donationList=donationDao.getLatestDonationFrontPage();
    	Article frontPost = articleDao.getFirstArticleForFrontPage();
    	List<Picture> imageList=pictureDao.findAll();
    	
        String desc=project.getDescription();

        if(desc.length()>500){
        	desc=desc.substring(0, 500);
        	project.setDescription(desc);
        }
        
        List<Article> olderPosts = articleDao.getOlderArticlesForFrontPage();
        return Results.html().render("frontArticle", frontPost).render("olderArticles", olderPosts).render("frontProject", project)
        		.render("picture", picture).render("newsEvent",newsEvent).render("newsPicture", newsPicture).render("imageList", imageList)
        		.render("donationList", donationList);

    }
    
    @FilterWith(SecureFilter.class)
    public Result dashboard(){
    	return Results.html();    		
    }
}
