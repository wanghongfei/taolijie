<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-5-9
  Time: 下午3:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%request.setCharacterEncoding("UTF-8");%>
<%--html头部--%>


<!doctype html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <title>兼职</title>
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
    <div class="main index">
      <div class="segment">
        <p class="pin-title">校园聚焦点
          <i class="pin-arrow"></i>
        </p>
      </div>
      <div class="segment focus">
        <div class="focus_detail">
          <div class="zixun zixun_top">
            <a href=""><img src="/static/images/users/${news.headPicturePath}" alt=""></a>
          </div>

          <div class="zixun zixun_bottom">
            <p class="title"><a href="">${news.title}</a></p>
            <p><i class="fa fa-history fa-lg"></i>时间:
                <fmt:formatDate value="${news.time}" pattern="yyyy-MM-dd"/>
            </p>
            <%--<p><i class="fa fa-map-marker fa-lg"></i>地点:理工大西侧三体西侧</p>--%>
            <div class="jianjie">
              <i class="fa fa-file-text-o fa-lg"></i>
              简介:<br>
                ${news.content}
            </div></p>
          </div>
          <div style="clear:both"></div>
        </div>
      </div>
    </div>




</div>

<%--脚部--%>
<jsp:include page="block/footer.jsp"/>

</body>
</html>
