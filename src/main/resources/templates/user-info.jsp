<!-- 用户详情弹框内容 -->
<div class="right-sider">
	<div class="forms-content">
		<h3 class="form-title">用户详情</h3>
		<form id="form_id" class="forms" action="" method="post">
			<input id="id" name="id" type="hidden" />
			<div class="input-group">
				<label for="user_name">姓名:</label>
				<input type="text" id="user_name_id" name="user_name" autocomplete="off" placeholder="请输入姓名">
			</div>
			<div class="input-group">
				<label for="login_name">账号:</label>
				<input type="text" id="login_name_id" name="login_name" autocomplete="off" placeholder="请输入账号">
			</div>
			<div class="input-group">
				<label for="pass_word">密码:</label>
				<input type="text" id="pass_word_id" name="pass_word" autocomplete="off" placeholder="请输入密码">
			</div>
			<div class="input-group">
				<label for="is_freeze">是否冻结:</label>						
				<div class="self_drop">
					<input type="text" id="is_freeze_id" name="is_freeze" autocomplete="off" placeholder="请选择是否停用" readonly="readonly">
					<i></i>
					<ul class="dropstyle">
						<li data-value="0">否</li>
						<li data-value="1">是</li>
					</ul>
				</div>
			</div>
			
			<div class="input-group">
				<label for="role_id">用户角色:</label>						
				<div class="self_drop">
					<input type="text" id="role_id" name="role" autocomplete="off" placeholder="请选择角色" readonly="readonly">
					<i></i>
					<ul class="dropstyle">
						<li data-value="402881fc69bce4880169bce532480000">VIP</li>
						<li data-value="402881fc69bce4880169bce59a560001">普通</li>
					</ul>
				</div>
			</div>
			<div class="input-group">
				<label for="mobile_phone">手机号码:</label>
				<input type="text" id="mobile_phone_id" name="mobile_phone" autocomplete="off" placeholder="请输入手机号码">
			</div>
			<div class="input-group">
				<label for="wechat">微信号:</label>
				<input type="text" id="wechat_id" name="wechat" autocomplete="off" placeholder="请输入微信号">
			</div>
			<div class="input-group">
				<label for="email">电子邮箱:</label>
				<input type="text" id="email_id" name="email" autocomplete="off" placeholder="请输入电子邮箱">
			</div>
			<div class="btns-group">
				<button type="button" class="save" onclick="save()">保存</button>
				<button type="button" class="update" onclick="update()">更新</button>
				<button type="button" class="cancle" onclick="cancel()">取消</button>
			</div>
		</form>
		</div>
</div>