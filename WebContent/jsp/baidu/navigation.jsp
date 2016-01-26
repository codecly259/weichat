<%@ page language="java" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<title>步行导航</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0,user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
body, html, #allmap {
	width: 100%;
	height: 100%;
	overflow: hidden;
	margin: 0;
}
</style>
<script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=2mye0ZM8302vZRLsTVH9d2C8"></script>
</head>
<%
	String p1 = request.getParameter("p1");
	String p2 = request.getParameter("p2");
%>
<body>
	<div id="allmap"></div>
</body>
<script type="text/javascript">
	// 创建起点、终点的经纬度坐标点
	var p1 = new BMap.Point(<%=p1%>);
	var p2 = new BMap.Point(<%=p2%>);
	// 创建地图、设置中心坐标和默认缩放级别
	console.info(p1);
	var map = new BMap.Map("allmap");
	map.centerAndZoom(new BMap.Point((p1.lng + p2.lng) / 2,
			(p1.lat + p2.lat) / 2), 17);
	// 右下角添加缩放按钮
	map.addControl(new BMap.NavigationControl({
		anchor : BMAP_ANCHOR_BOTTOM_RIGHT,
		type : BMAP_NAVIGATION_CONTROL_ZOOM
	}));
	// 步行导航检索
	var walking = new BMap.WalkingRoute(map, {
		renderOptions : {
			map : map,
			autoViewport : true
		}
	});
	walking.search(p1, p2);
</script>
</html>