package controllers;

import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

import models.ResetPassword;
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

import org.joda.time.LocalTime;
import org.mindrot.jbcrypt.BCrypt;

import com.google.inject.Inject;
import com.google.inject.Provider;

import dao.ResetPasswordDao;
import dao.UserDao;
import dto.Mailer;

public class MailController {
	Logger log=Logger.getLogger(MailController.class.getName());
	@Inject
    UserDao userDao;
	
	@Inject
    Provider<Mail> mailProvider;

    @Inject
    Postoffice postoffice;
    
    @Inject
    ResetPassword resetPassword;
    
    @Inject
    ResetPasswordDao resetPasswordDao;
    
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
    			String randomToken =UUID.randomUUID().toString();
    			String hashToken=BCrypt.hashpw(randomToken, BCrypt.gensalt());
    			int userId=user.getId();
    			LocalTime time=LocalTime.now();
    			time=time.plusMinutes(10);
    			Date validTill=time.toDateTimeToday().toDate();
    			resetPassword.setHashToken(hashToken);
    			resetPassword.setRandomToken(randomToken);
    			resetPassword.setValidTill(validTill);
    			resetPassword.setUserId(userId);
//    			resetPassword.setUser(user);
    			int resetPasswordId=resetPasswordDao.save(resetPassword);
    			sendMail(user.getEmail(),randomToken);
    			context.getSession().put("resetPasswordId", String.valueOf(resetPasswordId));
    		}
    	}
    	
    	context.getFlashScope().put("success", "reset.password.success");
    	return Results.redirect("/reset/password");
    }
    
    public void sendMail(String email,String randomToken) {
        
        Mail mail = mailProvider.get();

        // fill the mail with content:
        mail.setSubject("Reset Password");

        mail.setFrom("info@hamrobhabisya.org");

        mail.addReplyTo("info@hamrobhabisya.org");

        mail.setCharset("utf-8");
        mail.addHeader("header1", "value1");
        mail.addHeader("header2", "value2");

        mail.addTo(email);

        mail.setBodyHtml("<h3>Reset password notification</h3><p>We received a password reset request for this email. "
        		+ "If you didn't request this, please safely ignore this message.</p><br/>"
        		+ "Password reset link : "+"<a href='http://hamrobhabisya.org/token/"+randomToken+"'>Click here</a><br />"
        		+ "<a href='www.hamrobhabisya.org'>HamroBhabisya</a>");

        try {
            postoffice.send(mail);
        } catch (Exception e) {
        	log.warning("Email delivery failed");
			e.printStackTrace();
		}
    }
    
}
