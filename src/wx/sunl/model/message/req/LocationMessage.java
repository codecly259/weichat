package wx.sunl.model.message.req;

/**
 * 
* 地理位置消息
* @since weichat 1.0
* @version 1.0 2015年3月21日
* @author maxinchun
 */
public class LocationMessage extends BaseMessage {
	private String Location_X; //地理位置维度
	private String Location_Y; //地理位置经度 
	private String Scale; //地图缩放大小 
	private String Label; //地理位置信息 
	public String getLocation_X() {
		return Location_X;
	}
	public void setLocation_X(String location_X) {
		Location_X = location_X;
	}
	public String getLocation_Y() {
		return Location_Y;
	}
	public void setLocation_Y(String location_Y) {
		Location_Y = location_Y;
	}
	public String getScale() {
		return Scale;
	}
	public void setScale(String scale) {
		Scale = scale;
	}
	public String getLabel() {
		return Label;
	}
	public void setLabel(String label) {
		Label = label;
	}
	@Override
	public String toString() {
		return "LocationMessage [Location_X=" + Location_X + ", Location_Y="
				+ Location_Y + ", Scale=" + Scale + ", Label=" + Label + "]";
	}
	
}
