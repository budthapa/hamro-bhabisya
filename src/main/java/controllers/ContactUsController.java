package controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import models.ContactUs;
import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.validation.JSR303Validation;
import ninja.validation.Validation;

import com.google.inject.Singleton;

@Singleton
public class ContactUsController {
	
	public Result index(){
		return Results.html();
	}
	
	public Result create(Context context,@JSR303Validation ContactUs contactUs, Validation validation) throws IOException{
		if(validation.hasViolations()){
			flashError(context,contactUs);
			return Results.redirect("contact/new");
		}
		
		//TODO: do something with the value
		
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
}
