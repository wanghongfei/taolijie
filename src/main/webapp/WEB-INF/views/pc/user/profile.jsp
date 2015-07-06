<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-5-17
  Time: 下午8:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%request.setCharacterEncoding("UTF-8") ;%>



<!doctype html>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <title>个人资料修改</title>
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
    <link rel="stylesheet" href="/styles/user/profile.css">

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
        <jsp:param name="navShow" value="profile"/>
    </jsp:include>

    <div class="segment personal">
        <p class="pin-title  dark-green-bg">账号信息
            <i class="pin-arrow  dark-green-arrow"></i>
        </p>
        <p  class="accout-info">
            账号： ${sessionScope.user.username}
            <span>
                <c:if test="${sessionScope.role.rolename == 'ADMIN'}">管理员</c:if>
                <c:if test="${sessionScope.role.rolename == 'STUDENT'}">学生</c:if>
                <c:if test="${sessionScope.role.rolename == 'EMPLOYER'}">商家</c:if>
            </span>
        </p>
        <p class="pin-title  dark-green-bg">基本信息
            <i class="pin-arrow  dark-green-arrow"></i>
        </p>
        <form action="" id="ProfileForm">
            <div class="form-group">
                <div id="uploader-demo" class="wu-example">
                    <div id="fileList" class="uploader-list"
                         style="width: 75px;height: 75px; display: inline;">
                        <c:if  test="${sessionScope.user.profilePhotoId == 0}">
                            <img src="/images/default-img.jpg" alt="" style="border-radius: 10px" width="75" height="75" alt="">
                        </c:if>
                        <c:if  test="${sessionScope.user.profilePhotoId != 0}">
                            <img src="/static/images/users/${sessionScope.user.profilePhotoId}" style="border-radius: 10px" width="75" height="75" alt=""/>
                        </c:if>

                    </div>
                    <div id="filePicker" style="display: inline">修改头像</div>
                </div>
            </div>
            <div class="form-group">
                <label for="">昵称<i class="theme-color">*</i></label>
                <input type="text" name="name" class="form-control" placeholder="姓名" value="${user.name}">
            </div>
            <div class="form-group">
                <label for="">性别<i class="star-symbol">*</i></label>
                <input type="radio" name="gender" value="男" ${user.gender == "男"?'checked="checked"':''} id="gender-m">
                <label for="gender-m" class="label-radio">男</label>
                <input type="radio" name="gender" value="女" ${user.gender == "女"?'checked="checked"':''} id="gender-f">
                <label for="gender-f" class="label-radio">女</label>
            </div>
            <div class="segment">
                <div class="submit-btn big-btn dark-green-bg">
                    <span href="javascript:void(0);">保存资料</span>
                </div>
            </div>
        </form>

        <div class="jqmWindow dialog" >
            <div class="tlj_modal_header">桃李街提示</div>
            <div class="tlj_modal_content"></div>
        </div>
    </div>
</div>


<jsp:include page="../block/user-footer.jsp"></jsp:include>
<script>
    $(document).ready(function(){
        $(".dialog").jqm({
            overlayClass: 'jqmOverlay'
        });
    });

</script>
<script src="/scripts/profile.js"></script>
<%--<script src="/scripts/upload.js"></script>--%>
</body>
</html>
