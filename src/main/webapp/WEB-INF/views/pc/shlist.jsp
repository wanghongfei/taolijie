<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-5-20
  Time: 下午12:16
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>



<%request.setCharacterEncoding("UTF-8") ;%>
<%--html头部--%>


<!doctype html>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <title>二手列表</title>
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

      <div class="shlist main">
          <ul class="nav-bar">
              <li>最新发布</li>
              <li>最热二手</li>
              <li class="choose">结算方式&nbsp;&nbsp;&nbsp;&nbsp;<i class="fa fa-caret-down"></i>
                  <div class="choose-menu">
                      <ul>
                          <li class="actived">全部</li>
                          <li>日结</li>
                          <li>周结</li>
                          <li class="no-border">月结</li>
                      </ul>
                  </div>
              </li>
          </ul>

          <div class="shs">
              <c:forEach var="sh" items="${shs}" varStatus="status">
                  <a href="/item/sh/${sh.id}" style="color: #333333">
                      <div class="sh-slip fl ${(status.index+1)%3 == 0 ? 'no-margin-right':''}" >
                          <img src="/images/pig.jpg" alt="">
                          <p class="titile">${sh.title}</p>
                          <div class="fl">
                              <p>${sh.category.name}</p>
                              <span>${sh.member.username}</span>
                              <%--换成member的role--%>
                              <%--<span class="theme-color">1</span>--%>
                          </div>
                          <span class="fr">￥${sh.sellPrice.intValue()}</span>
                      </div>
                  </a>
              </c:forEach>
          </div>

          <div style="clear:both"></div>

          <div class="page">
              <ul>
                  <c:if test="${page != 1 && pageStatus !=0}">
                      <li><a class="next" href="/list/sh?page=${page-1}">上一页</a></li>
                  </c:if>
                  <c:if test="${pageStatus == 2}">
                      <li><a class="next" href="/list/sh?page=${page+1}">下一页</a></li>
                  </c:if>
                  <c:if test="${pageStatus == 0 }">
                      <h2>没有更多了</h2>
                  </c:if>
              </ul>
          </div>

      </div>


</div>

<%--脚部--%>
<jsp:include page="block/footer.jsp"/>

</body>
</html>

