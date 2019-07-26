<!DOCTYPE html>
<html lang="en">
<head>
    <title>切换</title>
</head>

<body>
<a href="javascript:void(0)" onclick="check();">点击切换</a>
<div id="navbtn" class="toggle" style="display:none">展现隐藏</div>

<style>
    .show{display: block;}
    .hide{display: none;}
</style>
<script type="text/javascript" src="/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">

	// 定义show和hide的具体样式, 在需要切换弹框的div上class添加 "toggle" 样式
    /* function form_toggle(){
    	$this = $("div").filter(".toggle");
        var flag = $this.hasClass('show');
        if(flag){
        	$this.removeClass('show');
        	$this.addClass('hide');
        }else{
        	$this.removeClass('hide');
        	$this.addClass('show');
        }
    } */
	
    function form_toggle(){
    	//style="display:none";
    	$this = $("div").filter(".toggle");
        var rt = $this.attr('style');
        //console.log(rt);
        //$("img").attr("width","180");
        if(rt == "display:none"){
        	console.log("comed");
        	$this.attr('style','display:block');
        }else{
        	$this.attr('style','display:none');
        	//$this.removeClass('hide');
        	//$this.addClass('show');
        }
    }
    
    function check() {
    	form_toggle();
    }
    
</script>
</body>
</html>