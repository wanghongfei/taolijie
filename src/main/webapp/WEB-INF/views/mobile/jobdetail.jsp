<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html class="no-js">
<head>
	<meta charset="utf-8">
	<title>兼职详情</title>
	<meta name="description" content="">

	<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>

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
			<h3 class="text-center"><a href="">兼职详情</a></h3>
		</div>
		<div class="detail-title">
			<div class="content-title large-title">${job.title}</div>
			<div class="content-body">
				<span><i class="fa fa-clock-o red"></i>&nbsp;&nbsp;${job.postTime}</span>&nbsp;&nbsp;&nbsp;&nbsp;
				<span><i class="fa fa-eye red"></i>&nbsp;&nbsp;已有0人评论</span>
			</div>
		</div>
		<div class="detail-info detail-bottom">
			<p><span>${job.wage}/天</span>&nbsp;&nbsp;${job.timeToPay}</p>
			<p style="float:left">有效日期 : </p>
			<table><tr><td>
				<p>${job.expiredTime}</p>
			</td></tr></table>

			<p style="float:left">工作时间 : </p>
			<table ><tr ><td>
				<p>暂无字段</p>
			</td></tr></table>

			<p style="float:left">工作地点 :</p>
			<table ><tr ><td>
				<p>${job.workPlace}</p>
			</td></tr></table>

			<p style="float:left">发布商家 : </p>
			<table ><tr ><td>
				<p>暂无字段</p>
			</td></tr></table>

		</div>
		<div class="detail-content detail-bottom">
			<div class="content-title">工作职位详情</div>

			<p style="float:left">工作内容 : </p>
			<table ><tr ><td>
				<p>${job.jobDetail}</p>
			</td></tr></table>

			<p style="float:left">工作要求 :</p>
			<table ><tr ><td>
				<p>${job.jobDescription}</p>
			</td></tr></table>
		</div>
		<div class="detail-contact detail-bottom">

			<p style="float:left">联系人 :</p>
			<table ><tr ><td>
				<p>${job.contact}</p>
			</td></tr></table>

			<p style="float:left">联系电话 :</p>
			<table ><tr ><td>
				<p>${job.contactPhone}</p>
			</td></tr></table>
		</div>
		<div class="detail-review">
			<div class="review-overview detail-bottom">
			<div class="content-title">用户评价</div>
				<!-- 四个按钮 -->
				<ul>
					<li><span class="fa fa-thumbs-up"></span><p>666</p></li>
					<li><span class="fa fa-thumbs-down"></span><p>1</p></li>
					<li><span class="fa fa-comment"></span><p>5</p></li>
					<li><span class="tousu">投诉</span></li>
				</ul>
				<div style="clear:both"></div>
			</div>
			<!-- 评论 -->
			<div class="reviews">
				<div class="review">
					<div class="user">
						<img class="review-img" src="images/avatar.png" alt="">
						<p>wynfrith</p>
					</div>
					<p>这里的环境很好,还有跟孩子们在一起也非常高兴,收获的不仅仅是快乐.</p>
					<div style="clear:both"></div>
				</div>
				<hr class="divide" />
				<div class="review" >
					<div class="user">
						<img class="review-img" src="images/avatar.png" alt="">
						<p>wynfrith</p>
					</div>
					<p>这里的环境很好,还有跟孩子们在一起也非常高兴,收获的不仅仅是快乐.</p>
					<div style="clear:both"></div>
				</div>
				<hr class="divide" />
				<div class="review" >
					<div class="user">
						<img class="review-img" src="images/avatar.png" alt="">
						<p>wynfrith</p>
					</div>
					<p>这里的环境很好,还有跟孩子们在一起也非常高兴,收获的不仅仅是快乐.</p>
					<div style="clear:both"></div>
				</div>
				<hr class="divide" />
				
				<!-- 查看更多 -->
				<p class="more">查看更多评价</p>

			</div>
		</div>


        <jsp:include page="/WEB-INF/views/mobile/common/footer.jsp"></jsp:include>


        <!-- 遮罩 -->
		<div id="cover" class="cover"></div>
	</div>
	<script src="/assets/scripts/vendor.js"></script>

	<script src="/assets/scripts/hammer.js"></script>

</body>
</html>
