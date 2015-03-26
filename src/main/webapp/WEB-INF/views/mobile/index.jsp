<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html class="no-js">
<head>
	<meta charset="utf-8">
	<title>桃李街</title>
	<meta name="description" content="">

	<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
	<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

	<link rel="stylesheet" href="/assets/styles/vendor.css">

	<link rel="stylesheet" href="/assets/styles/main.css">


</head>
<body>


	<div class="container page">
		<div class="header">
			<!-- 改为dropdown -->
			<ul class="nav pull-right">

				<li>
					<a href="/user/"><span class="glyphicon glyphicon-map-marker"></span>${username}
						<span class="caret"></span></a>
					</li>
				</ul>
				<h3 class="text-muted">桃李街</h3>
			</div>
			<ul class="info">
				<li>
					<a href="/user/register">
						<span class="icon-circle gray fa fa-file-text"></span>
						<span class="icon-desc">注册</span>
					</a>
				</li>
				<li>
					<a href="/user/login">
						<span class="icon-circle  green fa fa-user"></span>
						<span class="icon-desc">登陆</span>
					</a>
				</li>
				<li>
					<a href="/user/post/job">
						<span class="icon-circle  red fa fa-check-square-o"></span>
						<span class="icon-desc">发布兼职</span>
					</a>
				</li>
			</ul>

			<!-- 图片轮播模块 需要添加触摸插件 -->
			<div id="mycarousel" class="carousel slide" data-ride="carousel">
				<!-- Indicators -->
				<ol class="carousel-indicators">
					<li data-target="#mycarousel" data-slide-to="0" class="active"></li>
					<li data-target="#mycarousel" data-slide-to="1"></li>
					<li data-target="#mycarousel" data-slide-to="2"></li>
				</ol>

				<!-- Wrapper for slides -->
				<div class="carousel-inner" role="listbox">
					<div class="item active">
						<img src="/assets/images/pic.jpg" alt="..." height="200px">
					</div>
					<div class="item">
						<img src="/assets/images/pic.jpg" alt="...">
					</div>
					<div class="item">
						<img src="/assets/images/pic.jpg" alt="...">
					</div>  
				</div>

				<!-- Controls -->
				<a class="left carousel-control hidden" href="#mycarousel" role="button" data-slide="prev">
					<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
					<span class="sr-only">Previous</span>
				</a>
				<a class="right carousel-control hidden" href="#mycarousel" role="button" data-slide="next">
					<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
					<span class="sr-only">Next</span>
				</a>
			</div>
			<ul class="icons">
				<li class="icon">
					<a href="/joblist">
						<span class="icon-circle bg-green fa fa-clock-o"></span>
						<span class="icon-desc">学生兼职</span>
					</a>
				</li>
				<li class="icon">
					<a href="/resumelist">
						<span class="icon-circle bg-blue fa fa-user"></span>
						<span class="icon-desc">求职简历</span>
					</a>
				</li>

				<li class="icon">
					<a href="">
						<span class="icon-circle bg-grown fa fa-shopping-cart"></span>
						<span class="icon-desc">二手物品</span>
					</a>
				</li>
				<li class="icon">
					<a href="user/jobpos">
						 <span class="icon-circle bg-orange fa fa-check-square" style=></span>
						<span class="icon-desc">发布兼职</span>
					</a>
				</li>

				<li class="icon">
					<a href="">
						<span class="icon-circle bg-red fa fa-mortar-board"></span>
						<span class="icon-desc">考研辅导</span>
					</a>
				</li>
				<li class="icon">
					<a href="">
						<span class="icon-circle bg-yellow fa fa-car"></span>
						<span class="icon-desc">驾校招生</span>
					</a>
				</li>
				<li class="icon">
					<a href="">
						<span class="icon-circle bg-purple fa fa-home"></span>
						<span class="icon-desc">宾馆预定</span>
					</a>
				</li>
				<li class="icon">
					<a href="">
						<span class="icon-circle bg-gray fa fa-angle-double-down"></span>
						<span class="icon-desc">更多</span>
					</a>
				</li>
			</ul>




			<!-- 校园聚焦点 -->
			<div class="section news">
				<div class="title">
					<a href=""><p class="more">>>更多</p></a>
					<p>校园聚焦点</p>	
				</div>
                <c:set var="newColorStr" value="blue,red,green"/>
                <c:set var="newColor" value="${fn:split(newColorStr, ',')}"></c:set>

                <c:forEach var="news" items="${newsList}" varStatus="var">
                  <a href="">
                    <div class="content bg-${newColor[var.count-1]}">

                        <img src="/assets/images/info.jpg" alt="">
                        <span>${news.title}</span>
                    </div>
                  </a
                </c:forEach>

			<div class="section">
				<div class="title">
					<p>桃李街团队简介</p>	
				</div>
				<ul class="introduce">
					<li class="circle1">
						<a href="">
							<span class="bg-gray">服务宗旨</span>
						</a>
					</li>
					<li class="circle1">
						<a href="">
							<span class="bg-red">成员介绍</span>
						</a>
					</li>
					<li class="circle1">
						<a href="">
							<span class="bg-blue">JOIN US</span>
						</a>
					</li>
				</ul>
			</div>


			<div class="footer">
				<p style="text-align:center">Copyright@2014——山东理工大学桃李街版权所属</p>
			</div>
		</div>
		<script src="/assets/scripts/vendor.js"></script>

		<script src="/assets/scripts/plugins.js"></script>

		 <script src="/assets/scripts/hammer.js"></script>

		<script>
			'use strict';
			console.log($('#mycarousel'));
			var mc = Hammer(document.getElementById('mycarousel'));
			mc.on('panright',function(ev){

				$('#mycarousel').carousel('prev');
			});
			mc.on('panleft',function(ev){
				$('#mycarousel').carousel('next');
			});
		</script>


	</body>
	</html>
