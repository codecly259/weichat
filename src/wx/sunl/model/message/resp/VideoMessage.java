package wx.sunl.model.message.resp;

/**
 * 
* 回复视频消息
* @since weichat 1.0
* @version 1.0 2015年3月21日
* @author maxinchun
 */
public class VideoMessage extends BaseMessage {
	private Video video;//视频

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	@Override
	public String toString() {
		return "VideoMessage [video=" + video + "]";
	}

}
