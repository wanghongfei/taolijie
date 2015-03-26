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
				<a class="back" href="index">
					<div>&nbsp;&nbsp;</div>
					<div class="icon-circle fa fa-angle-left" style="font-size:2.4rem;margin-left:0.5rem"></div>
					<div>返回</div>
				</a>
				<h3 class="text-center"><a href="">修改密码</a>
				</h3>
			</div>
			<div class="input-group no-border-bottom" style="margin-top:4rem;">
				<label for=""> 原密码</label>
				<input class="input" type="text" placeholder="请输入原密码"  maxlength="30">
				<br>
			</div>
			<div class="input-group no-border-bottom">
				<label for="">新密码</label>
				<input class="input" type="password" placeholder="请输入6-20位字母和字符"  maxlength="30">
				<br>
			</div>
			<div class="input-group">
				<label for="">再确认</label>
				<input class="input" type="password" placeholder="请确认密码"  maxlength="30">
				<br>
			</div>
			<div class="sub-button">
				<button id="sub-button">提交</button>
			</div>



		<div class="footer">
			<p style="text-align:center">Copyright@2014——山东理工大学桃李街版权所属</p>
		</div>


		<script src="/assets/scripts/vendor.js"></script>


	</body>
	</html>
