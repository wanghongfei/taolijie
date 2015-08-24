<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-5-9
  Time: 下午3:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>

<!-- 头部 -->
<div class="header">
  <div class="header-content">
    <div class="logo">
      <a href="/">
        <img src="/images/logo.jpg" alt="logo" class="logo-img">
      </a>
    </div>
    <form id="search-form" action="/search">
    <div class="search-bar">

      <div class="search-left"></div>
      <input type="text" placeholder="搜索你最想要的" name="content" id="search-input">
      <input type="hidden" value="0" name="type" id="search-type"/>
      <div class="search-right">
        搜索
        <div class="active-text jianzhi">
          <span id="search-job">兼职</span>
        </div>
        <div class="active-text">
          <span id="search-sh">二手</span>
        </div>
      </div>
    </div>
    </form>

    <div class="text">
      <span><a href="/">首页</a></span>
      <span><a href="/user/job/post">发布信息</a></span>
    </div>
  </div>

</div>
