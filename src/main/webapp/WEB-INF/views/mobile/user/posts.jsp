<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html class="no-js">
<head>
	<meta charset="utf-8">
	<title>我的发布</title>
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
			<h3 class="text-center"><a href="">我的发布</a></h3>
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

		</div>

        <jsp:include page="/WEB-INF/views/mobile/common/footer.jsp"></jsp:include>

		<!-- 遮罩 -->
		<div id="cover" class="cover"></div>
	</div>
	<script src="/assets/scripts/vendor.js"></script>
    <script src="/assets/scripts/template.js"></script>
    <script src="/assets/scripts/main.js"></script>

    <script id="memberlist" type="text/html">
        {{each list as value i}}

        <%--<a href="/jobdetail/{{value.id}}">--%>
            <div class="deal unclassified">
                <input type="button" class="del-btn" id="del-{{value.id}}" value="删除" onclick="javascript:del(this);">
                <div class="title">{{value.title}}</div>
                <span>{{value.workPlace}}</span>&nbsp;&nbsp;
                <span>{{value.postTime | dateFormat:'yyyy-MM-dd'}}</span>&nbsp;&nbsp;
                <span>{{value.wage}} 元/天</span>&nbsp;&nbsp;
                <span>{{value.timeToPay}}</span>
            </div>
        <%--</a>--%>

        {{/each}}
    </script>

    <script>
        $(function(){
            $.ajax({
                type:"GET",
                dataType:"json",
                url:"/api/joblistbymember",
                success:function(data){
                    var datas = {
                        list:data
                    }
                    var html = template('memberlist', datas);
                    $("#deals").append(html);
                }
            });
        })


        var del = function(e){
            var id = $(e).attr('id');
            var url = "/api/deleteJob/"+id.substring(4);
            console.log(url);
            $.ajax({
                type:"POST",
                dataType:"json",
                url:url,
                success:function(data){
                    if(data.result==true){
                        location.assign("/user/posts");
                    }
                }

            })
        }
        $(".del-btn").click(function(){
            console.log("heheh");
            console.log($(this).next('div'));

        });
    </script>


</body>
</html>
