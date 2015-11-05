package controllers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import models.FinancialReport;
import models.Picture;
import ninja.AuthenticityFilter;
import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.SecureFilter;
import ninja.params.Params;
import ninja.params.PathParam;
import ninja.uploads.DiskFileItemProvider;
import ninja.uploads.FileItem;
import ninja.uploads.FileProvider;
import ninja.validation.JSR303Validation;
import ninja.validation.Validation;

import org.apache.commons.io.FileUtils;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import dao.FinancialReportDao;

@Singleton
public class FinancialReportController {
	List<String> imageNameList=new ArrayList<>();
	private String fileName;
	
	@Inject
	FinancialReportDao frd;
	
	public Result index(){
		List<FinancialReport> list=frd.findAll();
		this.fileName=list.get(0).getPicture().getPictureName();
		return Results.html().render("financialList", list);
	}
	
	@FilterWith(SecureFilter.class)
	public Result newReport(){
		return Results.html();
	}
	
	@FilterWith(AuthenticityFilter.class)
	public Result create(Context context, @JSR303Validation FinancialReport report, Validation validation){
		if(validation.hasViolations()){
			context.getFlashScope().put("error", "missing.required");
			if(report.getTitle().trim().length()<10){
				context.getFlashScope().put("invalidName", "invalid.name");
			}
			return Results.redirect("/report/new");
		}
		
		Picture picture=new Picture();
		
		if(imageNameList.size()==0){
			context.getFlashScope().put("error", "report.file.error");
			context.getFlashScope().put("title", report.getTitle());
			return Results.redirect("/report/new");
		}
		
		for(String name:imageNameList){
			picture.setPictureName(name);
		}
		picture.setReport(report);
		report.setPicture(picture);
		frd.save(report);
		
		context.getFlashScope().put("success", "report.success");
		return Results.redirect("/report/new");
		
	}
	
	@FilterWith(SecureFilter.class)
	public Result editReport(@PathParam("id") int reportId){
		return null;
		
	}

	@FilterWith(AuthenticityFilter.class)
	public Result update(){
		return null;
		
	}
	
	@FileProvider(DiskFileItemProvider.class)
	@FilterWith(SecureFilter.class)
	public Result upload(Context context, @Params("pictureName") FileItem uploadedfile[]){
		if(context.isMultipart()){
			if(uploadedfile.length==0){
				return Results.status(400);
			}
			for(FileItem fi:uploadedfile){
				if(!fi.getFileName().isEmpty()){
				   if (fi.getContentType().equals("application/pdf")) {
					   long fileSize=fi.getFile().length();
					   if((fileSize/1024)>200){
						   context.getFlashScope().put("invalidFileSize", "Invalid file size");
						   return Results.status(400);
					   }
						String uuid=UUID.randomUUID().toString();
						String fileName=uuid+" "+fi.getFileName();
						try {
							FileUtils.moveFile(fi.getFile(), new File("../hbjpa/src/main/java/assets/image/"+fileName));
							imageNameList.add(fileName);
						} catch (IOException e) {
							e.printStackTrace();
						}
				   }else{
					   context.getFlashScope().put("noFileSelected", "Not a valid file or no file(s) selected");
					   return Results.status(400);
				   }
				   
				}
			}
		}
		return Results.redirect("/report/new");
	}
	
	public Result download(@PathParam("fileName") String fileName, Context context){
		if(this.fileName.equals(fileName)){
			byte[] fileInputByte;
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			try {
				File file=new File("/home/budthapa/hbjpa/src/main/java/assets/image/"+fileName);
				fileInputByte = FileUtils.readFileToByteArray(file);
				output.write(fileInputByte);
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return Results.ok().renderRaw(output.toByteArray()).contentType("application/pdf");
		}else{
			context.getFlashScope().put("error", "report.notfound");
			return Results.redirect("/report");
		}
	}
}
