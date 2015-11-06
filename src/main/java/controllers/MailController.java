package controllers;

import models.User;
import ninja.AuthenticityFilter;
import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.postoffice.Mail;
import ninja.postoffice.Postoffice;
import ninja.validation.JSR303Validation;
import ninja.validation.Validation;

import com.google.inject.Inject;
import com.google.inject.Provider;

import dao.UserDao;
import dto.Mailer;

public class MailController {
	@Inject
    UserDao userDao;
	
	@Inject
    Provider<Mail> mailProvider;

    @Inject
    Postoffice postoffice;
	
	public Result forgotPassword(){
    	return Results.html();
    }
    
    @FilterWith(AuthenticityFilter.class)
    public Result sendEmail(Context context, @JSR303Validation Mailer mailer, Validation validation){
    	if(validation.hasViolations()){
    		context.getFlashScope().put("error", "invalid.email");
    		return Results.redirect("/reset/password");
    	}
    	
    	User user=userDao.getUserWithEmail(mailer.getEmail());
    	if(user!=null){
    		if(mailer.getEmail().equals(user.getEmail())){
    			sendMail(user.getEmail());
    		}
    	}
    	
    	context.getFlashScope().put("success", "reset.password.success");
    	return Results.redirect("/reset/password");
    }
    
    public void sendMail(String email) {
        
        Mail mail = mailProvider.get();

        // fill the mail with content:
        mail.setSubject("Reset Password");

        mail.setFrom("info@hamrobhabisya.org");

        mail.addReplyTo("info@hamrobhabisya.org");

        mail.setCharset("utf-8");
        mail.addHeader("header1", "value1");
        mail.addHeader("header2", "value2");

        mail.addTo(email);

        mail.setBodyHtml("<h1>Hello</h1><p>Paragraph</p>");

        mail.setBodyText("Password reset link : ");

        // finally send the mail
        try {
            postoffice.send(mail);
        } catch (Exception e) {
			e.printStackTrace();
		}
    }
}
