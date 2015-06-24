<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-5-17
  Time: 下午8:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%request.setCharacterEncoding("UTF-8") ;%>


<!doctype html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <title>意见反馈</title>
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



<jsp:include page="../block/top-bar-reverse.jsp"></jsp:include>


<div class="container user">
    <jsp:include page="../block/user.jsp">
      <jsp:param name="navShow" value="feedback"/>
    </jsp:include>
  <div class="segment feedback end-segment">
    <div>
      <img src="/images/logo.jpg" alt="">
    </div>
    <form action="" class="" id="FeedBackForm">
      <div class="form-group">
        <textarea name="content" class="form-control" placeholder="把您最棒的意见，最有创意的想法，告诉小桃吧"></textarea >
        <div>
          <input type="text" name="email" placeholder="输入您的邮箱,方便与您沟通哦">
          <span class="fr big-btn theme-color-bg submit-btn">提交</span>
        </div>
      </div>
    </form>
  </div>
</div>

<jsp:include page="../block/user-footer.jsp"></jsp:include>
<script src="/scripts/feedback.js"></script>
</body>
</html>