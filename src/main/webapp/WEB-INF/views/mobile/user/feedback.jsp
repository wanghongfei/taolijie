<%@ page contentType="text/html;charset=UTF-8" language="java" %>
	<!doctype html>
	<html class="no-js">
	<head>
		<meta charset="utf-8">
		<title>意见反馈</title>
		<meta name="description" content="">

		<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
		<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

		

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
				<h3 class="text-center"><a href="">意见反馈</a>
				</h3>
			</div>	
			<div class="input-group no-border-bottom" style="margin-top:1rem">
				<label for="">您的邮箱</label>
				<input class="" type="email" placeholder="请输入您的邮箱" maxlength="30">
				<br>
			</div>
			<div class="input-group">
				<label for="" class="text-label">反馈内容</label>
				<textarea name="" id="" placeholder="你还可以输入140字"></textarea>
				<!-- <input type="text"  class="input"> -->
			</div>
			<br>
			<p>留下您最宝贵的意见,我们会尽快和你联系</p>
			<div class="sub-button">
				<button id="sub-button">提交反馈</button>
			</div>

            <jsp:include page="/WEB-INF/views/mobile/common/footer.jsp"></jsp:include>




	</body>
	</html>
