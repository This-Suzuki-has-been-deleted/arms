package others;

public class SpaceKill {
	public String stringSpaceKill(String str){
		str = str.replaceAll("　", ""); //全角スペースを空文字に置換
		str = str.replaceAll(" ", ""); //文字の間の半角スペースを空文字に変換
		return str;
	}

}
