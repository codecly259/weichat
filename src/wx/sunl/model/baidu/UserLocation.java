package wx.sunl.model.baidu;

/**
 * 
* 用户地址封装类
* @since weichat 1.0
* @version 1.0 2015年3月27日
* @author maxinchun
 */
public class UserLocation {
	private int id;
	private String openId; //关注者的微信号
	private String lng; //用户发来的经度位置信息
	private String lat; //用户发来的纬度位置信息
	private String bd09Lng; //转化成百度地图的经度信息
	private String bd09Lat; //转化成百度地图的纬度信息
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getBd09Lng() {
		return bd09Lng;
	}
	public void setBd09Lng(String bd09Lng) {
		this.bd09Lng = bd09Lng;
	}
	public String getBd09Lat() {
		return bd09Lat;
	}
	public void setBd09Lat(String bd09Lat) {
		this.bd09Lat = bd09Lat;
	}
	
	@Override
	public String toString() {
		return "UserLocation [id=" + id + ", openId=" + openId + ", lng=" + lng
				+ ", lat=" + lat + ", bd09Lng=" + bd09Lng + ", bd09Lat="
				+ bd09Lat + "]";
	}
	
	
}
