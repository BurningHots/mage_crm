function openTab(text, url, iconCls){
    if($("#tabs").tabs("exists",text)){
        $("#tabs").tabs("select",text);
    }else{
        var content="<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='" + url + "'></iframe>";
        $("#tabs").tabs("add",{
            title:text,
            iconCls:iconCls,
            closable:true,
            content:content
        });
    }
}

// 用户退出
function logout(){
    $.messager.confirm("Crm系统","你确定退出当前系统",function (r) {
        if (r){
            setTimeout(function(){
                $.removeCookie("userName");
                $.removeCookie("trueName");
                window.location.href = "index";
            },200)
        }
    });
}

// 打开修改对话框
function openPasswordModifyDialog(){
    $("#dlg").dialog("open");
}

// 关闭修改对话框
function closePasswordModifyDialog(){
    $("#dlg").dialog("close");
}

// 绑定修改对话框中的保存点击按钮
function modifyPassword() {
    $("#fm").form("submit",{
        url:ctx+"/user/updatePwd",
        onSubmit:function () {
            return $("#fm").form("validate");
        },
        success:function (data) {
            // 将返回值转为JSON格式
            data = JSON.parse(data);
            if (data.code==200){
                $.messager.alert("crm系统","修改密码成功，两秒后退出系统","info");
                setTimeout(function (){
                    $.removeCookie("userName");
                    $.removeCookie("trueName");
                    $.removeCookie("id");
                    window.location.href = "index";
                },2000);
            }else{
                $.messager.alert("crm系统",data.msg,"info");
            }
        }
    });
}