<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-4-26
  Time: 下午8:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- header section start-->
<div class="header-section">

  <!--toggle button start-->
  <a class="toggle-btn"><i class="fa fa-bars"></i></a>
  <!--toggle button end-->

  <!--notification menu start -->
  <div class="menu-right">
    <ul class="notification-menu">
      <li>
        <a href="#" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
          <c:if  test="${currUser.profilePhotoId == null}">
            <img src="/images/default-img.jpg" alt="">
          </c:if>
          <c:if  test="${currUser.profilePhotoId != null}">
            <img src="/static/images/users/${currUser.profilePhotoId}" alt="">
          </c:if>
          ${username}
          <span class="caret"></span>
        </a>
        <ul class="dropdown-menu dropdown-menu-usermenu pull-right">
          <li><a href="#"><i class="fa fa-user"></i> 我的资料</a></li>
          <li><a href="#"><i class="fa fa-cog"></i> 设置</a></li>
          <li><a href="/manage/logout"><i class="fa fa-sign-out"></i> 注销</a></li>
        </ul>
      </li>
    </ul>
  </div>
  <!--notification menu end -->

</div>
<!-- header section end-->
