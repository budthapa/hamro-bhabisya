/**
 * Copyright (C) 2012 the original author or authors.
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
import java.util.logging.Logger;

import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.validation.FieldViolation;
import ninja.validation.JSR303Validation;
import ninja.validation.Validation;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import dao.UserDao;
import dto.UserDto;

@Singleton
public class LoginLogoutController {
    Logger log=Logger.getLogger(LoginLogoutController.class.getName());
    @Inject
    UserDao userDao;
    int countLoginAttempt=0;
    
    public Result login(Context context) {
        return Results.html();
    }

    public Result loginPost(Context context, @JSR303Validation UserDto user, Validation validation) {
    	if(validation.hasViolations()){
    		List<FieldViolation> list=validation.getBeanViolations();
    		for(FieldViolation fv:list){
    			if(!fv.field.equals("retypePassword")){
    				flashError(context,user);
    				return Results.redirect("/login");
    			}
    			break;
    		}
    	}
    	UserDto userDto = userDao.isUserAndPasswordValid(user.getEmail().trim());
    	if(userDto!=null){
    		if(!userDto.isActive()){
    			context.getFlashScope().put("email", user.getEmail());
    			context.getFlashScope().error("login.errorLogin");
    			return Results.redirect("/login");
    		}
    		
    		if(user.getPassword().equals(userDto.getPassword())){
    			context.getSession().put("username", user.getEmail());
    			context.getSession().put("isAdmin", String.valueOf(userDto.isAdmin()));
    			context.getFlashScope().success("login.loginSuccessful");
    			return Results.redirect("/app/dashboard");    			
    		}
    	} 
    	countLoginAttempt++;
    	if(countLoginAttempt==3){
    		log.warning("Invalid login attempt : "+countLoginAttempt+ " from "+user.getEmail());
    		countLoginAttempt=0;
    		//get this user's ip and ban this user
//    		userDao.blacklistUser();
    	}
    	
    	context.getFlashScope().put("email", user.getEmail());
    	context.getFlashScope().error("login.errorLogin");
    	return Results.redirect("/login");
    	
    	
    }

    private void flashError(Context context, UserDto user) {
   		context.getFlashScope().put("email", user.getEmail());
   		if(user.getPassword().trim().length()<8){
   			context.getFlashScope().put("invalidPasswordLength", "invalid.password.length");			
   		}
	}

	///////////////////////////////////////////////////////////////////////////
    // Logout
    ///////////////////////////////////////////////////////////////////////////
    public Result logout(Context context) {

        // remove any user dependent information
        context.getSession().clear();
        context.getFlashScope().success("login.logoutSuccessful");

        return Results.redirect("/");

    }

}
