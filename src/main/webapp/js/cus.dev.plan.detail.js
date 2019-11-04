$(function () {
    $("#dg").edatagrid({
        url:ctx+"/customerDevPlan/queryCusDevPlans?saleChanceId="+$("#saleChanceId").val(),
        saveUrl:ctx+"/customerDevPlan/insert?saleChanceId="+$("#saleChanceId").val(),
        updateUrl:ctx+"/customerDevPlan/update?saleChanceId="+$("#saleChanceId").val()
    });
});

function saveCusDevPlan(){
    $("#dg").edatagrid("saveRow");
    $("#dg").edatagrid("load");
}

function updateCusDevPlan(){
    $("#dg").edatagrid("saveRow");
}