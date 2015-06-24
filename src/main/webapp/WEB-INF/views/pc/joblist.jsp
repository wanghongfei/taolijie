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
    <div class="joblist main">
        <ul class="nav-bar">
            <li>最新发布</li>
            <li>最热兼职</li>
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
        <div class="lists">
            <c:forEach var="job" items="${jobs}" varStatus="status">
                <a href="/item/job/${job.id}" style="color: #353f4f">
                    <div class="list">
                        <div class="list-type">

                            <span>${job.category.name}</span>
                        </div>
                        <div class="list-title">${job.title}<span>${job.verified ? '已认证': ''}</span></div>
                        <span>${job.workPlace}</span>&nbsp;&nbsp;
                        <span>${job.wage.intValue()}元/${job.salaryUnit}</span>&nbsp;&nbsp;
                        <span>${job.timeToPay}</span>
                        <span>
                        <fmt:formatDate value="${job.postTime}" pattern="yyyy-MM-dd" /></span>
                    </div>
                </a>
            </c:forEach>
        </div>
        <div style="clear:both"></div>
        <div class="page">
            <ul>
                <c:if test="${page != 1 && pageStatus !=0}">
                    <li><a class="next" href="/list/job?page=${page-1}">上一页</a></li>
                </c:if>
                <c:if test="${pageStatus == 2}">
                    <li><a class="next" href="/list/job?page=${page+1}">下一页</a></li>
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