package controllers;

import java.util.Date;
import java.util.List;

import models.Donation;
import ninja.AuthenticityFilter;
import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.SecureFilter;
import ninja.validation.FieldViolation;
import ninja.validation.JSR303Validation;
import ninja.validation.Validation;

import com.google.inject.Singleton;

import etc.Validator;

@Singleton
public class DonationController {
	public Result index(){
		return Results.html();
	}
	
	@FilterWith(SecureFilter.class)
	public Result newDonation(){
		return Results.html();
	}
	
	@FilterWith(AuthenticityFilter.class)
	public Result create(Context context, @JSR303Validation Donation donation, Validation validation){
		if(validation.hasViolations()){
			System.out.println("voilated");
			List<FieldViolation> v=validation.getBeanViolations();
			for(FieldViolation f:v){
				System.out.println("invalid "+f.field);
			}
			return Results.redirect("/donation/new");
		}
		System.out.println("name "+donation.getTitle()+" amt "+donation.getAmount()+" add: "+donation.getAddress()+" date "+donation.getDate());
		//Extra validation
		if(!Validator.isStrictAlphabetAndSpace(donation.getTitle())){
			context.getFlashScope().put("invalidName", "invalid.name");
			return Results.redirect("/donation/new");
		}
		if(donation.getAmount()<1){
			context.getFlashScope().put("invalidAmount", "invalid.amount");
			return Results.redirect("/donation/new");
		}
		if(donation.getDate().after(new Date())){
			context.getFlashScope().put("invalidDate", "invalid.date");
			return Results.redirect("/donation/new");
		}
		return Results.redirect("/donation/new");
	}
}
