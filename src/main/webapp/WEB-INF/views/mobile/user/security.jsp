<%@ page contentType="text/html;charset=UTF-8" language="java" %>
	<!doctype html>
	<html class="no-js">
	<head>
		<meta charset="utf-8">
		<title>修改密码</title>
		<meta name="description" content="">

		<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
		<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

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
				<h3 class="text-center"><a href="">修改密码</a>
				</h3>
			</div>
			<div class="input-group no-border-bottom" style="margin-top:4rem;">
				<label for="oldPassword"> 原密码</label>
				<input class="input" type="password" id="oldPassword" placeholder="请输入原密码"  maxlength="30">
				<br>
			</div>
			<div class="input-group no-border-bottom">
				<label for="newPassword">新密码</label>
				<input class="input" id="newPassword" type="password" placeholder="请输入6-20位字母和字符"  maxlength="30">
				<br>
			</div>
			<div class="input-group">
				<label for="rePassword">再确认</label>
				<input class="input" id="rePassword" type="password" placeholder="请确认密码"  maxlength="30">
				<br>
			</div>
            <div class="sub-button">
                <input id="sub-button" type="button" class="button" value="提交">
            </div>



            <jsp:include page="/WEB-INF/views/mobile/common/footer.jsp"></jsp:include>


		<script src="/assets/scripts/vendor.js"></script>
         <script>
                $("#sub-button").click(function(){
                    var oldPassword = $("#oldPassword").val();
                    var newPassword = $("#newPassword").val();
                    var rePassword = $("#rePassword").val();
                    $.ajax({
                        type:'post',
                        url:"/user/setting/security",
                        data:{oldPassword:oldPassword,newPassword:newPassword,rePassword:rePassword},
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
