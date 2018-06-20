package validation;

import java.util.regex.Pattern;

public class Validation {

	public boolean nullCheck(String str){
		if(str == null){
			return false;
		}else if(str.equals("")){
			return false;
		}
		return true;
	}

	public boolean employeeCodeValidation(String str){
		Pattern pattern = Pattern.compile("^[0-9]*$");

		if(str.length() == 10){
			if((str.substring(0,2)).equals("aa")){
				if(pattern.matcher(str.substring(2)).matches()){
					return true;
				}
			}
		}
		return false;
	}

	public boolean passwordValidation(String str){
		boolean str_flg=false;
		boolean num_flg=false;
		if(str.length() >= 8){
			for(int i=0;str.length()>i;i++){
				if(str.substring(i).equals("[a-z]")){
					str_flg=true;
				}
				if(str.substring(i).equals("[0-9]")){
					num_flg=true;
				}
			}
			if(str_flg && num_flg){
				return true;
			}
		}
		return false;
	}

}
