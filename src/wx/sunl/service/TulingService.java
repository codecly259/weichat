package wx.sunl.service;

import com.google.gson.Gson;

import wx.sunl.model.tuling.TulingText;
import wx.sunl.util.HttpRequestUtil;

/**
 * 
* 图灵机器人服务类
* @since weichat 1.0
* @version 1.0 2015年3月25日
* @author maxinchun
 */
public class TulingService {
	
	public static String tulingRobot(String source){
		String result = null;
		//图灵账号注册激活后的APIkey
		String APIKEY = "da72e8dae4ef7a6b35e70ba830b45706";
		//文字UTF-8编码
		source = HttpRequestUtil.urlEncode(source, "utf-8");
		//调用图灵机器人的api地址
		String requestUrl = "http://www.tuling123.com/openapi/api?key=" + APIKEY + "&info=" + source;
		String json = HttpRequestUtil.httpRequset(requestUrl);
		TulingText tulingText = new Gson().fromJson(json, TulingText.class);
		result = tulingText.getText().replace("<br>", "\n").replace("#<br>", ""); 
		return result;
	}
	
	public static void main(String[] args) {
		String result = tulingRobot("笑话");
		System.out.println(result);
	}
}
