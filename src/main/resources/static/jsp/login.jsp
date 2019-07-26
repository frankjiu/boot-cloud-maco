<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>登录</title>
<meta name="description" content="particles.js is a lightweight JavaScript library for creating particles.">
<meta name="author" content="Vincent Garreau" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link href ="/img/m_icon.png" rel="shortcut icon">
<link rel="stylesheet" media="screen" href="/css/style.css">
<link rel="stylesheet" type="text/css" href="/css/reset.css" />
<script type="text/javascript" src="/js/jquery-1.11.1.min.js"></script>
<!-- 弹框css/js -->
<link rel="stylesheet" href="/alerts/myAlert.min.css" />
<script type="text/javascript" src="/alerts/myAlert.min.js" ></script>
</head>
<body>

	<div id="particles-js">
		<div class="login">
			<div class="login-top">登录</div>
			<form action="">
				<div align="center" class="didI" style="color: #f00;" id="loginErrorMsg"></div>
				<div class="login-center clearfix">
					<div class="login-center-img">
						<img src="/img/name.png" />
					</div>
					<div class="login-center-input">
						<input type="text" name="login_name" placeholder="请输入您的用户名" onfocus="this.placeholder=''" onblur="this.placeholder='请输入您的用户名'" />
						<div class="login-center-input-text">用户名</div>
					</div>
				</div>
				<div class="login-center clearfix">
					<div class="login-center-img">
						<img src="/img/password.png" />
					</div>
					<div class="login-center-input">
						<input type="password" name="pass_word" placeholder="请输入您的密码" onfocus="this.placeholder=''" onblur="this.placeholder='请输入您的密码'" />
						<div class="login-center-input-text">密码</div>
					</div>
				</div>
				<div class="login-center clearfix">
					<label class="style: background-position:-8px -49px"></label>
					<input id="code" type="text" name="code" style="width: 56%;height:30px;text-indent:5px;line-height:28px;" autocomplete="off" placeholder="请输入验证码" />
					<img src="/code/getCode" id="CreateCheckCode" onclick="myReload()" align="middle" style="float:right;width: 40%;height:30px;margin-top:2px">
					<a href="javascript:;" onclick="myReload()" style="display:block;text-align:right;line-height:24px;font-size: 14px;color: #b01818;"></a>
				</div>
				<div class="login-button">登陆</div>
			</form>
		</div>
		<div class="sk-rotating-plane"></div>
	</div>

	<!-- scripts -->
	<script src="/js/particles.min.js"></script>
	<script src="/js/app.js"></script>

	<script type="text/javascript">
	
		function myReload() {
			document.getElementById("CreateCheckCode").src = document.getElementById("CreateCheckCode").src + "?nocache=" + new Date().getTime();
		}
	
		$(document).ready(function() {
			$('body').keydown(function(e) {
				if (e.keyCode == 13) {
					loginCheck();
				}
			});
		});
	
		function loginCheck() {
			var formDataObj = {};
			var formArray = $('form').serializeArray();
		    $.each(formArray, function() {
		    	formDataObj[this.name] = this.value;
		    });
			$.ajax({
				url : '/checkLogin',
				type : 'post',
				data : formDataObj,
				dataType : 'json',
				async : false,
				success : function(js) {
					if (js.code == 4001) {
						$("#loginErrorMsg").html(js.msg);
					} else if (js.code == 4002) {
						$("#loginErrorMsg").html(js.msg);
					} else if (js.code == 4004) {
						$("#loginErrorMsg").html(js.msg);
					} else if (js.code == 200) {
						addClass(document.querySelector(".login"), "active")
						setTimeout(function() {
							addClass(document.querySelector(".sk-rotating-plane"), "active")
							document.querySelector(".login").style.display = "none"
						}, 800)
						setTimeout(function() {
							removeClass(document.querySelector(".login"), "active")
							removeClass(document.querySelector(".sk-rotating-plane"), "active")
							document.querySelector(".login").style.display = "block"
							$.myToast("登录成功!")
							window.location.href = '/main';
						}, 3000)
						
						//window.location.href = "/main";
						
					} else {
						addClass(document.querySelector(".login"), "active")
						setTimeout(function() {
							addClass(document.querySelector(".sk-rotating-plane"), "active")
							document.querySelector(".login").style.display = "none"
						}, 800)
						setTimeout(function() {
							removeClass(document.querySelector(".login"), "active")
							removeClass(document.querySelector(".sk-rotating-plane"), "active")
							document.querySelector(".login").style.display = "block"
							$.myAlert("登录失败!")
							window.location.href = '/login';
						}, 3000)
						
						//window.location.href = "/login";
					}
				},
				error : function(js) {
					if (js.code == 4003) {
						$("#loginErrorMsg").html(js.msg);
					} else {
						$("#loginErrorMsg").html(js.msg);
					}
				}
			})
		}
	
		function hasClass(elem, cls) {
			cls = cls || '';
			if (cls.replace(/\s/g, '').length == 0)
				return false; //当cls没有参数时，返回false
			return new RegExp(' ' + cls + ' ').test(' ' + elem.className + ' ');
		}

		function addClass(ele, cls) {
			if (!hasClass(ele, cls)) {
				ele.className = ele.className == '' ? cls : ele.className + ' ' + cls;
			}
		}

		function removeClass(ele, cls) {
			if (hasClass(ele, cls)) {
				var newClass = ' ' + ele.className.replace(/[\t\r\n]/g, '') + ' ';
				while (newClass.indexOf(' ' + cls + ' ') >= 0) {
					newClass = newClass.replace(' ' + cls + ' ', ' ');
				}
				ele.className = newClass.replace(/^\s+|\s+$/g, '');
			}
		}
		
		document.querySelector(".login-button").onclick = function() {
			
			loginCheck();
			
		}
		
	</script>

</body>
</html>