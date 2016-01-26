package wx.sunl.model.message.resp;

/**
 * 
* 相应消息的基类 (公众账号 -> 普通用户)
* @since weichat 1.0
* @version 1.0 2015年3月21日
* @author maxinchun
 */
public class BaseMessage {
	private String ToUserName; //接收方帐号（收到的OpenID） 
	private String FromUserName; //开发者微信号 
	private long CreateTime; //消息创建时间 （整型） 
	private String MsgType; //消息类型（text/image/voice/video/location/link）
	private int FuncFlag; //位0x0001被标志时，星标刚收到的消息 
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
	public int getFuncFlag() {
		return FuncFlag;
	}
	public void setFuncFlag(int funcFlag) {
		FuncFlag = funcFlag;
	}
	@Override
	public String toString() {
		return "BaseMessage [ToUserName=" + ToUserName + ", FromUserName="
				+ FromUserName + ", CreateTime=" + CreateTime + ", MsgType="
				+ MsgType + ", FuncFlag=" + FuncFlag + "]";
	}
	
	
}
