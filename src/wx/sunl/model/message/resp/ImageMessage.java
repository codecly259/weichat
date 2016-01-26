package wx.sunl.model.message.resp;

/**
 * 
* 回复图片消息
* @since weichat 1.0
* @version 1.0 2015年3月21日
* @author maxinchun
 */
public class ImageMessage extends BaseMessage {
	private String MediaId; //通过上传多媒体文件，得到的id

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	@Override
	public String toString() {
		return "ImageMessage [MediaId=" + MediaId + "]";
	}
	
}
