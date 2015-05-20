<%@ page import="cn.fh.security.credential.Credential" %>
<%@ page import="cn.fh.security.utils.CredentialUtils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-5-9
  Time: 下午3:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="top-bar">
  <div class="top-bar-content">
    <p>山东理工大学</p>
    <ul>
      <c:if test="${not empty sessionScope.user}">
        <li><a href="/user/" id="user-name-label">${sessionScope.user.username}</a>&nbsp;|&nbsp;</li>
        <li><a href="/user/logout">我的桃李街 <i class="fa fa-caret-down"> </i></a></li>
      </c:if>

      <c:if test="${empty sessionScope.user}">
        <li> <a href="/login">登陆</a>&nbsp;|&nbsp;</li>
        <li> <a href="/register">注册</a></li>
      </c:if>
    </ul>
  </div>

</div>
<div style="clear:both"></div>
