package wx.sunl.model.weather;

import java.util.List;

/**
 * 
* 未来一周天气封装类
* @since weichat 1.0
* @version 1.0 2015年3月26日
* @author maxinchun
 */
public class FutureWeather {
	
	private String success;//1:成功；0：失败
	private String msg; //出错消息,当出错时将会出现此节点，否则不出现
	private List<Weather> result;
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<Weather> getResult() {
		return result;
	}
	public void setResult(List<Weather> result) {
		this.result = result;
	}
	
	@Override
	public String toString() {
		return "FutherWeather [success=" + success + ", msg=" + msg
				+ ", result=" + result + "]";
	}
	
	
	
}
