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
    <ul>
      <li>学生兼职</li>
      <li>校园二手</li>
      <li>求职简历</li>
      <li>发布信息</li>
    </ul>
  </div>
  <div class="news">
    <p>校园聚焦点
      <i class="news-arrow"></i>
    </p>
    <ul>
        <c:forEach var="title" items="${titles}" varStatus="status">
            <li class="news-${status.index+1}" style="padding-top: 50px;">${title.title}</li>
        </c:forEach>
      <%--<li class="news-1"></li>--%>
      <%--<li class="news-2">大学里的拉时刻的减肥...</li>--%>
      <%--<li class="news-3">大学里的拉时刻的减肥...</li>--%>
    </ul>
  </div>
</div>
