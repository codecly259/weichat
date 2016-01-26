package wx.sunl.model.message.req;

/**
 * 
* 语音消息
* @since weichat 1.0
* @version 1.0 2015年3月21日
* @author maxinchun
 */
public class VoiceMessage extends BaseMessage {
	
	private String MediaId; //语音消息媒体id，可以调用多媒体文件下载接口拉取数据。 
	private String Formate; //语音格式，如amr，speex等 
	public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	public String getFormate() {
		return Formate;
	}
	public void setFormate(String formate) {
		Formate = formate;
	}
	@Override
	public String toString() {
		return "VoiceMessage [MediaId=" + MediaId + ", Formate=" + Formate
				+ "]";
	}
	
}
