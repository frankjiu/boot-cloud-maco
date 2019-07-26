<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
<title>MACO系统--主页</title>
<link href ="/img/m_icon.png" rel="shortcut icon">
<link rel="stylesheet" href="/css/reset.css" type="text/css">
<link rel="stylesheet" href="/ztree/resources/icomoon_styles.css" type="text/css">
<link rel="stylesheet" href="/ztree/resources/metroStyle.css" type="text/css">
<link rel="stylesheet" href="/css/maco.css" type="text/css">
<link type="text/css" rel="Stylesheet" href="/front-photo-effect/css/imageflow.css" />

<!-- 弹框css/js -->
<link rel="stylesheet" href="/alerts/myAlert.min.css" />

<script type="text/javascript" src="/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/js/maco.js"></script>
<script type="text/javascript" src="/ztree/resources/jquery.ztree.core.js"></script>
<script type="text/javascript" src="/ztree/resources/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="/ztree/resources/jquery.ztree.exedit.js"></script>

<!-- 弹框css/js -->
<script type="text/javascript" src="/alerts/myAlert.min.js" ></script>

<!-- 轮播图css/js -->
<script type="text/javascript" src="/front-photo-effect/js/jquery.js"></script>
<script type="text/javascript" src="/front-photo-effect/js/imageflow.js"></script>

</head>

<body>
	<div class="wrap_container">
		<!-- 页面头部  -->
		<%@ include file="/jsp/top.jsp" %>
		
		<!-- 页面头部下面内容区域 -->
		<div class="wrap_content">
			<!-- 左侧主页 -->
			<div class="left_sider">
				<h3 class="page_title">MACO系统主页</h3>
				<div><a href="/toTest">测试项目登录</a></div>
				<div class="menu">
					<div id="ztree_root_Id" class="ztree"></div>
				</div>
			</div>	
			<!-- 右侧内容 -->
			<div class="right-sider">
				<div class="forms-content">
					
					<!--效果html开始-->
					<div id="LoopDiv">
						<input id="S_Num" type="hidden" value="8" />
						<div id="starsIF" class="imageflow">
							<img src="/front-photo-effect/images/1.jpg" onclick="turnLarge(this)" alt="" />
							<img src="/front-photo-effect/images/2.jpg" onclick="turnLarge(this)" alt="" />
							<img src="/front-photo-effect/images/1.jpg" onclick="turnLarge(this)" alt="" />
							<img src="/front-photo-effect/images/3.jpg" onclick="turnLarge(this)" alt="" />
							<img src="/front-photo-effect/images/4.jpg" onclick="turnLarge(this)" alt="" />
							<img src="/front-photo-effect/images/5.jpg" onclick="turnLarge(this)" alt="" />
							<img src="/front-photo-effect/images/6.jpg" onclick="turnLarge(this)" alt="" />
							<img src="/front-photo-effect/images/1.jpg" onclick="turnLarge(this)" alt="" />
							<img src="/front-photo-effect/images/2.jpg" onclick="turnLarge(this)" alt="" />
							<img src="/front-photo-effect/images/3.jpg" onclick="turnLarge(this)" alt="" />
							<img src="/front-photo-effect/images/4.jpg" onclick="turnLarge(this)" alt="" />
							<img src="/front-photo-effect/images/5.jpg" onclick="turnLarge(this)" alt="" />
						</div>
					</div>
					<!--效果html结束-->
					<div id="large_image"></div>
					<div class="clear"></div>
					
 				</div>
			</div>	
		</div>
	</div>
	
	<script type="text/javascript">
		// 图片放大
		/* function turnLarge($this) {
			var large_image = '<img src= ' + $(this).attr("src") + '></img>';
			$('#large_image').html($(large_image).animate({
				height : '50%',
				width : '50%'
			}, 500));
		}; */

		// 显示时钟
		/* function getTime(){
			document.getElementById("timeId").innerHTML = new Date().toLocaleString();
			window.setInterval("getTime();",1000); //每隔1s执行函数调用
		};
		window.onload = getTime(); */
		
		// 递归函数
		function append_content(pArray) {
			// 遍历父节点
			for (var x = 0; x < pArray.length; x++) {
				// 遍历子集  插入子节点,同时添加url,name值
				if (pArray[x].children instanceof Array) {
					var cArray = pArray[x].children;
					for (var j = 0; j < cArray.length; j++) {
						//$("#" + pArray[x].id).append(" <div><a href='" + cArray[j].menu_url + "' data-type='second'>" + cArray[j].menu_name + "</a></div> ");
						$("#" + pArray[x].id).append(
								" <div><a href='" + cArray[j].menu_url + "' data-type='second'>" + cArray[j].menu_name + "</a><div id='" + cArray[j].id + "' class='' > ");
					}
					// 重置变量				
					pArray = cArray;
					// 递归调用
					append_content(pArray);
				}
			}

		}

		//根节点主键
		//var rootId = "7777ffff69b495f40169b7daba748888";
		$(function() {
			$.ajax({
				url : '/menu/getRecurse',////////////////////////////////////////////////////////////////////////
				type : 'get',
				//data : maco_root_menu,
				dataType : 'json',
				async : false,
				success : function(data) {
					if (data.flag) {
						$.myToast("目录获取成功!");
					} else {
						$.myAlert("目录获取失败!");
					}

					var pArray = data.data;
					if (pArray instanceof Array) {
						for (var i = 0; i < pArray.length; i++) {
							// 遍历父集 插入父节点,同时添加id和name值
							$("#ztree_root_Id").append(
									" <div class=''><a href='" + pArray[i].menu_url + "' data-type='first'>" + pArray[i].menu_name
											+ "</a><div id='" + pArray[i].id + "' class='' > ");
						}
						// 调用函数递归插入子结构
						append_content(pArray);
						$("#ztree_root_Id").append(" </div></div> ");
					}
				},
				error : function() {
					$.myAlert("目录获取异常!");
				}
			})
		})

		var $j = jQuery.noConflict();
		$j(document).ready(function() {
			//获取当前访问的浏览界面路径
			var window_url = window.location.pathname;
			var aPaths = $j("#ztree_root_Id").find("a");
			for (var i = 0; i < aPaths.length; i++) {
				//若包含则正在访问该路径
				if ($j(aPaths[i]).attr("href").indexOf(window_url) >= 0) {
					//为子节点则高亮并收缩父,展开定位子节点
					if ($j(aPaths[i]).attr("data-type") == "second") {
						$j(aPaths[i]).parents("li").addClass("layui-nav-itemed");
						$j(aPaths[i]).parent("dd").addClass("layui-this");
						//为父节点则高亮并收缩父
					} else if ($j(aPaths[i]).attr("data-type") == "first") {
						$j(aPaths[i]).parents("li").addClass("layui-nav-itemed");
					}
				}
			}
		});
	</script>
	
</body>
</html>
