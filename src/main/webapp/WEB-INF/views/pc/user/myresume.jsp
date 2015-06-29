<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-5-17
  Time: 下午8:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%request.setCharacterEncoding("UTF-8") ;%>

<!doctype html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <title>我的简历</title>
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
  <link rel="stylesheet" href="/styles/user/resume.css"/>

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
      <jsp:param name="navShow" value="resume"/>
    </jsp:include>
  <div class="segment infos resume link-segment">
    <p class="pin-title">个人信息
      <i class="pin-arrow"></i>
    </p>
    <form id="CreateResumeForm">
      <div class="form-group">
          <label for="">照片<i class="theme-color">*</i> </label>
          <img src="/static/images/users/${resume.photoPath}" alt="" class="resume-photo">
          <input type="hidden" name="photoPath" value="${resume.photoPath}">
          <div id="upload-btn">选择文件</div>
      </div>
      <div class="form-group">
        <label for="">真实姓名<i class="theme-color">*</i> </label>
        <input type="text" class="form-control" placeholder="姓名" name="name" value="${resume.name}">
      </div>
      <div class="form-group">
        <label for="">性别<i class="theme-color">*</i></label>
        <input type="radio" name="gender" value="男" ${resume.gender == '男' ? 'checked = "checked"':''}>男
        <input type="radio" name="gender" value="女" ${resume.gender == '女' ? 'checked = "checked"' : ''}>女
      </div>
      <div class="form-group">
        <label for="">身高<i class="theme-color">*</i></label>
        <input name="height" class="short-input form-control" type="text" placeholder="填写有效数字" value="${resume.height}">
        <span for="" class="input-unit">cm</span>
      </div>
      <div class="form-group">
        <label for="">年龄<i class="theme-color">*</i></label>
        <input name="age" class="short-input form-control" type="text" placeholder="" value="${resume.age}">
        <%--<span> 例如 <span class="dark-green">(1993-1-1)</span></span>--%>
      </div>

      <%--<div class="form-group">--%>
        <%--<label for="">城&nbsp;&nbsp;&nbsp;&nbsp;市<i class="theme-color">*</i></label>--%>
        <%--<input type="text" class="form-control" placeholder="填写您所在城市">--%>
      <%--</div>--%>
      <%--<div class="form-group">--%>
        <%--<label for="">学&nbsp;&nbsp;&nbsp;&nbsp;校<i class="theme-color">*</i></label>--%>
        <%--<input type="text" class="form-control" placeholder="填写您的学校">--%>
      <%--</div>--%>
    <p class="pin-title">求职意向
      <i class="pin-arrow"></i>
    </p>

      <%--<div class="form-group">--%>
        <%--<label for="">简历标题<i class="theme-color">*</i></label>--%>
        <%--<input type="text" name="title" class="form-control" placeholder="例：兼职派单员" value="${resume}">--%>
      <%--</div>--%>
      <div class="form-group">
        <label for="">求职意向<i class="theme-color">*</i></label>
         <select name="intend">
              <option value="">选择分类</option>
              <c:forEach items="${cates}" var="cate">
                  <option value="${cate.id}">${cate.name}</option>
              </c:forEach>
         </select>
      </div>
      <%-->
      <div class="form-group text">
        <label for="">空闲时间</label>
        <textarea name="spareTime" class="form-control" placeholder="">${resume.spareTime}</textarea>
      </div>
      --%>
      <div class="form-group">
          <label for="">公开程度</label>
        <input type="radio" name="accessAuthority" ${resume.accessAuthority == 'ALL' ? 'checked = "checked"':''} value="ALL"> 公开
        <input type="radio" name="accessAuthority" ${resume.accessAuthority == 'ME_ONLY' ? 'checked = "checked"':''} value="ME_ONLY"> 不公开
      </div>
      <div class="form-group text">
        <label for="">自我介绍</label>
        <textarea name="introduce" class="form-control" placeholder="200字以内">${resume.introduce}</textarea>
      </div>
      <div class="form-group text">
        <label for="">工作经历<i class="theme-color">*</i></label>
        <textarea name="experience"  class="form-control" placeholder="工作经历（200字以内）">${resume.experience}</textarea>
      </div>
      <p class="pin-title no-offset">
         联系方式
         <i class="pin-arrow"></i>
      </p>
      <div class="form-group ">
        <label for=""><i class="fa fa-phone red"></i>手机<i class="theme-color">*</i></label>
        <input name="phoneNumber" type="text" class="form-control" placeholder="" value="${resume.phoneNumber}">
      </div>
      <div class="form-group ">
        <label for=""><i class="fa fa-qq blue"></i>QQ</label>
        <input name="qq" type="text" class="form-control" placeholder="" value="${resume.qq}">
      </div>
      <div class="form-group">
        <label for=""><i class="fa fa-weixin light-green"></i>微信</label>
        <input name="wechatAccount" type="text" class="form-control" placeholder="" value="${resume.wechatAccount}">
      </div>
      <div class="form-group">
        <label for=""><i class="fa fa-envelope-o theme-color"></i>邮箱</label>
        <input name="email" type="text" class="form-control" placeholder="" value="${resume.email}">
      </div>
    </form>
  </div>

  <div class="segment">
    <div class="submit-btn big-btn theme-color-bg"
         data-type="${isChange?'change':'create'}">
        <span >保存简历</span>
    </div>
  </div>

  <div class="jqmWindow dialog" >
    <div class="tlj_modal_header">桃李街提示</div>
    <div class="tlj_modal_content"></div>
  </div>
</div>

<jsp:include page="../block/user-footer.jsp"></jsp:include>
<script src="/scripts/myresume.js"></script>
<script src="/scripts/uploader-resume.js"></script>
</body>
</html>
