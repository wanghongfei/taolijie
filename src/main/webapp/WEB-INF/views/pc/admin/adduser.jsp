<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-4-30
  Time: 下午5:04
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--头部--%>
<jsp:include page="block/start.jsp"></jsp:include>

<body class="sticky-header">

<section>

  <jsp:include page="block/side.jsp"></jsp:include>

  <!-- main content start-->
  <div class="main-content" >


    <jsp:include page="block/header.jsp"></jsp:include>

    <!-- page heading start-->
    <div class="page-heading">
      <h3>
        添加用户
      </h3>
      <ul class="breadcrumb">
        <li><a href="/manage"><i class="fa fa-home"></i> 控制台</a></li>
        <li><a href="/manage/users"></i> 用户管理</a></li>
        <li><a href=""> ${isEdit? '修改用户':'添加用户'}</a></li>
      </ul>

    </div>
    <!-- page heading end-->

    <!--body wrapper start-->
    <div class="wrapper">

      <section class="panel panel-info">
        <header class="panel-heading">
          <h3 class="panel-title">${isEdit ? ('修改用户 -- '.concat(member.username)):'添加用户'}</h3>

        </header>
        <div class="panel-body">
          <form action="#" class="form-horizontal" id="userForm">
            <h4>基本信息 *</h4>
            <%--<div class="form-group">--%>
              <%--<label class="control-label col-md-3" for="email">邮箱</label>--%>
              <%--<div class="col-md-4 col-xs-11">--%>
                <%--<input class="form-control" id="email" name="email" placeholder="输入邮箱" type="email">--%>
              <%--</div>--%>
            <%--</div>--%>
              <div class="form-group">
                <label class="control-label col-md-3" for="username">用户名</label>
                <div class="col-md-4 col-xs-11">
                   <input class="form-control" id="username" name="username" placeholder="输入用户名" type="text" value="${member.username}" ${isEdit?'disabled="disabled"':''} >
                </div>
            </div>
            <div class="form-group">
              <label class="control-label col-md-3" for="password">密码</label>
              <div class="col-md-4 col-xs-11" >
                <input class="form-control" id="password" name="password" placeholder="${isEdit ? '若不修改无需填写':'输入密码'}" type="password">
              </div>
            </div>


            <div class="form-group">
              <label class="control-label col-md-3" for="role">类型</label>
              <div class="col-md-2 col-xs-11" style="text-align:center">
                <div class="flat-red">
                  <div class="radio ">
                    <%--role实体设计的有问题,在这里暂时用roleId判断--%>
                    <input tabindex="3" type="radio" name="roleName" ${(!isEdit||member.roleList.get(0).rolename=='ADMIN')? 'checked="checked"':'' } value="ADMIN">
                    <label>管理员</label>
                  </div>
                </div>
                <div class="flat-blue">
                  <div class="radio">
                    <input tabindex="3" style="height:40px;width:100px" type="radio"  name="role" id="role" value="STUDENT" ${(isEdit&&member.roleList.get(0).rolename=='STUDENT') ? 'checked="checked"':''}>学生
                  </div>

                </div>
                <div class="flat-green">
                  <div class="radio ">
                    <input tabindex="3" type="radio" name="role" value="EMPLOYER" ${(isEdit&&member.roleList.get(0).rolename=='EMPLOYER') ? 'checked="checked"':''}>
                    <label>商家</label>
                  </div>
                </div>

              </div>

            </div>

            <h4>个人资料 (选填)</h4>
            <div class="form-group">
              <label class="control-label col-md-3">姓名</label>
              <div class="col-md-4 col-xs-11">
                <input class="form-control" name="name" placeholder="您的姓名" type="text" value="${member.name}">
              </div>
            </div>
            <div class="form-group">
              <label class="control-label col-md-3">年龄</label>
              <div class="col-md-2 col-xs-11">
                <input class="form-control" name="age" placeholder="年龄" type="text" value="${member.age}">
              </div>
            </div>
            <div class="form-group">
              <label class="control-label col-md-3">性别</label>
              <div class="col-md-2 col-xs-11 icheck" style="text-align:center">
                <div class="flat-blue">
                  <div class="radio ">
                    <input tabindex="3" name="gender" value="男" type="radio"  ${member.gender == "男"?'checked="checked"':''}>
                    <label>男</label>
                  </div>
                </div>
              </div>
              <div class="col-md-2 col-xs-11">
                <div class="flat-purple">
                  <div class="radio ">
                    <input tabindex="3"  name="gender" value="女" type="radio"   ${member.gender == "女"?'checked="checked"':''}>
                    <label>女</label>
                  </div>
                </div>
              </div>
            </div>

            <div class="form-group last">
              <label class="control-label col-md-3">头像</label>
              <div class="col-md-9">
                <div class="fileupload fileupload-new" data-provides="fileupload">
                  <div class="fileupload-new thumbnail" style="width: 200px; height: 150px;">
                    <img src="http://www.placehold.it/200x150/EFEFEF/AAAAAA&amp;text=no+image" alt="" />
                  </div>
                  <div class="fileupload-preview fileupload-exists thumbnail" style="max-width: 200px; max-height: 150px; line-height: 20px;"></div>
                  <div>
									<span class="btn btn-default btn-file">
										<span class="fileupload-new"><i class="fa fa-paper-clip"></i> 上传头像</span>
										<span class="fileupload-exists"><i class="fa fa-undo"></i> 修改</span>
										<input type="file" class="default" />
									</span>
                    <a href="#" class="btn btn-danger fileupload-exists" data-dismiss="fileupload"><i class="fa fa-trash"></i> 删除</a>
                  </div>
                </div>
                <br/>
                <span class="label label-danger ">NOTE!</span>
							<span>
								上传图片只支持ie10+,以及其他主流浏览器
							</span>
              </div>
            </div>

            <h4>联系方式 (选填)</h4>
            <div class="form-group">
              <label class="control-label col-md-3">手机号</label>
              <div class="col-md-4 col-xs-11">
                <input class="form-control" name="phone" placeholder="输入您的手机号" type="text" value="${member.phone}">
              </div>
            </div>
            <div class="form-group">
              <label class="control-label col-md-3">QQ</label>
              <div class="col-md-4 col-xs-11">
                <input class="form-control" name="qq" placeholder="输入QQ号码" type="text" value="${member.qq}">
              </div>
            </div>

            <button type="submit" style="margin-top:30px;margin-bottom:30px" class="col-md-offset-3 btn btn-primary">${isEdit?'修改':'添加'}用户</button>

            <input id="isEdit" type="hidden" value="${isEdit}"/>
            <input id="userId" type="hidden" value="${member.id}"/>
          </form>
        </div>
      </section>


    </div>
    <!--body wrapper end-->

    <jsp:include page="block/footer.jsp"></jsp:include>

  </div>
  <!-- main content end-->
</section>

<jsp:include page="block/end.jsp"></jsp:include>
<script src="/admin/js/iCheck/jquery.icheck.js"></script>
<script src="/admin/js/icheck-init.js"></script>

<!--file upload-->
<script type="text/javascript" src="/admin/js/bootstrap-fileupload.min.js"></script>

<script type="text/javascript" src="/admin/js/jquery.validate.min.js"></script>
<script src="/admin/js/validation-init.js"></script>

</body>
</html>

