<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-4-26
  Time: 下午8:30
  To change this template use File | Settings | File Templates.
--%>
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
        新闻管理
      </h3>
      <ul class="breadcrumb">
        <li><a href="/manage/"><i class="fa fa-home"></i> 控制台</a></li>
        <li><a href=""></i> 查看帖子</a></li>
        <li><a href=""> 新闻</a></li>
      </ul>

    </div>
    <!-- page heading end-->

    <!--body wrapper start-->
    <div class="wrapper">
      <div class="row">
        <div class="col-sm-12">
          <section class="panel panel-primary">
            <header class="panel-heading">
              新闻列表
								<span class="tools pull-right">
									<a href="javascript:;" class="fa fa-chevron-down"></a>
								</span>
            </header>

            <div class="panel-body">
              <div class="adv-table">
                <div class="clearfix">
                  <div class="btn-group">
                    <a href="/manage/news/add" class="btn btn-warning">
                      添加新闻 <i class="fa fa-plus"></i>
                    </a>
                  </div>

                </div>
                <div class="space15"></div>


                <table class="display table table-bordered " id="hidden-table-info">
                  <thead>
                  <tr>
                    <th>id</th>
                    <!-- 在表格上只显示前15个字 -->
                    <th>新闻标题</th>
                    <th class="hidden-phone">发布人</th>
                    <th class="hidden-phone">发布时间</th>
                    <th class="hidden-phone">修改</th>
                    <th class="hidden-phone">删除</th>
                  </tr>
                  </thead>
                  <tbody>
                  <c:forEach items="${news}" var="aNew">
                    <tr class="">
                      <td>${aNew.id}</td>
                      <td><a href="">${aNew.title}</a></td>
                      <td class="hidden-phone">${aNew.member.username}</td>
                      <td class="center hidden-phone">
                        <fmt:formatDate value="${aNew.time}" pattern="yyyy-MM-dd"/>
                      </td>
                      <td class="center hidden-phone">
                        <a href="/manage/news/edit/${aNew.id}" class="btn btn-success btn-xs">编辑</a>
                      </td>
                      <td class="center hidden-phone">
                        <a href="javascript:void(0);"
                           data-id="${aNew.id}"
                           data-type="news"
                           class="delete-btn btn btn-danger btn-xs">删除</a>
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


</body>
</html>




