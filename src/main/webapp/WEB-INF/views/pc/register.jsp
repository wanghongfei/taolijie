<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-3-7
  Time: 下午10:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册</title>
    <link rel="stylesheet" href="/assets/styles/main.css"/>
</head>
<body>
    <p>注册</p>
    <hr/>
    <label for="email">邮箱:</label>
    <input type="email" id="email"/>
    <br/>
    <label for="username">用户 :</label>
    <input type="test" id="username"/>
    <br/>
    <label for="password">密码:</label>
    <input type="password" id="password"/>
    <br/>
    <input id="sub_btn" type="button" value="注册"/>

    <script src="/assets/scripts/vendor.js"></script>
    <script>
        $("#sub_btn").click(function(){
            var email = $("#email").val();
            var username= $("#username").val();
            var password = $("#password ").val();

            var data = {email:email,username:username,password:password};

            $.ajax({
                type:"POST",
                data:data,
                url:"/user/register",
                dataType:"json",
                success:function(data){
                    console.log(data);
                }
            });
        });
    </script>
</body>
</html>
