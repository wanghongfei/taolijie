<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-4-29
  Time: 下午5:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="ThemeBucket">
    <link rel="shortcut icon" href="#" type="image/png">

    <title>登陆</title>

    <link href="/admin/css/style.css" rel="stylesheet">
    <link href="/admin/css/style-responsive.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="/admin/js/html5shiv.js"></script>
    <script src="/admin/js/respond.min.js"></script>
    <![endif]-->
</head>

<body class="login-body">

<div class="container">

    <form class="form-signin" id="form-signin" >
        <div class="form-signin-heading text-center">
            <h1 class="sign-title">用户登陆</h1>
            <img src="/admin/images/login-logo.png" alt=""/>
        </div>
        <div class="login-wrap">
            <div class="error" id="add_err"></div>
            <input type="text" name="username" id="username" class="form-control" placeholder="用户名" autofocus>
            <input type="password" name="password" id="password" class="form-control" placeholder="密码">

            <button class="btn btn-lg btn-login btn-block" type="submit" id="sub-btn">
                <i class="fa fa-check"></i>
            </button>

            <div class="registration">
                还没有账号?
                <a class="" href="registration">
                    注册
                </a>
            </div>
            <label class="checkbox">
                <input type="checkbox" value="true" name="rememberMe" id="rememberMe"> 记住我
                <span class="pull-right">
                    <a data-toggle="modal" href="#myModal"> 忘记密码?</a>
                </span>
            </label>

        </div>

        <!-- Modal -->
        <div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="myModal"
             class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">忘记密码 ?</h4>
                    </div>
                    <div class="modal-body">
                        <p>请输入您的邮箱以重置密码</p>
                        <input type="text"  placeholder="Email" autocomplete="off"
                               class="form-control placeholder-no-fix">

                    </div>
                    <div class="modal-footer">
                        <button data-dismiss="modal" class="btn btn-default" type="button">取消</button>
                        <button class="btn btn-primary" type="button">确认</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- modal -->

    </form>

</div>


<!-- Placed js at the end of the document so the pages load faster -->
<script src="/admin/js/jquery-1.10.2.min.js"></script>
<script src="/admin/js/bootstrap.min.js"></script>
<script src="/admin/js/modernizr.min.js"></script>
<script type="text/javascript" src="/admin/js/jquery.validate.min.js"></script>
<script src="/admin/js/validation-init.js"></script>

<script>

    $(document).ready(function(){
        var btn = document.getElementById('sub-btn');
        btn.addEventListener('submit',function(event){
            event.preventDefault();
        });


    });



</script>


</body>
</html>

