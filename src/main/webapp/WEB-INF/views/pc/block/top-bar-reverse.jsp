<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-5-9
  Time: 下午3:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>

<span class="loading-page"></span>
<div class="top-bar top-bar-reverse">
  <div class="top-bar-content">
    <p  ><a href="/" style="color: #fff;">桃李街首页</a></p>
    <ul>

      <c:if test="${not empty sessionScope.user}">
        <li><a href="/user/profile">${sessionScope.user.username} &nbsp;|&nbsp;</a></li>
        <li><a href="/logout">注销 
            <%--
            <i class="fa fa-caret-down"> </i>
            --%>
        </a></li>
      </c:if>

      <c:if test="${empty sessionScope.user}">
        <li> <a href="/login">登录</a>&nbsp;|&nbsp;</li>
        <li> <a href="/register">注册</a></li>
      </c:if>
      <%--<li> <a href="">登录</a>&nbsp;|&nbsp;</li>--%>
      <%--<li> <a href="">注册</a>&nbsp;|&nbsp;</li>--%>
      <%--<li>我的桃李街 <i class="fa fa-caret-down"> </i></li>--%>
    </ul>
  </div>

</div>
<div style="clear:both"></div>
