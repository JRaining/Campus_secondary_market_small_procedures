layui.use(['table','laydate'],function(){
    var table = layui.table;
    var form = layui.form;
    var laydate = layui.laydate;
    // 日期选择范围
    laydate.render({
        elem: '#dateRange',
        range: true
    });
    table.render({
        elem: '#list',
        url: '/commodity/commodityList',
        method: 'post',
        title: '商品列表',
        toolbar: '#toolbarList',
        cols: [[
            {field:'auto',type:'numbers', title: '序号', width:'6%',unresize: true, sort: true, align: 'center'},
            {field:'title',title: '商品', width:'35%', align: 'center',event:'toCommodityDetail'},
            {field:'category',title: '分类', width:'7%', align: 'center',templet:'#categoryTpl'},
            {field:'oldPrice',title: '原价', width:'5%', align: 'center'},
            {field:'price',title: '现价', width:'5%', align: 'center'},
            {field:'repertory',title: '库存', width:'5%', align: 'center'},
            {field:'user',title: '发布者', width:'8%', align: 'center',templet:'#userTpl'},
            {field:'publishDate',title: '发布时间', width:'14%', align: 'center'},
            {field:'state',title: '状态', width:'6%', align: 'center', templet:'#stateTpl'},
            {field:'right',title: '操作', toolbar: '#barInline', width:'9%', align: 'center'}
        ]],
        id: 'listTabReload',
        page: true
    });
    // 头部工具栏事件
    table.on('toolbar(list)',function(obj){

        // 刷新表格信息
        if(obj.event == 'refresh'){
            table.reload("listTabReload");
        console.log("刷新");
        } else if(obj.event = 'addCommodity'){
            layer.msg("添加商品");
            parent.layer.open({
                type : 2,
                title : false,
                area : [ '450px', '700px' ], //宽高
                closeBtn : 0, //不显示关闭按钮
                scrollbar: true, //禁止浏览器出现滚动条
                resize: false, //禁止拉伸
                move : false, //禁止拖拽
                shade : 0.5, //遮罩
                shadeClose : true, //点击遮罩关闭当前页面
                content : '/commodity/toAddCommodity' //这里content是一个Url
            });
        }

    });
// 单元格监听事件，显示商品详情
    table.on('tool(list)',function(obj){
        // 获取当前点击的商品的 id
        let commodityId = obj.data.id;
    console.log(obj.data);
    console.log(commodityId);
        let title = obj.data.title;
        // 当前商品的发布者的 id
        let userId = obj.data.user.id;
        if(obj.event == 'toCommodityDetail'){
            parent.layer.open({
                type : 2,
                title : false,
                area : [ '1000px', '700px' ], //宽高
                closeBtn : 0, //不显示关闭按钮
                scrollbar: true, //禁止浏览器出现滚动条
                resize: false, //禁止拉伸
                move : false, //禁止拖拽
                shade : 0.5, //遮罩
                shadeClose : true, //点击遮罩关闭当前页面
                content : '/commodity/toCommodityDetail?id=' + commodityId, //这里content是一个Url
            });
        } else if(obj.event == 'del'){
            layer.confirm("确定要下架该商品吗？",function(){
                $.ajax({
                    type: 'post',
                    url:'/commodity/delCommodity',
                    data:{"commodityId":commodityId,"title":title,"userId":userId},
                    success:function(result){
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
                                    table.reload("listTabReload");
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
        }
    });

// 条件查询商品所用表单
// 搜索表单中,商品状态和商品分类下拉框数据获取
    $.ajax({
        type: 'get',
        url: '/category/getAllCategory',
        dataType: 'json',
        success:function(data){
            $.each(data,function(index,value){
                if(value.id != 1){
                    $("#categoryId").append(new Option(value.cateName,value.id));
                }
            });
            form.render('select');
        }
    });

    // 事件组
    var active = {
        reload:function(){

        }
    }

// 点击提交按钮，执行重载事件
    $('#searchBtn').on('click',function(){
        var title = $("#title").val();
        var serial = $("#serial").val();
        var state = $("#state").val();
        var categoryId = $("#categoryId").val();
        // 日期范围
        let dateRange = $("#dateRange").val();
        let startTime = dateRange.substring(0,10);
        let endTime = dateRange.substring(12,23);

        // 表格数据重载
        table.reload('listTabReload',{
            type: 'post',
            url: '/commodity/commodityList',
            page:{
                curr: 1
            },
            where:{
                title: title,
                serial: serial,
                state: state,
                categoryId: categoryId,
                startTime: startTime,
                endTime: endTime
            }
        })
    });


// 结束
});

