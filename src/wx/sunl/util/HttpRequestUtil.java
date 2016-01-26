package wx.sunl.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 
* http请求工具类
* @since weichat 1.0
* @version 1.0 2015年3月25日
* @author maxinchun
 */
public class HttpRequestUtil {
	/**
	 * 
	* 发起http请求获取返回结果
	* @param requestUrl 请求地址
	* @return
	 */
	public static String httpRequset(String requestUrl){
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection)url.openConnection();
			
			httpUrlConn.setDoOutput(false);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			
			httpUrlConn.setRequestMethod("GET");
			httpUrlConn.connect();
			
			//将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			
			String str = null;
			while( (str = bufferedReader.readLine()) !=null ){
				buffer.append(str);
			}
			
			//释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			httpUrlConn.disconnect();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}
	
	/**
	 * 
	* 发送http请求获取返回的输入流
	* @param requestUrl 请求地址
	* @return
	 */
	public static InputStream httpRequestIO(String requestUrl) {
		InputStream inputStream = null;
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection)url.openConnection();
			httpUrlConn.setDoInput(true);
			httpUrlConn.setRequestMethod("GET");
			httpUrlConn.connect();
			//获取返回的输入流
			inputStream = httpUrlConn.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inputStream;
	}
	
	/**
	 * 
	* 编码
	* @param source 源字符串
	* @encode 目标编码
	* @return
	 */
	public static String urlEncode(String source, String encode){
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, encode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) {
		String res = HttpRequestUtil.httpRequset("http://www.baidu.com");
		System.out.println(res);
	}

}
