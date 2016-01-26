package wx.sunl.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import wx.sunl.model.baidu.UserLocation;

public class MySQLUtil {
	private static final Logger logger = LoggerFactory.getLogger(MySQLUtil.class);
	/**
	 * 
	* 获取mysql数据库连接
	* @param request
	* @return
	 */
	private Connection getConn(HttpServletRequest request){
		Connection conn = null;
		
//		String url = "jdbc:mysql://sqld.duapp.com:4050/nrZHqSESOIVANlDmiJIw";  // BAE链接数据库配置
//		String username = "2mye0ZM8302vZRLsTVH9d2C8"; //API Key
//		String password = "ynGEnAovd72qwTSdZPg1GxPPZbKLeipY"; ////SecretKey
		
		// openShift 链接数据库配置
		String url = "jdbc:mysql://127.9.92.2:3306/tomcat";
		String username = "adminl6ZBwxU"; //API Key  adminl6ZBwxU 
		String password = "CUI382T9gMhZ"; ////SecretKey   CUI382T9gMhZ
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url,username,password);
			logger.info("success:连接数据库成功");
		} catch (Exception e) {
			logger.info("false:连接数据库失败! " + e.getMessage());
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * 
	* 插入数据库操作
	* @param request
	* @param openId
	* @param lng
	* @param lat
	* @param bd09_lng
	* @param bd09_lat
	 */
	public static void saveUserLocation(HttpServletRequest request, String openId, String lng,
			String lat, String bd09_lng, String bd09_lat){
		/*String sql = "insert into position_weixin (open_id,lng,lat,bd09_lng,bd09_lat) "
				+ "values(?,?,?,?,?)";
		try {
			Connection conn = new MySQLUtil().getConn(request);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, openId);
			ps.setString(2, lng);
			ps.setString(3, lat);
			ps.setString(4, bd09_lng);
			ps.setString(5, bd09_lat);
			logger.info("open_id"+openId+",lng:"+lng+",lat"+lat+",bd09_lng:"+bd09_lng+",bd09_lat:"+bd09_lat);
			logger.info("save location sql:"+sql);
			ps.executeUpdate();
			conn.commit();
			ps.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		String sql = "insert into position_weixin (open_id,lng,lat,bd09_lng,bd09_lat) "+
				"values('"+openId+"', '"+lng+"', '"+lat+"', '"+bd09_lng+"', '"+bd09_lat+"')";
		try {
			Connection conn = new MySQLUtil().getConn(request);
			Statement stat = conn.createStatement();
			logger.info("insert location sql : "+sql);
			stat.executeUpdate(sql);
			
			stat.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void updateUserLocation(HttpServletRequest request, String openId, 
			String lng, String lat, String bd09_lng, String bd09_lat){
		String sql = "";
	}
	
	/**
	 * 
	* 查询上次位置信息
	* @param request
	* @param openId
	* @return
	 */
	public static UserLocation getLastLocation (HttpServletRequest request, String openId){
		UserLocation userLocation = null;
		String sql = "select open_id, lng, lat, bd09_lng, bd09_lat "
				+ "from position_weixin where open_id = ? order by id desc limit 0,1";
		try {
			Connection conn = new MySQLUtil().getConn(request);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, openId);
			ResultSet rs = ps.executeQuery();
			logger.info("select loction sql:"+sql);
			if(rs.next()){
				userLocation = new UserLocation();
				userLocation.setOpenId(rs.getString("open_id"));
				userLocation.setLng(rs.getString("lng"));
				userLocation.setLat(rs.getString("lat"));
				userLocation.setBd09Lng(rs.getString("bd09_lng"));
				userLocation.setBd09Lat(rs.getString("bd09_lat"));
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userLocation;
		
	}
	
	public static void main(String[] args) {
		/*saveUserLocation(null, "1111", "91", "29", "91.111", "29.001");
		String content = "附近ATM";
		String keyWord = content.replaceAll("^附近[\\+ ~@#%-_=]?", "");
		System.out.println(keyWord);
		content = "附近111";
		keyWord = content.replaceAll("^附近[\\+ ~@#%-_=]?", "");
		System.out.println("ok");*/
		String openId = "111";
		String lng = "222";
		String lat = "333";
		String bd09_lng = "444";
		String bd09_lat = "555";
		String sql = "insert into position_weixin (open_id,lng,lat,bd09_lng,bd09_lat) "+
				"values('"+openId+"', '"+lng+"', '"+lat+"', '"+bd09_lng+"', '"+bd09_lat+"')";
		logger.info("insert location sql : "+ sql);
	}
}
