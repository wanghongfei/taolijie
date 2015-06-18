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
        ${isEdit?'编辑':'添加'}分类
      </h3>
      <ul class="breadcrumb">
        <li><a href="/manage"><i class="fa fa-home"></i> 控制台</a></li>
        <li><a href=""></i> 分类管理</a></li>
        <li><a href=""> ${isEdit?'编辑':'添加'}分类 </a></li>
      </ul>

    </div>
    <!-- page heading end-->

    <!--body wrapper start-->
    <div class="wrapper">

      <section class="panel panel-info">
        <header class="panel-heading">
          <h3 class="panel-title"></h3>

        </header>
        <div class="panel-body">
          <form action="#" class="form-horizontal" id="categoryForm">

            <div class="form-group">
              <label class="control-label col-md-3">类型</label>
              <div class="col-md-2 col-xs-11 icheck" style="text-align:center">
                <div class="flat-blue">
                  <div class="radio ">
                    <input tabindex="3" value="job" type="radio"  name="type"
                    ${type == 'job' ?'checked="checked"':''} >
                    <label>兼职分类</label>
                  </div>
                </div>
              </div>
              <div class="col-md-2 col-xs-11 icheck">
                <div class="flat-purple">
                  <div class="radio ">
                    <input tabindex="3" value="sh" type="radio"  name="type"
                    ${type == 'sh' ?'checked="checked"':''} >
                    <label>二手分类</label>
                  </div>
                </div>
              </div>
            </div>
            <div class="form-group">
              <label class="control-label col-md-3" for="name">名称</label>
              <div class="col-md-4 col-xs-11">
                <input class="form-control"
                       value="${cate.name}"
                       id="name" name="name" placeholder="输入名称" type="text"  >
              </div>
            </div>
            <div class="form-group">
              <label class="control-label col-md-3" for="memo">描述</label>
              <div class="col-md-6 col-xs-11" >
                <input class="form-control"
                       value="${cate.memo}"
                       id="memo" name="memo" placeholder="" type="text">
              </div>
            </div>
            <div class="form-group">
              <label class="control-label col-md-3" for="level">分类等级</label>
              <div class="col-md-4 col-xs-11" >
                <input class="form-control"
                       value="${cate.level}"
                       id="level" name="level" type="text">
              </div>
            </div>
            <div class="form-group">
              <label class="control-label col-md-3" for="themeColor">颜色</label>
              <div class="col-md-4 col-xs-11" >
                <input class="form-control"
                       value="${cate.themeColor}"
                       id="themeColor" name="themeColor" type="text">
          </div>
            </div>

            <input type="hidden" name="id" value="${cate.id!=null?cate.id:'0'}"/>
            <input type="hidden" name="isEdit" value="${isEdit}"/>


            <button
                    data-action="${isEdit?'edit':'add'}"
                    style="margin-top:30px;margin-bottom:30px"
                    class="add-btn col-md-offset-3 btn btn-primary">
              ${isEdit?'修改':'添加'}分类
            </button>

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


