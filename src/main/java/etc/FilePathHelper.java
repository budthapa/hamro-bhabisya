package etc;

import com.google.inject.Inject;

import ninja.utils.NinjaProperties;
public class FilePathHelper {
	@Inject
	NinjaProperties ninja;
	public String getFilePath(){
		if(ninja.isProd()){
			return "../src/main/java/assets/image/";
		}else{
			return "../hbjpa/src/main/java/assets/image/";
		}
	}
}
