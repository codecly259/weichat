package wx.sunl.model.message.req;

/**
 * 
* 图片消息
* @since weichat 1.0
* @version 1.0 2015年3月21日
* @author maxinchun
 */
public class ImageMessage extends BaseMessage {
	
	private String PicUrl; //图片链接

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	@Override
	public String toString() {
		return "ImageMessage [PicUrl=" + PicUrl + "]";
	}
	
	
}
