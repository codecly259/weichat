package wx.sunl.model.message.req;

/**
 * 
* 请求消息的基类 (普通用户 -> 公众账号)
* @since weichat 1.0
* @version 1.0 2015年3月21日
* @author maxinchun
 */
public class BaseMessage {
	private String ToUserName; //开发者微信号
	private String FromUserName; //发送方账号(一个OpenId)
	private long CreateTime; //消息创建时间 (整型)
	private String MsgType; //消息类型（text/image/voice/video/location/link）
	private long MsgId; //消息id，64位整型 
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	public long getMsgId() {
		return MsgId;
	}
	public void setMsgId(long msgId) {
		MsgId = msgId;
	}
	
	@Override
	public String toString() {
		return "BaseMessage [ToUserName=" + ToUserName + ", FromUserName="
				+ FromUserName + ", CreateTime=" + CreateTime + ", MsgType="
				+ MsgType + ", MsgId=" + MsgId + "]";
	}
	
	
}
