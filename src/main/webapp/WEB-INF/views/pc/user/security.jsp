<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-5-17
  Time: 下午8:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
<%request.setCharacterEncoding("UTF-8") ;%>



<!doctype html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <title>安全设置</title>
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
    <jsp:param name="navShow" value="security"/>
  </jsp:include>

  <div class="segment end-segment">
    <p class="pin-title style2 ">修改密码</p>
    <form action="" id="ChangPasswordForm">
      <div class="form-group">
        <label for="">原密码</label>
        <input type="password" class="form-control" name="oldPassword" required>
      </div>
      <div class="form-group">
        <label for="">新密码</label>
        <input type="password" class="form-control" name="newPassword" required>
      </div>
      <div class="form-group">
        <label for="">再确认</label>
        <input type="password" class="form-control" name="rePassword" required>
      </div>

      <div class="segment">
        <div class="submit-btn big-btn dark-green-bg">
          <span href="javascript:void(0);">修改密码</span>
        </div>
      </div>
    </form>

    <div class="jqmWindow dialog" >
      <div class="tlj_modal_header">桃李街提示</div>
      <div class="tlj_modal_content"></div>
    </div>
  </div>
</div>

<jsp:include page="../block/user-footer.jsp"></jsp:include>
<script src="/scripts/security.js"></script>
</body>
</html>
