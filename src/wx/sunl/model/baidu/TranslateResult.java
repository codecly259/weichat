package wx.sunl.model.baidu;

import java.util.List;

/**
 * 
* 调用百度翻译API查询结果
* @since weichat 1.0
* @version 1.0 2015年3月24日
* @author maxinchun
 */
public class TranslateResult {
	//实际采用的源语言
	private String from;
	//实际采用的目标语言
	private String to;
	//结果集
	private List<ResultPair> trans_result;
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public List<ResultPair> getTrans_result() {
		return trans_result;
	}
	public void setTrans_result(List<ResultPair> trans_result) {
		this.trans_result = trans_result;
	}
	
	@Override
	public String toString() {
		return "TranslateResult [from=" + from + ", to=" + to
				+ ", trans_result=" + trans_result + "]";
	}
	
}
