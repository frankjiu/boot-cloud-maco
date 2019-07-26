
;(function ($) {

    $('.tab').each(function (index,item) {
        $(item).find('li').eq(0).addClass('tab-this');
        var type = $(item).attr('tab-filter'),
            $curitemLi = $(item).find('.tab-this');
        if(type == 'concise'){
            $curitemLi.css({'border-bottom':'2px solid #1AA094'});
        }else{
            $curitemLi.css({'border':'1px solid #e6e6e6','border-radius':'3px 3px 0 0','border-bottom-color':'#fff'})
        }
    });

    $.fn.myTab = function () {
        //这里的this 指的是 jquery的一个数组 谁调用就是谁
        return this.each(function(){
            var type = $(this).attr('tab-filter');
            var $navLis = $(this).find(".tab-title li"); //获取导航菜单的li数组
            var $navClose = $(this).find(".tab-title li i");
            var $conLis = $(this).find(".tab-content .tab-item");  //获取导航菜单的li对应显示的模块
            //初始化 下面这三行可以封装成一个方法
            $navLis.eq(0).addClass("tab-this");
            $conLis.hide();
            $conLis.eq(0).show();

            $navLis.on('click',function(){
                $navLis.removeClass('tab-this');
                $(this).addClass('tab-this');
                var ind=$(this).index();
                $conLis.hide();
                $conLis.eq(ind).show();
                if(type == 'concise'){
                    $(this).css({'border-bottom':'2px solid #1AA094'});
                    $(this).siblings().css('border-color','transparent');
                }else{
                    $(this).css({'border':'1px solid #e6e6e6','border-radius':'3px 3px 0 0','border-bottom-color':'#fff'})
                    $(this).siblings().css('border-color','transparent');
                }

            });
        });
    }

})(jQuery);

// tab调用
$('.tab').myTab();
