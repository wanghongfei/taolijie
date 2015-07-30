<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ju" uri="/WEB-INF/tld/JsonUtils.tld"%>
<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-5-31
  Time: 下午6:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%request.setCharacterEncoding("UTF-8") ;%>


<!doctype html>
<html class="no-js" ng-app="tljApp" ng-controller="resumeCtrl">
<head>
  <meta charset="utf-8">
  <title>${postUser.username}的简历</title>
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
  <link rel="stylesheet" href="/styles/resumedetail.css"/>

  <!-- build:js /scripts/vendor/modernizr.js -->
  <script src="/scripts/modernizr.js"></script>
  <!-- endbuild -->

</head>
<body>
<!--[if lt IE 10]>
<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->


<%--顶栏--%>
<jsp:include page="block/top-bar.jsp"/>
<%--页首--%>
<jsp:include page="block/header.jsp"/>

<div class="container">


  <%--轮播--%>
  <jsp:include page="block/silder.jsp"/>
  <%--侧边栏--%>
  <jsp:include page="block/side.jsp"/>

  <!-- 正文 -->
  <div class="detail main white-bg">
    <div class="detail-bar">
      <span>${postUser.name}的简历</span>
        <a href="/list/resume" style="color: #fa6a38"><p class="fl"><i class="fa fa-angle-left">&nbsp;&nbsp;</i>返回</p></a>

      <p class="fr" id="del" ng-attr-data-id="{{ resume.id }}" data-type="resume" style="cursor: pointer" ng-if="!isShow">删除</p>
      <p class="fr" id="edit" ng-attr-data-id="{{ resume.id }}" data-type="resume" style="cursor: pointer" ng-if="!isShow">
        <a href="/user/resume/change" style="color: #fa6a38">编辑</a>
      </p>

      <p class="fr" id="fav" ng-attr-data-id="{{ resume.id }}" data-type="resume" ng-if="isShow" ng-click="fav()">
          <i class="fa ${favStatus? 'fa-heart':'fa-heart-o'}">&nbsp;&nbsp;</i>
            ${favStatus? '已收藏':'收藏'}
      </p>

    </div>
    <div style="clean:both"></div>
    <div class="resume-info">
        <img src="/static/images/users/{{ resume.photoPath }}" alt="photo" ng-show="{{ resume.photoPath != ''}}"/>
        <img src="/images/miao.jpg" alt="" ng-show="{{ resume.photoPath == ''}}">
      <div class="infos">
          <p>姓名 : <span ng-bind="resume.name"></span></p>
          <p ng-if="resume.gender == 'm'">性别 : <span>男</span></p>
          <p ng-if="resume.gender == 'f'">性别 : <span>女</span></p>
          <p>年龄 : <span ng-bind="resume.age"></span>岁</p>
          <p>身高 : <span ng-bind="resume.height"></span>cm</p>
          <p>学校 : <span ng-bind="resume.school"></span></p>
          <p>专业 : <span ng-bind="resume.major"></span></p>
      </div>
    </div>
    <br/>
    <div class="resume-detail theme-color-bd theme-color-bg" >
      <div class="title">求职意向</div>
      <p>
        <c:forEach items="${intendJobs}" var="intendJob">
          ${intendJob.category.name}
        </c:forEach>
        &nbsp;&nbsp;&nbsp;&nbsp;
      </p>
      <div style="clear:both"></div>
    </div>
    <i class="resume-arrow theme-color"></i>
    <div class="resume-detail yellow-bd yellow-bg" >
      <div class="title">意向地区</div>
      <p ng-bind="resume.preferredRegion"></p>
      <div style="clear:both"></div>
    </div>
    <i class="resume-arrow yellow"></i>
    <div class="resume-detail purple-bd purple-bg" >
      <div class="title">空闲时间</div>
      <p ng-bind="resume.spareTime"></p>
      <div style="clear:both"></div>
    </div>
    <i class="resume-arrow purple"></i>

    <div class="resume-detail light-green-bg light-green-bd" >
      <div class="title">自我介绍</div>
      <p ng-bind="resume.introduce"></p>
      <div style="clear:both"></div>
    </div>
    <i class="resume-arrow light-green"></i>

    <div class="resume-detail dark-green-bg dark-green-bd" >
      <div class="title">工作经验</div>
      <p ng-bind="resume.experience"></p>
      <div style="clear:both"></div>
    </div>
    <i class="resume-arrow dark-green"></i>

    <div class="resume-detail red-bg red-bd resume-contact" >
      <div class="title">联系方式</div>
      <p><i class="fa fa-phone orange"></i><span ng-bind="resume.phoneNumber"></span></p>
      <p ng-if="resume.qq"><i class="fa fa-qq blue"></i><span ng-bind="resume.qq"></span></p>
      <p ng-if="resume.wechatAccount"><i class="fa fa-weixin green"></i> <span ng-bind="resume.wechatAccount"></span></p>
      <p ng-if="resume.email"><i class="fa fa-envelope-o red"></i> <span ng-bind="resume.email"></span></p>
      <div style="clear:both"></div>
    </div>

  </div>


</div>


<%--脚部--%>
<jsp:include page="block/footer.jsp"/>
<script>
    var resume = JSON.parse('${ju:toJson(resume)}');
    var postUser = JSON.parse('${ju:toJson(postUser)}');
    var isShow = JSON.parse('${ju:toJson(isShow)}');
    var intendJobs = JSON.parse('${ju:toJson(intendJobs)}');
</script>
<script src="/scripts/resumedetail.js"></script>
</body>
</html>
