<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
<title>MACO系统--文章管理</title>
<link href ="/img/m_icon.png" rel="shortcut icon">
<link href ="/img/m_icon.png" rel="shortcut icon">
<link rel="stylesheet" href="/css/reset.css" type="text/css">
<link rel="stylesheet" href="/ztree/resources/icomoon_styles.css" type="text/css">
<link rel="stylesheet" href="/ztree/resources/metroStyle.css" type="text/css">
<link rel="stylesheet" href="/css/maco.css" type="text/css">
<link rel="stylesheet" href="/css/maco-business.css" type="text/css">
<script type="text/javascript" src="/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/js/maco.js"></script>
<script type="text/javascript" src="/ztree/resources/jquery.ztree.core.js"></script>
<script type="text/javascript" src="/ztree/resources/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="/ztree/resources/jquery.ztree.exedit.js"></script>
<!-- 弹框css/js -->
<link rel="stylesheet" href="/alerts/myAlert.min.css" />
<script type="text/javascript" src="/alerts/myAlert.min.js" ></script>
<!-- 只需要引用 JS无需引用任何 CSS -->
<script type="text/javascript" src="/editor/wangEditor.min.js"></script>
</head>

<body>
	<div class="wrap_container">
		<!-- 页面头部  -->
		<%@ include file="/jsp/top.jsp" %>
		
		<!-- 页面头部下面内容区域 -->
		<div class="wrap_content">
			<!-- 左侧文章 -->
			<div class="left_sider">
				<!-- 条件组 -->
				<div class="person-group">
					<h3 class="page_title">条件组</h3>
					<input type="text" id="keyword_id" name="keyword" autocomplete="off" placeholder="基础检索词" />
					<input type="text" id="and_keyword_id" name="and_keyword" autocomplete="off" placeholder="AND检索词" />
					<input type="text" id="or_keyword_id" name="or_keyword" autocomplete="off" placeholder="OR检索词" />					
					<div class="self_drop">
						<input type="text" id="pid_id" name="pid" autocomplete="off" placeholder="请选择文章父目录" readonly="readonly" />
						<i></i>
						<ul class="dropstyle">									
						</ul>
					</div>
				</div>
				<!-- 操作组 -->
				<div class="opera-group">
					<h3 class="page_title">操作组</h3>
					<div class="btns-group">
						<button type="button" class="search" onclick="search()">Search</button>
						<button type="button" class="save" onclick="start()">解禁</button>
						<button type="button" class="save" onclick="save()">添加</button>
						<button type="button" class="del" onclick="del()">删除</button>
						<button id="update_id" type="button" class="update" onclick="update()">更新</button>
						<button type="button" class="cancle" onclick="refresh()">取消</button>
					</div>
				</div>
				<!-- 文章管理 -->
				<div class="article-group">
					<h3 class="page_title">文章管理</h3>
					<div class="menu">
						<div id="ztree_root_Id" class="ztree"></div>
					</div>
				</div>		
			</div>	
			
			<!-- 右侧内容 -->
			<div class="right-sider">
				<div class="forms-content">
				
					<form id="form_id" class="forms" action="" method="post">
						<div class="input-group">
							<input type="text" style="text-align:center;" id="title_id" name="title" autocomplete="off" placeholder="标题" />
						</div>
						<input id="id" name="id" type="hidden" />
						<input id="level_sign_id" name="level_sign" type="hidden" />
						
						<div id="editor" class="input-group-editor text" min-height="">
							<p>...</p>
						</div>
						
						<!-- <button onclick="$.myAlert('这里是提示框内的内容');">点击弹出提示框</button><br/><br/>
						
						<button onclick="$.myAlert({title:'Title',message:'message',callback:function(){alert(1)}});">点击弹出提示框(带有callback)</button><br/><br/>
						
						<button onclick="$.myConfirm({title:'确认框提示标题',message:'确认框提示内容',callback:function(){alert('callback')}})">点击弹出确认框</button><br/><br/>
						
						<button onclick="$.myToast('提示内容')">点击弹出自动消失的提示</button><br/><br/>
						
						<button onclick="$.myLoadding()">加载中提示框</button><br/><br/>
						
						<button style="z-index: 8000;position: fixed;" onclick="$.removeModa()">关闭提示框</button><br/><br/> -->
						
						<!-- <div class="btns-group">
							<button type="button" class="search" onclick="search()">Search</button>
							<button type="button" class="save" onclick="start()">开启编辑</button>
							<button type="button" class="save" onclick="save()">添加</button>
							<button type="button" class="del" onclick="del()">删除</button>
							<button id="update_id" type="button" class="update" onclick="update()">更新</button>
							<button type="button" class="cancle" onclick="refresh()">取消</button>
						</div> -->
					</form>
 				</div>
			</div>	
		</div>
	</div>

	<script type="text/javascript">
		// ====================富文本编辑器初始化Start====================
		var E = window.wangEditor;
		var editor = new E('#editor');
		// 自定义菜单配置
		 editor.customConfig.menus = [
		    'fontName',  // 字体
		    'fontSize',  // 字号
		    'bold',  // 粗体
		    'justify',  // 对齐方式
		    'foreColor',  // 文字颜色
		    'backColor',  // 背景颜色
		    'table',  // 表格      
	    ]
		
		/* [
		    'head',  // 标题
		    'bold',  // 粗体
		    'fontSize',  // 字号
		    'fontName',  // 字体
		    'italic',  // 斜体
		    'underline',  // 下划线
		    'strikeThrough',  // 删除线
		    'foreColor',  // 文字颜色
		    'backColor',  // 背景颜色
		    'link',  // 插入链接
		    'list',  // 列表
		    'justify',  // 对齐方式
		    'quote',  // 引用
		    'emoticon',  // 表情
		    'image',  // 插入图片
		    'table',  // 表格
		    'video',  // 插入视频
		    'code',  // 插入代码
		    'undo',  // 撤销
		    'redo'  // 重复
		] */
		
		// 自定义字体
		editor.customConfig.fontNames = [
			'微软雅黑',
			'宋体',
		]

		// 自定义配置颜色（字体颜色、背景色）
		editor.customConfig.colors = [
			'#FFFFFF',
			'#000000',
			'#FF0000',
			'#393939',
		]
		
		// 自定义 onchange 触发的延迟时间2000,单位 ms,默认为 200 ms
		editor.customConfig.onchangeTimeout = 5000
		// 监控文本内容变化,html为变化之后的内容
		editor.customConfig.onchange = function (html) {
	        // 5s自动保存
	        // console.log(html);
	        // update();
	        // 获取文章父级选中标记
			var level_sign = $("#level_sign_id").val();

			// 判断选中文章后进行操作
			if (level_sign != null && level_sign != "" && level_sign == 0) {
				return false;
			} else {
				var id = $("#id").val();
				var title = $("#title_id").val();
				var pid = curNode.pid;
				var content = html;
				//var content = editor.txt.html();
	
				var Obj = {
					"id" : id,
					"title" : title,
					"pid" : pid,
					"content" : content
				};
				
				// 提交后台更新
				$.ajax({
					url : '/article/update',
					type : 'post',
					data : Obj,
					dataType : 'json',
					async : true,
					success : function(data) {
						if (data.flag) {
							$.myToast("自动保存成功!");
							//刷新当前节点
							init();
						} else {
							$.myToast("自动保存失败!");
						}
					},
					error : function(data) {
						$.myToast("自动保存异常!");
					}
				})
			}
	        
	    }
		// 创建编辑器
		editor.create();
		// ====================富文本编辑器初始化End====================
	    
		/////////////////////////////////////////////////////////////////////////////
		var allNodes;//所有节点数据
		var treeObj;//所有节点对象
		var curNode;//当前点击节点数据
		var query;//条件组查询条件
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

		function search() {
			init();
		}

		//初始化tree(并展开第一级函数)
		function init() {
			// 条件组查询条件 keyword and_keyword or_keyword;
			var keyword = $("#keyword_id").val();
			var and_keyword = $("#and_keyword_id").val();
			var or_keyword = $("#or_keyword_id").val();
			query = {
				"keyword" : keyword,
				"and_keyword" : and_keyword,
				"or_keyword" : or_keyword
			};

			$.post("/article/findTree", query, function(js) {
				// console.log(JSON.stringify(query));
				treeObj = $.fn.zTree.init($("#ztree_root_Id"), setting, js.data, false);
				//初始化父级文章下拉框: 获取所有文章节点id与name,添加到下拉框
				/* allNodes = js.data;
				if (allNodes instanceof Array) {
					// console.log(allNodes);
					for ( var i in allNodes) {
						// $("#pid_id").append(" <option value='" + allNodes[i].id + "' selected='' >" + allNodes[i].name + "</option> ");
						$('.dropstyle').append("<li data-value='" + allNodes[i].id + "'>" + allNodes[i].name + "</li>");
					}
				}else {
					// $("#pid_id").append(" <option value='9999' selected='' >无数据</option> ");
					$('.dropstyle').append("<li data-value='9999'>无数据</li>");
				} */
				var nodes = treeObj.getNodes();
				//if (nodes.length>0) {
				//for(var i=0;i<nodes.length;i++){
				//treeObj.expandNode(nodes[i], true, false, false);
				//}
				//}

				//默认选中第一个节点
				var snode = nodes[0];
				//treeObj.selectNode(snode);
				//setting.callback.onClick(null, treeObj.setting.treeId, snode);

			}, 'json');
		}

		// 查询下拉二级目录
		function initRoot() {
			$.ajax({
				url : '/menu/getBy',
				type : 'get',
				data : {
					'menu_level' : 1
				},
				dataType : 'json',
				async : true,
				success : function(data) {
					if (data.flag) {
						// console.log(data);
						allNodes = data.data;
						if (allNodes instanceof Array) {
							// console.log(allNodes);
							for ( var i in allNodes) {
								if (allNodes[i].menu_name == "系统管理") {
									continue;
								}
								// $("#pid_id").append(" <option value='" + allNodes[i].id + "' selected='' >" + allNodes[i].name + "</option> ");
								$('.dropstyle').append("<li data-value='" + allNodes[i].id + "'>" + allNodes[i].menu_name + "</li>");
							}
						} else {
							// $("#pid_id").append(" <option value='9999' selected='' >无数据</option> ");
							$('.dropstyle').append("<li data-value='9999'>无数据</li>");
						}
					} else {
						$.myToast("二级目录查询失败!");
					}
				},
				error : function(data) {
					$.myToast("二级目录查询异常!");
				}
			})

		}

		// 开启编辑功能
		function start() {
			editor.$textElem.attr('contenteditable', true);
		}

		//调用
		$(function() {
			// 初始化树
			init();
			// 初始化下拉二级目录
			initRoot();
			// 禁用编辑功能
			editor.$textElem.attr('contenteditable', false);
		});

		// Start自定义下拉框赋值==================================================
		$(document).on({
			mouseover : function() {
				$(this).children('.dropstyle').show();
				$(this).find('i').addClass('down');
			},
			mouseout : function() {
				$(this).children('.dropstyle').hide();
				$(this).find('i').removeClass('down');
			}
		}, '.self_drop');
		$(document).on('click', '.self_drop li', function() {
			var $parent = $(this).parents('.self_drop');
			//$parent.find('input').val($(this).attr('data-value'));
			//设置自定义属性存储ID值
			$parent.find('input').attr("data-id", $(this).attr('data-value'));
			$parent.find('input').val($.trim($(this).text()));
			//$parent.find('i').text($.trim($(this).text()));   //  $.replace(/\s+/g,"") 函数用于去除字符串两端的空白字符。注意： $.replace(/\s+/g,"")函数会移除字符串开始和末尾处的所有换行符，空格(包括连续的空格)和制表符。如果这些空白字符在字符串中间时，它们将被保留，不会被移除。
			$(this).parent().hide();
			return false;
		});
		// End自定义下拉框赋值==================================================

		//刷新当前节点内置函数
		function selectNode(data) {
			var treeObj = $.fn.zTree.getZTreeObj("ztree_root_Id");
			var nodes = treeObj.getSelectedNodes();
			treeObj.setting.view.fontCss = {};
			treeObj.updateNode();
			if (nodes.length > 0) {
				nodes[0].id = data.id;
				// console.log("进入节点内置函数");
				treeObj.updateNode(nodes[0]);
				// 获取选中节点
				var node = treeObj.getNodeByParam("id", nodes[0].id);
				treeObj.selectNode(node, true);
				// 设置为父级
				node.isParent = true;
				// 展开
				treeObj.selectNode(node, true);
			}
		}

		//查询文章: 单击事件................................................................................................
		function zTreeOnClick(event, treeId, treeNode) {
			// console.log("节点id: " + treeNode.id + "  >>>" + "父节点pid: " + treeNode.pid + "  >>> 节点名称: " + treeNode.name);
			curNode = treeNode;
			
			// 使用ID入后台查询
			$.ajax({
				url : '/article/get',
				type : 'post',
				data : {
					'id' : treeNode.id
				},
				dataType : 'json',
				async : false,
				success : function(data) {
					//var jsonData = eval('(' + data + ')');
					//console.log(data.data[0]);
					if (data != null && data.data != null && data.data.length > 0) {
						var author = data.data[0].author;
						// 如果作者字段 不为空,说明为文章节点, 在右侧显示文章内容和相关信息
						if (author != null && author != "") {
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
							
							$("#id").val((data.data[0].id));
							$("#title_id").val((data.data[0].title));
							$("#pid_id").val((data.data[0].pid));

							//下拉框赋值
							// 菜单父级
							var Obj = {
								"id" : data.data[0].pid
							};
							$.ajax({
								url : '/menu/get',
								type : 'post',
								data : Obj,
								dataType : 'json',
								async : false,
								success : function(data) {
									var menu_data = data.data[0];
									// console.log(JSON.stringify(menu_data));
									if (menu_data != null) {
										$("#pid_id").attr("data-id", menu_data.id);
										$("#pid_id").val(menu_data.menu_name);
									}
								},
								error : function(data) {
								}
							})

							$("#level_sign_id").val("");
							editor.txt.html(data.data[0].content);
						} else {
							$(document).unbind('mouseover');
							
							// 如果字段不为空,则说明点击的为父节点, 获取父节点数据, 赋值到hidden中
							$("#id").val((treeNode.id));
							// 父节点标记
							$("#pid_id").val("");
							var level = treeNode.level;
							$("#level_sign_id").val(level);

							// 同时清空呈现文章数据
							$("#title_id").val("");
							editor.txt.html("");
						}
					}

				},
				error : function(data) {
					$.myToast("文章查询失败!");
				}
			})

			//console.log("node" + JSON.stringify(treeNode));
			$("#ztree_root_Id a").css('color', '#000000');
			$("#" + treeNode.tId + "_a").css('color', '#ff0006');

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

		// 添加文章,确认已选中的是文章节点
		function save() {
			// 获取文章父级选中标记
			var level_sign = $("#level_sign_id").val();

			// 判断选中父级后进行保存操作
			if (level_sign != null && level_sign != "" && level_sign == 0) {
				// 获取文章信息(title,pid,content)
				var title = $("#title_id").val();
				var pid = $("#id").val();
				var content = editor.txt.html();

				var Obj = {
					"title" : title,
					"pid" : pid,
					"content" : content
				};

				// 数据校验
				if (pid == null || title == null || title == "" || content == null) {
					$.myToast("请将文章标题与内容填写完整!");
					return;
				}

				// 提交后台保存
				$.ajax({
					url : '/article/save',
					type : 'post',
					data : Obj,
					dataType : 'json',
					async : true,
					success : function(data) {
						if (data.flag) {
							$.myToast("添加成功!!!!!");
							//刷新当前节点
							// console.log(curNode.id);
							init();
						} else {
							$.myToast("添加失败!");
						}
					},
					error : function(data) {
						$.myToast("添加异常!");
					}
				})

			} else {
				$.myToast("请选中父级后操作!");
			}
		}

		// 删除文章,确认已选中的是文章节点
		function del() {
			//var id = treeNode.id;
			var id = $("#id").val();
			// 获取文章父级选中标记
			var level_sign = $("#level_sign_id").val();
			// console.log(curNode.name);
			var msg = '您真的确认删除 "' + curNode.name + '" 吗?';
			// 判断选中文章后进行删除操作
			if (level_sign != null && level_sign != "" && level_sign == 0) {
				$.myToast("请选择文章进行操作!");
			} else {
				$.myConfirm({
					title : '删除确认框',
					message : msg,
					callback : function() {
						$.post('/article/delete', {
							"id" : id
						}, function(data, textStatus, xhr) {
							var jsonData = eval('(' + data + ')');
							if (jsonData.flag) {
								$.myToast("删除成功!");
								//刷新当前节点
								init();
							} else {
								$.myAlert("删除失败!");
							}
						});
					}
				})
				init();
			}
		}

		// 更新文章,确认已选中的是文章节点
		function update() {

			// 获取文章父级选中标记
			var level_sign = $("#level_sign_id").val();

			// 判断选中文章后进行删除操作
			if (level_sign != null && level_sign != "" && level_sign == 0) {
				$.myToast("请选择文章进行操作!");
			} else {
				var id = $("#id").val();
				var title = $("#title_id").val();
				var pid = $("#pid_id").attr("data-id");
				var content = editor.txt.html();
				var Obj;
				if (pid != null && pid != "") {
					Obj = {
						"id" : id,
						"title" : title,
						"pid" : pid,
						"content" : content
					};
				} else {
					Obj = {
						"id" : id,
						"title" : title,
						"content" : content
					};
				}
				// console.log(JSON.stringify(Obj));
				// 提交后台更新
				$.ajax({
					url : '/article/update',
					type : 'post',
					data : Obj,
					dataType : 'json',
					async : true,
					success : function(data) {
						if (data.flag) {
							$.myToast("更新成功!");
							//刷新当前节点
							init();
						} else {
							$.myToast("更新失败!");
						}
					},
					error : function(data) {
						$.myToast("更新异常!");
					}
				})
			}
		}

		// 取消更新操作
		function refresh() {
			window.location.reload();
		}
	</script>
</body>
</html>
