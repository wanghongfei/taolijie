<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-5-9
  Time: 下午3:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
<style>
    .side-news-body{
        width: 100%;
        height: 100%;
        background: url(/images/side.gif) no-repeat;
        padding: 0 8px 0 8px;
    }
    .side-news-body span{
        display: inline-block;
        color:#fff;
        text-align: left;
        text-indent: 2em;
        line-height: 28px;
        margin-top: 60px;
        width: 160px;
        word-break:break-all;
    }
</style>
<div class="side">
    <div class="services">
        <p>我们的服务</p>
        <ul class="menu_left" id="menu_left">
            <li>
                <a href="/list/job" class="menu-left-item"><i class="fa fa-history fa-lg"></i>学生兼职</a>
                <ul class="submenu">
                    <c:forEach items="${sideJobCate}" var="jobCate">
                        <li><a href="/list/job?cate=${jobCate.id}">${jobCate.name}</a></li>
                    </c:forEach>
                </ul>
            </li>
            <li>
                <a href="/list/sh" class="menu-left-item"><i class="fa fa-bicycle fa-lg"></i>校园二手</a>
                <ul class="submenu">
                    <c:forEach items="${sideSHCate}" var="shCate">
                        <li><a href="/list/sh?cate=${shCate.id}">${shCate.name}</a></li>
                    </c:forEach>
                </ul>
            </li>
            <li>
                <a href="/list/resume" class="menu-left-item"><i class="fa fa-paper-plane-o fa-lg"></i>求职简历</a>
                <ul class="submenu">
                    <c:forEach items="${sideResumeCate}" var="resumeCate">
                        <li><a href="/list/resume?cate=${resumeCate.id}">${resumeCate.name}</a></li>
                    </c:forEach>
                </ul>
            </li>
            <li>
                <a href="/user/job/post" class="menu-left-item"><i class="fa fa-check-square-o fa-lg"></i>发布信息</a>
                <ul class="submenu">
                    <li><a href="/user/job/post">发布兼职</a></li>
                    <li ><a href="/user/sh/post">发布二手</a></li>
                    <li ><a href="/user/resume/create">我的简历</a></li>
            </li>
        </ul>
    </div>
    <div class="news">

        <p>  <a href="/list/news">校园聚焦点
            <i class="news-arrow"></i>
        </a>
        </p>

        <ul>
            <c:forEach var="title" items="${titles}" varStatus="status">
                    <li class="news-${status.index+1}" style="overflow: hidden">
                        <a href="/detail/news/${title.id}">
                            <%--${title.title}--%>
                           <%--<div class="side-news-body" style="background-position: 0 -${status.index * 140}px;">--%>
                          <div class="side-news-body" style="background-image: url(/images/1.gif);;">
                            <span>
                            <c:choose>
                                <c:when test="${fn:length(title.title) > 12}">
                                    <c:out value="${fn:substring(title.title, 0, 12)}..."/>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${title.title}"/>
                                </c:otherwise>
                            </c:choose>
                          </span>
                           </div>
                        </a>
                    </li>
            </c:forEach>
        </ul>
    </div>
</div>
