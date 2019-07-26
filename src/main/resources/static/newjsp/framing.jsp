<%@ include file="/WEB-INF/inc/common.jsp"%>
<%@ taglib prefix="acth" uri="/WEB-INF/tld/permission-tags.tld"%>
<%@ page language="java" import="com.kl.coll.common.Constants" %>
<%@ page language="java" import="net.sf.json.JSONArray" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>MACO系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="edge" />
<link rel="stylesheet" href="${ctx}/css/reset.css" type="text/css">
<link rel="stylesheet" href="${ctx}/css/public.css" type="text/css">
<!-- 定位 -->
<style type="text/css">body, html {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}</style>
<!-- jQuery -->
<!-- 定位 -->
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=YGdiUGzB5A1RsDoDL947UtQBZBQaWz3G"></script>
<script type="text/javascript" src="${ctx}/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${ctx}/js/public.js"></script>
<script type="text/javascript" src="${ctx}/js/utils.js"></script>

<!--[if lt IE 9]>
<script type="text/javascript" src="${ctx}/js/Global.js"></script>
<![endif]-->

<script type="text/javascript">
	var levelData = <%=JSONArray.fromObject(session.getAttribute(Constants.SESSION_CASCADE_CRITERIA))%>
	var levelType = "${sessionScope[Constants.SESSION_POLICE_LEVEL_TYPE]}";
</script>

<script type="text/javascript">

$(function(){

		var ctx = "${ctx}";
	    window.setInterval(function() {
	    	 var iframe = document.getElementById("mainframe");  
		        try{  
		            var bHeight = iframe.contentWindow.document.body.scrollHeight;  
		            var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;  
		            
		            var height = Math.max(bHeight, dHeight);
		            var height = Math.max(height, 1000);
		            
		            iframe.height =  bHeight;
		            
		        }catch (ex){}  
		}, 1000);  
	
	    var menuMark = window.location.getParameter("menuMark");
	    if(!menuMark){
	    	menuMark = "rygl";
	    }
		$("#high_light_Id .nav-item .subnavbar li a,#gwxt").each(function(i) {
			$(this).click(function() {
				var $this = $(this);
				$("#navigation").text($this.text());
				var href = ctx + "/"+$this.attr("href");
				$(".subactive").removeClass("subactive");
				$this.addClass("subactive");
				$("#mainframe").attr("src", href);
				$("#mainframe")[0].height=0;
				return false;
			});
		});
		$("#" + menuMark + ",#" + menuMark + " .subnavbar li:eq(0) a").click();
		
		
		var ifra = document.getElementsByTagName("IFRAME");
		for (var i = 0, l = ifra.length; i < l; i++) {
			ifra[i].setAttribute("allowTransparency", "true");
		}

	});
</script>
<style type="text/css">
#mainframe {
	min-height: 1000px;
}
</style>

</head>
<body>
	<%@ include file="/jsp/include/top.jsp"%>
	<div class="container" id="container">
		<div class="navbar-down" >
			<%@ include file="/jsp/include/left.jsp"%>
			<div class="right_content">
				<div class="title-content">
					<h1 class="admin_header">
						<span id="navigation"></span>
					</h1>
					<div class="location">
						<p class="time"></p>
						<h6 id="located" class="adress">
						</h6>
					</div>
				</div>
				<iframe id="mainframe" frameborder="0"  name="mainframe" width="100%" scrolling="no"></iframe>
			</div>
		</div>
	</div>

<script type="text/javascript">
	var geolocation = new BMap.Geolocation();
	var myGeo = new BMap.Geocoder();
	geolocation.getCurrentPosition(function(r) {
		if (this.getStatus() == BMAP_STATUS_SUCCESS) {
			var pt = r.point;
			// 根据坐标得到地址描述  
			myGeo.getLocation(pt, function(result) {
				if (result) {
					var addComp = result.addressComponents;
					$("#located").html("<i></i>" + addComp.province + "：[<span>" + addComp.city + "</span>]");
				}else{
					$("#located").html("定位失败");
				}
			});
		}
	});
</script>
</body>
</html>