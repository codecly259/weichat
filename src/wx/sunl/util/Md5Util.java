package wx.sunl.util;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Md5Util {
	public static final Logger logger = LoggerFactory.getLogger(Md5Util.class);

	/**
	 * md5加密
	 * 
	 * @param data
	 *            需要加密的字符串
	 * @return 加密后的结果字符串
	 */
	public static final String encryptionWithMd5(String data) {
		// 如果有空则返回""
		String s = data == null ? "" : data;
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.reset();
            md5.update(strTemp);
            byte[] digest = md5.digest();
            return toHex(digest);
			
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String toHex(final byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (final byte b : bytes) {
            String s = Integer.toHexString(0xff & b);
            if (s.length() < 2) {
                sb.append("0");
            }
            sb.append(s);
        }
        return sb.toString();
    }
	
	public static void main(String[] args) {
		String data = "2015063000000001apple143566028812345678"; // f89f9594663708c1605f3d736d01d2d4
		System.out.println(encryptionWithMd5(data));
	}
}
