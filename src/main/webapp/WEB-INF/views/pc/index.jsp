<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-5-7
  Time: 下午3:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>

<%--html头部--%>
<!doctype html>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <title>首页</title>
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
<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade
    your browser</a> to improve your experience.</p>
<![endif]-->
<%--顶栏--%>
<jsp:include page="block/top-bar.jsp"/>
<%--页首--%>
<jsp:include page="block/header.jsp"/>

<style>

</style>

<div class="container">
    <%--轮播--%>
    <jsp:include page="block/silder.jsp"/>
    <%--侧边栏--%>
    <jsp:include page="block/side.jsp"/>

    <!-- 正文 -->
    <div class="main index">
        <div class="segment">
            <p class="pin-title"><a href="/list/job" style="color:#fff;">最火的兼职</a>
                <i class="pin-arrow"></i>
            </p>
        </div>


        <div class="segment jobs">
            <%--兼职--%>
            <c:forEach var="job" items="${jobs}">
                <a href="/item/job/${job.id}" style="color:#333333;">
                    <div class="job-slip">
                        <span class="cate"
                              style="background-color: ${job.category.themeColor};">${job.category.name}</span>
                        <span class="content">${job.title}</span>
                    </div>
                </a>


            </c:forEach>
        </div>

        <div class="segment">
            <p class="pin-title"><a href="/list/sh" style="color:#fff;">最畅销的二手</a>
                <i class="pin-arrow"></i>
            </p>
        </div>
        <div class="shs">

            <%--二手--%>
            <c:forEach var="sh" items="${shs}" varStatus="status">
                <a href="/item/sh/${sh.id}" style="color:#333333;">

                    <div class="sh-slip fl ${status.index == 2 ? 'no-margin':null}">
                        <div class="shs_pic"><img src="" data-pid="${sh.picturePath}" alt="" class="sh-item"></div>
                        <p class="title">
                            <c:choose>
                                <c:when test="${fn:length(sh.title) > 12}">
                                    <c:out value="${fn:substring(sh.title, 0, 12)}..."/>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${sh.title}"/>
                                </c:otherwise>
                            </c:choose>
                        </p>

                        <div class="fl">
                            <p class="sh-cate">${sh.category.name} <span>${sh.depreciationRate}</span></p>

                            <p class="sh-info">
                                <c:choose>
                                    <c:when test="${fn:length(sh.tradePlace) > 6}">
                                        <c:out value="${fn:substring(sh.tradePlace, 0, 6)}..."/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${sh.tradePlace}"/>
                                    </c:otherwise>
                                </c:choose>
                                <span class="theTime" data-time="<fmt:formatDate value="${sh.postTime}" pattern="yyyy-MM-dd HH:mm:ss"/>">
                                </span>
                            </p>
                        </div>
                        <span class="fr">￥${sh.sellPrice.intValue()}</span>
                    </div>
                </a>
            </c:forEach>
        </div>


        <div style="clean:both"></div>

        <div class="segment">
            <p class="pin-title"><a href="/about/index.html#fourthPage" style="color:#fff;">最帅的我们</a>
                <i class="pin-arrow"></i>
            </p>
        </div>
        <div class="segment auto about">
            <div>
                <a href="/about/index.html#firstPage"><span class="fa fa-compass"></span></a>

                <p>使用指南</p>
            </div>
            <div>
                <a href="/about/index.html#secondPage"><span class="fa fa-users"></span></a>

                <p>关于我们</p>
            </div>
            <div>
                <a href="/about/index.html#thirdPage"><span class="fa fa-phone"></span></a>

                <p>联系我们</p>
            </div>
            <div>
                <a href="/about/index.html#fourthPage"><span class="fa fa-users"></span></a>

                <p>加入我们</p>
            </div>
        </div>

    </div>

</div>

<%--脚部--%>
<jsp:include page="block/footer.jsp"/>
<script src="/scripts/sh-pic.js"></script>
<script>
    (function () {
        var $theTime= $(".theTime");
        console.log($theTime);
        $theTime.each(function(i){
           formatTime($(this));
        });

        function formatTime($this){
            var post = new Date($this.attr("data-time"));
            var date = new Date();
            var time = date.getTime() - post.getTime();
            if(time < 0){
                return "1分钟前";
            }
            if (date.getDate() === post.getDate() && time < 86400000) {
                if (time < 60 * 60 * 1000) {
                    $this.text(Math.ceil(time / (60 * 1000)) + "分钟前");
                } else {
                    $this.text(Math.ceil(time / (60 * 60 * 1000)) + "小时前");
                }

            } else {
                $this.text((post.getMonth() + 1) + "月" + post.getDate() + "日");
            }
        }
    })();
</script>
</body>
</html>
