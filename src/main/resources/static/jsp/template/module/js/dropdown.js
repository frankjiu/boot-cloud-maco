// dropdown 自定义下拉菜单
;(function () {
    var onOff = false;    // 开关作用，用于判断下拉菜单展示与收取
    // 遍历渲染页面的下拉菜单默认结构
    $('.dropdown').each(function (index, item) {
        var select = document.createElement('select'),
            i = document.createElement('i'),
            oUl = document.createElement('ul');
        i.className = 'fa fa-caret-down';
        oUl.className = 'dropdown-menu';
        $(item).append(select);
        $(item).append(i);
        $(item).append(oUl);
        $(item).find('select').insertBefore($(item).find('label'));
        var dropUrl = $(item).find('input').attr('url');
        getDropData(dropUrl,this);
    });

    // 请求下拉菜单数据
    function getDropData(dropUrl,currNode) {
        $.ajax({
            type: 'get',
            url: dropUrl,
            success: function (data) {
                render(data,currNode);
            },error: function (data) {
                console.log(data);
            }
        })
    }

    // 渲染下拉菜单结构
    function render(data,currNode) {
        var $select = $(currNode).find('select'),
            $dropdownMenu = $(currNode).find(".dropdown-menu");
        for(var i in data){
            $select.append('<option value="'+data[i].id+'">'+data[i].content+'</option>');
            $dropdownMenu.append('<li drop-value="'+data[i].id+'">'+data[i].content+'</li>');
        }
        $dropdownMenu.find('li').eq(0).addClass('drop-this');
        $dropdownMenu.find('li').on('click',clickDropMenu);
        getValue($dropdownMenu);
    }

    // 下拉输入框获取下拉菜单当前值
    function getValue(obj) {
        var curValue = obj.find('.drop-this').text(),
            curData = obj.find('.drop-this').attr('drop-value');
        obj.siblings('input').val(curData);
    }

    // 当前下拉菜单点击事件监听
    function clickDropMenu() {
        var that = $(this).parents('.dropdown'),
            curValue = $(this).text(),
            curData = $(this).attr('drop-value');
        $(this).addClass('drop-this').siblings().removeClass('drop-this');
        that.removeClass('selectedDropdown');
        that.find('input').val(curData);
        that.find('input').attr('name',curValue);

        arrowState(that);
        onOff = false;
    }



    // 下拉框的点击事件
    $('.dropdown input,.dropdown i').on('click',function () {
        $('.dropdown').removeClass('selectedDropdown');
        var that = $(this).parent('.dropdown');
        var oNextUl = $(this).parent('.dropdown').find('ul');
        $('.dropdown i').attr('class','fa fa-caret-down');
        if(oNextUl.children().length > 0){
            onOff = true;
            this.parentNode.className = onOff ? 'dropdown selectedDropdown' : 'dropdown';
        }else{
            $('.dropdown i').attr('class','fa fa-caret-down');
            onOff = false;
        }
        arrowState(that);
    });

    // 下拉箭头状态
    function arrowState(obj) {
        var oclass = obj.hasClass('selectedDropdown')
        if(oclass){
            obj.children('i').attr('class','fa fa-caret-up');
        }else{
            obj.children('i').attr('class','fa fa-caret-down');
        }
    }

    // 点击页面空白处隐藏下拉菜单
    $('body').on('click',function (ev) {
        if($(ev.target).parents('.dropdown').length == 0){
            $('.dropdown').removeClass('selectedDropdown');
            $('.dropdown i').attr('class','fa fa-caret-down');
            onOff = false;
        }
    });

})();