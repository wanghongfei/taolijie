<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html class="no-js">
<head>
	<meta charset="utf-8">
	<title>求职简历</title>
	<meta name="description" content="">

	<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>

	<link rel="stylesheet" href="/assets/styles/vendor.css">

	<link rel="stylesheet" href="/assets/styles/main.css">
</head>
<body ontouchstart="">


	<div class="container page">
		<div class="sec-header" style="postion:relative">
			<a class="back" href="/index.html">
				<div>&nbsp;&nbsp;</div>
				<div class="icon-circle fa fa-angle-left" style="font-size:2.4rem;margin-left:0.5rem"></div>
				<div>返回</div>
			</a>
			<h3 class="text-center"><a href="">求职简历</a></h3>
		</div>
		<div class="nav-bar">
			<ul class="nav">
				<li>
					<span id="type" class="dropdown">求职意向<i class="arrow-down"></i></span>
				</li>
				<li>
					<span id="method" class="dropdown">选择性别<i class="arrow-down"></i></span>
				</li>
				<li>
					<span id="region" class="dropdown">选择学校<i class="arrow-down"></i></span>
				</li>
			</ul>
			<div class="menu type-menu">
				<ul class="icons">
					<li class="icon">
						<span class="icon-circle icon-circle-small bg-green">家教</span>
					</li>
					<li class="icon">
						<span class="icon-circle icon-circle-small bg-blue">派单</span>
					</li>
					<li class="icon">
						
						<span class="icon-circle icon-circle-small bg-red">代理</span>
						
					</li>
					<li class="icon">
						
						<span class="icon-circle icon-circle-small bg-yellow">促销</span>
						
					</li>
					<li class="icon">
						
						<span class="icon-circle icon-circle-small bg-purple">服务员</span>
						
					</li>
					<li class="icon">
						
						<span class="icon-circle icon-circle-small bg-grown">送餐</span>
						
					</li>
					<li class="icon">
						<span class="icon-circle icon-circle-small bg-orange">礼仪</span>
						
					</li>
					<li class="icon">
						<span class="icon-circle icon-circle-small bg-gray">家政</span>
						
					</li>
					<li class="icon">
						<span class="icon-circle icon-circle-small bg-gray">家政</span>
					</li>
					<li class="icon">
						
						<span class="icon-circle icon-circle-small bg-green">家教</span>
					</li>
					<li class="icon">
						
						<span class="icon-circle icon-circle-small bg-blue">派单</span>
					</li>
					<li class="icon">
						
						<span class="icon-circle icon-circle-small bg-red">代理</span>
					</li>
				</ul>
			</div>
			<div class="menu method-menu no-border">
				<a class="menu-filter" href="">日结</a>
				<span class="menu-filter" href="">周结</span>
				<span class="menu-filter" href="">月结</span>
				<span class="menu-filter " href="">面谈</span>
			</div>
			<div class="menu region-menu">
				<a class="menu-filter" href="">张店</a>
				<span class="menu-filter" href="">淄川</span>
				<span class="menu-filter" href="">博山</span>
				<span class="menu-filter " href="">桓台</span>
				<span class="menu-filter " href="">周村</span>
			</div>
		</div>

		<div id="deals" class="deal-list">

			<div class="deal">
				<div class="deal-right">
					<p class="title2">山东理工大学</p>
					<i class="fa fa-eye red"></i> 30
				</div>
				<img src="images/avatar.png" class="deal-type deal-img" alt="">
				<p class="title2">王美丽</p>
				<p>家教、促销、礼仪</p>
			</div>

			<div class="deal">
				<div class="deal-right">
					<p class="title2">山东理工大学</p>
					<i class="fa fa-eye red"></i> 30
				</div>
				<img src="/assets/images/miao.jpg" class="deal-type deal-img" alt="">
				<p class="title2">喵帕斯</p>
				<p>家教、促销、礼仪</p>
			</div>


		</div>

        <jsp:include page="/WEB-INF/views/mobile/common/footer.jsp"></jsp:include>


		<!-- 遮罩 -->
		<div id="cover" class="cover"></div>
	</div>
	<script src="/assets/scripts/vendor.js"></script>
	<script src="/assets/scripts/hammer.js"></script>
    <script src="/assets/scripts/template.js"></script>
    <script src="/assets/scripts/main.js"></script>


    <script id="list" type="text/html">
        {{each list as value i}}
        <div class="deal">
            <div class="deal-right">
                <p class="title2">山东理工大学</p>
                <i class="fa fa-eye red"></i> 30
            </div>
            <img src="/assets/images/miao.jpg" class="deal-type deal-img" alt="">
            <p class="title2">喵帕斯</p>
            <p>家教、促销、礼仪</p>
        </div>
        {{/each}}
    </script>

	<script>
		'use strict';

		//针对不同设备选择触发事件
		var ua = navigator.userAgent;
		var event = (ua.match(/iPad/i)) ? 'touchend' : 'click';
		console.log(event);

		//选择分类
		function stopPropagation(e) { 
			if (e.stopPropagation) 
				e.stopPropagation(); 
			else 
				e.cancelBubble = true; 
		} 
		//alert($(document));
		$(document).on(event,function(){ 
			$('.menu').slideUp(200);
			$('#cover').hide();
		}); 


		var tigger = $('.dropdown');
		tigger.on(event,function(e){
			var list = $('.'+$(this).attr('id')+'-menu');
			if(list.css('display')==='none'){
				$('.menu').css('display','none');
			//首先把所有active的清除
			//$('.menu').css('display','none'); //把它改成移除active
			$('.'+$(this).attr('id')+'-menu').slideDown(200);
			$('#cover').show();
			//给点击的相应菜单加入active类
			//$('.'+'type'+'-menu').addClass('active');
		}else{
			$('.'+$(this).attr('id')+'-menu').slideUp(200);
			$('#cover').hide();
		}


		stopPropagation(e);
	});

	</script>


</body>
</html>
