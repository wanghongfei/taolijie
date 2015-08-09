<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-5-20
  Time: 下午12:16
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%request.setCharacterEncoding("UTF-8");%>
<%--html头部--%>


<!doctype html>
<html class="no-js" ng-app="tljApp" ng-controller="ShListCtrl">
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
    <link rel="stylesheet" href="/styles/shlist.css "/>
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
    <style>.ng-cloak {
        display: none
    }</style>


    <%--轮播--%>
    <jsp:include page="block/silder.jsp"/>
    <%--侧边栏--%>
    <jsp:include page="block/side.jsp"/>

    <!-- 正文 -->

    <div class="shlist main">

        <ul class="nav-bar">
            <li>热门推荐</li>
            <li class="choose"><span class="choose-title" data-default="新旧程度">新旧程度</span>&nbsp;&nbsp;&nbsp;&nbsp;<i
                    class="fa fa-caret-down"></i>

                <div class="choose-menu" data-type="depreciationRate">
                    <span class="active">全部</span>
                    <span>全新</span>
                    <span>九成新</span>
                    <span>八成新</span>
                    <span>七成新</span>
                    <span>六成新</span>
                </div>
            </li>
            <li class="choose"><span class="choose-title" data-default="价格筛选">价格筛选</span>&nbsp;&nbsp;&nbsp;&nbsp;<i
                    class="fa fa-caret-down"></i>

                <div class="choose-menu menu2" data-type="price">
                    <span class="active">全部</span>
                    <span data-min="0" data-max="10">￥0-10</span>
                    <span data-min="10" data-max="50">￥10-50</span>
                    <span data-min="50" data-max="100">￥50-100</span>
                    <span data-min="100" data-max="500">￥100-500</span>
                    <span data-min="500" data-max="1000">￥500-1000</span>
                    <span data-min="1000" data-max="5000">￥1000-5000</span>
                    <span data-min="5000" data-max="5000">￥5000以上</span>
                </div>
            </li>
        </ul>
        <div class="shs">
            <span class="loading-page"></span>
            <a ng-repeat="sh in list" href="/item/sh/{{sh.id}}" ng-cloak class="ng-cloak" style="color: #333333">
                <div class="sh-slip fl" ng-class="">
                    <div class="shs_pic">
                        <img src="/static/images/users/{{sh.picturePath}}" data-pid="{{sh.picturePath}}" alt=""
                             class="sh-item">
                    </div>
                    <p class="titile">{{sh.title | omit:'12'}}</p>

                    <div class="fl">
                        <p class="sh-cate">{{sh.category.name}}<span ng-bind="sh.depreciationRate"></span></p>

                        <p class="sh-info">{{sh.tradePlace | omit:'6'}}<span ng-bind="sh.postTime | dateShow"></span>
                        </p>
                    </div>
                    <span class="fr">￥{{sh.sellPrice}}</span>
                </div>
            </a>
        </div>

        <div style="clear:both"></div>

        <div class="page">
            <ul>
                <li ng-cloak class="ng-cloak"><a class="next" ng-click="pageChange(false)" ng-if="hasLastPage()" href="javascript:;">上一页</a></li>
                <li ng-cloak class="ng-cloak"><a class="next" ng-click="pageChange(true)" ng-if="hasNextPage()" href="javascript:;">下一页</a></li>
            </ul>
            </li>
        </div>
    </div>
</div>

<%--脚部--%>
<jsp:include page="block/footer.jsp"/>
<script src="/scripts/angular/filter.js"></script>
<%--<script src="/scripts/sh-pic.js"></script>--%>
<script src="/scripts/list/listoperate.js"></script>
<script src="/scripts/list/shlist.js"></script>
</body>
</html>

