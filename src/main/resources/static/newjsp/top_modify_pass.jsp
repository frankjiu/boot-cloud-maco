<div class="head_wrapper">
	<div class="header">
		<div class="header_title">
			<img class="logo" src="images/logo.png" alt="logo" width="55" height="55">
			<h1 class="title">MACO系统</h1>
		</div>
		<div class="top_wrapper">
			<div class="top_icons">
				<a class="modify" href="javascript:void(0)"><i></i>修改账号</a>
			</div>
			<div class="person">
				<img src="images/person.png" alt="人员头像" width="50" height="50">
				<h3 class="person_name">${sessionScope[Constants.SESSION_POLICE_INFO].name}</h3>
			</div>
			<a class="endicon" id="logout" href="${ctx}/logout"></a>
		</div>
	</div>
</div>	

<div class="clear"></div>

<!--修改密码弹出层-->
<div id="open_close" class="pop-up">
	<div class="mask-layer"></div>
	<form id="formId" action="" class="change_password form-ugroup" method="post">
		<h1 class="password_title ">
			修改账号<span class="closebtn">&times;</span>
		</h1>
		<div class="form-pgoup form-ugroup">
			请输入新账号：
			<input id="loginName" type="text" />
		</div>
		<div class="form-pgoup form-ugroup">
			请输入原密码：
			<input id="passWord" type="text" />
		</div>
		<div class="form-pgoup form-ugroup">
			请输入新密码：
			<input id="newPassWord" type="text" />
		</div>
		<div class="form-pgoup form-ugroup">
			请确认新密码：
			<input id="reNewPassWord" type="text" />
		</div>
		<button class="truebtn" onclick = "checkAndEdit()" type="button">确定</button>
	</form>
</div>

<script src="${ctx}/js/jquery-1.11.1.min.js"></script>
<style>
	.form-ugroup input.error{color:#f00;}
</style>

<script type="text/javascript">
	//var $j = jQuery.noConflict();
	//数据合法性校验
	$(function(){
		$("#loginName").blur(function(){
			if ($(this).val() == null || $(this).val() == undefined || $(this).val() == "" || $(this).val() == "请输入新账号!") {
				//alert("请输入新账号!");
				$(this).addClass('error').val("请输入新账号!");
	        	$(this).focus;
				return false;
			} else {
				$(this).removeClass('error');
			}
		})
		
		$("#passWord").blur(function(){
			if ($(this).val() == null || $(this).val() == undefined || $(this).val() == "" || $(this).val() == "请输入原密码!") {
				//alert("请输入原密码!");
				$(this).addClass('error').val("请输入原密码!");
	        	$(this).focus;
				return false;
			} else {
				$(this).removeClass('error');
			}
		})
		
		$("#newPassWord").blur(function(){
			if ($(this).val() == null || $(this).val() == undefined || $(this).val() == "" || $(this).val() == "请输入新密码!") {
				//alert("请输入新密码!");
				$(this).addClass('error').val("请输入新密码!");
	        	$(this).focus;
				return false;
			} else {
				$(this).removeClass('error');
			}
		})
		
		$("#reNewPassWord").blur(function(){
			if ($(this).val() == null || $(this).val() == undefined || $(this).val() == "") {
				//alert("请确认新密码!");
				$(this).addClass('error').val("请确认新密码!");
	        	$(this).focus;
				return false;
			} else if ($(this).val() != $("#newPassWord").val()) {
				//alert("新密码与确认新密码不一致!");
				$(this).addClass('error').val("两次输入新密码不一致!");
	        	$(this).focus;
				return false;
			} else {
				$(this).removeClass('error');
			}
		})
		
	});
	//点击确定,获取登录用户信息,验证原始密码
	function checkAndEdit() {
		//初始化校验
		var inputTags = $("#formId").find("input");
		for (var i = 0; i < inputTags.length; i++) {
			if ($(inputTags[i]).hasClass("error")) {
				alert("账号信息初始校验未通过!");
				return false;
			}
		}
		//获取数据
		var loginName = $("#loginName").val();
		var passWord = $("#passWord").val().replace(/(^\s*)|(\s*$)/g,'');
		var newPassWord = $("#newPassWord").val().replace(/(^\s*)|(\s*$)/g,'');
		var reNewPassWord = $("#reNewPassWord").val().replace(/(^\s*)|(\s*$)/g,'');
		//异步获取原始密码比对
		var flag = false;
		var uId = null;
		$.ajax({
			type : 'get',
			url : '${ctx}/sys/userinfo/searchCurrentBack?pass=' + passWord,
			dataType : 'json',
			async : false,
			success : function(js) {
				if (js != null && js.data != null && js.bflag == true) {
					uId = js.data.id;
					flag = true;
				}else {
					$("#formId")[0].reset();
					alert("密码错误,请重试! 如需重置密码请联系管理员!");
				}
			},
			error : function(js) {
			}
		})
		var userInfo = {"id" : uId, "password" : newPassWord, "loginName" : loginName};
		//比对成功则修改密码
		if (flag) {
			$.ajax({
				type : 'post',
				url : '${ctx}/sys/userinfo/updatePass',
				dataType : 'json',
				async : false,
				data : userInfo,
				success : function(js) {
					if (js.success) {
						alert("修改成功!");
						//重置表单
						$("#formId")[0].reset();
						//隐藏弹框 visibility : hidden
						if ($('.pop-up').css('display')=='none'){
							$('.pop-up').fadeIn();			
							$('body').css({'overflow':'hidden'})
						} else {
							$('.pop-up').hide();			 
							$('body').css({'overflow':'auto'})
						};
						window.location.href="/logout";
						
					}
				},
				error : function(js) {
					$("#formId")[0].reset();
					alert("修改失败!");
				}
			})
		}
	}
	
</script>