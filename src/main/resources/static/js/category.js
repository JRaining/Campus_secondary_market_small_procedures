layui.use('table',function(){
        var table = layui.table;

        table.render({
            elem: '#type',
            url: '/category/categoryList',
            toolbar: '#toolbarType',
            title: '分类列表',
            cols: [[
                {field:'id',title: 'ID', width:'6%',unresize: true, sort: true, align: 'center'},
                {field:'cateIcon',title: '分类图标', width:'15%', align: 'center',templet:'#cateIconTpl'},
                {field:'cateName',title: '分类名', width:'30%', align: 'center'},
                {field:'sort',title: '排序', width:'6%', align: 'center'},
                {field:'right',title: '操作', toolbar: '#barType', width:'9%', align: 'center'}
            ]],
            id: 'typeTabReload',
            page: true
        });
    // 监听头工具事件
        table.on('toolbar(type)',function(obj){

            if(obj.event == 'addCategory'){
                parent.layer.open({
                    type : 2,
                    title : false,
                    area : [ '450px', '380px' ], //宽高
                    closeBtn : 0, //不显示关闭按钮
                    scrollbar: false, //禁止浏览器出现滚动条
                    resize: false, //禁止拉伸
                    move : false, //禁止拖拽
                    shade : 0.8, //遮罩
                    shadeClose : true, //点击遮罩关闭当前页面
                    content : '/category/toAddCategory', //这里content是一个Url
                });
            } else if(obj.event == 'refresh'){
                table.reload("typeTabReload");
            }
        });
    // 监听行工具事件
        table.on('tool(type)',function(obj){
            var data = obj.data;
            console.log(obj);
            if(obj.event == 'del'){
                layer.confirm('确定删除该分类吗？',function(){
                    $.post(
                        '/category/delCategory',
                        {"id":data.id},
                        function(result){
                            if(result.success){
                                layer.msg(
                                    result.message,
                                    {
                                        icon:1,
                                        time:1000
                                    },
                                    function(){
                                        table.reload("typeTabReload");
                                    }
                                );
                            } else{
                                layer.msg(
                                    result.message,
                                    {
                                        icon:2,
                                        time:2000
                                    }
                                );
                            }
                        }
                    ,'json');
                });
            } else if(obj.event == 'edit'){
                let categoryId = data.id;
            console.log("分类 id :" + categoryId);
                parent.layer.open({
                    type : 2,
                    title : false,
                    area : [ '450px', '380px' ], //宽高
                    closeBtn : 0, //不显示关闭按钮
                    scrollbar: false, //禁止浏览器出现滚动条
                    resize: false, //禁止拉伸
                    move : false, //禁止拖拽
                    shade : 0.8, //遮罩
                    shadeClose : true, //点击遮罩关闭当前页面
                    content : '/category/toUpdateCategory/' + categoryId, //这里content是一个Url
                });
            }
        });
    }
);






