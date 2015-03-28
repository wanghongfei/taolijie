<%@ page contentType="text/html;charset=UTF-8" language="java" %>
	<!doctype html>
	<html class="no-js">
	<head>
		<meta charset="utf-8">
		<title>注册</title>
		<meta name="description" content="">

		<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
		<link rel="stylesheet" href="/assets/styles/vendor.css">

		<link rel="stylesheet" href="/assets/styles/main.css">
	</head>
	<body ontouchstart="">

		<div class="container page login">
			<div class="sec-header divide" style="postion:relative">
				<a class="back" href="/">
					<div>&nbsp;&nbsp;</div>
					<div class="icon-circle fa fa-angle-left" style="font-size:2.4rem;margin-left:0.5rem"></div>
					<div>返回</div>
				</a>
				<h3 class="text-center"><a href="">注册</a>
				</h3>
			</div>
			<div>
				<div class="logo"><img src="/assets/images/logo.jpg" alt=""></div>
				<div><span>用户注册</span></div>
				<div style="clear:both"></div>
			</div>
            <form action="" method="post">
                <div class="input-group no-border-bottom">
                    <label for=""> 用户名</label>
                    <input id="username" class="input" type="text" placeholder="请输入用户名"  maxlength="30">
                    <br>
                </div>
                <div class="input-group no-border-bottom">
                    <label for="">密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码</label>
                    <input class="input" id="password" type="password" placeholder="请输入密码"  maxlength="30">
                    <br>
                </div>
                <div class="input-group">
                    <label for="">确认密码</label>
                    <input class="input" id="repassword" type="password" placeholder="请确认密码"  maxlength="30">
                    <br>
                </div>
                <div class="sub-button">
                    <input type="button" class="button" value="注册" id="sub-button">
                </div>
            </form>

            <jsp:include page="/WEB-INF/views/mobile/common/footer.jsp"></jsp:include>

       </div>

		<script src="/assets/scripts/vendor.js"></script>o
        <script>
            $("#sub-button").click(function(){
                var username = $("#username").val();
                var password = $("#password").val();
                var repassword = $("#repassword").val();

                $.ajax({
                    type:'post',
                    url:"/user/register",
                    data:{username:username,password:password,repassword:repassword},
                    dataType:"json",
                    success:function(data){
                        console.log(data);
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
