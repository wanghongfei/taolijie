<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="/styles/user/post.css">
<div class="segment user-nav">
    <ul>
        <li class="${param.navShow == 'job'?'active':''}" ><a href="/user/job/post">发布兼职</a></li>
        <li class="${param.navShow == 'sh'?'active':''}" ><a href="/user/sh/post">发布二手</a></li>
        <li class="${param.navShow == 'resume'?'active':''}" ><a href="/user/resume/create">我的简历</a></li>
    </ul>
</div>