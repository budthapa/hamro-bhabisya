package controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import models.ContactUs;
import ninja.AuthenticityFilter;
import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.SecureFilter;
import ninja.session.Session;
import ninja.validation.JSR303Validation;
import ninja.validation.Validation;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import dao.ContactUsDao;

@Singleton
public class ContactUsController {
	
	@Inject
	ContactUsDao contactUsDao;
	@Inject
	ContactUs contactUs;
	Context context;
	@FilterWith(SecureFilter.class)
	public Result index(Session session){
		String name=session.get("username");
		System.out.println("name : "+name);
		System.out.println("t : "+session.getAuthenticityToken());
		return getAll();
	}
	
	public Result newContactUs(){
		return Results.html();
	}
	
	@FilterWith(AuthenticityFilter.class)
	public Result create(Context context,@JSR303Validation ContactUs contactUs, Validation validation) throws IOException{
		if(validation.hasViolations()){
			flashError(context,contactUs);
			return Results.redirect("contact/new");
		}
		
		contactUsDao.save(contactUs);
		context.getFlashScope().put("success", "Message sent successfully.");
		return Results.redirect("/contact/new");
	}

	private Result flashError(Context context, ContactUs contactUs){
		context.getFlashScope().put("error", "Missing required fields.");
		if(contactUs.getName().trim().length()<3){
			context.getFlashScope().put("invalidName", "Name too short.");			
		}
		context.getFlashScope().put("name", contactUs.getName());
		context.getFlashScope().put("email", contactUs.getEmail());
		context.getFlashScope().put("invalidMessage", "Message too short.");
		if(contactUs.getMessage().trim().length()<20){
			context.getFlashScope().put("message", contactUs.getMessage());
		}
		return Results.redirect("contact/new");
	}
	
	public Result getAll(){
		List<ContactUs> list=contactUsDao.findAll();
		Map<String, Object> map=Maps.newHashMap();
		map.put("contactUsList", list);
		return Results.html().render("contactUsList",list);
	}
}
