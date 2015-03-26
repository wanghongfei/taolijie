<%@ page contentType="text/html;charset=UTF-8" language="java" %>
	<!doctype html>
	<html class="no-js">
	<head>
		<meta charset="utf-8">
		<title>用户登录</title>
		<meta name="description	" content="">

		<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
		<link rel="stylesheet" href="/assets/styles/vendor.css">
		<link rel="stylesheet" href="/assets/styles/main.css">
	</head>
	<body ontouchstart="">

		<div class="container page login">
			<div class="sec-header divide" style="postion:relative">
				<a class="back" href="/index">
					<div>&nbsp;&nbsp;</div>
					<div class="icon-circle fa fa-angle-left" style="font-size:2.4rem;margin-left:0.5rem"></div>
					<div>返回</div>
				</a>
				<h3 class="text-center"><a href="">登陆</a>
				</h3>
			</div>
			<div>
				<div class="logo"><img src="/assets/images/logo.jpg" alt=""></div>
				<div><span>用户登录</span></div>
				<div style="clear:both"></div>
			</div>
            <form action="" method="post">
                <div class="input-group no-border-bottom">
                    <label for=""> 用户名称</label>
                    <input class="input" id="username" type="text" placeholder="请输入用户名"  maxlength="30">
                    <br>
                </div>
                <div class="input-group">
                    <label for="">密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码</label>
                    <input class="input" id="password" type="password" placeholder="请输入密码"  maxlength="30">
                    <br>
                </div>
                <div class="sub-button">
                    <input id="sub-button" type="button" class="button" value="立即登陆">
                </div>
            </form>


		<div class="footer">
			<p style="text-align:center">Copyright@2014——山东理工大学桃李街版权所属</p>
		</div>
</div>

		<script src="/assets/scripts/vendor.js"></script>
        <script>
            $("#sub-button").click(function(){
                var username = $("#username").val();
                var password = $("#password").val();
                $.ajax({
                    type:'post',
                    url:"/user/login",
                    data:{username:username,password:password},
                    dataType:"json",
                    success:function(data){
                        if(data.result == true){
                            location.assign('/index')
                        }
                    },
                    error:function(data){
                        console.log(data);
                    }
                });
            });
        </script>

	</body>
	</html>
