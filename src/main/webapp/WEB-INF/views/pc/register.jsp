<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-5-9
  Time: 下午5:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%request.setCharacterEncoding("UTF-8") ;%>
<%--html头部--%>

<!doctype html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <title>注册</title>
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

<%--顶栏--%>
<jsp:include page="block/top-bar.jsp"/>
<%--页首--%>
<jsp:include page="block/header2.jsp"/>


<!-- 正文 -->
<div class="login">
  <div class="login-container">
    <div class="login-box">
      <div class="login-body">
        <form id="reg-form">

          <div class="form-group role-choose">
                <div class="col-6">
                    <input type="radio" name="isEmployer" value="false" checked="checked"> 学生
                </div>
                <div class="col-6">
                    <input type="radio" name="isEmployer" value="true"> 商家
                </div>
          </div>
          <div class="form-group">
            <label for="" class="col-4 label-control">用户名</label>
            <div class="col-8">
                <input type="text" class="form-control" name="username" placeholder="推荐使用常用邮箱" pattern=".{6,}" title="至少6位" required>
            </div>

          </div>
          <div class="form-group">
            <label for="" class="col-4 label-control" name="password">密码</label>
            <div class="col-8">
              <input type="password" class="form-control" name="password" pattern=".{6,}" title="至少6位" required>
            </div>
          </div>
          <div class="form-group">
            <label for="" class="col-4 label-control" name="rePassword">确认密码</label>
            <div class="col-8">
              <input type="password" class="form-control" name="rePassword" pattern=".{6,}" title="至少6位" required>
            </div>
          </div>
            <div class="col-12" >
                <label class="red" id="error-box"></label>
            </div>
          <div class="col-12 no-pd">
            <input type="submit" class="login-btn btn theme-color-bg" id="sub-btn" value="注册">
          </div>
        </form>
      </div>
    </div>
  </div>
</div>

<%--脚部--%>
<jsp:include page="block/footer.jsp"/>


</body>
</html>

