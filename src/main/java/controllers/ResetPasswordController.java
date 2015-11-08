package controllers;

import java.util.Date;
import java.util.List;

import models.Login;
import models.ResetPassword;
import models.User;
import ninja.AuthenticityFilter;
import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.params.PathParam;
import ninja.validation.FieldViolation;
import ninja.validation.JSR303Validation;
import ninja.validation.Validation;

import org.mindrot.jbcrypt.BCrypt;

import com.google.inject.Inject;

import dao.ResetPasswordDao;
import dao.UserDao;
import dto.UserDto;

public class ResetPasswordController {
	@Inject
    ResetPasswordDao resetPasswordDao;
	
	@Inject
	Login login;
	
	@Inject
	UserDao userDao;
	
	@Inject
	User user;
	
	@Inject
	ResetPassword resetPassword;
	
    public Result resetPasswordToken(@PathParam("resetToken") String resetToken, Context context){
    	ResetPassword resetPasswordToken=resetPasswordDao.getToken(resetToken);
    	int resetUserId=0;
    	if(resetPasswordToken!=null){
	    	String hashedToken=resetPasswordToken.getHashToken();
	    	Date validDate=resetPasswordToken.getValidTill();
	    	Date currentDate=new Date();
	    	resetUserId=resetPasswordToken.getUserId();
	    	if(currentDate.after(validDate)){
	    		context.getFlashScope().put("error", "link.expired");
	    		return Results.redirect("/reset/password");
	    	}
	    	
	    	if(!BCrypt.checkpw(resetToken, hashedToken)){
	    		context.getFlashScope().put("error", "invalid.request");
	    		return Results.redirect("/reset/password");
	    	}
    	}else{
    		context.getFlashScope().put("error", "invalid.request");
    		return Results.redirect("/reset/password");
    	}
    	
    	//redirect to password reset form
    	context.getSession().put("resetUserId", String.valueOf(resetUserId));
    	return Results.redirect("/password/new");
    }
	
	public Result resetPassword(){
		return Results.html();
	}
	
	@FilterWith(AuthenticityFilter.class)
    public Result changePassword(Context context, @JSR303Validation UserDto userDto, Validation validation){
		String resetUserId=context.getSession().get("resetUserId");
		if(resetUserId!=null){
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
	    				return Results.redirect("/password/new");
	    			}
	    		}
	
	    		if(!userDto.getPassword().equals(userDto.getRetypePassword())){
	        		context.getFlashScope().put("invalidRetypePassword", "password.mismatch");
	        		return Results.redirect("/password/new");
	        	}
	    		
	    	}
	    	
	    	String password=BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt(13));
	    	int userId=Integer.parseInt(resetUserId);
	
	    	user=userDao.getUser(userId);
	    	boolean isAdmin=user.getLogin().isAdmin();
	    	
	    	userDao.delete(user.getLogin());
	    	
	    	login.setPassword(password);
	    	user.setId(userId);
	    	login.setUser(user);
	    	login.setAdmin(isAdmin);
	    	login.setId(user.getLogin().getId());
	    	userDao.updatePassword(login);
	    	context.getSession().remove("resetUserId");
	    	context.getFlashScope().put("success", "password.change.success");
	    	resetPassword.setId(Integer.parseInt(context.getSession().get("resetPasswordId")));
	    	resetPasswordDao.delete(resetPassword);
	    	context.getSession().remove("resetPasswordId");
	    	return Results.redirect("/login");
	    	
		}
		context.getFlashScope().put("error", "invalid.user");
    	return Results.redirect("/login");
    }
}
