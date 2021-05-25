layui.use(['table','form','laydate'],function(){
    var table = layui.table;
    var laydate = layui.laydate;


    table.render({
        elem: '#seekList',
        url: '/seek/seekList',
        method: 'post',
        title: '求购列表',
        toolbar: '#toobarSeek',
        cols: [[
            {field:'auto',type:'numbers', title: '序号', width:'6%',unresize: true, sort: true, align: 'center'},
            {field:'title',title: '求购标题', width:'26%', align: 'center',event:'edit'},
            {field:'remark',title: '备注', width:'25%', align: 'center'},
            {field:'price',title: '价格', width:'10%', align: 'center', templet: '#priceTpl'},
            {field:'user',title: '发布者', width:'9%', align: 'center',templet:'#userTpl'},
            {field:'publishDate',title: '发布时间', width:'13%', align: 'center'},
            {filed:'right', title: '操作', toolbar: '#barSeek' ,width:'11%', align: 'center'}
        ]],
        id: 'seekTabReload',
        page: true
    });


// 行工具栏监听事件
    table.on('tool(seekList)',function(obj){

        // 求购信息
        let seekId = obj.data.id;
        let title = obj.data.title;
        // 求购发布者 id
        let userId = obj.data.user.id;

       if(obj.event == 'del'){
           // confirm
           layer.confirm("确定要删除这条求购信息吗？",function(){
               $.ajax({
                   url: '/seek/delSeek',
                   type: 'post',
                   data:{'seekId':seekId,'title':title,'userId':userId},
                   success: function(result){
                       if(result.success){
                           layer.msg(
                               result.message,
                               {
                                   icon:1,
                                   time:1000
                               },
                               function(){
                                   // 先得到当前 iframe 层的索引
                                   var index = parent.layer.getFrameIndex(window.name);
                                   // 关闭当前 iframe 层
                                   parent.layer.close(index);
                                   table.reload("seekTabReload");
                               }
                           );
                       } else{
                           layer.msg(
                               result.message,
                               {
                                   icon:2,
                                   time:1000
                               }
                           );
                       }
                   },
                   error: function(data){
                       layer.msg("网络错误！");
                   }
               });
           });
           // confirm
       } else if(obj.event == 'edit'){
           parent.layer.open({
               type: 2,
               title: false,
               area: ['450px', '420px'], //宽高
               closeBtn: 0, //不显示关闭按钮
               scrollbar: true, //禁止浏览器出现滚动条
               resize: false, //禁止拉伸
               move: false, //禁止拖拽
               shade: 0.5, //遮罩
               shadeClose: true, //点击遮罩关闭当前页面
               content: '/seek/toEditSeek/' + seekId //这里content是一个Url
           })
       }
    });
// 行工具监听事件，end

// 头工具栏监听事件
    table.on('toolbar(seekList)',function(obj){
        if(obj.event == 'refresh'){
            table.reload('seekTabReload');
        } else if(obj.event == 'addSeek'){
            parent.layer.open({
                type : 2,
                title : false,
                area : [ '450px', '420px' ], //宽高
                closeBtn : 0, //不显示关闭按钮
                scrollbar: true, //禁止浏览器出现滚动条
                resize: false, //禁止拉伸
                move : false, //禁止拖拽
                shade : 0.5, //遮罩
                shadeClose : true, //点击遮罩关闭当前页面
                content : '/seek/toAddSeek' //这里content是一个Url
            });
        }
    });
// 头工具栏监听事件，end


//  日期范围选择
    laydate.render({
        elem: '#dateRange',
        range: true
    });
// 查询表单
    $("#searchBtn").on('click',function(){
        let title = $("#title").val();
        // 日期范围
        let dateRange = $("#dateRange").val();
        let startTime = dateRange.substring(0,10);
        let endTime = dateRange.substring(12,23);

        table.reload('seekTabReload',{
            type: 'post',
            url: '/seek/seekList',
            page: {
                curr:1
            },
            where:{
                title:title,
                startTime:startTime,
                endTime:endTime
            }
        });
    });


});