function userLogin(){
    var userName = $("#userName").val();
    var userPwd = $("#userPwd").val();
    // 判断用户名是否为空
    if (isEmpty(userName)){
        alert("用户名不能为空");
        return;
    }
    // 判断用户密码是否为空
    if (isEmpty(userPwd)){
        alert("用户密码不能为空");
        return;
    }
    // 封装参数
    var params = {};
    params.userName = userName;
    params.userPwd = userPwd;
    // 发送请求，并传递参数接受返回结果
    $.ajax({
        type:"post",
        url:ctx+"/user/userLogin",
        data:params,
        dataType:"json",
        success:function(data){
            if (data.code==200){
                // 将返回数据存入cookie中
                $.cookie("userName",data.result.userName);
                $.cookie("trueName",data.result.trueName);
                $.cookie("id",data.result.id);
                // 成功跳转页面
                window.location.href="main";
            }else{
                alert(data.msg)
            }
        }
    })
}
