package others;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginLogic {
	public String passHash(String pass) {
		MessageDigest md = null;
		StringBuffer buffer = new StringBuffer();

		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// メッセージダイジェスト更新
		md.update(pass.getBytes());

		// ハッシュ値を格納
		byte[] valueArray = md.digest();

		// ハッシュ値の配列をループ
		for (int i = 0; i < valueArray.length; i++) {
			// 値の符号を反転させ、16進数に変換
			String tmpStr = Integer.toHexString(valueArray[i] & 0xff);

			if (tmpStr.length() == 1) {
				// 値が一桁だった場合、先頭に0を追加し、バッファに追加
				buffer.append('0').append(tmpStr);
			} else {
				// その他の場合、バッファに追加
				buffer.append(tmpStr);
			}
		}
		String password = buffer.toString();

		return password;
	}



}
