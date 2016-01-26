package wx.sunl.util;

import redis.clients.jedis.Jedis;


public class RedisUtil {
	public static void testRedis(String content) {
		/***** 填写数据库相关信息(请查找数据库详情页) *****/
		String databaseName = "GGvqlltHAnhwdhcbhSZF"; // 数据库名称
		String host = "redis.duapp.com";
		String portStr = "80";
		int port = Integer.parseInt(portStr);
		String username = "457d526cc6514149b43c733d71cd3fe7"; // 用户AK
		String password = "02e64d3d7c50430e8bbfb7a99d545e72"; // 密码SK

		/****** 接着连接并选择数据库名为databaseName的服务器 ******/
		Jedis jedis = new Jedis(host, port);
		jedis.connect();
		jedis.auth(username + "-" + password + "-" + databaseName);
		System.out.println("redis连接成功！！");

		/* 至此连接已完全建立，就可对当前数据库进行相应的操作了 */
		/* 接下来就可以使用redis数据库语句进行数据库操作,详细操作方法请参考java-redis官方文档 */
//		PrintWriter out = resp.getWriter();
		// 删除所有redis数据库中的key-value
		jedis.flushDB();
		// 简单的key-value设置
		jedis.set("name", content);
		System.out.println("name | " + jedis.get("name"));
	}
}
