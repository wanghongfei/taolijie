<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-3-7
  Time: 下午1:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登陆</title>
    <link rel="stylesheet" href="/assets/styles/main.css"/>
</head>
<body>
<label for="username">用户名: </label>
<input type="text" id="username"/>
<label for="password">密码: </label>
<input type="password" id="password"/>
<br/>
<input id="sub_button" type="button" value="提交"/>

<script src="/assets/scripts/vendor.js"></script>
<script>
    $("#sub_button").click(function(){
        var username = $("#username").val();
        var password = $("#password").val();

        $.ajax({
            type:'post',
            url:"/user/login",
            data:{username:username,password:password},
            dataType:"json",
            success:function(data){
                console.log(data);
            },
            error:function(data){
                console.log(data);
            }
        });
    });
</script>
</body>


</html>
