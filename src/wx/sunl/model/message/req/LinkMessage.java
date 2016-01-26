package wx.sunl.model.message.req;

/**
 * 
* 链接消息
* @since weichat 1.0
* @version 1.0 2015年3月21日
* @author maxinchun
 */
public class LinkMessage extends BaseMessage {
	private String Title; //消息标题
	private String Description; //消息描述
	private String Url; //消息链接
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
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
	@Override
	public String toString() {
		return "LinkMessage [Title=" + Title + ", Description=" + Description
				+ ", Url=" + Url + "]";
	}
	
}
