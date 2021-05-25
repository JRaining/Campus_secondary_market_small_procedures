layui.use(['table','form'],function(){
    var table = layui.table;


    table.render({
        elem: '#informList',
        url: '/inform/informList',
        method: 'post',
        title: '举报列表',
        toolbar: '#toobarInform',
        cols: [[
            {field:'auto',type:'numbers', title: '序号', width:'6%',unresize: true, sort: true, align: 'center'},
            {field:'',title: '商品/求购', width:'7%', align: 'center',templet:'#commodityOrSeekTpl'},
            {field:'',title: '举报', width:'26%', align: 'center',templet:'#commodityTpl'},
            {field:'content',title: '举报信息', width:'26%', align: 'center'},
            {field:'user',title: '举报者', width:'9%', align: 'center',templet:'#userTpl'},
            {field:'createTime',title: '举报时间', width:'13%', align: 'center'},
            {filed:'right', title: '操作', toolbar: '#barInform' ,width:'13%', align: 'center'}
        ]],
        id: 'informTabReload',
        page: true
    });


// 行工具栏监听事件
    table.on('tool(informList)',function(obj){
        let id = obj.data.id;   // 举报信息的 id
        let commodity = obj.data.commodity;
        let seek = obj.data.seek;
        let topic = obj.data.topic;
        if(obj.event == 'edit'){
            if(commodity != null){
                let commodityId = commodity.id; // 获取商品 id
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
            } else if(seek != null){
                let seekId = seek.id;   // 获取求购 id
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
            } else if(topic != null){
                let topicId = topic.id;
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
            }
        } else if(obj.event == 'ok'){
            $.ajax({
                type: 'post',
                url: '/inform/updateInform',
                data:{'id':id},
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
                                table.reload("informTabReload");
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
        }
    });
// 行工具监听事件，end

// 头工具栏监听事件
    table.on('toolbar(informList)',function(obj){
        if(obj.event == 'refresh'){
            table.reload('informTabReload');
        }
    });
// 头工具栏监听事件，end



});