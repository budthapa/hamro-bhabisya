package etc;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.params.PathParam;

import org.apache.commons.io.FileUtils;

import com.google.inject.Inject;

public class ServeFileHelper {
	Logger log=Logger.getLogger(ServeFileHelper.class.getName());
	@Inject
    FilePathHelper filePath;
    
	public Result download(@PathParam("fileName") String fileName, Context context){
//		if(FinancialReportController.fileName.equals(fileName)){
			byte[] fileInputByte;
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			try {
				File file=new File(filePath.getFilePath()+fileName);
				if(file.exists()){
					log.fine("File exists "+file.getAbsolutePath());
				}else{
					log.warning("File doesn't exists "+file.getAbsolutePath());
				}
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
			if(fileName.contains("pdf")){
				return Results.ok().renderRaw(output.toByteArray()).contentType("application/pdf");
			}else{
				return Results.ok().renderRaw(output.toByteArray()).contentType("image/jpg");
			}
//		}else{
//			context.getFlashScope().put("error", "report.notfound");
//			return Results.redirect("/report");
//		}
	}
}
