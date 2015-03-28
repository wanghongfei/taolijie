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
				<a class="back" href="/">
					<div>&nbsp;&nbsp;</div>
					<div class="icon-circle fa fa-angle-left" style="font-size:2.4rem;margin-left:0.5rem"></div>
					<div>返回</div>
				</a>
				<h3 class="text-center"><a href="">发布兼职</a>
				</h3>
			</div>

			<div class="input-group">
				<label for="">兼职标题</label>
				<input id="title" class="input" type="text" placeholder="请输入标题"  maxlength="30">
			</div>

			<div class="input-group no-border-bottom style2 " style="margin-top:1rem;">
				<label for="">选择分类</label>
				<!-- <input class="input" type="text" placeholder="请输入密码"  maxlength="30"> -->
				<select class="inline" name="" id="categoryId">
					<option value="">点击选择</option>
				</select>
			</div>
			<div class="input-group inline no-border-bottom style2">
				<label for="">工资待遇</label>
				<input class="input " id="wage" type="text" placeholder="请输入有效数字"  maxlength="30">
				<select name="" id="wageUnit">
					<option value="小时">元 / 小时</option>
                    <option value="天">元 / 天</option>
					<option value="周">元 / 周</option>
					<option value="月">元 / 月</option>
				</select>

			</div>
			<div class="input-group no-border-bottom style2">
				<label for="">结算方式</label>
                <select name="" id="timeToPay">
                    <option value="日结">日结</option>
                    <option value="周结">周结</option>
                    <option value="月结">月结</option>
                </select>

			</div>
			<div class="input-group">
				<label for="">有效时间</label>
				<!-- 时间控件 -->
				<input class="input" id="expiredTime" type="text" placeholder="请输入有效时间,之后会代替为时间控件"  maxlength="30">
			</div>

			<div class="input-group no-border-bottom" style="margin-top:1rem;" >
				<label for="">工作时间</label>
				<input class="input" id="workTime" type="text" placeholder="请填写工作时间(暂时补天)"  maxlength="30">
			</div>

			<div class="input-group no-border-bottom">
				<label for="">工作地点</label>
				<textarea class="small" name="" id="workPlace" placeholder="请输入详情地点" cols="10"></textarea>

			</div>
			<div class="input-group no-border-bottom">
				<label for="">工作内容</label>
				<textarea name="" id="jobDetail" placeholder="请输入工作内容"></textarea>
			</div>
			<div class="input-group">
				<label for="">工作要求</label>
				<textarea name="" id="jobDescription" placeholder="请输入具体工作要求"></textarea>
			</div>


			<div class="input-group no-border-bottom" style="margin-top:1rem;">
				<label for="">公司名称</label>
				<input class="input" id="company" type="text" placeholder="请填写公司名称(暂时不填)"  maxlength="30">
			</div>
			<div class="input-group no-border-bottom">
				<label for="">联系人</label>
				<input class="input" id="contact" type="text" placeholder="请填写联系人姓名"  maxlength="30">
			</div>
			<div class="input-group">
				<label for="">联系电话</label>
				<input class="input" id="contactPhone" type="text" placeholder="请输入联系电话"  maxlength="30">
			</div>

			<div class="sub-button">
                <input type="button" class="button" id="sub-button" value="发布"/>
			</div>


            <jsp:include page="/WEB-INF/views/mobile/common/footer.jsp"></jsp:include>
</div>

		<script src="/assets/scripts/vendor.js"></script>
        <script src="/assets/scripts/template.js"></script>

		<script src="/assets/scripts/selectui.js"></script>

        <script id="category" type="text/html">
                {{each list as value i}}
                <option value="{{value.id}}">{{value.name}}</option>
                {{/each}}
        </script>


		<script>
			jQuery(function($) {
                $.ajax({
                    type:"GET",
                    url:"/api/jobcate",
                    dataType:"json",
                    success:function(data){
                        var datas = {
                            list:data
                        }
                        var cateHtml = template('category', datas);

                       $("#categoryId").append(cateHtml);
                    }
                });
                $("select").not(".native select").selectui({
			// 是否自动计算宽度
			autoWidth: true,
			// 是否启用定时器刷新文本和宽度
			interval: true
		});
			});
            $("#sub-button").click(function(){

                var title= $("#title").val();
                var categoryId= $("#categoryId").val();
                var wage= $("#wage").val();
               // var wageUnit= $("#wageUnit").val();
                var timeToPay= $("#timeToPay").val();
                var expiredTime= $("#expiredTime").val();
               // var workTime= $("#workTime").val();
                var workPlace= $("#workPlace").val();
                var jobDetail= $("#jobDetail").val();
                var jobDescription= $("#jobDescription").val();
                var company= $("#company").val();
                var contact= $("#contact").val();
                var contactPhone= $("#contactPhone").val();

                $.ajax({
                    type:"POST",
                    url:"/api/post/job",
                    data:{
                        title:title,
                        categoryId:categoryId,
                        wage:wage,
                        timeToPay:timeToPay,
                        expiredTime:expiredTime,
                        workPlace:workPlace,
                        jobDetail:jobDetail,
                        jobDescription:jobDescription,
                        company:company,
                        contact:contact,
                        contactPhone:contactPhone
                    },
                    dataType:"json",
                    success:function(data){
                        if(data.result == true){
                            location.assign("/index")
                        }
                    },
                    error:function(data){
                        console.log(data);
                    }
                });

            });

		</script>

	</body>
	</html>
