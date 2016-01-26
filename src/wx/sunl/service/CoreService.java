package wx.sunl.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wx.sunl.model.baidu.UserLocation;
import wx.sunl.model.message.resp.Article;
import wx.sunl.model.message.resp.Music;
import wx.sunl.model.message.resp.MusicMessage;
import wx.sunl.model.message.resp.NewsMessage;
import wx.sunl.model.message.resp.TextMessage;
import wx.sunl.util.MessageUtil;
import wx.sunl.util.MySQLUtil;
import wx.sunl.util.RedisUtil;
import wx.sunl.util.XiaomUtil;

/** 
 * 核心服务类 
 *  
 * @author liufeng 
 * @date 2013-05-20 
 */  
public class CoreService {
	private static final Logger logger = LoggerFactory.getLogger(CoreService.class);
    /** 
     * 处理微信发来的请求 
     *  
     * @param request 
     * @return 
     */  
    public static String processRequest(HttpServletRequest request) {  
        String respMessage = null;  
        try {  
            // 默认返回的文本消息内容  
            String respContent = "请求处理异常，请稍候尝试！";  
            //返回给微信服务器的xml消息
  
            // xml请求解析  
            Map<String, String> requestMap = MessageUtil.parseXml(request);  
  
            // 发送方帐号（open_id）  
            String fromUserName = requestMap.get("FromUserName");  logger.info("FromUserName:"+fromUserName);
            // 公众帐号  
            String toUserName = requestMap.get("ToUserName");  logger.info("ToUserName:"+toUserName);
            // 消息类型  
            String msgType = requestMap.get("MsgType");  
  
            // 回复文本消息  
            TextMessage textMessage = new TextMessage();  
            textMessage.setToUserName(fromUserName);  
            textMessage.setFromUserName(toUserName);  
            textMessage.setCreateTime(new Date().getTime());  
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
            textMessage.setFuncFlag(0);  
  
            logger.info("type:" + msgType);
            logger.info("收到的map:" + requestMap);
            // 文本消息  
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                respContent = "您发送的是文本消息！";
                String content = requestMap.get("Content");
//                RedisUtil.testRedis(content);
//                logger.info(new String(("收到内容："+content).getBytes("ISO-8859-1"), "UTF-8"));
                logger.info("收到内容："+content);
                if("?".equals(content) || "？".equals(content) || "菜单".equals(content)){
                	respContent = CoreService.getMainMenu();
                }else if("1".equals(content)){
                	respContent = getWeatherUsage();
                }else if("2".equals(content)){
                	respContent = getTranslateUsage();
                }else if("3".equals(content)){
                	respContent = getMusicUsage();
                }else if("4".equals(content)){
                	respContent = getFaceUsage();
                }else if("5".equals(content)){
                	respContent = getRobotUsage();
                }else if("6".equals(content)){
                	respContent = getSearchNearUsage();
                }
                else if(XiaomUtil.isQqFace(content)){
                	//用户发什么QQ表情，就返回什么QQ表情
                	respContent = content;
                }else if("历史上的今天".equals(content)){
                	respContent = TodayInHistoryService.getTodayInHistoryInfo();
                }else if(content.startsWith("翻译")){
                	
                	String keyWord = content.replace("翻译", "").trim();
                	if("".equals(keyWord)){
                		respContent = getTranslateUsage();
                	}else{
                		respContent = BaiduTranslateService.translate(keyWord);
                	}
                }else if(content.startsWith("歌曲")){
                	// 将歌曲2个字及歌曲后面的+、空格、-等特殊符号去掉  
                	String keyWord = content.replaceAll("^歌曲[\\+ ~@#%-_=]?", "");
                	// 如果歌曲名称为空  
                	if("".equals(keyWord)){
                		respContent = getMusicUsage();
                	}else{
                		String[] kwArr = keyWord.split("@");
                		//歌曲名称
                		String musicTitle = kwArr[0];
                		//演唱者默认为空
                		String musicAuthor = "";
                		if(2 == kwArr.length){
                			musicAuthor = kwArr[1];
                		}
                		//搜索音乐
                		Music music = BaiduMusicService.searchMusic(musicTitle, musicAuthor);
                		if(null == music){
                			respContent = "对不去，没有找到您想听的歌曲<"+musicTitle+">。";
                		}else{
                			MusicMessage musicMessage = new MusicMessage();
                			musicMessage.setToUserName(fromUserName);
                			musicMessage.setFromUserName(toUserName);
                			musicMessage.setCreateTime(new Date().getTime());
                			musicMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_MUSIC);
                			musicMessage.setMusic(music);
                			respMessage = MessageUtil.musicMessageToXml(musicMessage);
                			return respMessage;
                		}
                	}
            	}else if(content.trim().equals("人脸检测")){
            		respContent = getFaceUsage();
            	}else if(content.startsWith("天气")){
            		String keyWord = content.replaceAll("^天气[\\+ ~@#%-_=]?", "");
            		if("".equals(keyWord)){
            			respContent = getWeatherUsage();
            		}else{
            			respContent = WeatherService.weatherReport(keyWord);
            		}
            	}else if(content.startsWith("附近")){
            		String keyWord = content.replaceAll("^附近[\\+ ~@#%-_=]?", "");
            		if("".equals(keyWord)){
            			respContent = getSearchNearUsage();
            		}else{
            			UserLocation location = MySQLUtil.getLastLocation(request, fromUserName);
            			if(location == null){
            				respContent = "请先发送您的位置信息后,再搜索附近信息";
            			}else{
            				List<Article> articles = BaiduMapService.searchNear(keyWord, location.getLng(), location.getLat(),getBasePath(request));
                            if(articles != null && articles.size() > 0){
                            	NewsMessage newsMessage = new NewsMessage();
                            	newsMessage.setToUserName(fromUserName);
                            	newsMessage.setFromUserName(toUserName);
                            	newsMessage.setCreateTime(new Date().getTime());
                            	newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
                            	newsMessage.setArticleCount(articles.size());
                            	newsMessage.setArticles(articles);
                            	respMessage = MessageUtil.newsMessageToXml(newsMessage);
                            	
                            	return respMessage;
                            }else{
                            	respContent = "没有找到附近的【"+keyWord+"】~~";
                            }
            			}
            		}
            		
            	}else{
            		respContent = TulingService.tulingRobot(content);
            	}
            }  
            // 图片消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
            	//获取图片地址
            	String picUrl = requestMap.get("PicUrl");
                respContent = FaceService.detect(picUrl); 
            }  
            // 地理位置消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {  
                respContent = "您发送的是地理位置消息！"; 
                String lng = requestMap.get("Location_Y");
                String lat = requestMap.get("Location_X");
                UserLocation location = BaiduMapService.convertCoord(lng, lat);
                //保存用户地理位置到数据库
                MySQLUtil.saveUserLocation(request, fromUserName, lng, lat,
                		location.getBd09Lng(), location.getBd09Lat());
                List<Article> articles = BaiduMapService.searchNear("美食", lng, lat, getBasePath(request));
                logger.info("Location_X:"+lat);
                logger.info("Location_Y:"+lng);
                
                if(articles != null && articles.size() > 0){
                	NewsMessage newsMessage = new NewsMessage();
                	newsMessage.setToUserName(fromUserName);
                	newsMessage.setFromUserName(toUserName);
                	newsMessage.setCreateTime(new Date().getTime());
                	newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
                	newsMessage.setArticleCount(articles.size());
                	newsMessage.setArticles(articles);
                	respMessage = MessageUtil.newsMessageToXml(newsMessage);
                	
                	return respMessage;
                }else{
                	respContent = "没有找到附近的【美食】~~";
                }
                
            } 
            // 链接消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {  
                respContent = "您发送的是链接消息！";  
            }  
            // 音频消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {  
                respContent = "您发送的是音频消息！";  
            }  
            // 事件推送  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {  
                // 事件类型  
                String eventType = requestMap.get("Event");  
                // 订阅  
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {  
                    respContent = "谢谢您的关注！\nxiaozhushou,目前正在开发测试阶段,欢迎提出宝贵意见，欢迎吐槽\n\n";  
                    respContent += getMainMenu();
                }  
                // 取消订阅  
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {  
                    // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息  
                }  
                // 自定义菜单点击事件  
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {  
                    // TODO 自定义菜单权没有开放，暂不处理该类消息  
                }  
            }  
            //为搜索到音乐返回指南
            if(null == respMessage){
            	if(null == respContent){
            		respContent = getMusicUsage();
            	}
            	textMessage.setContent(respContent);  
                respMessage = MessageUtil.textMessageToXml(textMessage);
            }
              
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        logger.info("result:"+respMessage);
        return respMessage;  
    }  
    
    /**
     * 
    * xiaozhushou的主菜单
    * @return
     */
    public static String getMainMenu(){
    	StringBuffer buffer = new StringBuffer();
    	buffer.append("您好，我是小m，请回复数字选择服务").append("\n\n");
    	buffer.append("1 天气预报").append("\n");
    	buffer.append("2 词语翻译").append("\n");
    	buffer.append("3 歌曲点播").append("\n");
    	buffer.append("4 人脸检测").append("\n");
    	buffer.append("5 聊天唠嗑").append("\n");
    	buffer.append("6 周边搜索").append("\n\n");
    	buffer.append("回复“?”显示此帮助菜单");
    	return buffer.toString();
    }
    
    /** 
     * Q译通使用指南 
     *  
     * @return 
     */  
    public static String getTranslateUsage() {  
        StringBuffer buffer = new StringBuffer();  
        buffer.append(XiaomUtil.emoji(0xe148)).append("Q译通使用指南").append("\n\n");  
        buffer.append("Q译通为用户提供专业的多语言翻译服务，目前支持以下翻译方向：").append("\n");  
        buffer.append("    中 -> 英").append("\n");  
        buffer.append("    英 -> 中").append("\n");  
        buffer.append("    日 -> 中").append("\n\n");  
        buffer.append("使用示例：").append("\n");  
        buffer.append("    翻译我是中国人").append("\n");  
        buffer.append("    翻译dream").append("\n");  
        buffer.append("    翻译さようなら").append("\n\n");  
        buffer.append("回复“?”显示主菜单");  
        return buffer.toString();  
    }  
    
    /** 
     * 歌曲点播使用指南 
     *  
     * @return 
     */  
    public static String getMusicUsage() {  
        StringBuffer buffer = new StringBuffer();  
        buffer.append("歌曲点播操作指南").append("\n\n");  
        buffer.append("回复：歌曲+歌名").append("\n");  
        buffer.append("例如：歌曲存在").append("\n");  
        buffer.append("或者：歌曲存在@汪峰").append("\n\n");  
        buffer.append("回复“?”显示主菜单");  
        return buffer.toString();  
    }  
    
    /** 
     * 人脸检测帮助菜单 
     */  
    public static String getFaceUsage() {  
        StringBuffer buffer = new StringBuffer();  
        buffer.append("人脸检测使用指南").append("\n\n");  
        buffer.append("发送一张清晰的照片，就能帮你分析出种族、年龄、性别等信息").append("\n");  
        buffer.append("快来试试你是不是长得太着急");  
        return buffer.toString();  
    }  
    
    /**
     * 
    * 天气预报帮助菜单
    * @return
     */
    public static String getWeatherUsage(){
    	StringBuffer buffer = new StringBuffer();
    	buffer.append("天气预报使用指南").append("\n\n");
    	buffer.append("回复：天气+城市名称").append("\n");
    	buffer.append("例如：天气拉萨").append("\n");
    	buffer.append("或者：天气lasa").append("\n\n");
    	buffer.append("回复“?”显示主菜单");  
    	return buffer.toString();
    }
    
    /**
     * 
    * 聊天唠嗑帮助菜单
    * @return
     */
    public static String getRobotUsage(){
    	StringBuffer buffer = new StringBuffer();
    	buffer.append("聊天唠嗑使用说明：\n\n");
    	buffer.append("闲暇无聊,来找小m唠嗑吧，小m很能聊的,有问必答！").append("\n");
    	buffer.append("例如：讲个笑话").append("\n");
    	buffer.append("或者：西藏有什么好玩的").append("\n\n");
    	buffer.append("回复“?”显示主菜单");
    	return buffer.toString();
    }
    
    public static String getSearchNearUsage(){
    	StringBuffer buffer = new StringBuffer();
    	buffer.append("周边搜索使用说明\n\n");
    	buffer.append("1) 发送地理位置\n");
    	buffer.append("点击窗口底部的“+”按钮，选择“位置”，点发送\n");
    	buffer.append("默认搜索周边“美食”\n");
    	buffer.append("2) 指定关键词搜索格式：附近+关键词\n");
    	buffer.append("例如：附近ATM、附近KTV、附近厕所\n");
    	buffer.append("指定关键词为搜索上次发送位置附近的关键词,如需更改位置请先发送位置信息再搜索【附近+管检测】\n\n");
    	buffer.append("回复“？”显示主菜单");
    	return buffer.toString();
    }
    
    public static void main(String[] args) throws UnsupportedEncodingException {
//		String x = CoreService.getMainMenu();
		String content ="Nihao";
		logger.info(new String(("收到内容："+content).getBytes("ISO-8859-1"), "UTF-8"));
//		logger.info(x);
	}
    
    /**
     * 获取项目完整地址
     * @param request
     * @return
     */
    public static String getBasePath(HttpServletRequest request){
    	if(request != null){
    		String path = request.getContextPath();
    		String basePath = "";
    		if(request.getScheme().equals("https")){ // https的直接使用域名 不适用端口(443端口会出问题)
    			basePath = request.getScheme() + "://"+ request.getServerName() + path + "/";
    		}
    		basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
    		logger.info("项目地址:【"+basePath+"】");
    		return basePath;
    	}
    	logger.info("缺少request");
    	return null;
    }
    
}  
