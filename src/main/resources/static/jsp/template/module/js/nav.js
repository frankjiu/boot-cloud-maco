(function ($) {
    var ospan = $('<span class="cust-navbar"></span>');
    ospan.css({'left':'20px','width':'0px','opacity':'0','filter':'alpha(opacity=0)'});
    $('.horizontal-navbar').append(ospan);
    // 水平导航条
    $.fn.navhal = function(){
        var $oli = $(this).find('li');
        var $odl = $(this).find('dl');
        $oli.each(function (i) {
            $(this).hover(function () {
                var thisW = $(this).width(),
                    oleft = $(this).position().left.toFixed(0);
                $(this).siblings('.cust-navbar').css({
                    'left':oleft+'px',
                    'width':thisW+'px',
                    'opacity':'1',
                    'filter':'alpha(opacity=100)'
                });
                $(this).css({'z-index':10});
                $(this).children('dl').show();
                $(this).find('i').removeClass('fa-caret-down');
                $(this).find('i').addClass('fa-caret-up');
            },function () {
                $(this).siblings('.cust-navbar').css({
                    'left':'20px',
                    'width':'0px',
                    'opacity':'0',
                    'filter':'alpha(opacity=0)'
                });
                $(this).css({'z-index':0});
                $(this).children('dl').hide();
                $(this).find('i').removeClass('fa-caret-up');
                $(this).find('i').addClass('fa-caret-down');
            });

            $(this).click(function () {
                $(this).addClass('active').siblings().removeClass('active');
            });
        });

        $odl.each(function (i,item) {
            $(item).find('dd').eq(0).addClass('navbar-this');
        })
        $('.horizontal-navbar dd').click(function () {
            $(this).addClass('navbar-this').siblings().removeClass('navbar-this');
        });
    };

    // 垂直导航条
    $.fn.navval = function () {
        var $this = $(this),
            $oli = $(this).find('li'),
            $odd = $(this).find('dd');
        $this.each(function (i,item) {
            var $itemli = $(item).find('li');
            $itemli.eq(0).addClass('curnavbar');
            $itemli.eq(0).find('i').removeClass('fa-caret-down').addClass('fa-caret-up');
        });
        $oli.click(function (ev) {
            var types = $(this).parent('ul').attr('navbar-filter'),
                $oi = $(this).find('i');
            var tagName = $(ev.target).parent()[0].tagName;
            if(!types){
                $(this).toggleClass('curnavbar');
                if(tagName.toLowerCase() == 'dd'){
                    $(this).addClass('curnavbar');
                }
                if(!this.className){
                    $oi.attr('class','fa fa-caret-down');
                }else{
                    $oi.attr('class','fa fa-caret-up');
                }
            }else{
                $(this).addClass('curnavbar').siblings().removeClass('curnavbar');
                $(this).parent('ul').find('i').addClass('fa-caret-down').removeClass('fa-caret-up');
                $(this).find('i').addClass('fa-caret-up').removeClass('fa-caret-down');
            }
        });
        $odd.click(function () {
            $(this).parents('ul').find('dd').removeClass('navbar-this');
            $(this).addClass('navbar-this').siblings().removeClass('navbar-this');
        });
    }
})(jQuery);

$('.horizontal-navbar').navhal();
$('.vertical-navbar').navval();

