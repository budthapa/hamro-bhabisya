package etc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
	/**
	 * Is email valid
	 * 
	 * @param email
	 * @return true if it is valid
	 */
	public static boolean isValidEmail(String email) {
		if (email.length() > 128) {
			return false;
		}
		Pattern p = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*"
				+ "@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * Check the password requirement minimum 8 digit,1 number and 1 alphabet
	 * 
	 * @param password
	 * @return true if it is valid and full fill the requirement.
	 */
	public static boolean isValidPsw(String password) {
		String PSW_PATTERN = "^.*(?=.{8,})(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[\\W_]).*$";
		Pattern p = Pattern.compile(PSW_PATTERN);
		Matcher m = p.matcher(password);

		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean validatePassword(String password) {
		String regex = "^[a-zA-Z0-9]{6,15}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}

	/**
	 * Check whether the input is alphabet or not.
	 * 
	 * @param alphabet
	 * @return true if the input is alphabet
	 */
	public static boolean isAlphabet(String alphabet) {
		String expression="^[\\p{L} .'-]+$"; //name validation with space
//		String expression = "[a-z A-Z]+$";
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(alphabet);
		if (matcher.matches()) {
			return true;
		}
		return false;

	}

	/**
	 * Check whether the input is alphabet only or not.
	 * 
	 * @param alphabet
	 * @return true if the input is alphabet
	 */
	public static boolean isAlphabetOnly(String alphabet) {
		String expression = "[a-zA-Z]+$";
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(alphabet);
		if (matcher.matches()) {
			return true;
		}
		return false;

	}

	/**
	 * Check whether the input is alphabet or not.
	 * 
	 * @param alphabet
	 * @return true if the input is alphabet
	 */
	public static boolean isStrictAlphabetAndSpace(String alphabet) {
		String expression = "[a-z A-Z]+$";
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(alphabet);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * Check whether the alias name is valid or not
	 * 
	 * @param aliasName
	 * @return true if the alias name is valid
	 */
	public static boolean isAliasNameValid(String aliasName) {
		String expression = "^[[a-zA-Z]-\\d]{0,10}$";
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(aliasName);
		if (matcher.matches())
			return true;
		return false;
	}

	/**
	 * Check whether the input is number or not.
	 * 
	 * @param num
	 * @return true if the input is number
	 */
	public static boolean isNumber(String num) {
		String expression = "^[0-9]+$";
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(num);
		if (matcher.matches()) {
			return true;
		}
		return false;

	}

	public static boolean isDoubleValid(String num) {
		String expression = "^[0-9]*\\.[0-9]*$";
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(num);
		if (matcher.matches()) {
			return true;
		}
		return false;

	}

	/**
	 * Method takes date in string format and return date in date format.
	 * 
	 * @param dateValue
	 * @return date
	 */
	public static Date getDateFromString(String dateValue) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			return sdf.parse(dateValue);
		} catch (Exception e) {
			return null;
		}
	}

	
	/**
	 * Check whether the phone number is valid or not.
	 * 
	 * @param phoneNumber
	 * @return true if phone number is valid
	 */
	public static boolean isValidPhoneNumber(String phoneNumber) {
		String expression = "[0-9]*";
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(phoneNumber);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}

	public static boolean isValidPhone(String phone) {
		String exp = "^([+][0-9]{0,3}[-][0-9]{1,4}[-])?[0-9]{7,18}$";
		Pattern pattern = Pattern.compile(exp);
		Matcher matcher = pattern.matcher(phone);
		if (matcher.matches()) {
			return true;
		}
		return false;

	}

	/**
	 * It round of the value upto 2 decimal places.Eg. 123.9684 -->123.97,
	 * 123.9486-->123.95,123.9584-->123.96
	 * 
	 * @param num
	 * @return float rounded value.
	 */
	public static Float roundThreePlaces(Float num) {
		BigDecimal bigD = new BigDecimal(num);
		bigD = bigD.setScale(2, RoundingMode.HALF_EVEN);
		return bigD.floatValue();
	}

	public static boolean isAlphaNumeric(String s) {
		String pattern = "^[a-zA-Z0-9 ]*$";
		if (s.matches(pattern)) {
			return true;
		}
		return false;
	}

	public static boolean isValidAddress(String address) {
		String pattern = "^[a-zA-Z - 0-9]*$";
		if (address.matches(pattern)) {
			return true;
		}
		return false;
	}

	// // CHECK IF THE URL IS VALID OR NOT //////
	public static boolean isValidURL(String url) {
		String pattern = "(http|https://)?[a-zA-Z_0-9\\-]+"
				+ "(\\.\\w[a-zA-Z_0-9\\-]+)+(/[#&\\n\\-=?\\+\\%/\\.\\w]+)?";
		if (url.matches(pattern))
			return true;
		return false;
	}

	public static String getAliasName(Long maxId, char charAt) {
		String autoAlias = "";

		if (maxId <= 9)
			autoAlias = charAt + "-000" + maxId;
		else if (maxId <= 999)
			autoAlias = charAt + "-00" + maxId;
		else if (maxId <= 9999)
			autoAlias = charAt + "-" + maxId;
		return autoAlias;
	}

	public static boolean isValidCitizen(String citizenshipNumber){
		String pattern="[a-zA-Z0-9/-]+";
		if(citizenshipNumber.matches(pattern)){
			return true;
		}
		return false;
	}
		
	public static String setFloat(double value, int floatingPoint) {
		if (floatingPoint < 0)
			throw new IllegalArgumentException();

		return String.format("%." + floatingPoint + "f", value);
	}

	public static Double roundTwoDecimalFormat(Double d) {
		int decimalPlaces = 2;
		BigDecimal bd = new BigDecimal(d);
		bd = bd.setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP);
		return bd.doubleValue();
	}
	
	public static String getMonth(int value){
		String month = "";
		switch(value){
			case 0 : month = "Jan"; break;
			case 1 : month = "Feb"; break;
			case 2 : month = "Mar"; break;
			case 3 : month = "Apr"; break;
			case 4 : month = "May"; break;
			case 5 : month = "Jun"; break;
			case 6 : month = "Jul"; break;
			case 7 : month = "Aug"; break;
			case 8 : month = "Sep"; break;
			case 9 : month = "Oct"; break;
			case 10 : month = "Nov"; break;
			case 11 : month = "Dec"; break;
		}
		return month;
	}

}
