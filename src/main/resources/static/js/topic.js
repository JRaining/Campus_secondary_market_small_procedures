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
        elem: '#topicList',
        url: '/topic/topicList',
        method: 'post',
        title: '帖子列表',
        toolbar: '#toolbarList',
        cols: [[
            {field:'auto',type:'numbers', title: '序号', width:'6%',unresize: true, sort: true, align: 'center'},
            {field:'theme',title: '帖子主题', width:'25%', align: 'center',templet:'#themeTpl',event:'toTopicDetail'},
            {field:'description',title: '帖子描述', width:'26%', align: 'center'},
            {field:'user',title: '发布者', width:'8%', align: 'center',templet:'#userTpl'},
            {field:'clickCount',title: '点击率', width:'6%', align: 'center'},
            {field:'commentCount',title: '评论数', width:'6%', align: 'center'},
            {field:'publishDate',title: '发布时间', width:'14%', align: 'center'},
            {field:'right',title: '操作', toolbar: '#barInline', width:'9%', align: 'center'}
        ]],
        id: 'topicListTabReload',
        page: true
    });
// 头部工具栏事件
    table.on('toolbar(topicList)',function(obj){

        // 刷新表格信息
        if(obj.event == 'refresh'){
            table.reload("topicListTabReload");
        } else if(obj.event = 'addTopic'){
            parent.layer.open({
                type : 2,
                title : false,
                area : [ '450px', '500px' ], //宽高
                closeBtn : 0, //不显示关闭按钮
                scrollbar: true, //禁止浏览器出现滚动条
                resize: false, //禁止拉伸
                move : false, //禁止拖拽
                shade : 0.5, //遮罩
                shadeClose : true, //点击遮罩关闭当前页面
                content : '/topic/toAddTopic' //这里content是一个Url
            });
        }

    });

// 单元格/行内操作 监听事件，显示商品详情
    table.on('tool(topicList)',function(obj){
        // 获取当前点击的商品的 id
        let topicId = obj.data.id;
        let theme = obj.data.theme;
        // 当前商品的发布者的 id
        let userId = obj.data.user.id;
        if(obj.event == 'toTopicDetail'){
            parent.layer.open({
                type : 2,
                title : false,
                area : [ '370px', '700px' ], //宽高
                closeBtn : 0, //不显示关闭按钮
                scrollbar: true, //禁止浏览器出现滚动条
                resize: false, //禁止拉伸
                move : false, //禁止拖拽
                shade : 0.5, //遮罩
                shadeClose : true, //点击遮罩关闭当前页面
                content : '/topic/toTopicDetail?id=' + topicId, //这里content是一个Url
            });
        } else if(obj.event == 'del'){
            layer.confirm("确定要删除该帖子吗？",function(){
                $.ajax({
                    type: 'post',
                    url:'/topic/delTopic',
                    data:{"topicId":topicId,"theme":theme,"userId":userId},
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
                                    table.reload("topicListTabReload");
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

// 点击提交按钮，执行重载事件
    $('#searchBtn').on('click',function(){
        var theme = $("#theme").val();
        var description = $("#description").val();
        var state = $("#state").val();
        // 日期范围
        let dateRange = $("#dateRange").val();
        let startTime = dateRange.substring(0,10);
        let endTime = dateRange.substring(12,23);

        // 表格数据重载
        table.reload('topicListTabReload',{
            type: 'post',
            url: '/topic/topicList',
            page:{
                curr: 1
            },
            where:{
                theme: theme,
                description: description,
                state: state,
                startTime: startTime,
                endTime: endTime
            }
        })
    });


// 结束
});

