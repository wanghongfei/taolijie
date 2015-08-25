<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-6-2
  Time: 下午4:23
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%request.setCharacterEncoding("UTF-8") ;%>

<!doctype html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <title>${isFav?'我的收藏':'我的发布'}</title>
  <meta name="description" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

  <!-- build:css /styles/vendor.css -->
  <!-- bower:css -->
  <!-- endbower -->
  <!-- endbuild -->

  <!-- build:css({.tmp,app}) /styles/css/style.css -->
  <link rel="stylesheet" href="/styles/animate.css"/>
  <link rel="stylesheet" href="/styles/style.css">
  <%--图片上传美化--%>
  <link rel="stylesheet" href="/styles/webuploader.css"/>
  <!-- endbuild -->
  <link rel="stylesheet" href="/styles/jquery.bxslider.css">
  <%--<link rel="stylesheet" href="http://libs.useso.com/js/font-awesome/4.2.0/css/font-awesome.min.css">--%>
  <link rel="stylesheet" href="/styles/font-awesome.min.css"/>

  <!-- build:js /scripts/vendor/modernizr.js -->
  <script src="/scripts/modernizr.js"></script>
  <!-- endbuild -->

</head>
<body>
<!--[if lt IE 10]>
<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->

<jsp:include page="../block/top-bar-reverse.jsp"></jsp:include>

<div class="container user">
  <jsp:include page="../block/user.jsp">
      <jsp:param name="navShow" value="${isFav?'favlist':'postlist'}"/>
    </jsp:include>
  <style>
    .col_main_top .title a:hover{
      color: #666 !important;
    }
  </style>

  <div class="segment infos resume link-segment">
    <div class="quickbtn">
      <ul>
        <li><a href="/user/job/${isFav?'myfav':'mypost'}" >兼职信息</a></li>
        <li><a href="/user/sh/${isFav?'myfav':'mypost'}" style="border-bottom: 2px #4ccda4 solid;">二手物品</a></li>
        <%--${isFav?'<li><a href="/user/resume/myfav">兼职简历</a></li>':''}--%>
      </ul>
    </div>
    <div style="clear:both"></div>
    <div class="col_content">
      <c:forEach items="${shs}" var="sh">
        <div class="col_list">
          <div class="col_choice" style="line-height:112px; width:45px;">
            <input name="collection" type="checkbox" value="" data-id="${sh.id}" class="col_del_check">
          </div>
          <div class="col_style" style="">${sh.category.name}

          </div></a>
          <div class="col_main" style="margin-left:40px; width:630px;">
            <div class="col_main_top">
              <div class="title" style="width:450px;"><a href="/item/sh/${sh.id}" style="color: #333">${sh.title}</a></div>
            </div>
            <div class="col_main_bottom">
              <div class="location" style="width:180px;"><i class="fa fa-map-marker fa-lg"></i>
              <c:choose>
                  <c:when test="${fn:length(sh.tradePlace) > 10}">
                      <c:out value="${fn:substring(sh.tradePlace, 0, 10)}..."/>
                  </c:when>
                  <c:otherwise>
                      <c:out value="${sh.tradePlace}"/>
                  </c:otherwise>
              </c:choose>
             </div>
              <div class="salary" style="color:#a47e3c;width: 120px;" ><i class="fa fa-jpy fa-lg"></i> ${sh.sellPrice.intValue()}元</div>
              <%--<div class="salarystyle" >&nbsp;&nbsp;${job.timeToPay}</div>--%>
              <div class="time"><i class="fa fa-clock-o fa-lg"></i>
                <fmt:formatDate value="${sh.postTime}" pattern ="yyyy-MM-dd HH:mm" />
              </div>

                <%--<div class="clickno">800</div>--%>
            </div>
          </div>
          <div class="col_delete">
              <c:if test="${!isFav}">
            <a href="/user/sh/change/${sh.id}">修改</a>
          </c:if>
            <a href="javascript:void(0);" class="UserDel" data-id="${sh.id}" data-type="sh"  data-option = "${isFav?'fav':''}" >
                <%--
                <i class="fa fa-trash fa-2x"></i>
                --%>
                删除
            </a>
          </div>
        </div>
      </c:forEach>

      <div class="page">
        <ul>
          <c:if test="${page != 1 && pageStatus !=0}">
            <li><a class="next" href="${isFav?'/user/sh/myfav':'/user/sh/mypost'}?page=${page-1}">上一页</a></li>
          </c:if>
          <c:if test="${pageStatus == 2}">
            <li><a class="next" href="${isFav?'/user/sh/myfav':'/user/sh/mypost'}?page=${page+1}">下一页</a></li>
          </c:if>
          <c:if test="${pageStatus == 0 }">
            <h2>没有更多了</h2>
          </c:if>
        </ul>
      </div>
      <div class="col_delete_all">
        <div class="col_choice">
            <input name="" type="checkbox" value="" id="checkAll">
            <label for="checkAll" class="label-checkbox">全选</label>
            <a class="del_all" data-type="sh" data-option= "${isFav?'fav':''}">删除选中项</a>
        </div>
      </div>
    </div>
    <%--</div>--%>
  </div>
    </div>

  <jsp:include page="../block/user-footer.jsp"></jsp:include>
  <script src="/scripts/security.js"></script>
  </body>
  </html>
