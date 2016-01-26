package wx.sunl.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import wx.sunl.model.baidu.BaiduPlace;
import wx.sunl.model.baidu.UserLocation;
import wx.sunl.model.message.resp.Article;
import wx.sunl.util.HttpRequestUtil;

/**
 * 
* 百度地图搜索服务类
* @since weichat 1.0
* @version 1.0 2015年3月27日
* @author maxinchun
 */
public class BaiduMapService {
	/**
	 * 
	* 调用百度地图API搜索周边信息
	* @param query 搜索关键字
	* @param lng 经度
	* @param lat 纬度
	* @return 搜索百度地图结果
	* @throws Exception
	 */
	public static List<BaiduPlace> searchPlace(String query, String lng, String lat) throws Exception{
		//调用百度地图接口
		String requestUrl ="http://api.map.baidu.com/place/v2/search?&query=[QUERY]&location=[LAT],[LNG]&radius=2000&output=xml&"
				+ "scope=2&page_size=10&page_num=0&ak=[AK]";
		requestUrl = requestUrl.replace("[QUERY]", java.net.URLEncoder.encode(query, "utf-8"));
		requestUrl = requestUrl.replace("[LAT]", lat);
		requestUrl = requestUrl.replace("[LNG]", lng);
		requestUrl = requestUrl.replace("[AK]", "2mye0ZM8302vZRLsTVH9d2C8");
		
		String respXml = HttpRequestUtil.httpRequset(requestUrl);
		List<BaiduPlace> placeList = parsePlaceXml(respXml);
		return placeList;
	}
	
	/**
	 * 
	* 解析xml文件为百度地图集合
	* @param respXml 返回的xml内容
	* @return
	 */
	@SuppressWarnings("unchecked")
	private static List<BaiduPlace> parsePlaceXml(String respXml) {
		List<BaiduPlace> placeList = null;
		try {
			Document document = DocumentHelper.parseText(respXml);
			Element root = document.getRootElement();
			Element resultsElement = root.element("results");
			List<Element> resultElementList = resultsElement.elements("result");
			if(resultElementList != null && resultElementList.size() > 0){
				placeList = new ArrayList<BaiduPlace>();
				Element nameElement = null;
				Element addressElement = null;
				Element locationElement = null;
				Element telephoneElement = null;
				Element detailInfoElement = null;
				Element distanceElement = null;
				for(Element resultElement : resultElementList){
					nameElement = resultElement.element("name");
					addressElement = resultElement.element("address");
					locationElement = resultElement.element("location");
					telephoneElement = resultElement.element("telephone");
					detailInfoElement = resultElement.element("detail_info");
					
					BaiduPlace place = new BaiduPlace();
					place.setName(nameElement.getText());
					place.setAddress(addressElement.getText());
					place.setLng(locationElement.element("lng").getText());
					place.setLat(locationElement.element("lat").getText());
					if(telephoneElement != null){
						place.setTelephone(telephoneElement.getText());
					}
					if(detailInfoElement != null){
						distanceElement = detailInfoElement.element("distance");
						if(distanceElement != null){
							place.setDistance(Integer.parseInt(distanceElement.getText()));
						}
					}
					placeList.add(place);
				}
				Collections.sort(placeList);
						
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return placeList;
	}

	/**
	 * 
	* 百度地图集合转化为图文类集合
	* @param placeList 查询到的百度地图位置信息
	* @param bd09Lng 百度地址经度
	* @param bd09Lat 百度地址纬度
	* @param basePath 项目完整地址
	* @return
	 */
	public static List<Article> makeArticleList(List<BaiduPlace> placeList,  String bd09Lng, String bd09Lat, String basePath){
		
//		String navigationPath = basePath + "jsp/baidu/navigation.jsp";
		// 由于openShift必须使用https才能访问，而https百度的接口会有问题。所有把页面改到sae上的php页面
		String navigationPath = "http://codecly1.applinzi.com/baidu/navigation.php";
		List<Article> list = new ArrayList<Article>();
		BaiduPlace place = null;
		String picUrl = "";
		for(int i = 0; i < placeList.size(); i++){
			place = placeList.get(i);
			Article article = new Article();
			article.setTitle(place.getName() + "\n距离约" + place.getDistance() + "米\n电话："+place.getTelephone());
			article.setUrl(String.format(navigationPath + "?p1=%s,%s&p2=%s,%s", bd09Lng, bd09Lat, place.getLng(), place.getLat()));
			if(i == 0){
				picUrl = "http://api.map.baidu.com/staticimage?center=" + place.getLng() + "," + place.getLat() +
						"&width=320&height=160&zoom=18&markers=" + place.getLng() + "," + place.getLat() + "&markerStyles=l,A";
				article.setPicUrl(picUrl);
			}else{
				picUrl = "http://api.map.baidu.com/staticimage?center=" + place.getLng() + "," + place.getLat() + 
						"&width=80&height=80&zoom=15&markers=" + place.getLng() + "," + place.getLat() + "&markerStyles=l,A";
				article.setPicUrl(picUrl);
			}
			list.add(article);
		}
		return list;
	}
	
	/**
	 * 
	* 搜搜地图经纬度坐标 转化为百度地图经纬度坐标
	* @param lng
	* @param lat
	* @return
	 */
	public static UserLocation convertCoord(String lng, String lat){
		String convertUrl = "http://api.map.baidu.com/geoconv/v1/?coords=[LNG],[LAT]&from=3&to=5&ak=[AK]";
		convertUrl = convertUrl.replace("[LNG]", lng);
		convertUrl = convertUrl.replace("[LAT]", lat);
		convertUrl = convertUrl.replace("[AK]", "2mye0ZM8302vZRLsTVH9d2C8");
		UserLocation location = new UserLocation();
		try {
			String jsonCoord = HttpRequestUtil.httpRequset(convertUrl);
			JSONObject jsonObject = JSONObject.fromObject(jsonCoord);
			location.setBd09Lng(jsonObject.getJSONArray("result").getJSONObject(0).getString("x"));
			location.setBd09Lat(jsonObject.getJSONArray("result").getJSONObject(0).getString("y"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return location;
	}
	
	public static List<Article> searchNear(String query, String lng, String lat, String basePath) throws Exception{
		UserLocation location = convertCoord(lng, lat);
		List<BaiduPlace> placeList = searchPlace(query, location.getBd09Lng(), location.getBd09Lat());
		List<Article> articles = makeArticleList(placeList, location.getBd09Lng(), location.getBd09Lat(), basePath);
		return articles;
	}
	
	public static void main(String[] args) throws Exception {
		List<Article> list = searchNear("美食", "91.136215", "29.658976","https://tomcat-codecly.rhcloud.com/");
//		List<Article> aritcles = makeArticleList(list, bd09Lng, bd09Lat);
//		convertCoord("91.136215", "29.658976");
//		System.out.println("ok");
		System.out.println("完成"+list.toString());
	}
	
	
	
	
	
	
	
	
}
