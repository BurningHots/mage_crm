function formatterState(val){
    if (val==0){
        return "未分配";
    }else  if (val == 1){
        return "已分配";
    }else {
        return "未定义";
    }
}
// 网页初始化操作
$(function () {
    searchSaleChances();
});

// 查询方法
function searchSaleChances() {
    $("#dg").datagrid("load",{
        createMan:$("#createMan").val(),
        customerName:$("#customerName").val(),
        createDate:$("#createDate").datebox("getValue"),
        state:$("#state").combobox("getValue")
    });
}

// 打开添加对话框
function openAddAccountDialog(){
    // 设置对话框标题
    $("#dlg").dialog("setTiTle","添加营销机会记录");
    // 清除表单数据
    $("#fm").dialog("clear");
    $("#dlg").dialog("open");
}

// 绑定取消按钮点击事件
function closeDialog(){
    $("#dlg").dialog("close");
}

// 绑定保存按钮点击事件
function saveAccount(){
    // 根据否是拥有id，判断url的取值
    var id = $("#id").val();
    var url = ctx+"/saleChance/add";
    if(isEmpty(id)){
        url = ctx +"/saleChance/updateSaleChance"
    }
    $("#fm").form("submit",{
        url:url,
        onSubmit:function(params){
            params.createMan = $.cookie("trueName");
            return $("#fm").form("validate");
        },
        success:function (data) {
            data=JSON.parse(data);
            // 判断返回结果
            if (data.code==200){
                // 成功
                $.messager.alert("添加营销机会",data.msg,"info");
                // 关闭对话框
                closeDialog();
                // 刷新数据表格
                searchSaleChances();
            }else{
                // 失败
                $.messager.alert("添加营销机会",data.msg,"error");
            }
        }
    });
}

// 绑定修改按钮点击事件
function openModifyAccountDialog(){
    var rows = $("#dg").datagrid("getSelections");
    // 判断是否选中要修改的记录
    if (rows.length<1){
        $.messager.alert("crm系统","请至少选中一条记录","info");
        return;
    }
    if (rows.length>1){
        $.messager.alert("crm系统","只能选中一条记录","info");
        return;
    }
    // 加载表单数据
    $("#fm").form("load",rows[0]);
    // 打开修改对话框并修改对话框标题
    $("#dlg").dialog("open").dialog("setTitle","修改对话框");
}

//绑定删除按钮点击事件
function deleteAccount(){
    var rows = $("#dg").datagrid("getSelections");
    if (rows.length==0){
        $.messager.alert("crm系统","请至少选中一条记录","info");
        return;
    }

    var params = "id=";
    for (var i = 0;i<rows.length;i++){
        if (i<rows.length-1){
            params = params + rows[i].id +"&";
        }else{
            params = params +rows[i].id;
        }
    }
    $.messager.confirm("crm系统","您确定要删除选中的记录",function (r) {
        if (r){
            $.ajax({
                url:ctx+"/saleChance/deleteSaleChance",
                data:params,
                dataType:'json',
                success:function(data){
                    if (data.code==200){
                        $.messager.alert("删除营销机会记录",data.msg,"info");
                        // 刷新数据表格
                        searchSaleChances();
                    }else{
                        $.messager.alert("删除营销机会",data.msg,"error");
                    }
                }
            });
        }
    });
}

