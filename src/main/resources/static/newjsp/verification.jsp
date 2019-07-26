<!doctype html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
<title>兼容IE6/IE7/IE8/火狐---下拉菜单select样式设置</title>
<%-- <script type="text/javascript" src="<%=basePath %>js/jquery-1.11.1.min.js"></script> --%>
<script type="text/javascript" src="http://ajax.microsoft.com/ajax/jquery/jquery-1.11.1.min.js"></script>
<style type="text/css">
body {background-color: #FFF;}

.redColor{ color: red; }
.msg{ font-size: 13px; }
.onError{ color: red; }
.onSuccess{ color: green; }

*{margin:0;padding:0}
::-webkit-scrollbar{width:4px;background:#eee;border-radius:2px;}
ul,li{list-style:none;}
.submitForm{margin:20px;}
.unit{margin-bottom:5px;}
.unit label{display:inline-block;font-size:15px;line-height:32px;color:#333;}
.unit input,.unit .self_drop{display:inline-block;width: 240px;height: 32px;font-size:15px;padding:0 5px;-webkit-box-sizing:border-box;box-sizing:border-box;line-height: 30px;position:relative;border:1px solid #ccc;*display:inline;*zoom:1;border-radius:3px;}
.unit input{outline:none;}
.unit input[type='radio'],.unit input[type='checkbox']{width:18px;margin-right:4px;vertical-align:middle;}
.self_drop span,.self_drop i{display:inline-block;}
.self_drop span{color:#999;}
.self_drop i{width:30px;height:30px;background:#eee;position:absolute;right:0;}
.self_drop i:after{width:0;height:0;content:'';border-right:7px solid transparent;border-left:7px solid transparent;border-top:7px solid #666;position:absolute;top:12px;left:8px;}
.self_drop i.down:after{width:0;height:0;content:'';border-right:7px solid transparent;border-top:7px solid transparent;border-left:7px solid transparent;border-bottom:7px solid #666;position:absolute;top:4px;left:8px;}
.list{display:none;width: 100%;line-height: 30px;height:210px;background:#fff;z-index:100;position: absolute;left: -1px;top: 32px;border: 1px solid #ccc;_height: 210px;cursor:pointer;overflow:hidden;overflow-y:auto;}
.list li{width: 100%;overflow:hidden;white-space:nowrap;-webkit-text-overflow:ellipsis;text-overflow:ellipsis;font-size: 14px;line-height:30px;text-indent:5px;}
.list li:hover {background: #008b8b;color: #fff;}
.button{display:inline-block;height:38px;font-size:15px;line-height:36px;border:none;outline:none;padding:0 15px;-webkit-box-sizing:border-box;box-sizing:border-box;border-radius:4px;color:#fff;cursor:pointer;}
.btnGroup{clear:both;display:block;margin-top:20px;}
.btnGroup .green{background:#090;}
.btnGroup .red{background:#F00;}


</style>
</head>
<body>
	<h2 align="center">测试页</h2>
			<form id="form_id" class="submitForm" method="post" action="">
				<div class="unit">
					<label>姓名：</label>
					<input type="text" name="name" id="name" placeholder="请输入姓名" class="required" />
				</div>
				<div class="unit">
					<label>性别：</label>
					<input type="radio" name="sex" value="1" checked="checked" /><label>男</label>
					<input type="radio" name="sex" value="0" /><label>女</label>
				</div>
				<div class="unit">
					<label>手机：</label>
					<input type="text" name="phone" id="phone" placeholder="请输入手机号" class="required" />
				</div>
				<div class="unit">
					<label>邮箱：</label>
					<input type="text" name="email" id="email" placeholder="请填写电子邮箱" class="required" />
				</div>
				<div class="unit">
					<label>住址：</label>
					<input type="text" name="address" id="address" placeholder="请填写住址" />
				</div>			
				<div class="unit">
					<label>区域：</label>
					<div class="self_drop">
						<span>--请选择--</span>
						<i></i>
						<input type="hidden" name="area" value="" />
						<ul class="list">
							<li data-value="">--请选择--</li>
							<li data-value="0">内蒙古自治A0区</li>
							<li data-value="1">内蒙古自治B1区</li>
							<li data-value="2">内蒙古自治C2区</li>
							<li data-value="3">内蒙古自治D3区</li>
						</ul>
					</div>
				</div>
				<div class="unit">
					<label for="work">管辖内容：</label>
					<input type="checkbox" name="work" value="0" /><label>狩猎</label>
					<input type="checkbox" name="work" value="1" checked="checked" /><label>防火</label>
					<input type="checkbox" name="work" value="2" checked="checked" /><label>培育</label>
				</div>
				<div class="btnGroup">
					<input type="button" class="button green" value="提交" onclick="formData()"/>
					<input type="button" class="button red" value="重置" id="reset_id" />
				</div>
			</form>

<script type="text/javascript">
	$(function() {
		// BEGIN 自定义下拉框通用JS代码(里面的代码如果没BUG或者特殊需求最好不要动)
		/* $(document).on({
			mouseover : function() {
				$(this).css('z-index', 2);
				$(this).children('.list').show();
			},
			mouseout : function() {
				$(this).css('z-index', 1);
				$(this).children('.list').hide();
			}
		}, '.self_drop');

		$(document).on('click', '.self_drop a', function() {
			var $parent = $(this).parents('.self_drop');
			$parent.find('input').val($(this).attr('data-value'));
			$parent.find('span').text($.trim($(this).text()));
			$(this).parent().hide();
			return false;
		}); */
		$(document).on({
            mouseover: function () {               
                $(this).children('.list').show();
				$(this).find('i').addClass('down');
            },
            mouseout: function () {              
                $(this).children('.list').hide();
				$(this).find('i').removeClass('down');
			}		
	    }, '.self_drop');
 
        $(document).on('click','.self_drop li', function () {
            var $parent = $(this).parents('.self_drop');			
            $parent.find('input').val($(this).attr('data-value'));
            $parent.find('span').text($.trim($(this).text()));   //  $.replace(/\s+/g,"") 函数用于去除字符串两端的空白字符。注意： $.replace(/\s+/g,"")函数会移除字符串开始和末尾处的所有换行符，空格(包括连续的空格)和制表符。如果这些空白字符在字符串中间时，它们将被保留，不会被移除。
            $(this).parent().hide();
            return false;
        }); 
		// END 自定义下拉框通用JS代码
	});
	
	// 1.表单提交
	// 1.1 自定义serializeJson序列化表单函数
	$.fn.serializeJson = function() {
		var serializeObj = {};
		var array = this.serializeArray();
		$(array).each(function() { //遍历数组
			// 复选框:判断对象中是否已经存在该name值,若不存在(undefined),则在else中添加键值对.若已经存在work
			if (serializeObj[this.name]) {
				// 且work为数组
				if ($.isArray(serializeObj[this.name])) {
					// 向数组追加第三个元素的value值
					serializeObj[this.name].push(this.value);
				} else {
					// 组成放入第二个元素的value值
					serializeObj[this.name] = [ serializeObj[this.name], this.value ];
				}
			} else {
				serializeObj[this.name] = this.value;
			}
		});
		return serializeObj;
	};
	// 1.2 调用自定义函数
	/* function formData() {
		var objData = $("#form_id").serializeJson();
		alert(JSON.stringify(objData));
	} */

	//02. 若表单没有复选框,使用此方法
	/* function formData(){
		var Obj={};
		$($("#form_id").serializeArray()).each(function(){
			Obj[this.name]=this.value.trim();
		});
		alert(JSON.stringify(Obj));
	} */
	
	// 2.表单框架验证
	// 2.1 为表单的必填文本框添加提示信息（选择form中的所有后代input元素）
    $("form :input.required").each(function () {
        //通过jquery api：$("HTML字符串") 创建jquery对象
        var $required = $("<strong class='redColor'>*</strong>");
        //添加到this对象的父级对象下
        $(this).parent().append($required);
    });
	// 2.2  为表单元素添加失去焦点事件
	$("form :input").blur(function() {
		var $parent = $(this).parent();
		var okMsg = "输入正确";
		//删除旧提示,查找匹配元素
		$parent.find(".msg").remove();
		//验证姓名
		if ($(this).is("#name")) {
			var val = $.trim(this.value);
			//var reg = /^[\u4E00-\u9FA5]{2,4}$/;
			var reg = /(^[\u4e00-\u9fa5]{1}[\u4e00-\u9fa5\.·。]{0,8}[\u4e00-\u9fa5]{1}$)|(^[a-zA-Z]{1}[a-zA-Z\s]{0,8}[a-zA-Z]{1}$)/;
			if (val == "" || val.length < 2 || !reg.test(val)) {
				var errorMsg = "姓名非空,2-10个汉字或英文字母,符合中英姓名规则!";
				$parent.append("<span class='msg onError'>" + errorMsg + "</span>");
			} else {
				$parent.append("<span class='msg onSuccess'>" + okMsg + "</span>");
			}
		}
		//验证手机(固话号码为 /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;)
		if ($(this).is("#phone")) {
			var val = $.trim(this.value);
			var reg = /^[1][3,4,5,7,8][0-9]{9}$/;
			if (val == "" || !reg.test(val)) {
				var errorMsg = "请输入11位标准手机号码!";
				$parent.append("<span class='msg onError'>" + errorMsg + "</span>");
			} else {
				$parent.append("<span class='msg onSuccess'>" + okMsg + "</span>");
			}
		}
		//验证邮箱
		if ($(this).is("#email")) {
			var val = $.trim(this.value);
			var reg = /.+@.+\.[a-zA-Z]{2,4}$/;
			if (val == "" || (val != "" && !reg.test(val))) {
				var errorMsg = "请输入正确的E-Mail地址!";
				$parent.append("<span class='msg onError'>" + errorMsg + "</span>");
			} else {
				$parent.append("<span class='msg onSuccess'>" + okMsg + "</span>");
			}
		}
	}).keyup(function() {
		// triggerHandler 防止事件执行完后 浏览器自动为标签获得焦点
		$(this).triggerHandler("blur");
	}).focus(function() {
		$(this).triggerHandler("blur");
	});

	//(新增初始打开窗口页面时)重置表单 并清除提示
	$("#reset_id").click(function(){
		$(".onSuccess,.onError").html("");
		$("form")[0].reset();
	})
	//前置校验
	function checkForm(){
		if ($(".onError").text() == "") return true;
		return false;
	}
	// 点击提交按钮,优先触发onclick,再触发submit;全局监控判断(该段可放置在任何地方,无$(function(){} 时只能在绑定元素之后才能触发该函数))
	function formData() {
		//表单提交  $("form").submit(function(){ return true;})没必要,因需处理返回数据故用ajax与后台交互
		//序列化表单数据
		var objData = $("#form_id").serializeJson();
		console.log(JSON.stringify(objData));
		//前置校验,校验成功发送Ajax
		if (checkForm()) {
			$.ajax({
				data:objData,
				type:'post',
				dataType:'json',
				url:'${ctx}/sys/showOrg/findCurrentLevelType',
				async:false,
				success:function(jsr){
					console.log("ok... : " + jsr.data);
				},
				error:function(jsr){
					console.log("no");
				}
			})
		}else{
			return false;
		}
	}
	
	
</script>
</body>
</html>
