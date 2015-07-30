<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-6-2
  Time: 下午7:18
  To change this template use File | Settings | File Templates.
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="ju" uri="/WEB-INF/tld/JsonUtils.tld"%>

<%request.setCharacterEncoding("UTF-8");%>
<%--html头部--%>


<!doctype html>
<html class="no-js" ng-app="tljApp" ng-controller="resumeListCtrl">
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


      <div class="resumelist main">
          <ul class="nav-bar">
              <li>热门推荐</li>
              <li>求职意向</li>
              <li class="choose">性别选择<i class="fa fa-caret-down"></i>
                  <div class="choose-menu">
                      <ul>
                          <li class="actived">男</li>
                          <li>女</li>
                      </ul>
                  </div>
              </li>
          </ul>
          <div class="lists">
                  <a href="/item/resume/{{resume.id}}" ng-repeat="resume in resumes">
                      <div class="list">
                          <img src="/static/images/users/{{resume.photoPath}}" alt="">
                          <div>
                              <div class="fl">
                                  <p class="info">
                                      <span ng-bind="resume.name" class="name"></span>
                                      <i class="fa fa-cog theme-color"></i>
                                      <span ng-bind="resume.age"></span>岁
                                  </p>
                                  <p class="intent">
                                      <span class="intent-title">求职意向</span>
                                      <%--<span>${resume.}</span>--%>
                                  </p>
                              </div>
                              <div class="fr">
                                  <%--<p>${resume.}</p>--%>
                                  <p>
                                      <span class="time" ng-bind="resume.createdTime"></span>
                                  </p>
                              </div>
                          </div>
                      </div>
                  </a>
          </div>
          <div style="clear:both"></div>
          <div class="page">
              <ul>
                  <c:if test="${page != 1 && pageStatus !=0}">
                      <li><a class="next" href="/list/resume?page=${page-1}">上一页</a></li>
                  </c:if>
                  <c:if test="${pageStatus == 2}">
                      <li><a class="next" href="/list/resume?page=${page+1}">下一页</a></li>
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
<script src="/scripts/list/resumelist.js"></script>
<script>
    var resumes = JSON.parse('${ju:toJson(resumes)}');
</script>

</body>
</html>
