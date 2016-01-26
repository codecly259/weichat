package wx.sunl.model.message.resp;

/**
 * 
* 响应消息之文本消息
* @since weichat 应用程序版本号
* @version 1.0 2015年3月21日
* @author maxinchun
 */
public class TextMessage extends BaseMessage {
	private String Content; //回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示） 

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	@Override
	public String toString() {
		return "TextMessage [Content=" + Content + "]";
	}
	
}
