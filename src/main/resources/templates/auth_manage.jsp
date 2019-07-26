<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
<title>MACO系统--权限管理</title>
<link href ="/img/m_icon.png" rel="shortcut icon">
<link rel="stylesheet" href="/css/reset.css" type="text/css">
<link rel="stylesheet" href="/ztree/resources/icomoon_styles.css" type="text/css">
<link rel="stylesheet" href="/ztree/resources/metroStyle.css" type="text/css">
<link rel="stylesheet" href="/css/maco.css" type="text/css">
<script type="text/javascript" src="/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/js/maco.js"></script>
<script type="text/javascript" src="/ztree/resources/jquery.ztree.core.js"></script>
<script type="text/javascript" src="/ztree/resources/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="/ztree/resources/jquery.ztree.exedit.js"></script>
<!-- 弹框css/js -->
<link rel="stylesheet" href="/alerts/myAlert.min.css" />
<script type="text/javascript" src="/alerts/myAlert.min.js" ></script>
</head>

<body>
	<div class="wrap_container">
		<!-- 页面头部  -->
		<%@ include file="/jsp/top.jsp" %>
		
		<!-- 页面头部下面内容区域 -->
		<div class="wrap_content">
			<!-- 左侧菜单 -->
			<div class="left_sider">
				<h3 class="page_title">权限管理</h3>
				<div class="maco">
					<div id="ztree_root_Id" class="ztree"></div>
				</div>
			</div>	
			<!-- 右侧内容 -->
			<div class="right-sider">
				<div class="forms-content">
					<h3 class="form-title">权限设置</h3>
					<form style="overflow:auto;">
						<div id="self_tree_id">
						</div>
					</form>

					<div class="btns-group">
						<button type="button" class="save" onclick="save()" id="save">保存</button>
						<button type="button" class="cancle" onclick="cancel()" id="cancel">取消</button>
					</div>
				</div>
			</div>	
		</div>
	</div>

	<script type="text/javascript">
		//根节点主键
		var rootId = "7777ffff69b495f40169b7daba748888";
		var macomenu = {"id": rootId};
		var pId = rootId;
		
		$(function() {
			$.ajax({
				url : '/menu/getBy',
				type : 'post',
				data : macomenu,
				dataType : 'json',
				//url : '${ctx}/auth/getRootMenu?id=' + rootId,////////////////////////////////////////////////////////////////////////
				async : false,
				success : function(rootdata) {
					// console.log(rootdata);
					if(rootdata.data.length == 0){
						return null;
					}
					// 插入根节点
					var rootName = (rootdata.data)[0].menu_name;
					$("#self_tree_id").append("<div style='margin-left: 2%; margin-bottom: 5px; font-size:20px;' id='rootId'>" + "<h3 style='color:#fff;'>" + rootName + "</h3>" + "</div>");
					//$("#rootId").append("<br>");
					//console.log("ok1");
					
					// 查询父节点
					var parentmenu = {"pid": rootId};
					$.ajax({
						url : '/menu/getBy',
						type : 'post',
						data : parentmenu,
						dataType : 'json',
						async : false,
						success : function(parentdata) {
							var pnodes = parentdata.data;
							//console.log(pnodes);
							for (var i = 0; i < pnodes.length; i++) {
								// 遍历父集 插入父节点,同时添加value,name和动态id值
								//$("#rootId").append("<div id='rootDiv' class='form-group'></div");
								$("#rootId").append(
									"<div style='margin-left: 30px; margin:20px 46px; font-size:18px;color:#f1f1f1;' class='groupDiv' style='display: block;' id='" + pnodes[i].id 
											+ "'><div class='parentDiv'><input class='pClass' type = 'checkbox' name = 'pItems' value = '" + pnodes[i].id + "'/>"
											+ "<b>" + pnodes[i].menu_name + "</b>" + "</div></div>");
								//$("#"+pnodes[i].id).append("<div id='subdiv'></div>");
								//$("#"+pnodes[i].id).append("&nbsp;&nbsp;&nbsp;&nbsp;");
								// 查询子节点
								var childmenu = {"pid": pnodes[i].id};
								var cnodes;
								$.ajax({
									url : '/menu/getBy',////////////////////////////////////////////////////////////////////////
									type : 'post',
									data : childmenu,
									dataType : 'json',
									//url : '${ctx}/auth/getChildMenus?id=' + pnodes[i].id,////////////////////////////////////////////////////////////////////////
									async : false,
									success : function(cdata) {
										cnodes = cdata.data;
										//console.log("ok3");
									},
									error : function() {
										//console.log("no3");
									}
								})
								// 遍历子集  插入子节点,同时添加value,name和动态id值
								if (cnodes instanceof Array) {
									for (var j = 0; j < cnodes.length; j++) {
										$("#" + pnodes[i].id).append(
												"<div class='childDiv' style='display: inline;' id='" + cnodes[j].id + "'><input class='cClass' type = 'checkbox' name = 'cItems' value = '" + cnodes[j].id + "'/>"
														+ cnodes[j].menu_name + "</div>");
									}
								}
							}
							//console.log("ok2");
						},
						error : function() {
							//console.log("no2");
						}
					})
				},
				error : function() {
					//console.log("no1");
				}
			})
		})

		$(function() {
			//获取子节点
			var cTags = $(".cClass");
			//获取父节点
			var pTags = $(".pClass");
			//根据子节点获取父节点
			//for (var i = 0; i < cTags.length; i++) {
			//var curPtags = $(cTags[i]).parent().parent().find("input .pClass");
			//}
			//1.父选中  或取消 (父选中则组选,父取消则取消组选)
			pTags.click(function() {
				//$.myToast("父选中");
				//若已有checked属性则移除
				if ($(this).attr('checked') == 'checked') {
					$(this).removeAttr('checked');
					$(this).prop('checked', false);
					//同时移除所有子节点checked属性
					$(this).parent().siblings().find(":input").removeAttr('checked');
					$(this).parent().siblings().find(":input").prop('checked', false);
				} else {
					//否则添加
					$(this).attr('checked', 'checked');
					$(this).prop('checked', true);
					//同时添加所有子节点checked属性
					$(this).parent().siblings().find(":input").attr('checked', 'checked');
					$(this).parent().siblings().find(":input").prop('checked', true);
				}
			})
			//2.子选中  或取消  ( 任一子选中,则父选中;  子取消则判断: a.当前子取消, b.如果其兄弟checkbox全为未选中状态否则父也取消)
			cTags.click(function() {
				//$.myToast("子选中");
				//获取当前子的父节点
				var curPtag = $(this).parent().siblings(".parentDiv").find("input");
				//若已有checked属性则移除
				if ($(this).attr('checked') == 'checked') {
					$(this).removeAttr('checked');
					$(this).prop('checked', false);
					//取消 则进一步判断 其兄弟checkbox全部为未选中状态则父也取消
					var flag = false;
					$(this).parent().parent().find(".childDiv input[type='checkbox']").each(function() {
						if ($(this).prop('checked')) {
							flag = true;
						}
					})
					if (!flag) {
						$(curPtag).removeAttr('checked');
						$(curPtag).prop('checked', false);
					}
				} else {
					//否则添加
					$(this).attr('checked', 'checked');
					$(this).prop('checked', true);
					$(curPtag).attr('checked', 'checked');
					$(curPtag).prop('checked', true);
				}
			})

		})

		//1.树形结构............................................................
		//1.1树结构初始化设置
		var allNodes;//所有节点数据
		var treeObj;//所有节点对象
		//tree设置
		var setting = {
			view : {
				selectedMulti: false,
				showLine: false,
				fontCss: getFont
			},
			edit: {
				enable: false //单独设置为true时，可加载修改、删除图标
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
				onClick : zTreeOnClick //单击事件
			}
		};
		
		//初始化tree并展开第一级函数
		function init(){
			$.post("/role/findTree", {}, function(js) {
				treeObj = $.fn.zTree.init($("#ztree_root_Id"), setting, js.data, false);
				//初始化父级菜单下拉框: 获取所有菜单节点id与name,添加到下拉框
				allNodes = js.data;
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

		//2.数据处理............................................................
		var id; // 当前节点主键id (角色主键id)
		var curMenus; // 当前节点权限
		var menuTags; // 所有标签数组
		// 节点单击事件
		function zTreeOnClick(event, treeId, treeNode) {
			//2.1获取节点id
			//console.log("节点id: " + treeNode.id + "  >>>  节点名称: " + treeNode.name);
			id = treeNode.id;
			//点击节点背景色
			$("#ztree_root_Id a").css('color', '#000000');
			$("#" + treeNode.tId + "_a").css('color', '#ff0006');
			//清空状态
			$("input:checkbox").each(function() {
				$(this).removeAttr("checked");
				$(this).prop("checked", false);
			});
			//2.2根据节点id 即角色主键查询权限信息,设置节点权限状态
			var rolemenu = {"role_id" : id};
			$.ajax({
				url : '/auth/getBy',////////////////////////////////////////////////////////////////////////
				type : 'post',
				data : rolemenu,
				dataType : 'json',
				//url : '${ctx}/auth/get',////////////////////////////////////////////////////////////////////////
				async : false,
				success : function(data) {
					//当前节点权限
					curMenus = data.data;
					
					//获取所有菜单标签数组
					menuTags = $("input:checkbox");
					//对比id添加权限状态
					for (var i = 0; i < menuTags.length; i++) {
						for (var j = 0; j < curMenus.length; j++) {
							if (curMenus[j] != null && $(menuTags[i]).attr("value") == curMenus[j].menu_id) {
								$(menuTags[i]).attr("checked", "checked");
								$(menuTags[i]).prop("checked", true);
							}
						}
					}
					//console.log("yes");
				},
				error : function() {
					//console.log("no");
				}
			})
		};

		//权限设置保存
		//选择节点显示权限curMenus,选择点击保存删除原有后台权限curMenus,再获取表单现有选中数据保存nowMenus
		//2.3 点击保存,开始post同步删除
		function save() {
			if (id == null || id == 'undefined') {
				$.myToast("请先选择一个节点!");
				return;
			}
			//console.log("id............." + id);
			// ajax以同步方式在后台删除当前用户的权限值,如果用户拥有权限为空,则不需要删除
			if (curMenus != null && curMenus.length > 0) {
				
				//console.log(JSON.stringify(curMenus));
				
				//创建权限ID容器
				var newArr = new Array();
				for (var g = 0; g < curMenus.length; g++) {
					if (curMenus[g] != null) {
						newArr[g] = curMenus[g].menu_id;
						//console.log("用户拥有的角色菜单权限menu_id[" + g + "]:" + curMenus[g].menu_id);
					}
				}
				// 根据节点id和菜单id删除当前权限
				if (newArr != null && newArr.length > 0) {
					$.ajax({
						url : "/auth/delete",////////////////////////////////////////////////////////////////////////
						type : "post",
						data : {
							"role_id" : id,
							"menu_ids" : newArr
						},
						dataType : "json",
						//url : "${ctx}/auth/delete",////////////////////////////////////////////////////////////////////////
						async : false,
						success : function(result) {
							$.myToast("保存成功!");
							//console.log("OK: 删除成功" + JSON.stringify(result));
						},
						error : function(result) {
							$.myAlert("保存失败!");
							//console.log("NO: 删除失败" + JSON.stringify(result));
						}
					})
				} else {
					return;
				}
			}

			// 2.4  获取权限值,请求后台保存当前页面的数据到当前用户, 获取选中标签的value属性值
			//var idArr = $('input[checked="checked"]').attr("value");
			var checkArr = $('input[checked="checked"]');
			var idArr = new Array();
			for (var k = 0; k < checkArr.length; k++) {
				var value = $(checkArr[k]).attr("value");
				idArr[k] = value;
				//console.log("value......" + value);
			}
			// 格式转化
			/* for (var int = 0; int < idArr.length; int++) {
				console.log("idArr(请求保存的权限id" + int + ")................" + idArr[int]);
			} */
			// 若权限为空则无须保存
			if (idArr != null && idArr.length > 0) {
				//数组转字符串再转数组
				// substring截取
				// ["George","John","Thomas"]-->join("")使用指定分隔符将数组转字符串--> George,John,Thomas
				// 字符串替换: "Visit Microsoft!".replace("Microsoft","Runoob")-->Visit Runoob!
				// split(" ") 将字符串按指定字符""进行分割为数组
				// 合并父子元素数组
				//取出上面mIds,根据当前角色rId保存菜单主键mIds保存角色权限(关系表)
				$.ajax({
					url : "/auth/save",////////////////////////////////////////////////////////////////////////
					type : "post",
					data : {
						"role_id" : id,
						"menu_ids" : idArr
					},
					dataType : "json",
					//url : "${ctx}/auth/save",////////////////////////////////////////////////////////////////////////
					async : false,
					success : function(result) {
						//console.log(result);
						$.myToast("保存成功!");
						//$.myToast("保存成功!");
						//console.log("保存成功,当前节点id......" + id);
					},
					error : function(result) {
						$.myAlert("保存失败!");
					}
				})
			}
		}

		// 返回
		function cancel() {
			window.location.reload();
		}
		
	</script>
</body>
</html>
