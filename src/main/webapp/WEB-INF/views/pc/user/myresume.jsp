<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ju" uri="/WEB-INF/tld/JsonUtils.tld"%>
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
<html class="no-js" ng-app="tljApp" ng-controller="myresumeCtrl">
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
          <c:if test="${resume.photoPath == null}">
            <img src="/images/no-avatar.jpeg" alt="resume photo" class="resume-photo">
          </c:if>
          <c:if test="${resume.photoPath != null}">
          <img src="/static/images/users/{{ resume.photoPath }}" alt="" class="resume-photo">
          </c:if>
          <input type="text" name="photoPath" ng-model="resume.photoPath" required style="width: 0;height: 0;position: relative;top: 100px;right: 50px;">
          <div id="upload-btn">选择文件</div>
      </div>
      <div class="form-group">
        <label for="">真实姓名<i class="theme-color">*</i> </label>
        <input type="text" class="form-control" placeholder="姓名" name="name" ng-model="resume.name" required maxlength="10">
      </div>
      <div class="form-group">
        <label for="">性别<i class="theme-color">*</i></label>
        <input type="radio" name="gender" value="m" ng-model="resume.gender" required>男
        <input type="radio" name="gender" value="f" ng-model="resume.gender" required>女
      </div>
      <div class="form-group">
        <label for="">身高</label>
        <input name="height" class="short-input form-control" type="number" placeholder="填写有效数字" ng-model="resume.height" min="50" max="250">
        <span for="" class="input-unit">cm</span>
      </div>

      <div class="form-group">
        <label for="">年龄<i class="theme-color">*</i></label>
        <input name="age" class="short-input form-control" type="number" placeholder="" value="${resume.age}" min="14" max="80">
      </div>

      <%-- <div class="form-group">
        <label for="">出生日期<i class="theme-color">*</i></label>
        <input name="birthday" class="short-input form-control datepicker" type="text" placeholder="" ng-model="resume.birthday">
      </div> --%>
      <div class="form-group">
        <label for="">学校<i class="theme-color">*</i></label>
        <input type="text" class="form-control" placeholder="填写您的学校" name="school" required maxlength="20" ng-model="resume.school">
      </div>
      <div class="form-group">
        <label for="">专业<i class="theme-color">*</i></label>
        <input type="text" class="form-control" placeholder="填写您的专业" name="major" required maxlength="20" ng-model="resume.major">
      </div>
      <div class="form-group text">
        <label for="">自我介绍<i class="theme-color">*</i></label>
        <textarea name="introduce" class="form-control" placeholder="来个自我介绍呗，来亮瞎他们的眼(15字以上)" pattern=".{15,}" required maxlength="200">${resume.introduce}</textarea>
      </div>
      <div class="form-group text">
        <label for="">工作经历<i class="theme-color">*</i></label>
        <textarea name="experience"  class="form-control" placeholder="快来写下你最炫酷的经历吧(15字以上)" required pattern=".{15,}" maxlength="200">${resume.experience}</textarea>
      </div>
    <p class="pin-title">求职意向
      <i class="pin-arrow"></i>
    </p>

      <%--<div class="form-group">--%>
        <%--<label for="">简历标题<i class="theme-color">*</i></label>--%>
        <%--<input type="text" name="title" class="form-control" placeholder="例：兼职派单员" value="${resume}">--%>
      <%--</div>--%>
      <div class="form-group">
        <label for="">求职意向<i class="theme-color">*</i></label>
          <div class="select-multiple">
              <span ng-repeat="cate in cates" ng-attr-data-id="{{ cate. id }}" class="option-multiple" ng-class="{'option-selected': cate.selected}" ng-click="setIntendIds($index)">{{cate.name}}</span>
          </div>
          <span class="right-tip">（最多选3个）</span>
          <input type="hidden" name="intendIds" value="${ resumeIntendJobs }">
      </div>
      <div class="form-group">
        <label for="">求职地区<i class="theme-color">*</i></label>
         <select name="preferredProvince" required class="select-area short_select">
              <option value="山东省">山东省</option>
          </select>
         <select name="preferredCity" required class="select-area short_select">
              <option value="淄博市">淄博市</option>
          </select>
         <select name="preferredRegion" required class="select-area short_select" ng-model="resume.preferredRegion">
              <option value="张店区">张店区</option>
              <option value="周村区">周村区</option>
              <option value="淄川区">淄川区</option>
              <option value="临淄区">临淄区</option>
              <option value="博山区">博山区</option>
              <option value="桓台县">桓台县</option>
              <option value="沂源县">沂源县</option>
              <option value="高青县">高青县</option>
          </select>
      </div>
      <div class="form-group">
        <label for="">空闲时间<i class="theme-color">*</i></label>
        <input type="text" name="spareTime" class="form-control" placeholder="请填写自己的空闲时间" ng-model="resume.spareTime" required maxlength="100">
      </div>
      <div class="form-group">
        <label for="">公开程度</label>
        <input type="radio" name="accessAuthority" ng-model="resume.accessAuthority" value="ALL" required> 公开
        <input type="radio" name="accessAuthority" ng-model="resume.accessAuthority" value="EMPLOYER" required> 对商家用户公开
        <input type="radio" name="accessAuthority" ng-model="resume.accessAuthority" value="ME_ONLY" required> 不公开
      </div>
      <p class="pin-title">
         联系方式
         <i class="pin-arrow"></i>
      </p>
      <div class="form-group ">
        <label for=""><i class="fa fa-phone red"></i>手机<i class="theme-color">*</i></label>
        <input name="phoneNumber" type="text" class="form-control" placeholder="请输入您的手机号" ng-model="resume.phoneNumber" required pattern="^1\d{10}$|^(0\d{2,3}-?|\(0\d{2,3}\))?[1-9]\d{4,7}(-\d{1,8})?$">
      </div>
      <div class="form-group ">
        <label for=""><i class="fa fa-qq blue"></i>QQ</label>
        <input name="qq" type="number" class="form-control medium-input" placeholder="请输入您的QQ号" ng-model="resume.qq" maxlength="15">
      </div>
      <div class="form-group">
        <label for=""><i class="fa fa-weixin light-green"></i>微信</label>
        <input name="wechatAccount" type="text" class="form-control" placeholder="请输入的您的微信" ng-model="resume.wechatAccount" maxlength="20">
      </div>
      <div class="form-group">
        <label for=""><i class="fa fa-envelope-o theme-color"></i>邮箱</label>
        <input name="email" type="email" class="form-control medium-input" placeholder="请输入您的邮箱" ng-model="resume.email">
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
<script>
    var resume = JSON.parse('${ju:toJson(resume)}') || {};
    resume.qq = parseInt(resume.qq) || '';
    var resumeIntendIds = JSON.parse('${ju:toJson(resumeIntendIds)}');
    var cates = JSON.parse('${ju:toJson(cates)}');
    var intendIds = $('input[name=intendIds]')[0].value.split(';');
    if(intendIds[0] == "" || intendIds[0] == "[]") {
        intendIds = [];
    }
    for(var i = 0; i < cates.length; i++) {
        for(var j = 0; j < intendIds.length; j++) {
            cates[i].selected = intendIds[j] == cates[i].id ? true: false;
            if(cates[i].selected)
                break;
        }
        if(!cates[i].selected) {
            cates[i].selected = false;
        }
    }
</script>
<script src="/scripts/myresume.js"></script>
<script src="/scripts/uploader-resume.js"></script>
</body>
</html>
