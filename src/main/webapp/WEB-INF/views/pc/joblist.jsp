<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ju" uri="JsonUtils" %>
<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-5-9
  Time: 下午3:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page session="false" %>

<%request.setCharacterEncoding("UTF-8");%>
<%--html头部--%>


<!doctype html>
<html class="no-js" ng-app="tljApp" ng-controller="jobListCtrl">
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
    <link rel="stylesheet" href="/styles/joblist.css"/>
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
<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade
    your browser</a> to improve your experience.</p>
<![endif]-->


<%--顶栏--%>
<jsp:include page="block/top-bar.jsp"/>
<%--页首--%>
<jsp:include page="block/header.jsp"/>

<div class="container">
    <style>.ng-cloak{display: none}</style>

    <%--轮播--%>
    <jsp:include page="block/silder.jsp"/>
    <%--侧边栏--%>
    <jsp:include page="block/side.jsp"/>

    <!-- 正文 -->
    <div class="joblist main">

        <ul class="nav-bar">
            <li class="" ng-click="getHot()">热门推荐</li>
            <li class="choose"><span class="choose-title" data-default="区域选择">区域选择</span>&nbsp;&nbsp;&nbsp;&nbsp;<i class="fa fa-caret-down"> </i>
                <div class="choose-menu" data-type="region" >
                <span class="active">全部</span>
                <span>张店区</span>
                <span>周村区</span>
                <span>淄川区</span>
                <span>临淄区</span>
                <span>博山区</span>
                <span>桓台县</span>
                <span>高青县</span>
                <span>沂源县</span>
                </div>

            </li>
            <li class="choose"><span class="choose-title" data-default="结算方式">结算方式</span>&nbsp;&nbsp;&nbsp;&nbsp;<i class="fa fa-caret-down"></i>
                <div class="choose-menu" data-type="timeToPay">
                    <span class="active">全部</span>
                    <span>日结</span>
                    <span>周结</span>
                    <span>月结</span>
                    <span>完工结算</span>
                </div>
            </li>
        </ul>

        <div class="lists">
            <span class="loading-page"></span>

            <style ng-repeat-start="job in list">
                .list[data-color="{{job.category.themeColor}}"] .list-type{
                    background-color:{{job.category.themeColor}};
                }
                .list[data-color="{{job.category.themeColor}}"]:hover .list-type{
                    background-color: #fff;
                    border: 2px solid  {{job.category.themeColor}};
                }
                .list[data-color="{{job.category.themeColor}}"]:hover .list-type span{
                    color: {{job.category.themeColor}};
                }
            </style>
            <a ng-repeat-end href="/item/job/{{job.id}}" target="_blank" style="color: #353f4f"
               ng-cloak class="ng-cloak">
                <div class="list " data-color="{{job.category.themeColor}}">
                    <div class="list-type">
                        <span ng-bind="job.category.name"></span>
                    </div>
                    <div class="list-title">{{job.title}}
                        <span class="right-span" ng-bind="'￥'+job.wage+'元/'+job.salaryUnit"></span>
                    </div>
                    <span class="workplace"><i class="fa fa-map-marker"></i> {{job.region}}</span>&nbsp;&nbsp;
                        <span><i class="fa fa-clock-o"></i> {{job.postTime | date:'yyyy-MM-dd HH:mm:ss'}}</span>
                    <span class="right-span" ng-bind="job.timeToPay"></span>
                </div>
            </a>
        </div>
        <div style="clear:both"></div>
        <div class="page">
            <ul>
                <li ng-cloak class="ng-cloak"><a class="next" ng-click="pageChange(false)" ng-if="lastPage()" href="javascript:;">上一页</a></li>
                <li ng-cloak class="ng-cloak"><a class="next" ng-click="pageChange(true)" ng-if="nextPage()" href="javascript:;">下一页</a></li>
            </ul>
            </li>
        </div>
    </div>


</div>

<%--脚部--%>

<jsp:include page="block/footer.jsp"/>
<script src="/scripts/list/listoperate.js"></script>
<script src="/scripts/joblist.js"></script>

</body>
</html>
