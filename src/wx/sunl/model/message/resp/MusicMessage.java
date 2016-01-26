package wx.sunl.model.message.resp;

/**
 * 
* 回复音乐消息
* @since weichat 1.0
* @version 1.0 2015年3月21日
* @author maxinchun
 */
public class MusicMessage extends BaseMessage {
	private Music Music; //音乐

	public Music getMusic() {
		return Music;
	}

	public void setMusic(Music music) {
		this.Music = music;
	}

	@Override
	public String toString() {
		return "MusicMessage [Music=" + Music + "]";
	}
	
}
