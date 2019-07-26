// 日历插件
function laydate(options) {
    $('.laydate').remove();
    // 获取input元素的距左、巨顶部距离
    var left = options.ele.offsetLeft,
        top = options.ele.offsetHeight + options.ele.offsetTop;
    var laydate = document.createElement('div');
        laydate.className = 'laydate';
        laydate.style.left = left + 'px';
        laydate.style.top = top + 'px';
    var laydateMain = document.createElement('div');
        laydateMain.className = 'laydate-main';
    // 监听窗口大小
    window.onresize = function () {
        left = options.ele.offsetLeft;
        top = options.ele.offsetHeight + options.ele.offsetTop;
        laydate.style.left = left + 'px';
        laydate.style.top = top + 'px';
    }
    // 获取当前年、月、日
    var today,thisyear,thismonth;
    var oheader = '',     // 日历头部
        ocontent = '',    // table表格
        ofooter = '';      // 日历尾部

    // 获取焦点输入框的值
    var ovalue = options.ele.value;
    if(ovalue.length > 0){
        today = parseInt(ovalue.split('-')[2]);
        thisyear = parseInt(ovalue.split('-')[0]);
        thismonth = parseInt(ovalue.split('-')[1]);
    }else{
        today = new Date().getDate();
        thisyear =  new Date().getFullYear();
        thismonth =  new Date().getMonth()+1;
    }
    getcalendar(thisyear,thismonth);
    // 头部内容
    oheader += '<div class="laydate-header">\n'+
                    '<i id="prevyear" class="fa fa-angle-double-left"></i>\n'+
                    '<i id="prevmonth" class="fa fa-angle-left"></i>\n'+
                    '<div class="laydate-set-ym">\n'+
                        '<span id="year" lay-type="year">'+thisyear+'年</span>\n'+
                        '<span id="month" lay-type="month">'+thismonth+'月</span>\n'+
                    '</div>\n'+
                    '<i id="nextmonth" class="fa fa-angle-right"></i>\n'+
                    '<i id="nextyear" class="fa fa-angle-double-right"></i>\n'+
                '</div>';

    // 日历主体内容
    ocontent += '<div class="laydate-content"></div>';

    // 底部内容
    ofooter += '<div class="laydate-footer">\n'+
                    '<span id="deter" class="laydate-comfirm">确定</span>\n'+
                    '<span id="now" class="laydate-now">现在</span>\n'+
                    '<span id="clear" class="laydate-clear">清空</span>\n'+
                '</div>';

    // 创建table表格
    var otable = document.createElement('table');
    otable.id = 'table';

    // 生成日历函数
    function getcalendar(yy,mm) {
        // 日历星期表头
        var ostr = '<tr><th>日</th><th>一</th><th>二</th><th>三</th><th>四</th><th>五</th><th>六</th></tr>';
        var last = new Date(yy,mm-1,0),     //获取上个月份的时间对象
            lastdate = last.getDate(),      //获取上个月的最大日期
            lastday = last.getDay(),        //获取上个月最大日期的星期几
            months = new Date(yy,mm,0),
            Maxdate = months.getDate(),    //获取这个月最大的日期
        //获取上个月在这个月份中存在的最后日期
            remainlastdate = lastdate - lastday,
            t = 1;
        ostr +='<tr>';
        for(var g = 0;g <= 6;g++){
            if(g <= lastday){
                ostr += '<td class="laydate-day-prev" lay-ym="'+thisyear+'-'+(thismonth-1)+'-'+remainlastdate+'">'+(remainlastdate++)+'</td>';
            }else{
                if(thisyear == yy && thismonth == mm && today == t){
                    ostr +='<td class="lay-this" lay-ym="'+thisyear+'-'+thismonth+'-'+t+'">'+(t++)+'</td>';
                }else{
                    //该月份的普通日期
                    ostr +='<td lay-ym="'+thisyear+'-'+thismonth+'-'+t+'">'+(t++)+'</td>';
                }
            }
        }
        ostr +='</tr>';
        var remainlastday = 6 - lastday + 1;   //除去上面的部分，这个月从哪里开始
        var nextMonthday = 1;   //下一个月份的开始日期
        for(var i=1;i<=5;i++){
            ostr += '<tr>';
            for(var j=0;j<7;j++){
                if(remainlastday <= Maxdate){
                    if(thisyear == yy && thismonth == mm && today == remainlastday){
                        ostr +='<td class="lay-this" lay-ym="'+thisyear+'-'+thismonth+'-'+remainlastday+'">'+ remainlastday++ +'</td>';
                    }else{
                        ostr +='<td lay-ym="'+thisyear+'-'+thismonth+'-'+remainlastday+'">'+ remainlastday++ +'</td>';
                    }
                }else{
                    ostr +='<td class="laydate-day-next" lay-ym="'+thisyear+'-'+(thismonth+1)+'-'+nextMonthday+'">'+(nextMonthday++)+'</td>';
                }
            }
            ostr += '</tr>';
        }
        return ostr;
    }

    // 将生成html结构添加至页面中
    $('.fluid-container').append(laydate);
    $('.laydate').append(laydateMain);
    $('.laydate-main').html(oheader+ocontent+ofooter);
    $('.laydate-content').append(otable);
    $('.laydate #table').html(getcalendar(thisyear,thismonth));


    // 日历的事件部分
    (function () {
        var prevYear = document.getElementById('prevyear'),
            prevMonth = document.getElementById('prevmonth'),
            nextMonth = document.getElementById('nextmonth'),
            nextYear = document.getElementById('nextyear'),
            syear = document.getElementById('year'),
            smonth = document.getElementById('month'),
            clear = document.getElementById('clear'),
            now = document.getElementById('now'),
            deter = document.getElementById('deter');
        var layAttr;
        // 上一年按钮点击监听事件
        prevYear.onclick = function () {
            thisyear--;
            arrowchange();
        }
        // 下一年按钮点击监听事件
        nextYear.onclick = function () {
            thisyear++;
            arrowchange();
        }
        // 上一月按钮点击监听事件
        prevMonth.onclick = function () {
            thismonth = thismonth < 1 ? 12 : --thismonth;
            if(thismonth < 1){
                thismonth = 12;
                thisyear--;
            }
            arrowchange();
        }
        // 下一月按钮点击监听事件
        nextMonth.onclick = function () {
            thismonth = thismonth > 12 ? 1 : ++thismonth;
            if(thismonth > 12){
                thismonth = 1;
                thisyear++;
            }
            arrowchange();
        }

        // 左右键公共函数
        function arrowchange() {
            syear.innerHTML = thisyear + '年';
            smonth.innerHTML = thismonth + '月';
            $('.laydate #table').html(getcalendar(thisyear,thismonth));
        }

        // 清空按钮点击监听事件
        clear.onclick = function () {
            btnchangeDate();
            options.ele.value = '';
        }
        // 现在按钮点击监听事件
        now.onclick = function () {
            btnchangeDate();
            var nowDay = new Date().getDate(),
                nowYear = new Date().getFullYear(),
                nowMonth = new Date().getMonth() + 1;
            nowMonth = nowMonth < 10 ? '0' + nowMonth : nowMonth;
            nowDay = nowDay < 10  ? '0' + nowDay : nowDay;
            options.ele.value = nowYear +'-'+ nowMonth +'-'+ nowDay;
        }
        // 确定按钮点击监听事件
        deter.onclick = function () {
            layAttr = $('.lay-this').attr('lay-ym').split('-');
            for(var i = 0; i < layAttr.length;i++){
                layAttr[i] = layAttr[i] < 10 ? '0' + layAttr[i] : layAttr[i];
            }
            options.ele.value = layAttr.join('-');
            laydate.style.display = 'none';
        }

        // 底部按钮组公共函数
        function btnchangeDate() {
            thisyear = new Date().getFullYear();
            thismonth = new Date().getMonth() + 1;
            syear.innerHTML = thisyear + '年';
            smonth.innerHTML = thismonth + '月';
            $('.laydate #table').html(getcalendar(thisyear,thismonth));
            layAttr = $('.lay-this').attr('lay-ym').split('-');
            for(var i = 0; i < layAttr.length;i++){
                layAttr[i] = layAttr[i] < 10  ? '0' + layAttr[i] : layAttr[i];
            }
            options.ele.value = layAttr.join('-');
            laydate.style.display = 'none';
        }

        // 单元格点击监听事件
        $('.laydate #table').click(function (ev) {
            if(ev.target.tagName.toLowerCase() === 'td'){
                $(ev.target).parents('#table').find('td').removeClass('lay-this');
                $(ev.target).addClass('lay-this');
                layAttr = $(ev.target).attr('lay-ym').split('-');
                for(var i = 0; i < layAttr.length;i++){
                    layAttr[i] = layAttr[i] < 10  ? '0' + layAttr[i] : layAttr[i];
                }
                options.ele.value = layAttr.join('-');
                laydate.style.display = 'none';
            };
        });
    })();

    // 点击页面空白处隐藏日历
    $('body').on('click',function (ev) {
        if($(ev.target).parents('.laydate').length === 0){
            if(ev.target.id === options.ele.id){
                $('.laydate').show();
            }else{
                $('.laydate').hide();
            }
        }
    });

};







