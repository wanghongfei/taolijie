<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ju" uri="/WEB-INF/tld/JsonUtils.tld"%>
<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-5-9
  Time: 下午3:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page session="false" %>

<%request.setCharacterEncoding("UTF-8") ;%>

<!doctype html>
<html class="no-js" ng-app="tljApp" ng-controller="jobCtrl">
<head>
  <meta charset="utf-8">
  <title ng-bind="'兼职-'+job.title"></title>
  <meta name="description" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

  <!-- build:css /styles/vendor.css -->
  <!-- bower:css -->
  <!-- endbower -->
  <!-- endbuild -->

  <!-- build:css({.tmp,app}) /styles/css/style.css -->
  <link rel="stylesheet" href="/styles/animate.css"/>
  <link rel="stylesheet" href="/styles/style.css">
  <link rel="stylesheet" href="/styles/jobdetail.css">
  <%--图片上传美化--%>
  <link rel="stylesheet" href="/styles/webuploader.css"/>
  <!-- endbuild -->
  <link rel="stylesheet" href="/styles/jquery.bxslider.css">
  <%--<link rel="stylesheet" href="http://libs.useso.com/js/font-awesome/4.2.0/css/font-awesome.min.css">--%>
  <link rel="stylesheet" href="/styles/font-awesome.min.css"/>

  <!-- build:js /scripts/vendor/modernizr.js -->
  <script src="/scripts/modernizr.js"></script>
  <!-- endbuild -->

</head>
<body>
<!--[if lt IE 10]>
<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->

<%--顶栏--%>
<jsp:include page="block/top-bar.jsp"/>
<%--页首--%>
<jsp:include page="block/header.jsp"/>

<div class="container">


  <%--轮播--%>
  <jsp:include page="block/silder.jsp"/>
  <%--侧边栏--%>
  <jsp:include page="block/side.jsp"/>

  <!-- 正文 -->
  <div class="detail main">
    <div class="detail-bar">
      <span>兼职详情</span>
      <a href="javascript:history.back(-1);" style="color:#fa6a38;"><p class="fl"><i class="fa fa-angle-left">&nbsp;&nbsp;</i>返回</p></a>
      <%--<p class="fr">分享</p>--%>
      <p class="fr" id="fav" ng-attr-data-id="{{ job.id }}" data-type="job">
      <i class="fa"  ng-class="{'fa-heart': job.favStatus, 'fa-heart-o': !job.favStatus}"></i>
      ${favStatus? '已收藏':'收藏'}
      </p>
    </div>
    <div style="clean:both"></div>
    <div class="title">
      <p ng-bind="job.title">&nbsp;&nbsp;</p>
      <label class="label">兼职类型 : </label><span ng-bind="job.category.name">&nbsp;&nbsp;</span>
      <label class="label" ng-bind="'发布时间 : '+ (job.postTime|date:'yyyy-MM-dd hh:mm:ss')"></label>

    </div>
    <div class="info">
        <p class="money">
            <span ng-bind="job.wage+'元/'+job.salaryUnit" class="wage"></span>
            <span ng-bind="job.timeToPay" class="time-to-pay"></span>
        </p>
        <%--
            /${job.salaryUnit}
        --%>

      <p><span class="context-title">有效日期 : </span><span ng-bind="job.expiredTime|date:'yyyy-MM-dd'"></span></p>
      <p><span class="context-title">工作时间 : </span></span><span ng-bind="job.workTime"></span></p>
      <p><span class="context-title">工作区域 : </span><span ng-bind="job.region"></span></p>
      <p><span class="context-title">工作地点 : </span><span ng-bind="job.workPlace"></span></p>
    </div>
    <div class="description">
      <p class="pin-title">工作详情
        <i class="pin-arrow"></i>
      </p>
      <p><span class="context-title">工作内容 : </span><span ng-bind="job.jobDetail"></span></p>
      <p><span class="context-title">工作要求 : </span><span ng-bind="job.jobDescription"></span></p>
    </div>
      <div class="description">
          <p class="pin-title">联系方式
              <i class="pin-arrow"></i>
          </p>
          <p><span class="context-title" style="letter-spacing: 5px;">联系人 :</span><span ng-bind="job.contact"></span></p>
          <p><span class="context-title">联系电话 :</span><span><img src="/gen.do?jobId={{ job.id }}"></span></p>
          <p><span class="context-title" style="letter-spacing: 7px;">QQ号 :</span><span ng-bind="job.contactQq"></span></p>
      </div>
<%--    <div class="contact">
      <p><span class="context-title">联系人 : </span><span ng-bind="job.contact"></span></p>
      <p class="phone" ><span class="context-title">联系电话 : </span><span ><img src="/gen.do?jobId={{ job.id }}"></span></p>
    </div>--%>
    <div class="comment clearfix">
      <p class="pin-title">用户评论
        <i class="pin-arrow"></i>
      </p>
      <div class="operates">
        <div class="operate">
          <span id="like" ng-attr-data-id="{{ job.id }}" class="fa fa-thumbs-up" style="cursor: pointer"></span>
          <p ng-bind="job.likes"></p>
        </div>
        <%--<div class="operate">--%>
          <%--<span  id="dislike" data-id="${job.id}" class="fa fa-thumbs-down"></span>--%>
          <%--<p >${job.dislikes}</p>--%>
        <%--</div>--%>
        <div class="operate">
          <span id="toComment" class="fa fa-comment" style="cursor: pointer" ></span>
          <p ng-bind="job.reviewCount"></p>
        </div>
<%--        <div class="operate">
          <span id="complaint" data-id="${job.id}" class="text" style="cursor: pointer" >举报</span>
        </div>--%>
      </div>
      <jsp:include page="block/comment.jsp">
        <jsp:param name="postId" value="${job.id}"/>
      </jsp:include>
      <div class="content" id="contents">
        <div ng-class="{'no-border-bottom' : $last}" ng-repeat="review in job.reviews">
            <img src="/static/images/users/{{ review.member.profilePhotoId }}" alt="user photo">
            <p >{{review.member.username}}
            <a class="red delete-review" href="javascript:void(0);"
               ng-attr-data-id="{{ job.id }}" data-reviewId="{{ review.id }}"
               ng-show="{{ currentUser.id == review.member.id }}"> 删除 </a>
           </p>
            <div class="span"><span ng-bind="review.content"></span></div>
        </div>
      </div>

      <div class="load-more" style="text-align: center;display: none">
        <button id="loadMore">加载更多评论</button>
      </div>
    </div>
  </div>
</div>

<%--脚部--%>
<jsp:include page="block/footer.jsp"/>
<script>
    var job = JSON.parse('${ju:toJson(job)}');
    job.expiredTime = new Date(job.expiredTime);
    job.favStatus = JSON.parse('${ju:toJson(favStatus)}');
    job.reviewCount = JSON.parse('${ju:toJson(reviewCount)}');
    job.reviews = JSON.parse('${ju:toJson(reviews)}');
    var currentUser = JSON.parse('${ju:toJson(currUser)}');
    console.log(currentUser);
    console.log(job.reviews);
</script>
<script src="/scripts/comment.js"></script>
<script src="/scripts/jobdetail.js"></script>

</body>
</html>
