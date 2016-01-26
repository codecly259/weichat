package wx.sunl.model.message.resp;

/**
 * 
* 音乐封装类
* @since weichat 应用程序版本号
* @version 1.0 2015年3月21日
* @author maxinchun
 */
public class Music {
	private String Title ;//音乐标题
	private String Description; //音乐描述
	private String MusicUrl ; //音乐链接
	private String HQMusicUrl ; //高质量音乐链接，WIFI环境优先使用该链接播放音乐 
	private String ThumbMediaId ; //缩略图的媒体id，通过上传多媒体文件，得到的id 
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getMusicUrl() {
		return MusicUrl;
	}
	public void setMusicUrl(String musicUrl) {
		MusicUrl = musicUrl;
	}
	public String getHQMusicUrl() {
		return HQMusicUrl;
	}
	public void setHQMusicUrl(String hQMusicUrl) {
		HQMusicUrl = hQMusicUrl;
	}
	public String getThumbMediaId() {
		return ThumbMediaId;
	}
	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}
	@Override
	public String toString() {
		return "Music [Title=" + Title + ", Description=" + Description
				+ ", MusicUrl=" + MusicUrl + ", HQMusicUrl=" + HQMusicUrl
				+ ", ThumbMediaId=" + ThumbMediaId + "]";
	}
	
	
}
