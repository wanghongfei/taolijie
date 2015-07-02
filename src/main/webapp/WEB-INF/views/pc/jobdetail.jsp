<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-5-9
  Time: 下午3:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%request.setCharacterEncoding("UTF-8") ;%>

<!doctype html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <title>兼职-${job.title}</title>
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
      <a href="/" style="color:#fa6a38;"><p class="fl"><i class="fa fa-angle-left">&nbsp;&nbsp;</i>返回</p></a>
      <%--<p class="fr">分享</p>--%>
      <p class="fr" id="fav" data-id="${job.id}" data-type="job">
          <i class="fa ${favStatus? 'fa-heart':'fa-heart-o'}">&nbsp;&nbsp;</i>
          ${favStatus? '已收藏':'收藏'}
      </p>
    </div>
    <div style="clean:both"></div>
    <div class="title">
      <p>${job.title}</p>
      <span>兼职类型 : ${job.category.name}</span>
      <span>${job.verified ? '已认证' : '未认证'}</span>
      <span>发布时间 :
          <fmt:formatDate value="${job.postTime}" pattern="yyyy-MM-dd"/>

  </span>
    </div>
    <div class="info">
        <p class="money"><span>${job.wage.intValue()}元</span>${job.timeToPay}</p>
        <%--
            /${job.salaryUnit}
        --%>
      <p>有效日期 : <span>
        <fmt:formatDate value="${job.expiredTime}" pattern="yyyy-MM-dd"/>
      </span></p>
      <p>工作时间 : <span>${job.workTime}</span></p>
      <p>工作地点 : <span>${job.workPlace}</span></p>
    </div>
    <div class="description">
      <p class="pin-title">工作详情
        <i class="pin-arrow"></i>
      </p>
      <p>工作内容 : <span>${job.jobDetail}</span></p>
      <p>工作要求 : <span>${job.jobDescription}</span></p>
    </div>
    <div class="contact">
      <p>联系人 : <span>${job.contact}</span></p>
      <p class="phone" >联系电话 : <span >${job.contactPhone}</span></p>
    </div>
    <div class="comment clearfix">
      <p class="pin-title">用户评论
        <i class="pin-arrow"></i>
      </p>
      <div class="operates">
        <div class="operate">
          <span id="like" data-id="${job.id}" class="fa fa-thumbs-up" style="cursor: pointer"></span>
          <p >${job.likes}</p>
        </div>
        <%--<div class="operate">--%>
          <%--<span  id="dislike" data-id="${job.id}" class="fa fa-thumbs-down"></span>--%>
          <%--<p >${job.dislikes}</p>--%>
        <%--</div>--%>
        <div class="operate">
          <span id="toComment" class="fa fa-comment" style="cursor: pointer" ></span>
          <p >${reviewCount}</p>
        </div>
<%--        <div class="operate">
          <span id="complaint" data-id="${job.id}" class="text" style="cursor: pointer" >举报</span>
        </div>--%>
      </div>
      <div class="content" id="contents">
        <c:forEach var="review" items="${reviews}" varStatus="status">
        <div class="${status.index == status.count-1 ? 'no-border-bottom':null}" >
           <img src="/static/images/users/${review.member.profilePhotoId}" alt="user photo">
           <p>${review.member.username}
             <%--判断是该用户发的显示删除按钮--%>
             <c:if test="${sessionScope.user.id == review.member.id}">
               <a class="red delete-review" href="javascript:void(0);"  data-id="${job.id}" data-reviewId="${review.id}"> 删除</a>
             </c:if>

           </p>

           <span>${review.content}</span>
        </div>
        </c:forEach>
      </div>
      <jsp:include page="block/comment.jsp">
        <jsp:param name="postId" value="${job.id}"/>
      </jsp:include>
    </div>

  </div>


</div>

<%--脚部--%>
<jsp:include page="block/footer.jsp"/>
<script src="/scripts/comment.js"></script>
<script src="/scripts/jobdetail.js"></script>
</body>
</html>
