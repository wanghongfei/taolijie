<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html class="no-js">
<head>
	<meta charset="utf-8">
	<title>我的收藏</title>
	<meta name="description" content="">

	<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
	<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

	<link rel="stylesheet" href="/assets/styles/vendor.css">

	<link rel="stylesheet" href="/assets/styles/main.css">
</head>
<body ontouchstart="">


	<div class="container page">
		<div class="sec-header" style="postion:relative">
			<a class="back" href="/index">
				<div>&nbsp;&nbsp;</div>
				<div class="icon-circle fa fa-angle-left" style="font-size:2.4rem;margin-left:0.5rem"></div>
				<div>返回</div>
			</a>
			<h3 class="text-center"><a href="">我的收藏</a></h3>
		</div>
		<div class="nav-bar posts-bar">
			<ul class="nav">
				<li class="active">
					<span id="type" class="dropdown ">兼职信息</span>
				</li>
				<li>
					<span id="method" class="dropdown">求职简历</span>
				</li>
				<li class="no-border">
					<span id="region" class="dropdown">二手物品</span>
				</li>
			</ul>
		</div>
		<div id="deals" class="deal-list">
			<div class="deal unclassified">
				<input type="button" id="del-btn" class="del-btn" value="删除">

				<div class="title">诚聘高中物理老师一名</div>
				<span>华光路103号</span>&nbsp;&nbsp;
				<span>2014-1-28</span>&nbsp;&nbsp;
				<span>200元/天</span>&nbsp;&nbsp;
				<span>月结</span>
			</div>

			<div class="deal unclassified">
				<input type="button" id="del-btn" class="del-btn" value="删除">
				<div class="title">诚聘高中物理老师一名</div>
				<span>华光路103号</span>&nbsp;&nbsp;
				<span>2014-1-28</span>&nbsp;&nbsp;
				<span>200元/天</span>&nbsp;&nbsp;
				<span>月结</span>
			</div>
			<div class="deal unclassified">
				<input type="button" id="del-btn" class="del-btn" value="删除">
				<div class="title">诚聘高中物理老师一名</div>
				<span>华光路103号</span>&nbsp;&nbsp;
				<span>2014-1-28</span>&nbsp;&nbsp;
				<span>200元/天</span>&nbsp;&nbsp;
				<span>月结</span>
			</div>
			<div class="deal unclassified">
				<input type="button" id="del-btn" class="del-btn" value="删除">
				<div class="title">诚聘高中物理老师一名</div>
				<span>华光路103号</span>&nbsp;&nbsp;
				<span>2014-1-28</span>&nbsp;&nbsp;
				<span>200元/天</span>&nbsp;&nbsp;
				<span>月结</span>
			</div>
			<div class="deal unclassified">
				<input type="button" id="del-btn" class="del-btn" value="删除">

				<div class="title">诚聘高中物理老师一名</div>
				<span>华光路103号</span>&nbsp;&nbsp;
				<span>2014-1-28</span>&nbsp;&nbsp;
				<span>200元/天</span>&nbsp;&nbsp;
				<span>月结</span>
			</div>
			<div class="deal unclassified">
				<input type="button" id="del-btn" class="del-btn" value="删除">

				<div class="title">诚聘高中物理老师一名</div>
				<span>华光路103号</span>&nbsp;&nbsp;
				<span>2014-1-28</span>&nbsp;&nbsp;
				<span>200元/天</span>&nbsp;&nbsp;
				<span>月结</span>
			</div>
			

		</div>

        <jsp:include page="/WEB-INF/views/mobile/common/footer.jsp"></jsp:include>

	</div>
</body>
</html>
