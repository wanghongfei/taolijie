<%@ page contentType="text/html;charset=UTF-8" language="java" %>
	<!doctype html>
	<html class="no-js">
	<head>
		<meta charset="utf-8">
		<title>发布兼职</title>
		<meta name="description" content="">

		<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
		<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

			<link rel="stylesheet" href="/assets/styles/vendor.css">

		<link rel="stylesheet" href="/assets/styles/main.css">

<style>

</style>

	</head>
	<body ontouchstart="">
		<div class="container page" style="background-color:#f1f1f1;">
			<div class="sec-header divide" style="postion:relative">
				<a class="back" href="index.html">
					<div>&nbsp;&nbsp;</div>
					<div class="icon-circle fa fa-angle-left" style="font-size:2.4rem;margin-left:0.5rem"></div>
					<div>返回</div>
				</a>
				<h3 class="text-center"><a href="">发布兼职</a>
				</h3>
			</div>

			<div class="input-group">
				<label for="">兼职标题</label>
				<input class="input" type="text" placeholder="请输入用户名"  maxlength="30">
			</div>

			<div class="input-group no-border-bottom style2 " style="margin-top:1rem;">
				<label for="">选择分类</label>
				<!-- <input class="input" type="text" placeholder="请输入密码"  maxlength="30"> -->
				<select class="inline" name="" id="">
					<option value="1">点击选择</option>
					<option value="1">美工</option>
					<option value="1">后台工程师</option>
					<option value="1">前端工程师</option>
				</select>
			</div>
			<div class="input-group inline no-border-bottom style2">
				<label for="">工资待遇</label>
				<input class="input " type="text" placeholder="请输入有效数字"  maxlength="30">
				<select name="" id="">
					<option value="1">元/小时</option>
					<option value="1">元/天</option>
					<option value="1">元/周</option>
					<option value="1">元/月</option>
				</select>

			</div>
			<div class="input-group no-border-bottom style2">
				<label for="">结算方式</label>
						<select required="true" name="birthday_year">
							<option value="2010" label="2010">2010</option>
							<option value="2009" label="2009">2009</option>
							<option value="2008" label="2008">2008</option>
							<option value="2007" label="2007">2007</option>
						</select>

			</div>
			<div class="input-group">
				<label for="">有效时间</label>
				<!-- 时间控件 -->
				<input class="input " type="text" placeholder="请输入有效时间,之后会代替为时间控件"  maxlength="30">
			</div>

			<div class="input-group no-border-bottom" style="margin-top:1rem;" >
				<label for="">工作时间</label>
				<input class="input" type="text" placeholder="请填写工作时间"  maxlength="30">
			</div>

			<div class="input-group no-border-bottom">
				<label for="">工作地点</label>
				<textarea class="small" name="" id="" placeholder="请输入详情地点" cols="10"></textarea>

			</div>
			<div class="input-group no-border-bottom">
				<label for="">工作内容</label>
				<textarea name="" id="" placeholder="请输入工作内容"></textarea>
			</div>
			<div class="input-group">
				<label for="">工作要求</label>
				<textarea name="" id="" placeholder="请输入具体工作要求"></textarea>
			</div>


			<div class="input-group no-border-bottom" style="margin-top:1rem;">
				<label for="">公司名称</label>
				<input class="input" type="text" placeholder="请填写公司名称"  maxlength="30">
			</div>
			<div class="input-group no-border-bottom">
				<label for="">联系人</label>
				<input class="input" type="text" placeholder="请填写联系人姓名"  maxlength="30">
			</div>
			<div class="input-group">
				<label for="">联系电话</label>
				<input class="input" type="text" placeholder="请输入联系电话"  maxlength="30">
			</div>

			<div class="sub-button">
				<button id="sub-button">发布</button>
			</div>



		<div class="footer">
			<p style="text-align:center">Copyright@2014——山东理工大学桃李街版权所属</p>
		</div>


		<script src="/assets/scripts/vendor.js"></script>

		<script src="/assets/scripts/selectui.js"></script>

		


		<script>
			jQuery(function($) {
				$("select").not(".native select").selectui({
			// 是否自动计算宽度
			autoWidth: true,
			// 是否启用定时器刷新文本和宽度
			interval: true
		});
			});
		</script>

	</body>
	</html>
