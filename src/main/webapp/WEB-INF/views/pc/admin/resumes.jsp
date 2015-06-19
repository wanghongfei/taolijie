<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-4-29
  Time: 下午9:26
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
        兼职管理
      </h3>
      <ul class="breadcrumb">
        <li><a href="/manage/index"><i class="fa fa-home"></i> 控制台</a></li>
        <li><a href=""></i> 查看帖子</a></li>
        <li><a href=""> 兼职</a></li>
      </ul>

    </div>
    <!-- page heading end-->

    <!--body wrapper start-->
    <div class="wrapper">
      <div class="row">
        <div class="col-sm-12">
          <section class="panel panel-primary">
            <header class="panel-heading">
              兼职列表
								<span class="tools pull-right">
									<a href="javascript:;" class="fa fa-chevron-down"></a>
								</span>
            </header>


            <div class="panel-body">
              <div class="adv-table">
                <table class="display table table-bordered " id="hidden-table-info">
                  <thead>
                  <tr>
                    <th>id</th>
                    <th>简历姓名</th>
                    <th class="hidden-phone">更新时间</th>
                    <th class="hidden-phone">性别</th>
                    <th class="hidden-phone">发布人</th>
                    <th class="hidden-phone">删除</th>
                  </tr>
                  </thead>
                  <tbody id="table-body">
                  <c:forEach items="${resumes}" var="resume">
                    <tr class="">
                      <td>${resume.id}</td>
                      <td>${resume.name}</td> <!-- 不超15字 -->
                      <td class="hidden-phone">${resume.createdTime}</td>
                      <td class="center hidden-phone">${resume.gender}</td>
                      <td class="center hidden-phone">${resume.member.username}</td>
                      <td class="center hidden-phone">
                        <button href="" class="btn btn-danger btn-xs">删除</button>
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
<script src="/admin/js/dynamic_table_init.js"></script>
<script src="/admin/js/acustom/table-init.js"></script>

<%--<script>--%>
  <%--$(document).ready(function(){--%>
    <%--var nCloneTd = InitDetailsColumn();--%>
    <%--var url = '/api/list/resume/1';--%>
    <%--var detailUrl = '/api/item/resume/';--%>
    <%--var deleteUrl = '/user/resume/del/';--%>
    <%--var columns =  [--%>
      <%--{--%>
        <%--"mData": null,--%>
        <%--"bSortable": false,--%>
        <%--"mRender": function(o){ return nCloneTd.outerHTML}--%>
      <%--},--%>
      <%--{ "mData":"id" },--%>
      <%--{ "mData": "name" },--%>
      <%--{ "mData": "createdTime" },--%>
      <%--{ "mData": "gender" },--%>
      <%--{ "mData": "memberId"},--%>
      <%--{--%>
        <%--"mData": null,--%>
        <%--"bSortable": false,--%>
        <%--"mRender": function (data, type, full) {--%>
          <%--return '<a href="javascript:void(0);" onclick="toDelete(this,'+full.id+',\''+deleteUrl+'\')" class="btn btn-xs btn-warning to-delete">删除</a>';--%>
        <%--}--%>
      <%--}--%>
    <%--];--%>


    <%--var formatFunc = generateDetail;--%>

    <%--initDataTable(url,detailUrl,columns,formatFunc);--%>
  <%--});--%>

  <%--function generateDetail(data){--%>
    <%--var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';--%>
    <%--sOut += '<tr><td>年龄:</td><td>'+data.age+'</td></tr>';--%>
    <%--sOut += '<tr><td>工作经历:</td><td>'+data.experience+'</td></tr>';--%>
    <%--sOut += '<tr><td>自我介绍:</td><td>'+data.introduce+'</td></tr>';--%>
    <%--sOut += '<tr><td>QQ:</td><td>'+data.qq+'</td></tr>';--%>
    <%--sOut += '<tr><td>手机号:</td><td>'+data.phoneNumber+'</td></tr>';--%>
    <%--sOut += '</table>';--%>

    <%--return sOut;--%>
  <%--}--%>


<%--</script>--%>


</body>
</html>




