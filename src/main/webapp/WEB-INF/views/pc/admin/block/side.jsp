<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-4-26
  Time: 下午8:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- left side start-->
<div class="left-side sticky-left-side">

  <!--logo and iconic logo start-->
  <div class="logo">
    <a href="/admin/index"><img src="/admin/images/logo.png" alt=""></a>
  </div>

  <div class="logo-icon text-center">
    <a href="admin/index"><img src="/admin/images/logo_icon.png" alt=""></a>
  </div>
  <!--logo and iconic logo end-->


  <div class="left-side-inner">

    <!-- visible to small devices only -->
    <div class="visible-xs hidden-sm hidden-md hidden-lg">
      <div class="media logged-user">
        <img alt="" src="/admin/images/photos/user-avatar.png" class="media-object">
        <div class="media-body">
          <h4><a href="#">Wynfrith</a></h4>
          <span>"Hello There..."</span>
        </div>
      </div>

      <h5 class="left-nav-title">账号信息</h5>
      <ul class="nav nav-pills nav-stacked custom-nav">
        <li><a href="#"><i class="fa fa-user"></i> <span>用户资料</span></a></li>
        <li><a href="#"><i class="fa fa-cog"></i> <span>设置</span></a></li>
        <li><a href="#"><i class="fa fa-sign-out"></i> <span>注销</span></a></li>
      </ul>
    </div>

    <!--sidebar nav start-->
    <ul class="nav nav-pills nav-stacked custom-nav">
      <li><a href="/manage/"><i class="fa fa-laptop"></i> <span>控制台</span></a></li>
      <li class="menu-list "><a href=""><i class="fa fa-bookmark"></i> <span>查看帖子</span></a>
        <ul class="sub-menu-list">
          <li ><a href="/manage/job"> 兼职</a></li>
          <li><a href="/manage/sh"> 二手</a></li>
          <li><a href="/manage/resume"> 简历</a></li>
        </ul>
      </li>

      <li class="menu-list"><a href=""><i class="fa fa-user"></i> <span>用户管理</span></a>
        <ul class="sub-menu-list">
          <li ><a href="/manage/user/add"> 添加用户</a></li>
          <li><a href="/manage/user"> 管理用户</a></li>

        </ul>
      </li>
      <li class="menu-list"><a href=""><i class="fa fa-tasks"></i> <span>校园聚焦点</span></a>
        <ul class="sub-menu-list">
          <li ><a href="/manage/news/add"> 添加新闻</a></li>
          <li><a href="/manage/news"> 管理新闻</a></li>

        </ul>
      </li>

      <%--<li class="menu-list"><a href=""><i class="fa fa-bullhorn"></i> <span>公告管理</span></a>--%>
        <%--<ul class="sub-menu-list">--%>
          <%--<li ><a href="/manage/"> 新增公告</a></li>--%>
          <%--<li><a href="/manage/"> 所有公告</a></li>--%>

        <%--</ul>--%>
      <%--</li>--%>
      <%--<li class="menu-list "><a href=""><i class="fa fa-envelope  "></i> <span>发送通知</span></a>--%>
        <%--<ul class="sub-menu-list">--%>
          <%--<li ><a href="/manage/"> 新增通知</a></li>--%>
          <%--<li><a href="/manage/"> 所有通知</a></li>--%>
        <%--</ul>--%>
      <%--</li>--%>


      <li class="menu-list"><a href=""><i class="fa fa-bullhorn"></i> <span>分类管理</span></a>
        <ul class="sub-menu-list">
          <li><a href="/manage/cate/job"><i class="fa fa-folder-open-o"></i> <span>兼职分类</span></a>
          <li><a href="/manage/cate/sh"><i class="fa fa-folder-open-o"></i> <span>二手分类</span></a>
        </ul>
      </li>

      <%--<li><a href="/manage/statics"><i class="fa fa-cloud"></i> <span>资源管理</span></a></li>--%>

      <%--<li><a href="/manage/banner"><i class="fa fa-picture-o"></i> <span>修改Banner</span></a></li>--%>
      <%--<li><a href="/manage/complaints"><i class="fa fa-bug"></i> <span>投诉管理</span></a></li>--%>
      <%--<li><a href="/manage/feedback"><i class="fa fa-file-text"></i> <span>用户反馈</span></a></li>--%>
      <%--<li><a href="/manage/404"><i class="fa fa-bug"></i> <span>404</span></a></li>--%>
      <%--<li><a href="/manage/500"><i class="fa fa-bug"></i> <span>500</span></a></li>--%>
    </ul>
    <!--sidebar nav end-->
  </div>
</div>
<!-- left side end-->
