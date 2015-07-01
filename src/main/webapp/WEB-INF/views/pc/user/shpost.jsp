<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-5-17
  Time: 下午8:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%request.setCharacterEncoding("UTF-8") ;%>


<!doctype html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <title>二手发布</title>
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
  <jsp:include page="../block/post-nav.jsp">
    <jsp:param name="navShow" value="sh"/>
  </jsp:include>
  <div class="img-list-wrapper">
      <ul>
        <li class="img-list-btn dark-green-bg">
            <span class="btn-add">添加照片</span>
        </li>
      </ul>
      <div class="clearfix"></div>
  </div>
  <div class="segment end-segment">
    <p class="pin-title dark-green-bg ">二手物品介绍
      <i class="pin-arrow  dark-green-arrow"></i>
    </p>
    <form action="" id="ShPostForm">
      <div class="form-group">
        <label for="">标题<span class="theme-color">*</span></label>
        <input name="title" type="text" class="form-control" value="${sh.title}" placeholder="请填写物品名称" required>
      </div>
      <div class="form-group">
        <label for="">商品分类<span class="theme-color">*</span></label>
        <select name="secondHandPostCategoryId" required>
          <option value="">选择分类</option>
          <c:forEach items="${cates}" var="cate">
            <option value="${cate.id}" ${sh.secondHandPostCategoryId == cate.id ? 'selected="selected"' : ''}>${cate.name}</option>
          </c:forEach>
        </select>
      </div>
      <div class="form-group">
        <label for="">价格<span class="theme-color">*</span></label>
        <input name="sellPrice" type="number" class="form-control short-input" value="${sh.sellPrice}" required>
        <span class="input-unit">元</span>
      </div>
      <div class="form-group">
        <label for="">商品描述<span class="theme-color">*</span></label>
        <textarea name="description" class="form-control" placeholder="如：2015年1月购买，九成新……" required>${sh.description}</textarea>
      </div>
      <input name="picIds" class="form-control" type="hidden" value="${sh.picturePath}">
      <div class="form-group">
        <label for="">交易地点<span class="theme-color">*</span></label>
        <input name="tradePlace" type="text" class="form-control" value="${sh.tradePlace}" required>
      </div>

    <p class="pin-title dark-green-bg ">联系方式
      <i class="pin-arrow  dark-green-arrow"></i>
    </p>

      <div class="form-group">
        <label for="">联系人<span class="theme-color">*</span></label>
        <input type="text" class="form-control" name="contactName" value="${sh.contactName}" required>
      </div>
      <div class="form-group">
        <label for="">手机号<span class="theme-color">*</span></label>
        <input type="text" class="form-control" name="contactPhone" value="${job.contactPhone}" required pattern="^1\d{10}$|^(0\d{2,3}-?|\(0\d{2,3}\))?[1-9]\d{4,7}(-\d{1,8})?$">
      </div>
      <div class="form-group">
        <label for="">QQ号</label>
        <input type="number" class="form-control medium-input" name="contactQq" value="${sh.contactQq}">
      </div>
    </form>

    <div class="segment">
      <div class="submit-btn big-btn dark-green-bg">
        <span href="javascript:void(0);">发布二手</span>
      </div>
    </div>

    <div class="jqmWindow dialog" >
      <div class="tlj_modal_header">桃李街提示</div>
      <div class="tlj_modal_content"></div>
    </div>

  </div>
</div>

<jsp:include page="../block/user-footer.jsp"></jsp:include>
<script src="/scripts/shpost.js"></script>
<script src="/scripts/uploader.js"></script>
</body>
</html>
