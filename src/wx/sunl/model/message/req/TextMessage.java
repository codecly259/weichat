package wx.sunl.model.message.req;

/**
 * 
* 文本消息
* @since weichat 1.0
* @version 1.0 2015年3月21日
* @author maxinchun
 */
public class TextMessage extends BaseMessage {
	
	private String Content;//消息内容

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		this.Content = content;
	}

	@Override
	public String toString() {
		return "TextMessage [Content=" + Content + "]";
	}
	
}
