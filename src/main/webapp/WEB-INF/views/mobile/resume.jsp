<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html class="no-js">
<head>
	<meta charset="utf-8">
	<title>个人简历--王美丽</title>
	<meta name="description" content="">

	<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
	<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

	<link rel="stylesheet" href="/assets/styles/vendor.css">

	<link rel="stylesheet" href="/assets/styles/main.css">
</head>
<body ontouchstart="">

	<div class="container page">
		<div class="sec-header divide" style="postion:relative">
			<a class="back" href="/">
				<div>&nbsp;&nbsp;</div>
				<div class="icon-circle fa fa-angle-left" style="font-size:2.4rem;margin-left:0.5rem"></div>
				<div>返回</div>
			</a>
			<h3 class="text-center"><a href="">${resume.name}</a>
			</h3>
		</div>
		<div class="resume-info">
			<img src="${resume.phonePath}" alt="">
			<div class="infos">
				<p>年龄 : ${resume.age}岁</p>
				<p>身高 : ${resume.height}cm</p>
				<p>城市 : 没有</p>
				<p>学校 : 没有</p>
				<p>专业 : 暂时没有</p>
			</div>
		</div>

		<!-- <div class="resume-detail">
			<p class="title">求职意向</p>
			<p>礼仪、家教、促销</p>
			<div style="clear:both"></div>
		</div> -->
		<br/>
		<div class="resume-detail bg-red bd-red" >
			<div class="title">求职意向</div>
			<p>好像也是没有</p>
			<div style="clear:both"></div>
		</div>
		<i class="resume-arrow red"></i>

		<div class="resume-detail bg-green bd-green" >
			<div class="title">自我介绍</div>
			<p>${resume.introduce}</p>
			<div style="clear:both"></div>
		</div>
		<i class="resume-arrow green"></i>

		<div class="resume-detail bg-gray bd-gray" >
			<div class="title">工作经验</div>
			<p>${resume.experience}</p>
			<div style="clear:both"></div>
		</div>
		<i class="resume-arrow gray"></i>

		<div class="resume-detail bg-orange bd-orange resume-contact" >
			<div class="title">联系方式</div>
			<p><i class="fa fa-phone orange"></i> 没有..</p>
			<p><i class="fa fa-qq blue"></i> ${resume.qq}</p>
			<p><i class="fa fa-weixin green"></i> 没有..</p>
			<p><i class="fa fa-envelope-o red"></i> ${resume.email}</p>
			<div style="clear:both"></div>
		</div>


	<!-- 	<div class="resume-detail">
			<p>工作经验</p>
			<p>责任心强，有爱心，耐心，细心热心，定期能完成制定目标，同时也是特别有上进心的。</p>
		</div>

		<div class="resume-detail">
			<p>联系方式</p>
			<p><i class="fa fa-phone"></i></p>
			<p><i class="fa fa-qq"></i></p>
			<p><i class="fa fa-weixin"></i></p>
			<p><i class="fa fa-envelope-o"></i></p>
		</div>  -->



        <jsp:include page="/WEB-INF/views/mobile/common/footer.jsp"></jsp:include>

		<!-- 遮罩 -->
		<div id="cover" class="cover"></div>
	</div>
	<script src="/assets/scripts/vendor.js"></script>


	<script src="/assets/scripts/main.js"></script>
	<script src="/assets/scripts/hammer.js"></script>

</body>
</html>
