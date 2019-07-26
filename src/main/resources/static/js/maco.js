// Maco JS Content
function clickbtn(obj){
	var othis = $(obj);
	othis.toggleClass('btn-right');
	if(othis.hasClass('btn-right')){
		// 左侧菜单缩进
		$('.wrap_content').animate({paddingLeft:0},200);
		$('.left_sider').animate({left:'-240px'},200);
		$('.input-group-editor').css({'width':'100%'});
		
	}else{
		// 左侧菜单展开
		$('.wrap_content').animate({paddingLeft:'240px'},200);
		$('.left_sider').animate({left:0},200);
		$('.input-group-editor').css({'width':'87%'});
	}
}

// 定义show和hide的具体样式 或者 style="display:none"; 与display:block 的属性和值, 在需要切换弹框的div上class添加 "toggle" 样式
function form_toggle(){
	$this = $("div").filter(".toggle");
    var rt = $this.attr('style');
    if(rt == "display:none"){
    	$this.attr('style','display:block');
    }else{
    	$this.attr('style','display:none');
    }
}

// 自定义树节点字体颜色
function getFont(treeId, node) {
	//如果节点id==0则是红色字体（这里自己写自己的判断逻辑就可以了）
	if (node.id == 0) {
		return {
			"color" : "red"
		};
	} else {
		return {
			"color" : "red"
		};
	}
}

// 自定义serializeJson序列化表单函数(含复选框)
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

// 若表单没有复选框,使用此方法
function formData(){
	var Obj={};
	$($("#form_id").serializeArray()).each(function(){
		Obj[this.name]=this.value.trim();
	});
	console.log(JSON.stringify(Obj));
	return Obj;
}