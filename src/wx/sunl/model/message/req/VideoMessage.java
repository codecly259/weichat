package wx.sunl.model.message.req;

/**
 * 
* 视屏消息
* @since weichat 1.0
* @version 1.0 2015年3月21日
* @author maxinchun
 */
public class VideoMessage extends BaseMessage {
	private String MediaId; //视频消息媒体id，可以调用多媒体文件下载接口拉取数据
	private String ThumbMediaId; //视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据
	public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	public String getThumbMediaId() {
		return ThumbMediaId;
	}
	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}
	@Override
	public String toString() {
		return "VideoMessage [MediaId=" + MediaId + ", ThumbMediaId="
				+ ThumbMediaId + "]";
	}
	
	
}
