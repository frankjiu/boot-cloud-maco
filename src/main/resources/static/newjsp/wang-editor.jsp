<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>首页</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.9.1.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<!-- 注意， 只需要引用 JS，无需引用任何 CSS ！！！-->
<script type="text/javascript" src="/wangEditor-3.1.1/release/wangEditor.min.js"></script>
<link rel="stylesheet" href="http://jqueryui.com/resources/demos/style.css">

<style>
#feedback {
	font-size: 30px;
}

#selectable .ui-selecting {
	background: #252c35;
}

#selectable .ui-selected {
	background: #252c35;
	color: white;
}

#selectable {
	list-style-type: none;
	margin: 0;
	padding: 0;
	width: 12%;
}

#selectable li {
	margin: 5px;
	padding: 10px;
	font-size: 15px;
	height: 18px;
}

body {
	background-color: #252c35;
	/*body的背景色是不受body本身的宽高的影响的。
	  body的背景色就是铺满整个页面的。
	*/
}
</style>

</head>
<body>

	<ol id="selectable">
		<li class="ui-widget-content">文件夹</li>
		<li class="ui-widget-content">文件夹</li>
		<li class="ui-widget-content">文件夹</li>
		<li class="ui-widget-content">文件夹</li>
		<li class="ui-widget-content">文件夹</li>
		<li class="ui-widget-content">文件夹</li>
		<li class="ui-widget-content">文件夹</li>
	</ol>

	<script>
		$(function() {
			$("#selectable").selectable();
		});
	</script>

	<div id="editor">
		<p>编辑器</p>
	</div>

	<script type="text/javascript">
		var E = window.wangEditor
		var editor = new E('#editor')
		// 或者 var editor = new E( document.getElementById('editor') )
		editor.create()
	</script>



</body>

</html>