<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-5-1
  Time: 下午5:12
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
        二手分类
      </h3>
      <ul class="breadcrumb">
        <li><a href="/manage/"><i class="fa fa-home"></i> 控制台</a></li>
        <li><a href=""></i> 二手分类</a></li>
      </ul>

    </div>
    <!-- page heading end-->


    <!--body wrapper start-->
    <div class="wrapper">
      <div class="row">
        <div class="col-sm-12">
          <section class="panel">
            <header class="panel-heading">
              Editable Table
									<span class="tools pull-right">
										<a href="javascript:;" class="fa fa-chevron-down"></a>
										<a href="javascript:;" class="fa fa-times"></a>
									</span>
            </header>
            <div class="panel-body" id="JobCategory">
              <div class="adv-table editable-table ">
                <div class="clearfix">
                  <div class="btn-group">
                    <a class="add-button btn btn-success"
                       href="/manage/cate/sh/add">
                      添加二手分类 <i class="fa fa-plus"></i>
                    </a>
                  </div>

                </div>
                <div class="space15"></div>
                <table class="table table-striped table-hover table-bordered show-table" id="hidden-table-info">
                  <thead>
                  <tr>
                    <th>id</th>
                    <th>名称</th>
                    <th>描述</th>
                    <th>分类等级</th>
                    <th>颜色</th>
                    <th>编辑</th>
                    <th>删除</th>
                  </tr>
                  </thead>
                  <tbody>
                  <c:forEach items="${shCates}" var="cate">
                    <tr>
                      <td>${cate.id}</td>
                      <td>${cate.name}</td>
                      <td>${cate.memo}</td>
                      <td>${cate.level}</td>
                      <td>${cate.themeColor}</td>
                      <td class="center hidden-phone">
                        <a href="/manage/cate/sh/${cate.id}/edit"
                           class="btn btn-success btn-xs">编辑</a>
                      </td>
                      <td class="center hidden-phone">
                        <button href="javascript:void(0);"
                                data-id="${cate.id}"
                                data-type="cate/sh"
                                class="delete-btn btn btn-danger btn-xs">
                          删除
                        </button>
                      </td>
                    </tr>
                  </c:forEach>
                  </tbody>
                </table>
              </div>
            </div>
          </section>
        </div>
      </div>
    </div>
    <!--body wrapper end-->

    <jsp:include page="block/footer.jsp"></jsp:include>

  </div>
  <!-- main content end-->
</section>

<jsp:include page="block/end.jsp"></jsp:include>

<!--dynamic table-->
<script type="text/javascript" language="javascript" src="/admin/js/advanced-datatable/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="/admin/js/data-tables/DT_bootstrap.js"></script>

<!--dynamic table initialization -->
<script src="/admin/js/acustom/table-init.js"></script>
<script src="/admin/js/acustom/request.js"></script>
<!--script for editable table-->
<%--<script src="/admin/js/editable-table.js"></script>--%>


</body>
</html>

