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
<title>MACO系统--用户管理</title>
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
			<input id="totalPage" type="hidden" value="${page.totalPage}" />
			<input id="totalCount" type="hidden" value="${page.totalCount}" />
			<!-- 数据表格 -->
			<table class="table">
				<thead>
					<tr>
						<th>序号</th>
						
						<th>姓名</th>
						<th>账号</th>
						<th>密码</th>
						
						<th>手机号码</th>
						<th>微信号</th>
						<th>电子邮箱</th>
						
						<th>是否冻结</th>
						<th>最近登录时间</th>
						<th>最近登出时间</th>
						
						<th>操作</th>
					</tr>
				</thead>
				<tbody id="tbody">
				</tbody>
			</table>
		    <!-- 分页start -->
			<div class="box" id="box"></div>
			<!-- 分页end -->
		</div>
		<!-- 弹框start -->
		<div class="layer toggle" style="display:none">
			<div class="mask"></div>
			<div class="layer-content">
				<%@ include file="/jsp/user-info.jsp" %>
			</div>			
		</div>
		<!-- 弹框end -->
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
				url : '/user/getPageList',
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

						str += '<td>'+arr[i].user_name+'</td>';
						str += '<td>'+arr[i].login_name+'</td>';
						str += '<td>'+arr[i].pass_word+'</td>';

						str += '<td>'+arr[i].mobile_phone+'</td>';
						str += '<td>'+arr[i].wechat+'</td>';
						str += '<td>'+arr[i].email+'</td>';

						if (arr[i].is_freeze == 0) {
							str += '<td>'+ '否' +'</td>';
						} else {
							str += '<td>'+ '是' +'</td>';
						}
						str += '<td>'+arr[i].login_time+'</td>';
						str += '<td>'+arr[i].logout_time+'</td>';

						str += '<td>';
						str += '<a class="btn" href="#" onclick="add()"><i class="">添加</i></a>';
						str += '<a class="btn" href="#" onclick="edit(\''+arr[i].id+'\')"><i class="">修改</i></a>';
						str += '<a class="btn" href="#" onclick="del(\''+arr[i].login_name + '\',\'' + arr[i].id+'\')"><i class="">删除</i></a>';
						str += '</td>';
						
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
        
		/////////////////////////////////////////////////////////业务处理区域/////////////////////////////////////////////////////////////////////
		
		// 自定义下拉框赋值
		$(document).on({
            mouseover: function () {               
                $(this).children('.dropstyle').show();
				$(this).find('i').addClass('down');
            },
            mouseout: function () {              
                $(this).children('.dropstyle').hide();
				$(this).find('i').removeClass('down');
			}		
	    }, '.self_drop');
		
        $(document).on('click','.self_drop li', function () {
            var $parent = $(this).parents('.self_drop');			
            //设置自定义属性存储ID值
            $parent.find('input').attr("data-id", $(this).attr('data-value'));
            $parent.find('input').val($.trim($(this).text()));
            //$parent.find('i').text($.trim($(this).text()));   //  $.replace(/\s+/g,"") 函数用于去除字符串两端的空白字符。注意： $.replace(/\s+/g,"")函数会移除字符串开始和末尾处的所有换行符，空格(包括连续的空格)和制表符。如果这些空白字符在字符串中间时，它们将被保留，不会被移除。
            $(this).parent().hide();
            return false;
        });
		
		// 条件查询
		function search() {
			// 获取查询条件 暂缺,待补充
			
			// 发送ajax请求
			$.ajax({
				url : '/user/getBy',
				type : 'post',
				data : {"id" : id},
				dataType : 'json',
				async : false,
				success : function(data) {
					var data = data.data[0];
					// 赋值
					$("#id").val(data.id);
					
					$("#user_name_id").val(data.user_name);
					$("#login_name_id").val(data.login_name);
					$("#pass_word_id").val(data.pass_word);
					
					$("#mobile_phone_id").val(data.mobile_phone);
					$("#wechat_id").val(data.wechat);
					$("#email_id").val(data.email);
					
					$("#is_freeze_id").val(data.is_freeze);
				},
				error : function(data) {
					$.myAlert("查询异常!");
				}
			})
		}
		
		// 新增
        function add() {
			// 重置form表单; 赋值弹框标题; 隐藏更新按钮; 弹出添加框;
        	$("form")[0].reset();
			$('h3').text("新增用户");
			$(".update").attr("style","display: none");
			$(".save").attr("style","display: inline-block");
        	form_toggle();
		}
		
		// 保存
        function save() {
        	// 序列化表单数据
        	var objData = $("#form_id").serializeJson();
        	// 特值单独处理
        	var is_freeze = $("#is_freeze_id").attr("data-id");
        	var role_id = $("#role_id").attr("data-id");
        	objData.is_freeze = is_freeze;
        	objData.role_id = role_id;
            // 发送ajax请求
			$.ajax({
				url : '/user/save',
				type : 'post',
				data : objData,
				dataType : 'json',
				async : false,
				success : function(data) {
					if (data.flag) {
						$.myToast("保存成功!");
					} else {
						$.myAlert("保存出错!");
					}
					form_toggle();
					window.location.reload();
				},
				error : function(data) {
					$.myAlert("保存异常!");
					form_toggle();
				}
			})
		}

		// 删除
		function del(login_name,id) {
			//if (window.confirm('确定删除 <' + login_name + '> 吗?')) {
			$.myConfirm({title:'确认框',message:'您真的确认删除吗?',callback:function(){
				// 发送ajax请求
				$.ajax({
					url : '/user/delete',
					type : 'post',
					data : {"id" : id},
					dataType : 'json',
					async : false,
					success : function(data) {
						//var jsonData = eval('(' + data + ')');
						if (data.flag) {
							$.myToast("删除成功!");
							window.location.reload();
						} else {
							$.myAlert("删除失败!");
						}
					},
					error : function(data) {
						$.myAlert("删除异常!");
					}
				});
			}})
		}

		// 修改
		function edit(id) {
			// 重置form表单; 赋值弹框标题; 隐藏更新按钮; 弹出修改框;
        	$("form")[0].reset();
			$('h3').text("修改用户");
			$(".save").attr("style","display: none");
			$(".update").attr("style","display:inline-block");
			
			// 查询当前修改用户
			// 发送ajax请求
			$.ajax({
				url : '/user/get',
				type : 'post',
				data : {"id" : id},
				dataType : 'json',
				async : false,
				success : function(data) {
					var data = data.data[0];
					// 赋值
					$("#id").val(data.id);
					
					$("#user_name_id").val(data.user_name);
					$("#login_name_id").val(data.login_name);
					$("#pass_word_id").val(data.pass_word);
					
					$("#mobile_phone_id").val(data.mobile_phone);
					$("#wechat_id").val(data.wechat);
					$("#email_id").val(data.email);
					
					if (data.is_freeze == 0) {
						var is_freeze = "否";
					} else {
						var is_freeze = "是";
					}
					
					$("#is_freeze_id").attr("data-id", data.is_freeze);
					$("#is_freeze_id").val(is_freeze);
					
					/* if (data.role_id == '5555ffff69b495f40169b7daba749999') {
						var role_id = "管理员";
					} else if (data.role_id == '402881fc69bce4880169bce532480000') {
						var role_id = "VIP";
					} else if (data.role_id == '402881fc69bce4880169bce59a560001') {
						var role_id = "普通";
					} else {
						var role_id = "未指定";
					} */
					
					// 查询角色名称
					$.ajax({
						url : '/role/get',
						type : 'post',
						data : {"id" : data.role_id},
						dataType : 'json',
						async : false,
						success : function(data) {
							var role_data = data.data[0];
							if (role_data != null) {
								$("#role_id").val(role_data.role_name);
							}
						},
						error : function(){
						}
					})
					
					$("#role_id").attr("data-id", data.role_id);
					
					// 弹框
					form_toggle();
					
				},
				error : function(data) {
					$.myAlert("修改异常!");
				}
			})
		}
		
		// 更新
		function update() {
			// 序列化表单数据
        	var objData = $("#form_id").serializeJson();
        	var is_freeze = $("#is_freeze_id").attr("data-id");
        	var role_id = $("#role_id").attr("data-id");
        	objData.is_freeze = is_freeze;
        	objData.role_id = role_id;
            // console.log(JSON.stringify(objData));
            // 发送ajax请求
			$.ajax({
				url : '/user/update',
				type : 'post',
				data : objData,
				dataType : 'json',
				async : false,
				success : function(data) {
					if (data.flag) {
						$.myToast("更新成功!");
					} else {
						$.myAlert("更新失败!");
					}
					form_toggle();
					window.location.reload();
				},
				error : function(data) {
					$.myAlert("更新异常!");
				}
			})
		}

		// 取消
		function cancel() {
			form_toggle();
		}
	</script>
</body>
</html>
