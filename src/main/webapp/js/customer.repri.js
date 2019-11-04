$(function () {
    var lossId = $("#lossId").val();
    $("#dg").edatagrid({
        url:ctx+"customer_repri/customerReprieveByLossId?lossId="+lossId,
        saveUrl:ctx+"/customer_repri/insertReprive?lossId="+lossId,
        updateUrl:ctx+"/customer_repri/updateReprive?lossId="+lossId
    });
    var state = $("#state").val();
    if (state == 1){
        $("#toolbar").remove();
        $("#dg").edatagrid("disableEditing");
    }
});

function saveCustomerRepri(){
    $("#dg").edatagrid("saveRow");
    $("#dg").edatagrid("load");
}

function updateCustomerLossState(){
    $.messager.confirm("crm系统","确认该客户是否流失",function (r) {
        if (r){
            $.messager.prompt("crm系统","请输入流失原因",function (msg) {
                if (msg){
                    $.ajax({
                        type:'post',
                        url:ctx+"/customer_loss/updateCustomerLossState",
                        data:"lossId="+$("#lossId").val()+"&lossReason="+msg,
                        dataType:'json',
                        success:function(data){
                            if (data.code==200){
                                $.messager.alert("crm系统",data.msg,"info");
                                $("#toolbar").remove();
                            }else{
                                $.messager.alert("crm系统",data.msg,"error");
                            }
                        }
                    });
                }else {
                    $.messager.alert("crm系统","流失原因不能为空","error");
                }
            });
        }
    });
}

function delCustomerRepri() {
    var rows = $("#dg").edatagrid("getSelections");
    if (rows.length==0){
        $.messager.alert("来自crm", "请先选择要删除的记录", "info");
        return;
    }

    $.messager.confirm("crm","你确定要删除所选项",function (r) {
        if (r){
            $.ajax({
                type:'post',
                url:ctx+"customer_repri/delete",
                data:"id="+rows[0].id,
                dataType:'json',
                success:function(data){
                    if (data.code==200){
                        $.messager.alert("crm系统",data.msg,"info");
                        $("#dg").edatagrid("load");
                    }else{
                        $.messager.alert("crm系统",data.msg,"error");
                    }
                }
            });
        }
    });
}