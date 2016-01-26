package wx.sunl.service;

import java.util.List;

import wx.sunl.model.weather.FutureWeather;
import wx.sunl.model.weather.Weather;
import wx.sunl.util.HttpRequestUtil;

import com.google.gson.Gson;

/**
 * 
* 天气预报服务类
* @since weichat 1.0
* @version 1.0 2015年3月26日
* @author maxinchun
 */
public class WeatherService {
	/**
	 * 
	* 调用天气接口查询未来一周天气
	 */
	private static FutureWeather futureWeather(String weaid){
		FutureWeather futureWeather = null;
		try {
			//调用天气预报API的地址
			String requestUrl = "http://api.k780.com:88/?app=weather.future&weaid=[WEAID]&appkey=[APPKEY]&sign=[SIGN]&format=json";
			requestUrl = requestUrl.replace("[WEAID]", java.net.URLEncoder.encode(weaid, "utf-8"));
			requestUrl = requestUrl.replace("[APPKEY]", "13451");
			requestUrl = requestUrl.replace("[SIGN]", "533b4207160c0716ee29d8da93166334");
			//发送http get请求
			String json = HttpRequestUtil.httpRequset(requestUrl);
			//将返回的json格式转化为 天气预报类
			futureWeather = new Gson().fromJson(json, FutureWeather.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return futureWeather;
	}
	
	/**
	 * 
	* 根据天气预报类组装消息
	* @param futureWeather 查询的天气预报类
	* @return
	 */
	private static String makeMessage(FutureWeather futureWeather){
		StringBuffer result = new StringBuffer("网络错误,请稍后再试！");
		if(futureWeather != null){
			if("0".equals(futureWeather.getSuccess())){
				result = new StringBuffer(futureWeather.getMsg());
			}else{
				List<Weather> weathers = futureWeather.getResult();
				if(weathers != null && weathers.size() > 0){
					result = new StringBuffer("未来一周天气预报：\n");
					for(Weather weather : weathers){
						result.append(weather.getDays()).append("\t").append(weather.getWeek()).append("\t");
						result.append(weather.getWeather()).append("\t").append(weather.getTemperature()).append("\t");
						result.append(weather.getWind()).append("\t").append(weather.getWinp()).append("\n");
					}
				}
			}
		}
		
		return result.toString();
	}
	
	/**
	 * 
	* 提供外部使用的天气预报方法
	* @param weaid 地市名称/地市拼音/地市气象编码
	* @return
	 */
	public static String weatherReport(String weaid){
		FutureWeather futureWeather = futureWeather(weaid);
		String result = makeMessage(futureWeather);
		return result;
	}
	
	public static void main(String[] args) {
		String message = weatherReport("hefei");
		System.out.println(message);
	}
	
}
