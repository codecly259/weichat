package wx.sunl.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.Gson;

import wx.sunl.model.baidu.TranslateResult;
import wx.sunl.util.Md5Util;

/**
 * 
 * 百度翻译服务
 * 
 * @since weichat 1.0
 * @version 1.0 2015年3月24日
 * @author maxinchun
 */
public class BaiduTranslateService {

	/**
	 * 
	* 发起http请求获取返回结果
	* @param requsetUrl 请求地址
	* @return
	 */
	public static String httpRequset(String requsetUrl){
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(requsetUrl);
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
	* utf编码
	* @param source
	* @return
	 */
	public static String urlEncodeUTF8(String source){
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 
	* 翻译（中 -> 英；英 -> 中；日 -> 中）
	* @param source
	* @return
	 */
	public static String translate(String source){
		String url = "http://api.fanyi.baidu.com/api/trans/vip/translate";
		String appid = "20160126000009445"; 
		String key = "04OklJg9qpyd8M6GhBXy";//秘钥
		String salt = "123456"; //随机数
		//appid+q+salt+密钥 的MD5值
		String sign = Md5Util.encryptionWithMd5(appid + source + salt + key);
		
		String dst = null;
		//组装查询地址
//		String requestUrl = "http://openapi.baidu.com/public/2.0/bmt/translate?client_id=2mye0ZM8302vZRLsTVH9d2C8&q={keyWord}&from=auto&to=auto";
		String requestUrl = url+"?q={keyWord}&from=auto&to=en&appid="+appid+"&salt="+salt+"&sign="+sign;
		//对参数q的值进行urlEncode utf-8编码
		requestUrl = requestUrl.replace("{keyWord}", urlEncodeUTF8(source));
		
		//查询并解析结果
		try {
			//查询并获得返回结果
			String json = httpRequset(requestUrl);
			//通过Gson工具将json转换成TranslateResult对象
			TranslateResult translateResult = new Gson().fromJson(json, TranslateResult.class);
			//取出translateResult中的译文
			dst = translateResult.getTrans_result().get(0).getDst();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(null == dst){
			dst = "翻译系统异常，请稍后重试！";
		}
		return dst;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String source = "测试";
		System.out.println(translate(source));
		
	}
}
