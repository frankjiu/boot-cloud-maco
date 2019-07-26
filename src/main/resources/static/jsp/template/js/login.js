var winWidth,winHeight,offsetLeft,offsetTop;
$(function () {
    calculPos($('.loginFace'));
    $(window).on('resize',function () {
        calculPos($('.loginFace'));
        calculPos($('.errorNotes'));
    });
    $('input').focus(function () {
        $(this).parent().css({'borderColor':'#f38000'});
    });
    // 校验用户名
    $('#user').blur(function () {
        $(this).parent().css({'borderColor':'#666'});
        var oValue = $(this).val(),
            reg = /^[a-zA-Z0-9_]{8,}/g;
        if(!reg.test(oValue)){
            $('.errorNotes').html('用户名错误，必须是8位以上字母、数字组成！');
            errorInfo();
        }
    });
    // 校验密码
    $('#psw').blur(function () {
        $(this).parent().css({'borderColor':'#666'});
        var oValue = $(this).val(),
            reg = /^[a-zA-Z0-9_]{8,12}/g;
        if(!reg.test(oValue)){
            $('.errorNotes').html('密码错误，必须是8-12位字母、数字、下划线组成！');
            errorInfo();
        }
    });

});
// 登录界面内容位置尺寸计算方法
function calculPos(obj) {
    winWidth = $(window).width();
    winHeight = $(window).height();
    offsetLeft = winWidth / 2;
    offsetTop = winHeight / 2;
    var Mleft = obj.width()/ 2;
    var Mright = obj.height() / 2;
    $(obj).css({
        'left': offsetLeft - Mleft + 'px',
        'top' : offsetTop - Mright + 'px'
    });
};

// 错误提示框
function errorInfo(){
    calculPos($('.errorNotes'));
    $('.errorNotes').show();
    setTimeout(function () {
        $('.errorNotes').hide().html('');
    },1500);
}


// 表单提交
function checkForm() {
    $('input').trigger('blur');
    var numError = $(".errorNotes").text().length;
    if(numError){
        errorInfo();
        return false;
    }else{
        $('.errorNotes').text('登录成功');
        errorInfo();
        return window.location.href = 'jsp/template/main.html';
    }

}