<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%request.setCharacterEncoding("UTF-8") ;%>




<!doctype html>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <title>消息通知</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="/styles/animate.css"/>
    <link rel="stylesheet" href="/styles/style.css">
    <link rel="stylesheet" href="/styles/webuploader.css"/>
    <link rel="stylesheet" href="/styles/jquery.bxslider.css">
    <%--<link rel="stylesheet" href="http://libs.useso.com/js/font-awesome/4.2.0/css/font-awesome.min.css">--%>
    <link rel="stylesheet" href="/styles/font-awesome.min.css"/>
    <link rel="stylesheet" href="/styles/user/notify.css"/>

    <script src="/scripts/modernizr.js"></script>

</head>
<body>
<!--[if lt IE 10]>
<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->


<jsp:include page="../block/top-bar-reverse.jsp"></jsp:include>

<div class="container user">
  <jsp:include page="../block/user.jsp">
    <jsp:param name="navShow" value="notify"/>
  </jsp:include>

    <div class="segment infos link-segment">
        <div class="nav">
            <ul>
                <li class="active"><a href="/">未读</a></li>
                <li><a href="/">已读</a></li>
            </ul>
        </div>

        <table class="note-table">
          <thead>
            <tr>
              <th ></th>
              <th>发件人</th>
              <th>消息内容</th>
              <th>发件时间</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>
                <input type="checkbox" class="choose" value="">
              </td>
              <td><span class="note-poster">发件人</span></td>
              <td>
                  <a class="note-content" href="">
                  你的兼职被人评论了,赶紧去回复吧</a>
              </td>
              <td>2019-09-09 12:00</td>
              <td>
                <button type="button" name="button" class="note-del-btn"><i class="fa fa-trash-o"></i></button>
              </td>
            </tr>
          </tbody>
        </table>

        <div class="operate">
          <input type="checkbox" name="name" value=""> 全选
          <button type="button" name="button">标记为已读</button>
          <button type="button" name="button">删除选中项</button>
        </div>
    </div>
<jsp:include page="../block/user-footer.jsp"></jsp:include>

</body>
</html>
