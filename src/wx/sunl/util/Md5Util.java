package wx.sunl.util;

import java.security.MessageDigest;

import org.apache.axis.types.HexBinary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Md5Util {
	public static final Logger logger = LoggerFactory.getLogger(Md5Util.class);
	
	/**
	 * md5加密
	 * @param data 需要加密的字符串
	 * @return 加密后的结果字符串
	 */
	public static final String encryptionWithMd5(String data) {
		// 如果有空则返回""
		String s = data == null ? "" : data;
		try {
			// 将字符串转为字节数组
			byte[] strTemp = s.getBytes();
			// 加密器
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			// 执行加密
			mdTemp.update(strTemp);
			// 加密结果
			byte[] md = mdTemp.digest();
			// return byteArrayToString(md);
			return HexBinary.encode(md);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static void main(String[] args) {
		String data = "2015063000000001apple143566028812345678"; //f89f9594663708c1605f3d736d01d2d4
		System.out.println(encryptionWithMd5(data));
	}
}
