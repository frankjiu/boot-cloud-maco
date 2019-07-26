<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
<title>MACO系统--角色管理</title>
<link href ="/img/m_icon.png" rel="shortcut icon">
<link rel="stylesheet" href="/css/reset.css" type="text/css">
<link rel="stylesheet" href="/ztree/resources/icomoon_styles.css" type="text/css">
<link rel="stylesheet" href="/ztree/resources/metroStyle.css" type="text/css">
<link rel="stylesheet" href="/css/maco.css" type="text/css">
<link rel="stylesheet" href="/alerts/myAlert.min.css" />

<script type="text/javascript" src="/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/js/maco.js"></script>
<script type="text/javascript" src="/ztree/resources/jquery.ztree.core.js"></script>
<script type="text/javascript" src="/ztree/resources/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="/ztree/resources/jquery.ztree.exedit.js"></script>

</head>

<body>
	<div class="wrap_container">
		<!-- 页面头部  -->
		<%@ include file="/jsp/top.jsp" %>
		
		<!-- 页面头部下面内容区域 -->
		<div class="wrap_content">
			<!-- 左侧角色 -->
			<div class="left_sider">
				<h3 class="page_title">角色管理</h3>
				<div class="maco">
					<div id="ztree_root_Id" class="ztree"></div>
				</div>
			</div>	
			<!-- 右侧内容 -->
			<div class="right-sider">
				<div class="forms-content">
					<h3 class="form-title">角色详情编辑</h3>
					<form id="form_id" class="forms" action="" method="post">
						<input id="id" name="id" type="hidden" />
						<div class="input-group">
							<label for="role_name">角色名称:</label>
							<input type="text" id="role_name_id" name="role_name" autocomplete="off" placeholder="请输入角色名称">
						</div>
						<div class="input-group">
							<label for="pid">父角色:</label>						
							<div id="parent_rule" class="self_drop">
								<input type="text" id="pid_id" name="pid" autocomplete="off" placeholder="请选择父角色" readonly="readonly" disabled="true">
								<i></i>
								<ul class="dropstyle">									
								</ul>
							</div>
						</div>
						<div class="input-group">
							<label for="classes">角色级别:</label>
							<div class="self_drop">
								<input type="text" id="classes_id" name="classes" autocomplete="off" placeholder="请选择角色级别" readonly="readonly">
								<i></i>
								<ul class="dropstyle">
									<li data-value="0">管理员</li>
									<li data-value="1">VIP</li>
									<li data-value="2">普通</li>
								</ul>
							</div>
						</div>
						<div class="input-group">
							<label for="is_stop">是否停用:</label>						
							<div class="self_drop">
								<input type="text" id="is_stop_id" name="is_stop" autocomplete="off" placeholder="请选择是否停用" readonly="readonly">
								<i></i>
								<ul class="dropstyle">
									<li data-value="0">否</li>
									<li data-value="1">是</li>
								</ul>
							</div>
						</div>
						
						<div class="btns-group">
							<button type="button" class="update" onclick="update()">更新</button>
							<button type="button" class="cancle" onclick="refresh()">取消</button>
						</div>
					</form>
 				</div>
			</div>	
		</div>
	</div>

	<!-- 弹框css/js -->
	<script type="text/javascript" src="/alerts/myAlert.min.js" ></script>
	<script type="text/javascript">
		var allNodes;//所有节点数据
		var treeObj;//所有节点对象
		//tree设置
		var setting = {
			view : {
				addHoverDom: addHoverDom, //当鼠标移动到节点上时，显示角色自定义控件
				removeHoverDom: removeHoverDom, //离开节点时的操作
				selectedMulti: false,
				showLine: false,
				fontCss: getFont
			},
			edit: {
				enable: true //单独设置为true时，可加载修改、删除图标
			},
		 	check: {
                enable: false
            },
			data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "pid",
					rootPId : '0'
				},
				key: {
		            url:"nourl",
		            name : "name"
		        }
			},
			callback : {
				onClick : zTreeOnClick, //单击事件
				beforeRemove: beforeRemove, //子父删除判定
				onRemove: onRemove, //移除事件
				onRename: onRename //修改事件
			}
		};
		
		//初始化tree并展开第一级函数
		function init(){
			$.post("/role/findTree", {}, function(js) {
				treeObj = $.fn.zTree.init($("#ztree_root_Id"), setting, js.data, false);
				//初始化父级角色下拉框: 获取所有角色节点id与name,添加到下拉框
				allNodes = js.data;
		        if (allNodes instanceof Array) {
					for ( var i in allNodes) {
						// $("#pid_id").append(" <option value='" + allNodes[i].id + "' selected='' >" + allNodes[i].name + "</option> ");
						$('.dropstyle').eq(0).append("<li data-value='" + allNodes[i].id + "'>" + allNodes[i].name + "</li>");						
					}
				}else {
					// $("#pid_id").append(" <option value='9999' selected='' >无数据</option> ");
					$('.dropstyle').append("<li data-value='9999'>无数据</li>");
				}
				var nodes = treeObj.getNodes();
				if (nodes.length>0) {
				    for(var i=0;i<nodes.length;i++){
				    	treeObj.expandNode(nodes[i], true, false, false);
				    }
				}
				
				//默认选中第一个节点
				var snode = nodes[0];
				treeObj.selectNode(snode);
				setting.callback.onClick(null, treeObj.setting.treeId, snode);
				
			},'json');
		}
		
		//调用
		$(function() {
			init();
		});	
		
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
            //$parent.find('input').val($(this).attr('data-value'));
            //设置自定义属性存储ID值
            $parent.find('input').attr("data-id", $(this).attr('data-value'));
            $parent.find('input').val($.trim($(this).text()));
            //$parent.find('i').text($.trim($(this).text()));   //  $.replace(/\s+/g,"") 函数用于去除字符串两端的空白字符。注意： $.replace(/\s+/g,"")函数会移除字符串开始和末尾处的所有换行符，空格(包括连续的空格)和制表符。如果这些空白字符在字符串中间时，它们将被保留，不会被移除。
            $(this).parent().hide();
            return false;
        });
		
		//刷新当前节点内置函数
		function refreshNode(data) {
			var treeObj = $.fn.zTree.getZTreeObj("ztree_root_Id");
			var nodes = treeObj.getSelectedNodes();
			treeObj.setting.view.fontCss = {};
			if (nodes.length > 0) {
				nodes[0].name = data.name;
				// console.log("进入节点内置函数");
				treeObj.updateNode(nodes[0]);
			}
		}
		
		//查询角色信息: 单击事件................................................................................................
		function myFunction(){
			
		}
		
		function zTreeOnClick(event, treeId, treeNode) {
			if (treeNode.name == "管理员") {
				$("#role_name_id").attr("readonly", "readonly");
				$(document).unbind('mouseover');
			}else {
				$("#role_name_id").removeAttr("readonly");
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
			}
			// console.log("node" + JSON.stringify(treeNode));
			$("#ztree_root_Id a").css('color', '#000000');
	        $("#"+treeNode.tId+"_a").css('color', '#ff0006');
	        refreshNode(treeNode);
	        //将当前节点数据赋值给角色详情form
	        $("#id").val(treeNode.id);
	        $("#role_name_id").val(treeNode.name);
	        $("#pid_id").val(treeNode.pid);
			$("#classes_id").val(treeNode.classes);
			
			//下拉框赋值
			// 角色父级
			var Obj = {"id" : treeNode.pid};
			$.ajax({
				url : '/role/get',
				type : 'post',
				data : Obj,
				dataType : 'json',
				async : false,
				success : function(data) {
					var role_data = data.data[0];
					if (role_data != null) {
						$("#pid_id").attr("data-id", role_data.id);
						$("#pid_id").val(role_data.role_name);
					}
				},
				error : function(data) {
				}
			})
			// 角色级别
			if (treeNode.classes == 0) {
				$("#classes_id").attr("data-id", treeNode.classes);
				$("#classes_id").val("管理员");
			} else if (treeNode.classes == 1) {
				$("#classes_id").attr("data-id", treeNode.classes);
				$("#classes_id").val("VIP");
			} else if (treeNode.classes == 2) {
				$("#classes_id").attr("data-id", treeNode.classes);
				$("#classes_id").val("普通");
			} else {
				$("#classes_id").attr("data-id", treeNode.classes);
				$("#classes_id").val("未指定");
			}
			
			// 是否停用
			if (treeNode.is_stop == 0) {
				$("#is_stop_id").attr("data-id", treeNode.is_stop);
				$("#is_stop_id").val("否");
			} else {
				$("#is_stop_id").attr("data-id", treeNode.is_stop);
				$("#is_stop_id").val("是");
			}
			//$("#is_stop_id").val(treeNode.is_stop);
		};

		// 后台返回Long类型时间转化为时间字符串
		function getCurrentTime(date) {
			// var date = new Date();//当前时间
			var month = zeroFill(date.getMonth() + 1);//月
			var day = zeroFill(date.getDate());//日
			var hour = zeroFill(date.getHours());//时
			var minute = zeroFill(date.getMinutes());//分
			var second = zeroFill(date.getSeconds());//秒
			var curTime = date.getFullYear() + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
			return curTime;
		}
		
		// 时间字符串转Data类型
		function convertDateFromString(dateString) {
			if (dateString) {
				var date = new Date(dateString.replace(/-/, "/"))
				return date;
			}
		}
		
		//角色按钮显示
		var newCount = 1;
		var indexCount = 10000;
		function addHoverDom(treeId, treeNode) {
			var sObj = $("#" + treeNode.tId + "_span"); //获取节点信息
			if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0)
				return;
			var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='添加' onfocus='this.blur();'></span>"; //定义添加按钮
			sObj.after(addStr); //加载添加按钮
			var btn = $("#addBtn_" + treeNode.tId);
			// return false;
			// 添加角色树节点: 绑定添加事件,并定义添加操作
			if (btn)
				btn.bind("click", function() {
					var zTree = $.fn.zTree.getZTreeObj("ztree_root_Id");
					var random = parseInt(Math.random() * 101, 10);
					//对象数据(树对象默认含有level层级字段)
					$("#role_name_id").val(treeNode.name);
			        $("#pid_id").val(treeNode.pid);
					$("#classes_id").val(treeNode.classes);
					$("#is_stop_id").val(treeNode.is_stop);
					
					var roleObj = {
						"role_name" : "role" + (random + newCount++),
						"pid" : treeNode.id,
						"classes" : 2, //普通角色
						"is_stop" : 0, //不停用
						"update_time" : new Date()
					};
					
					// 将新节点对象数据添加到数据库中
					$.post('/role/save', roleObj, function(data) {
						var jsonData = eval('(' + data + ')');
						// 返回(或获取)新添加的节点Id
						var return_id = jsonData.id;
						//页面上添加节点, 注意name属性值
						zTree.addNodes(treeNode, {
							id : return_id,
							pid : roleObj.pid,
							name : roleObj.role_name,
							classes : roleObj.classes,
							is_stop : roleObj.is_stop
						});
						//刷新当前节点
						refreshNode(treeNode);
						//根据新的id找到新添加的节点
						var node = zTree.getNodeByParam("id", return_id, null);
						//让新添加的节点处于选中状态
						zTree.selectNode(node);
						
					});
				});
		};
		
		// 角色按钮隐藏
		function removeHoverDom(treeId, treeNode) {
			$("#addBtn_" + treeNode.tId).unbind().remove();
		};
		
		// Long类型时间转化为Date类型
		function datetimeFormat(longTypeDate) {
			var dateTypeDate = "";
			var date = new Date();
			date.setTime(longTypeDate);
			dateTypeDate += date.getFullYear(); //年  
			dateTypeDate += "-" + date.getMonth(); //月  
			dateTypeDate += "-" + date.getDay(); //日  
			dateTypeDate += " " + date.getHours(); //时  
			dateTypeDate += ":" + date.getMinutes(); //分 
			dateTypeDate += ":" + date.getSeconds(); //秒
			return dateTypeDate;
		}

		// 修改角色树节点
		var format_create_time;
		function onRename(e, treeId, treeNode, isCancel) {
			// 禁止修改管理员角色
			if (treeNode.id == '5555ffff69b495f40169b7daba749999') {
				$.myToast(" 该角色属管理员! 您无权修改!!! ");
				return false;
			}
			
			//获取表单数据,提交后台更新
			var node_date = treeNode.create_time;
			//如果整颗树不刷新,此处不会查数据库,该节点的创建时间为空; 若此处创建时间为空说明属于新加节点,对其创建时间进行初始化
			if (node_date == undefined || node_date == null || node_date == "") {
				format_create_time = null;
			} else {
				format_create_time = datetimeFormat(node_date);
				format_create_time = convertDateFromString(format_create_time);
			}
			
			var roleObj = {
				"id" : treeNode.id,
				"role_name" : treeNode.name,
				"pid" : treeNode.pid,

				"classes" : treeNode.classes,
				"is_stop" : treeNode.is_stop,
				
				"update_time" : new Date()
			};
			
			$.post('/role/update', roleObj, function(data, textStatus, xhr) {
				var jsonData = eval('(' + data + ')');
				if (jsonData.flag) {
					$.myToast("修改成功!");
					var zTree = $.fn.zTree.getZTreeObj("ztree_root_Id");
					//刷新当前节点
					refreshNode(treeNode);
					//根据新的id找到新添加的节点
					var node = zTree.getNodeByParam("id", roleObj.id, null);
					//让新修改的节点处于选中状态
					zTree.selectNode(node);
				} else {
					$.myAlert("修改失败!");
				}
			});
		}

		// 禁止删除非空角色: 删除父子角色判定
		function beforeRemove(treeId, treeNode) {
			// 禁止删除根目录
			if (treeNode.id == '5555ffff69b495f40169b7daba749999') {
				$.myToast(" 该角色属管理员! 严禁删除!!! ");
				return false;
			}
			if (treeNode.isParent) {
				$.myToast("该角色下有子角色, 禁止删除!");
				return false;
			}
			// return $.myConfirm({title:'确认框',message:'您真的确认删除吗?'});
			// return false;
			// return $.myConfirm("确认删除角色 <" + treeNode.name + "> 吗?");
		}

		// 删除角色树节点
		function onRemove(e, treeId, treeNode) {
			$.myConfirm({title:'确认框',message:'您真的确认删除吗?',callback:function(){
				// 发送ajax请求
				var id = treeNode.id;
				$.post(
					'/role/delete', 
					{"id" : id}, 
					function(data, textStatus, xhr) {
						var jsonData = eval('(' + data + ')');
						if (jsonData.flag) {
							$.myToast("删除成功!");
							//刷新当前节点
							//refreshNode(treeNode);
							init();
						} else {
							$.myAlert("删除失败!");
						}
					}
	
				);
			}})
			init();
		}

		// 获取角色Table数据
		var Obj = {};
		function formData() {
			$($("#form_id").serializeArray()).each(function() {
				Obj[this.name] = this.value;
			});
			Obj.pid = $("#pid_id").attr("data-id");
			Obj.classes = $("#classes_id").attr("data-id");
			Obj.is_stop = $("#is_stop_id").attr("data-id");
			return Obj;
		}
		
		// 更新角色完整信息
		function update() {
			formData();
			// 提交后台更新
			$.ajax({
				data : Obj,
				type : 'post',
				dataType : 'json',
				url : '/role/update',
				async : false,
				success : function(data) {
					// var jsonData = eval('(' + data + ')');
					if (data.flag) {
						$.myToast("更新成功!");
						//刷新当前节点
						refreshNode(Obj);
					} else {
						$.myAlert("更新失败!");
					}
				},
				error : function(data) {
					$.myAlert("更新异常!");
				}
			})
		}

		// 取消更新操作
		function refresh() {
			window.location.reload();
		}
		
	</script>
</body>
</html>
