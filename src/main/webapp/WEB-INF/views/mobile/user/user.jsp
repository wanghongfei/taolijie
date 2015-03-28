<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html class="no-js">
<head>
	<meta charset="utf-8">
	<title>个人中心</title>
	<meta name="description" content="">

	<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>

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
				<p class="name">${username}</p>
				<p>2015-1-1</p>
			</div>
			<a href="">
				<span class="right icon-circle fa fa-angle-right"></span>
			 <span class="text right">完善资料</span>
			</a>
			 
		</div>

		<ul class="user-func">
			<li>
				<a href="/user/resume" class="no-border-bottom">
					<i class="blue fa fa-file-text"></i>我的简历
					<span class="icon-circle fa fa-angle-right"></span>
					<span class="text">去完善您的简历吧</span>
				</a>
				
			</li>
			<li>
				<a href="/user/fav" class="no-border-bottom">
					<i class="red fa fa-heart"></i>我的收藏
					<span class="icon-circle fa fa-angle-right"></span>
				</a>
			</li>
			<li>
				<a href="/user/posts" class="no-border-bottom">
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
				<a href="/user/job" class="no-border-bottom">
					<i class="red fa fa-check-square-o"></i>发布信息
					<span class="icon-circle fa fa-angle-right"></span>
				</a>
			</li>
			<li>
				<a href="/user/setting/security" class="no-border-bottom">
					<i class="gray fa fa-lock"></i>修改密码
					<span class="icon-circle fa fa-angle-right"></span>
				</a>
			</li>
			<li>
				<a href="/user/feedback"><i class="red fa fa-lightbulb-o"></i>意见反馈
				<span class="icon-circle fa fa-angle-right"></span>
				<span class="text">留下您最好的想法</span>
				</a>
			</li>
		</ul>
        <jsp:include page="/WEB-INF/views/mobile/common/footer.jsp"></jsp:include>

	</div>
	<script src="/assets/scripts/vendor.js"></script>


</body>
</html>
