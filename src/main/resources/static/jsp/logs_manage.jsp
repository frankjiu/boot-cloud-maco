<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>
<c:set var="maco" value="${pageContext.request.contextPath}"></c:set>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
<title>MACO系统--日志管理</title>
<link href ="/img/m_icon.png" rel="shortcut icon">
<link rel="stylesheet" href="/css/reset.css" type="text/css">
<link rel="stylesheet" href="/ztree/resources/icomoon_styles.css" type="text/css">
<link rel="stylesheet" href="/ztree/resources/metroStyle.css" type="text/css">
<link rel="stylesheet" href="/css/maco.css" type="text/css">
<link rel="stylesheet" href="/css/maco-add.css" type="text/css">
<link rel="stylesheet" type="text/css" href="/pagination-js-1.1.0/css/pagination.css"/>
<script type="text/javascript" src="/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/js/maco.js"></script>
<script type="text/javascript" src="/ztree/resources/jquery.ztree.core.js"></script>
<script type="text/javascript" src="/ztree/resources/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="/ztree/resources/jquery.ztree.exedit.js"></script>
<script type="text/javascript" src="/pagination-js-1.1.0/js/pagination.js"></script>
<!-- 弹框css/js -->
<link rel="stylesheet" href="/alerts/myAlert.min.css" />
<script type="text/javascript" src="/alerts/myAlert.min.js" ></script>
</head>

<body>
	<div class="wrap_container">
		<!-- 页面头部  -->
		<%@ include file="/jsp/top.jsp" %>
		
		<!-- 页面头部下面内容区域 -->
		<div class="table-content">
			<input id="totalPage" type="hidden" value="${page_logs.totalPage}" />
			<input id="totalCount" type="hidden" value="${page_logs.totalCount}" />
			<!-- 数据表格 -->
			<table class="table">
				<thead>
					<tr>
						<th>序号</th>
						
						<th>操作用户</th>
						<th>用户IP地址</th>
						<th>操作模块</th>
						
						<th>操作描述</th>
						<th>操作时间</th>
					</tr>
				</thead>
				<tbody id="tbody">
				</tbody>
			</table>
		    <!-- 分页start -->
			<div class="box" id="box"></div>
			<!-- 分页end -->
		</div>
	</div>
	
	<script type="text/javascript">
		// 分页统计总数
		var click_page = 1;
		var totalPage = $("#totalPage").val();
		var totalCount = $("#totalCount").val();
	
		//添加表格序号
		function initIndex() {
	        var len = $('table tr').length;
	        for(var i = 1;i<len;i++){
	            $('table tr:eq('+i+') td:first').text(i);
	        }
		};
		
		//进后台查询分页数据
		function loading(currentPage) {
			$.ajax({
				url : '/logs/getPageList',
				type : 'post',
				data : {"currentPage" : currentPage},
				//dataType : 'json',
				async : true,
				success : function(data) {
					var jsonData = eval('(' + data + ')');
					var arrObj = jsonData.data;
					var arr = arrObj.pageList;
					var str = "";
					$("#tbody").html(str);
					
					for (var i = 0; i < arr.length; i++) {
						str += '<tr>';
						str += '<td></td>';

						str += '<td>'+arr[i].user_id+'</td>';
						str += '<td>'+arr[i].ip_address+'</td>';
						str += '<td>'+arr[i].op_module+'</td>';

						str += '<td>'+arr[i].introduce+'</td>';
						str += '<td>'+arr[i].create_time+'</td>';
						
						str += '</tr>';
					}
					
					$("#tbody").html(str);
					initIndex();
					
				},
				error : function() {
					$.myAlert("请求失败!");
				}
			});
		}
		
		//分页处理
        $('#box').paging({
            initPageNo: 1, // 初始点击页码
            totalPages: totalPage, //总页数
            totalCount: '合计 [' + totalCount + '] 条数据', // 条目总数
            slideSpeed: 600, // 缓动速度。单位毫秒
            jump: true, //是否支持跳转
            callback: function(page) { // 回调函数
                loading(page);
            }
        })
		
	</script>
</body>
</html>
