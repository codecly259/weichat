package wx.sunl.model.baidu;

/**
 * 
* 结果对
* @since weichat 1.0
* @version 1.0 2015年3月24日
* @author maxinchun
 */
public class ResultPair {
	//原文
	private String src;
	//译文
	private String dst;
	
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getDst() {
		return dst;
	}
	public void setDst(String dst) {
		this.dst = dst;
	}
	
	@Override
	public String toString() {
		return "ResultPair [src=" + src + ", dst=" + dst + "]";
	}
	
}
