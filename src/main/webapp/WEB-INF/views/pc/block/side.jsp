<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-5-9
  Time: 下午3:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="side">
    <div class="services">
        <p>我们的服务</p>
        <ul class="menu_left" id="menu_left">
            <li>
                <a href="/list/job"><i class="fa fa-history fa-lg"></i> 学生兼职</a>
                <ul class="submenu">
                    <c:forEach items="${sideJobCate}" var="jobCate">
                        <li><a href="/list/job?cate=${jobCate.id}">${jobCate.name}</a></li>
                    </c:forEach>
                </ul>
            </li>
            <li>
                <a href="/list/sh"><i class="fa fa-bicycle fa-lg"></i> 校园二手</a>
                <ul class="submenu">
                    <c:forEach items="${sideSHCate}" var="shCate">
                        <li><a href="/list/sh?cate=${shCate.id}">${shCate.name}</a></li>
                    </c:forEach>
                </ul>
            </li>
            <li>
                <a href="/list/resume"><i class="fa fa-paper-plane-o fa-lg"></i> 求职简历</a>
                <ul class="submenu">
                    <c:forEach items="${sideResumeCate}" var="resumeCate">
                        <li><a href="/list/resume?cate=${resumeCate.id}">${resumeCate.name}</a></li>
                    </c:forEach>
                </ul>
            </li>
            <li>
                <a href="/user/job/post"><i class="fa fa-check-square-o fa-lg"></i> 发布信息</a>
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
                    <li class="news-${status.index+1}" style="">
                        <a href="/detail/news/${title.id}">
                            <%--${title.title}--%>
                        <img src="/images/1.gif" alt="">
                        </a>
                    </li>


            </c:forEach>
        </ul>
    </div>
</div>


