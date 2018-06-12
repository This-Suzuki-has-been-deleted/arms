package validation;

public class Validation {
	public boolean integerValidation(Integer num){
		if(num < 0){
			return false;
		}
		return true;
	}

	public boolean nullCheck(Integer num){
		if(num == null){
			return false;
		}
		return true;
	}

	public boolean nullCheck(String str){
		if(str == null){
			return false;
		}
		return true;
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
