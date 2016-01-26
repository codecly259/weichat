package wx.sunl.model.weather;

import java.util.Date;

/**
 * 
* 天气封装类
* @since weichat 1.0
* @version 1.0 2015年3月26日
* @author maxinchun
 */
public class Weather {
	
	//城市气象编号
	private String weaid;
	private String days;
	private String week;
	private String citynm; //城市/地区
	private String cityid; //气象编号
	private String temperature; //温度
	private String humidity; //湿度
	private String weather; //天气
	private String weather_icon; //天气图标(目录b/c/d/n供使用)
	private String weather_icon1; //天气图标1(目录b/c/d/n供使用)
	private String wind; //风向
	private String winp; //风力
	private int temp_high; //最高温度
	private int temp_low; //最低温度
	private int humi_high; //最高湿度
	private int humi_low; //最低湿度
	private int weatid; //天气ID 详见:http://api.k780.com:88/?app=weather.wtype&format=xml
	private int weatid1; //天气ID1 详见:http://api.k780.com:88/?app=weather.wtype&format=xml
	private int windid; //风向ID
	private int winpid; //风力id
	public String getWeaid() {
		return weaid;
	}
	public void setWeaid(String weaid) {
		this.weaid = weaid;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getCitynm() {
		return citynm;
	}
	public void setCitynm(String citynm) {
		this.citynm = citynm;
	}
	public String getCityid() {
		return cityid;
	}
	public void setCityid(String cityid) {
		this.cityid = cityid;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getHumidity() {
		return humidity;
	}
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getWeather_icon() {
		return weather_icon;
	}
	public void setWeather_icon(String weather_icon) {
		this.weather_icon = weather_icon;
	}
	public String getWeather_icon1() {
		return weather_icon1;
	}
	public void setWeather_icon1(String weather_icon1) {
		this.weather_icon1 = weather_icon1;
	}
	public String getWind() {
		return wind;
	}
	public void setWind(String wind) {
		this.wind = wind;
	}
	public String getWinp() {
		return winp;
	}
	public void setWinp(String winp) {
		this.winp = winp;
	}
	public int getTemp_high() {
		return temp_high;
	}
	public void setTemp_high(int temp_high) {
		this.temp_high = temp_high;
	}
	public int getTemp_low() {
		return temp_low;
	}
	public void setTemp_low(int temp_low) {
		this.temp_low = temp_low;
	}
	public int getHumi_high() {
		return humi_high;
	}
	public void setHumi_high(int humi_high) {
		this.humi_high = humi_high;
	}
	public int getHumi_low() {
		return humi_low;
	}
	public void setHumi_low(int humi_low) {
		this.humi_low = humi_low;
	}
	public int getWeatid() {
		return weatid;
	}
	public void setWeatid(int weatid) {
		this.weatid = weatid;
	}
	public int getWeatid1() {
		return weatid1;
	}
	public void setWeatid1(int weatid1) {
		this.weatid1 = weatid1;
	}
	public int getWindid() {
		return windid;
	}
	public void setWindid(int windid) {
		this.windid = windid;
	}
	public int getWinpid() {
		return winpid;
	}
	public void setWinpid(int winpid) {
		this.winpid = winpid;
	}
	
	@Override
	public String toString() {
		return "Weather [weaid=" + weaid + ", days=" + days + ", week=" + week
				+ ", citynm=" + citynm + ", cityid=" + cityid
				+ ", temperature=" + temperature + ", humidity=" + humidity
				+ ", weather=" + weather + ", weather_icon=" + weather_icon
				+ ", weather_icon1=" + weather_icon1 + ", wind=" + wind
				+ ", winp=" + winp + ", temp_high=" + temp_high + ", temp_low="
				+ temp_low + ", humi_high=" + humi_high + ", humi_low="
				+ humi_low + ", weatid=" + weatid + ", weatid1=" + weatid1
				+ ", windid=" + windid + ", winpid=" + winpid + "]";
	}
	
}
