<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html class="no-js">
<head>
	<meta charset="utf-8">
	<title>个人中心</title>
	<meta name="description" content="">

	<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
	<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

	<link rel="stylesheet" href="/assets/styles/vendor.css">

	<link rel="stylesheet" href="/assets/styles/main.css">
</head>
<body ontouchstart="">


	<div class="container page">
		<div class="sec-header divide" style="postion:relative">
			<a class="back" href="/index">
				<div>&nbsp;&nbsp;</div>
				<div class="icon-circle fa fa-angle-left" style="font-size:2.4rem;margin-left:0.5rem"></div>
				<div>返回</div>
			</a>
			<h3 class="text-center"><a href="">个人中心</a></h3>
            <a href="/user/logout"><p style="float:right">注销</p></a>
		</div>
		<div class="user-info">
			<img src="/assets/images/miao.jpg" alt="">
			<div class="infos">
				<p class="name">wynfrith</p>
				<p>2015-1-1</p>
			</div>
			<a href="">
				<span class="right icon-circle fa fa-angle-right"></span>
			 <span class="text right">完善资料</p>
			</a>
			 
		</div>

		<ul class="user-func">
			<li>
				<a href="/user/resume.html" class="no-border-bottom">
					<i class="blue fa fa-file-text"></i>我的简历
					<span class="icon-circle fa fa-angle-right"></span>
					<span class="text">去完善您的简历吧</p>
				</a>
				
			</li>
			<li>
				<a href="/user/fav.html" class="no-border-bottom">
					<i class="red fa fa-heart"></i>我的收藏
					<span class="icon-circle fa fa-angle-right"></span>
				</a>
			</li>
			<li>
				<a href="/user/posts.html" class="no-border-bottom">
					<i class="green fa fa-file-text"></i>我的发布
					<span class="icon-circle fa fa-angle-right"></span>
				</a>
			</li>

			<!-- 加入角标 -->
			<li>
				<a href="" class="no-border-bottom">
					<i class="orange fa fa-envelope"></i>通知消息
					<span class="icon-circle fa fa-angle-right"></span>
				</a>
			</li>

			<li>
				<a href="/user/jobpost.html" class="no-border-bottom">
					<i class="red fa fa-check-square-o"></i>发布信息
					<span class="icon-circle fa fa-angle-right"></span>
				</a>
			</li>
			<li>
				<a href="/user/security.html" class="no-border-bottom">
					<i class="gray fa fa-lock"></i>修改密码
					<span class="icon-circle fa fa-angle-right"></span>
				</a>
			</li>
			<li>
				<a href="/user/feedback.html"><i class="red fa fa-lightbulb-o"></i>意见反馈
				<span class="icon-circle fa fa-angle-right"></span>
				<span class="text">留下您最好的想法</p>
				</a>
			</li>
		</ul>
		<div class="footer">
			<p style="text-align:center">Copyright@2014——山东理工大学桃李街版权所属</p>
		</div>

		<!-- 遮罩 -->
		<div id="cover" class="cover"></div>
	</div>
	<script src="/assets/scripts/vendor.js"></script>


</body>
</html>
