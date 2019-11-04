function searchCustomer() {
    $("#dg").datagrid("load",{
        khno:$("#s_khno").val(),
        customerName:$("#s_name").val()
    })
}
// 绑定添加按钮
function openCustomerAddDialog(){
    $("#fm").form("clear");
    $("#dlg").dialog("open").dialog("setTitle","添加客户信息");
}

// 绑定取消按钮点击事件
function closeCustomerDialog() {
    $("#dlg").dialog("close");
}

// 绑定保存按钮点击事件
function saveOrUpdateCustomer() {
    var id = $("#id").val();
    var url = ctx+"/customer/update";
    if(isEmpty(id)){
        url = ctx+"/customer/insert";
    }
    $("#fm").form("submit",{
        url:url,
        onSubmit:function () {
            return $("#fm").form("validate");
        },
        success:function(data){
            data=JSON.parse(data);
            if (data.code==200){
                $.messager.alert("添加客户信息",data.msg,"info");
                // 刷新数据表格
                searchCustomer();
                // 关闭添加对话框
                closeCustomerDialog();
            }else{
                $.messager.alert("添加客户信息",data.msg,"error");
            }
        }
    });
}

// 绑定修改按钮点击事件
function openCustomerModifyDialog(){
    var rows = $("#dg").datagrid("getSelections");
    if (rows.length==0){
        $.messager.alert("修改客户信息","请选择一条记录","error");
        return;
    }
    if (rows.length>1){
        $.messager.alert("修改客户信息","只能选择一条记录","error");
        return;
    }

    $("#fm").form("load",rows[0]);
    $("#dlg").dialog("open").dialog("setTitle","修改客户信息")
}

// 绑定删除按钮点击事件
function deleteCustomer() {
    var rows = $("#dg").datagrid("getSelections");
    if (rows.length==0){
        $.messager.alert("删除客户信息","请至少选择一条记录","error");
        return;
    }
    var params = "id=";
    for (var i =0;i<rows.length;i++){
        if (i<rows.length-1){
            params = params + rows[i].id + "&";
        }else {
            params = params + rows[i].id;
        }
    }
    $.messager.confirm("删除客户信息","您确定要删除选中的客户信息",function (r) {
        if (r){
            $.ajax({
                url:ctx+"/customer/delete",
                data:params,
                dataType:'json',
                success:function (data) {
                    if (data.code==200){
                        $.messager.alert("删除客户信息",data.msg,"info");
                        // 刷新数据表格
                        searchCustomer();
                    }else {
                        $.messager.alert("删除客户信息",data.msg,"error");
                    }
                }
            });
        }
    });

}

// 绑定历史订单查看按钮点击事件
function  openCustomerOtherInfo(title,type) {
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("查看历史订单","请先选中一个客户","error");
        return;
    }
    if(rows.length>1){
        $.messager.alert("查看历史订单","只能选中一个客户","error");
        return;
    }
    window.parent.openTab(title,ctx+"/customer/openCustomerOtherInfo/"+type+"/"+rows[0].id);
}