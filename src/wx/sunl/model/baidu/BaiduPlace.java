package wx.sunl.model.baidu;

/**
 * 
* 百度地图位置类
* @since weichat 1.0
* @version 1.0 2015年3月27日
* @author maxinchun
 */
public class BaiduPlace implements Comparable<BaiduPlace>{
	private String name; //poi名称 
	private String address; //poi地址信息 
	private String lng; //经度信息
	private String lat; //纬度信息
	private String telephone; //poi电话信息 
	private int distance; //距离中心点的距离 
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}


	@Override
	public String toString() {
		return "BaiduPlace [name=" + name + ", address=" + address + ", lng="
				+ lng + ", lat=" + lat + ", telephone=" + telephone
				+ ", distance=" + distance + "]";
	}

	/**
	 * 按照距离中心点的距离 由近到远排序
	* @param o
	* @return
	* @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(BaiduPlace o) {
		int result = 0;
		if(this.distance > o.getDistance()){
			result = 1;
		}else{
			result = -1;
		}
		return result;
	}
}
