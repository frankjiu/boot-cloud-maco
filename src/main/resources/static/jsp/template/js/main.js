
$(function () {
    var winw,winh,header,menutitle;
    // 顶部导航条hover事件监听
    $('.right-navbar li').hover(
        function () {
            $(this).find('i').removeClass('fa-angle-down');
            $(this).find('i').addClass('fa-angle-up');
        },function () {
            $(this).find('i').addClass('fa-angle-down');
            $(this).find('i').removeClass('fa-angle-up');
        }
    );

    // 页面弹出层按钮点击事件监听
    $('a[data-reveal-id]').on('click',function(e) {
        e.preventDefault();
        var modalLocation = $(this).attr('data-reveal-id');
        $('#'+modalLocation).reveal($(this).data());
    });

    // 退出按钮点击事件监听
    $('.layout').click(function () {
        window.location.href = 'login.html';
    });

    (function () {
        winw = $(window).width();
        winh = $(window).height();
        header = $('.header').height();
        menutitle = $('.menu-header').height();
        window.onresize = arguments.callee;
        $('.menu-content').css({'height':winh - header - menutitle});
    })();

    $('.menu-content').mCustomScrollbar();

    // 左侧菜单ajax请求数据
    AjaxData();

});




// 请求左侧菜单接口数据
function AjaxData() {
    $.ajax({
        type: 'get',
        url: 'menu.json',
        dataType: 'json',
        success: function (data) {
            menuRender(data);
        },
        error: function (data) {
            console.log('数据请求失败，错误信息为：'+data);
        }
    })
}


// 渲染左侧菜单
function menuRender(data) {
    var ohtml = '';
    for(var i in data){
        ohtml +='<li><a class="item" href="javascript:;">'+data[i].title+'<i class="fa fa-caret-down"></i></a><dl>'
        var submenu = data[i].submenu;
        for(var j in submenu){
            ohtml +='<dd><a class="subitem" href="#'+submenu[j].page+'">'+submenu[j].name+'</a></dd>'
        }
        ohtml +='</dl></li>';
    }
    $('.vertical-navbar').html(ohtml);
    initPage();
    $('.item,.subitem').unbind('click');
    $('.item').on('click',selectIndex);
    $('.subitem').on('click',subSelectIndex);
}

function initPage() {
    if(location.hash){
        $('.vertical-navbar li .subitem[href = "'+location.hash+'"]').parents('li').addClass('curnavbar');
        $('.vertical-navbar li .subitem[href = "'+location.hash+'"]').parents('dd').addClass("navbar-this");
    }else{
        $('.vertical-navbar li').eq(0).addClass('curnavbar');
        $('.vertical-navbar dd').eq(0).addClass('navbar-this');
    }
    $('.vertical-navbar li.curnavbar').find('i').removeClass('fa-caret-down');
    $('.vertical-navbar li.curnavbar').find('i').addClass('fa-caret-up');
    getPage();
}

// 请求获取右侧子页面数据
function getPage(){
    var code = $('.navbar-this a').attr('href').split('#')[1];
    var opageUrl = 'module/pages/'+ code;
    location.hash = '#' + code;
    $.ajax({
        type: 'get',
        url: opageUrl+'.html',
        dataType: 'html',
        success: function (data) {
            $('.fluid-container').html(data);
        },error: function (data) {
            console.log('页面请求失败，错误信息为：'+data);
        }
    });

}

// 左侧菜单一级点击事件监听
function selectIndex() {
    var oli = $(this).parent('li'),
        oitem = oli.parent().children(),
        type = $(this).parents('ul').attr('navbar-filter');
    if(!type){
        if(oli.is('.curnavbar')){
            $(this).parent('li').removeClass('curnavbar');
            $(this).find('i').addClass('fa-caret-down').removeClass('fa-caret-up');
        }else{
            $(this).parent('li').addClass('curnavbar');
            $(this).find('i').addClass('fa-caret-up').removeClass('fa-caret-down');
        }
    }else{
        $(this).parents('ul').find('li').removeClass('curnavbar');
        $(this).parents('ul').find('i').addClass('fa-caret-down').removeClass('fa-caret-up');
        $(this).parent('li').addClass('curnavbar');
        $(this).find('i').addClass('fa-caret-up').removeClass('fa-caret-down');
    }

}

// 左侧菜单二级点击事件监听
function subSelectIndex() {
    $('.subitem').parents('dl').find('dd').removeClass('navbar-this');
    $(this).parent('dd').addClass('navbar-this');
    getPage();
}

// 锁屏弹出层解锁按钮点击监听事件处理
function unlock() {
    var otxt = $('.decform input').val(),
        onext = $('.decform p'),
        oparents = $('#lockScreen');
    if(otxt == '123456'){
        oparents.trigger('reveal:close');
        $('.decform input').val("");
        onext.text('');
    }else{
        onext.text('解锁密码有误！');
    }
}







