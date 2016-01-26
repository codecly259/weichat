package wx.sunl.model.tuling;

/**
 * 
* 图灵机器人返回的文本信息类
* @since weichat 1.0
* @version 1.0 2015年3月25日
* @author maxinchun
 */
public class TulingText {
	//状态码
	private String code; 
	//文本内容
	private String text;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return "Text [code=" + code + ", text=" + text + "]";
	}
	
}
