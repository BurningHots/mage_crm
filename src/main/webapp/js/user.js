function searchUsers(){
    $("#dg").datagrid("load",{
        userName:$("#userName").val(),
        trueName:$("#trueName").val(),
        phone:$("#phone").val(),
        email:$("#email").val()
    });
}

function openAddUserDialog(){
    $("#dlg").dialog("open").dialog("setTitle","添加用户信息");
    $("#fm").form("clear");
}

function closeDialog(){
    $("#dlg").dialog("close");
}

function openModifyUserDialog() {
    var rows = $("#dg").datagrid("getSelections");
    if (rows.length==0){
        $.messager.alert("修改用户信息","请至少选中一条用户信息","error");
        return;
    }
    if (rows.length>1){
        $.messager.alert("修改用户信息","只能选中一条用户信息","error");
        return;
    }
    $("#fm").form("load",rows[0]);
    $("#dlg").dialog("open").dialog("setTitle","修改用户信息");
}

function saveOrUpdateUser() {
    var id = $("#id").val();
    var url = ctx+"/user/update";
    if (isEmpty(id)){
        url = ctx+"/user/insert";
    }
    $("#fm").form("submit",{
        url:url,
        onSubmit:function(){
            return $("#fm").form("validate");
        },
        success:function(data){
            data = JSON.parse(data)
            if (isEmpty(id)){
                $.messager.alert("添加用户信息",data.msg,"info");
            }else {
                $.messager.alert("添加用户信息",data.msg,"info");
            }
            data=JSON.parse(data);
            if (data.code == 200){
                searchUsers();
                closeDialog();
            }
    }
    });
}

function deleteUser(){
    var rows = $("#dg").datagrid("getSelections");
    if (rows.length==0){
        $.messager.alert("删除用户信息","请至少选中一条用户信息","error");
        return;
    }
    if (rows.length>1){
        $.messager.alert("删除用户信息","只能选中一条用户信息","error");
        return;
    }
    $.messager.confirm("删除用户信息","确定是否删除选中信息",function (r) {
        if (r){
            $.ajax({
                type:'post',
                url:ctx+"/user/delete",
                data:'id='+rows[0].id,
                dataType:'json',
                success:function(data){
                    $.messager.alert("删除用户信息",data.msg,"info");
                    if (data.code==200){
                        closeDialog();
                        searchUsers();
                    }
                }
            });
        }
    });
}
