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
import ninja.params.PathParam;
import ninja.validation.JSR303Validation;
import ninja.validation.Validation;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import dao.DonationDao;
import etc.Validator;

@Singleton
public class DonationController {
	@Inject
	DonationDao donationDao;
	private int id;
	
	public Result index(){
		List<Donation> list=donationDao.findAll();
		return Results.html().render("donationList",list);
	}
	
	@FilterWith(SecureFilter.class)
	public Result newDonation(){
		return Results.html();
	}
	
	@FilterWith(AuthenticityFilter.class)
	public Result create(Context context, @JSR303Validation Donation donation, Validation validation){
		if(validation.hasViolations()){
			flashError(context, donation);
			return Results.redirect("/donation/new");
		}
		//Extra validation
		boolean validName=validateName(donation.getTitle(), context, donation);
		boolean validAmount=validateAmount(donation.getAmount(),context,donation);
		boolean validDate=validateDate(donation.getDate(),context,donation);
		
		if(validName && validAmount && validDate){
			donationDao.save(donation);
			context.getFlashScope().put("success", "donation.success");			
		}
		return Results.redirect("/donation/new");
	}
	
	private boolean validateDate(Date date, Context context, Donation donation) {
		if(date.after(new Date())){
			context.getFlashScope().put("invalidDate", "invalid.date");
			flashError(context, donation);
			return false;
		}		
		return true;
	}

	private boolean validateAmount(int amount, Context context, Donation donation) {
		if(!Validator.isNumber(String.valueOf(amount)) || amount<1){
			context.getFlashScope().put("invalidAmount", "invalid.amount");
			flashError(context, donation);
			return false;
		}
		return true;
	}

	private boolean validateName(String name, Context context, Donation donation) {
		if(!Validator.isStrictAlphabetAndSpace(name)){
			context.getFlashScope().put("invalidName", "invalid.name");
			flashError(context, donation);	
			return false;
		}
		return true;
	}

	@FilterWith(SecureFilter.class)
	public Result editDonation(@PathParam("id") int donationId){
		Donation donation=donationDao.getDonation(donationId);
		id=donationId;
		String date=donation.getDate().toString();
		donation.setTempDate(date);
		return Results.html().render("donation", donation);
	}
	
	public Result update(Context context, @JSR303Validation Donation donation, Validation validation){
		if(validation.hasViolations()){
			flashError(context, donation);
			return Results.redirect("/donation/edit"+this.id);
		}
		
		//Extra validation
		boolean validName=validateName(donation.getTitle(), context, donation);
		boolean validAmount=validateAmount(donation.getAmount(),context,donation);
		boolean validDate=validateDate(donation.getDate(),context,donation);
		if(validName && validAmount && validDate){
			donation.setId(this.id);
			donationDao.saveOrUpdate(donation);
			context.getFlashScope().put("success", "donation.update.success");
		}
		return Results.redirect("/donation/edit/"+this.id);
	}
	
	private void flashError(Context context, Donation donation){
		context.getFlashScope().put("error", "missing.required");
		if(donation.getTitle().trim().length()<7){
			context.getFlashScope().put("title", donation.getTitle());
		}
		context.getFlashScope().put("title", donation.getTitle());
		context.getFlashScope().put("amount", donation.getAmount());
		context.getFlashScope().put("address", donation.getAddress());
		context.getFlashScope().put("date", donation.getDate());
	}
}
