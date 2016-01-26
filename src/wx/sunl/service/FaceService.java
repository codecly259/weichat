package wx.sunl.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import wx.sunl.model.facepp.Face;
import wx.sunl.util.HttpRequestUtil;

/**
 * 
* 人脸检测服务
* @since weichat 1.0
* @version 1.0 2015年3月26日
* @author maxinchun
 */
public class FaceService {
	
	/**
	 * 
	* 调用Face++ API实现人脸检测
	* @param picUrl 待检测图片的访问地址
	* @return List<Face> 人脸列表
	 */
	private static List<Face> faceDetect(String picUrl){
		List<Face> faceList = new ArrayList<Face>();
		try {
			//拼接Face++人脸检测的请求地址
			String queryUrl = "http://apicn.faceplusplus.com/v2/detection/detect?url=URL&api_secret=API_SECRET&api_key=API_KEY";
			queryUrl = queryUrl.replace("URL", java.net.URLEncoder.encode(picUrl, "utf-8"));
			queryUrl = queryUrl.replace("API_SECRET", "-TO9EfSn8OE2WXhZ_dp13XIxXhiMl07t");
			queryUrl = queryUrl.replace("API_KEY", "517dc919776c893d9f7ec19eb1b7643c");
			
			//发送http请求调用人脸检测的接口
			String json = HttpRequestUtil.httpRequset(queryUrl);
			//解析返回json中的Face列表
			JSONArray jsonArray = JSONObject.fromObject(json).getJSONArray("face");
			//遍历检测到的人脸
			for(int i = 0; i < jsonArray.size(); i++){
				//face
				JSONObject faceObject = (JSONObject)jsonArray.get(i);
				//attribute
				JSONObject attrObject = (JSONObject)faceObject.get("attribute");
				//position
				JSONObject posObject = (JSONObject)faceObject.get("position");
				Face face = new Face();
				face.setFaceId(faceObject.getString("face_id"));
				face.setAgeValue(attrObject.getJSONObject("age").getInt("value"));
				face.setAgeRange(attrObject.getJSONObject("age").getInt("range"));
				face.setGenderValue(genderConvert(attrObject.getJSONObject("gender").getString("value")));
				face.setGenderConfidence(attrObject.getJSONObject("gender").getDouble("confidence"));
				face.setRaceValue(raceConvert(attrObject.getJSONObject("race").getString("value")));
				face.setRaceConfidence(attrObject.getJSONObject("race").getDouble("confidence"));
				face.setSimilingValue(attrObject.getJSONObject("smiling").getDouble("value"));
				face.setCenterX(posObject.getJSONObject("center").getDouble("x"));
				face.setCenterY(posObject.getJSONObject("center").getDouble("y"));
				faceList.add(face);
			}
			//将检测出的Face按从左至右的顺序排序
			Collections.sort(faceList);
			
		} catch (Exception e) {
			faceList = null;
			e.printStackTrace();
		}
		return faceList;
	}
	
	
    /** 
     * 性别转换（英文->中文） 
     *  
     * @param gender 
     * @return 
     */  
    private static String genderConvert(String gender) {  
        String result = "男性";  
        if ("Male".equals(gender))  
            result = "男性";  
        else if ("Female".equals(gender))  
            result = "女性";  
  
        return result;  
    }  
	
    /** 
     * 人种转换（英文->中文） 
     *  
     * @param race 
     * @return 
     */  
    private static String raceConvert(String race) {  
        String result = "黄色";  
        if ("Asian".equals(race))  
            result = "黄色";  
        else if ("White".equals(race))  
            result = "白色";  
        else if ("Black".equals(race))  
            result = "黑色";  
        return result;  
    }  
    
    /**
     * 
    * 根据人脸识别结果组装消息
    * @param faceList 人脸列表
    * @return
     */
    private static String makeMessage(List<Face> faceList){
    	StringBuffer buffer = new StringBuffer();
    	//检测到1张脸
    	if(1 == faceList.size()){
    		buffer.append("共检测到 ").append(faceList.size()).append(" 张人脸").append("\n");
    		for(Face face : faceList){
    			buffer.append(face.getRaceValue()).append("人种");
    			buffer.append(face.getGenderValue()).append("，");
    			buffer.append(face.getAgeValue()).append("岁左右").append("，");
    			buffer.append("微笑程度").append((int)(face.getSimilingValue())).append("%\n");
    		}
    	}
    	//检测到2到10张人脸
    	else if(faceList.size() > 1 && faceList.size() <= 10){
    		buffer.append("共检测到 ").append(faceList.size()).append(" 张人脸，按脸部中心位置从左至右依次为：").append("\n");
    		for(Face face : faceList){
    			buffer.append(face.getRaceValue()).append("人种");
    			buffer.append(face.getGenderValue()).append("，");
    			buffer.append(face.getAgeValue()).append("岁左右").append("，");
    			buffer.append("微笑程度").append((int)(face.getSimilingValue())).append("%\n");
    		}
    	}
    	//检测到10张以上人脸
    	else if(faceList.size() > 10){
    		buffer.append("共检测到 ").append(faceList.size()).append(" 张人脸").append("\n");
    		//统计各人种、性别的人数
    		int asiaMale = 0;
    		int asiaFemale = 0;
    		int whiteMale = 0;
    		int whiteFemale = 0;
    		int blackMale = 0;
    		int blackFemale = 0;
    		for(Face face : faceList){
    			if("黄色".equals(face.getRaceValue())){
    				if("男性".equals(face.getGenderValue())){
    					asiaMale++;
    				}else{
    					asiaFemale++;
    				}
    			}else if("白色".equals(face.getRaceValue())){
    				if("男性".equals(face.getGenderValue())){
    					whiteMale++;
    				}else{
    					whiteFemale++;
    				}
    			}else if("黑色".equals(face.getRaceValue())){
    				if("男性".equals(face.getGenderValue())){
    					blackMale++;
    				}else{
    					blackFemale++;
    				}
    			}
    		}
    		
    		if(0 != asiaMale || 0 != asiaFemale){
    			buffer.append("黄色人种：").append(asiaMale).append("男").append(asiaFemale).append("女").append("\n");
    		}
    		if(0 != whiteMale || 0 != whiteFemale){
    			buffer.append("白色人种：").append(whiteMale).append("男").append(whiteFemale).append("女").append("\n");
    		}
    		if(0 != blackMale || 0 != blackFemale){
    			buffer.append("白色人种：").append(blackMale).append("男").append(blackFemale).append("女").append("\n");
    		}
    	}
    	//移除末尾换行符
    	buffer = new StringBuffer(buffer.substring(0, buffer.lastIndexOf("\n")));
    	return buffer.toString();
    }
    
    /**
     * 
    * 提供给外部使用的人脸检测方法
    * @param picUrl 待检测图片的访问地址
    * @return
     */
    public static String detect(String picUrl){
    	String result = "未检测到人脸，请换一张清晰的照片再试！";
    	List<Face> faceList = faceDetect(picUrl);
    	if(null != faceList && faceList.size() > 0){
    		result = makeMessage(faceList);
    	}
    	return result;
    }
    
	public static void main(String[] args) throws UnsupportedEncodingException {
		//拼接Face++人脸检测的请求地址
//		String picUrl = "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3331684307,3306922515&fm=58";
		String picUrl = "http://b146.photo.store.qq.com/psb?/V13zf7Vw2AlyJ0/ZNSCm*YHHxlUS5lAN*KhSOFzN*C8JmszRSYAf0SLPVA!/b/dCqXE1dXDwAA&bo=VQOAAgAAAAABB*Q!&rf=viewer_4";
		/*queryUrl = queryUrl.replace("URL", java.net.URLEncoder.encode("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3331684307,3306922515&fm=58", "utf-8"));
		queryUrl = queryUrl.replace("API_SECRET", "-TO9EfSn8OE2WXhZ_dp13XIxXhiMl07t");
		queryUrl = queryUrl.replace("API_KEY", "517dc919776c893d9f7ec19eb1b7643c");
		
		//发送http请求调用人脸检测的接口
		String json = HttpRequestUtil.httpRequset(queryUrl);*/
//		System.out.println(json);
		List<Face> faceList = faceDetect(picUrl);
		String message = makeMessage(faceList);
		System.out.println(message);
		System.out.println(faceList.toString());
	}
} 
