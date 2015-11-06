<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--头部--%>
<jsp:include page="block/start.jsp"></jsp:include>
<%@ page session="false" %>

<body class="sticky-header">

<section>

  <jsp:include page="block/side.jsp"></jsp:include>

  <!-- main content start-->
  <div class="main-content" >


    <jsp:include page="block/header.jsp"></jsp:include>


    <!-- page heading start-->
    <div class="page-heading">
      <h3>
        所有用户
      </h3>
      <ul class="breadcrumb">
        <li><a href="/manage/index"><i class="fa fa-home"></i> 控制台</a></li>
        <li><a href=""></i> 用户管理</a></li>
        <li><a href=""> 所有用户</a></li>
      </ul>

    </div>
    <!-- page heading end-->

    <!--body wrapper start-->
    <div class="wrapper">
      <div class="row">
        <div class="col-sm-12">
          <section class="panel">
            <header class="panel-heading">
              用户列表
                        <span class="tools pull-right">
                            <a href="javascript:;" class="fa fa-chevron-down"></a>
                        </span>
            </header>


            <div class="panel-body">
              <div class="adv-table">
                <div class="clearfix">
                  <div class="btn-group">
                    <a href="/manage/user/add" class="btn btn-primary">
                      新增用户 <i class="fa fa-plus"></i>
                    </a>
                  </div>

                </div>
                <div class="space15"></div>


                <table class="display table table-bordered " id="hidden-table-info">
                  <thead>
                  <tr>
                    <th>id</th>
                    <th>用户名</th>
                    <th class="hidden-phone">用户状态</th>
                    <th class="hidden-phone">权限</th>
                    <th class="hidden-phone">修改</th>
                    <th class="hidden-phone">删除</th>
                  </tr>
                  </thead>
                  <tbody>
                  <c:forEach items="${users}" var="user">
                    <tr class="">
                      <td>${user.id}</td>
                      <td>${user.username}</td> <!-- 不超15字 -->
                      <td class="hidden-phone">${user.valid?'正常':'封号'}</td>
                      <td class="center hidden-phone">${user.id}</td>
                      <td class="center hidden-phone">
                        <a href="/manage/user/edit/${user.id}" class="btn btn-success btn-xs">修改</a>
                      </td>
                      <td class="center hidden-phone">
                        <button href="javascript:void(0);"
                                data-id="${user.id}"
                                data-type="user"
                                data-confirm-msg="确定${user.valid?'封号':'解封'}?"
                                data-ok-msg="${user.valid?'封号':'解封'}成功"
                                data- class="delete-btn btn ${user.valid?'btn-danger':'btn-warning'} btn-xs">
                                ${user.valid?'封号':'解封'}
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
<!--dynamic table initialization -->
<script src="/admin/js/acustom/table-init.js"></script>
<script src="/admin/js/acustom/request.js"></script>

<%--<script>--%>
  <%--jQuery(document).ready(function(){--%>
    <%--var nCloneTd = InitDetailsColumn();--%>
    <%--var url = '/manage/getuser';--%>
    <%--var detailUrl = '/manage/findUser/';--%>
    <%--var updateUrl = '/manage/updateUser/'--%>
    <%--var deleteUrl = '/manage/deleteUser/';--%>
    <%--var columns =  [--%>
      <%--{--%>
        <%--"mData": null,--%>
        <%--"bSortable": false,--%>
        <%--"mRender": function(o){ return nCloneTd.outerHTML}--%>
      <%--},--%>
      <%--{ "mData":"id" },--%>
      <%--{ "mData": "username" },--%>
      <%--{ "mData": "valid",--%>
        <%--"mRender": function(data,type,full){--%>
          <%--if(full.valid == false){--%>
            <%--return '<i class="text-danger">封号</i>';--%>
          <%--}else{--%>
            <%--return '正常';--%>
          <%--}--%>
        <%--}--%>
      <%--},--%>
      <%--{--%>
        <%--"mData": null,--%>
        <%--"bSortable": false,--%>
        <%--"mRender": function (data, type, full) {--%>
          <%--return '<a href="'+updateUrl+full.id+'" class="btn btn-xs btn-warning to-delete">修改</a>';--%>
        <%--}--%>
      <%--},--%>
      <%--{--%>
        <%--"mData": null,--%>
        <%--"bSortable": false,--%>
        <%--"mRender": function (data, type, full) {--%>
          <%--return '<a href="javascript:void(0);" onclick="toDelete(this,'+full.id+',\''+deleteUrl+'\')" class="btn btn-xs btn-danger to-delete">删除</a>';--%>
        <%--}--%>
      <%--}--%>
    <%--];--%>


    <%--var formatFunc = generateDetail;--%>

    <%--initDataTable(url,detailUrl,columns,formatFunc);--%>
  <%--});--%>




  <%--function generateDetail(data){--%>
    <%--var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';--%>
    <%--console.log(data);--%>
    <%--sOut += '<tr><td>age:</td><td>'+(data.age == undefined ? '未填写':data.age)+'</td></tr>';--%>
    <%--sOut += '<tr><td>email:</td><td>'+(data.email == undefined ? '未填写':data.email)+'</td></tr>';--%>
    <%--sOut += '<tr><td>name:</td><td>'+(data.name== undefined ? '未填写':data.name)+'</td></tr>';--%>
    <%--sOut += '<tr><td>用户类型:</td><td>'+data.roleIdList[0]+'</td></tr>';--%>

    <%--sOut += '</table>';--%>

    <%--return sOut;--%>
  <%--}--%>


</script>

</body>
</html>
