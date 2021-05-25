layui.use(['table','form','laydate'],function(){
    var table = layui.table;
    var laydate = layui.laydate;


    table.render({
        elem: '#userList',
        url: '/user/userList',
        method: 'post',
        title: '用户列表',
        toolbar: '#toobarUser',
        cols: [[
            {field:'auto',type:'numbers', title: '序号', width:'6%',unresize: true, sort: true, align: 'center'},
            {field:'openid',title: 'openid', width:'8%', align: 'center'},
            {field:'nickName',title: '昵称', width:'9%', align: 'center'},
            {field:'avatarUrl',title: '头像', width:'7%', align: 'center',templet:'#headPortraitTpl'},
            {field:'gender',title: '性别', width:'4%', align: 'center',templet:'#genderTpl'},
            {field:'createTime',title: '创建时间', width:'13%', align: 'center'},
            {field:'updateTime',title: '修改时间', width:'13%', align: 'center'},
            {field:'haveUserInfo',title: '信息完善度', width:'8%', align: 'center', templet: '#haveUserInfoTpl'},
            {field:'qqNum',title: 'QQ号', width:'9%', align: 'center'},
            {field:'wechatNum',title: '微信号', width:'13%', align: 'center'},
            {filed:'right', title: '操作', toolbar: '#barUser' ,width:'10%', align: 'center'}
        ]],
        id: 'userTabReload',
        page: true
    });


// 行工具栏监听事件
    table.on('tool(userList)',function(obj){

        let id = obj.data.id;
        let openid = obj.data.openid;
    console.log(openid);
        if(obj.event == 'disableUser'){
            // confirm
            layer.confirm("确定要禁用该用户的发布功能吗？",function(){
                $.ajax({
                    url: '/user/updateHaveUserInfo',
                    type: 'post',
                    data:{'openid':openid,'haveUserInfo':3},
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
                                    table.reload("userTabReload");
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
        } else if(obj.event == 'enableUser'){
            // confirm
            layer.confirm("确定要恢复该用户的发布商品功能吗？",function(){
                $.ajax({
                    url: '/user/updateHaveUserInfo',
                    type: 'post',
                    data:{'openid':openid,'haveUserInfo':2},
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
                                    table.reload("userTabReload");
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

        } else if(obj.event == 'sendMsg'){
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
                content: '/user/toSendMsg?userId=' + id//这里content是一个Url
            })
        }
    });
// 行工具监听事件，end

// 头工具栏监听事件
    table.on('toolbar(userList)',function(obj){
        if(obj.event == 'refresh'){
            table.reload('userTabReload');
        }
    });


// 查询表单
    $("#searchBtn").on('click',function(){
        let openid = $("#openid").val();
        let nickName = $("#nickName").val();
        let qqNum = $("#qqNum").val();
        let wechatNum = $("#wechatNum").val();

        table.reload('userTabReload',{
            type: 'post',
            url: '/user/userList',
            page: {
                curr:1
            },
            where:{
                openid:openid,
                nickName:nickName,
                qqNum:qqNum,
                wechatNum:wechatNum
            }
        });
    });


});