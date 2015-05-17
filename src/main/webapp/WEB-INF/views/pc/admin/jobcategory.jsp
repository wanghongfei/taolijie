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
        分类管理
      </h3>
      <ul class="breadcrumb">
        <li><a href="/manage/index"><i class="fa fa-home"></i> 控制台</a></li>
        <li><a href=""></i> 分类管理</a></li>
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
                    <a class="add-button btn btn-success" href="/manage/addcategory?type=0">
                      添加兼职分类 <i class="fa fa-plus"></i>
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

<!--script for editable table-->
<%--<script src="/admin/js/editable-table.js"></script>--%>

<script>
  $(document).ready(function(){
    var url = '/manage/jobcatelist';
    var deleteUrl = '/manage/deljobcate/';

    var columns =  [
      { "mData":"id" },
      { "mData":"name" },
      { "mData":"memo" },
      { "mData":"level" },
      { "mData":"themeColor" },
      {
        "mData": null,
        "bSortable": false,
        "mRender": function (data, type, full) {
          return '<a class="edit" href="javascript:;" >编辑</a>';
        }
      },
      {
        "mData": null,
        "bSortable": false,
        "mRender": function (data, type, full) {
          return '<a class="delete" onclick="toDelete(this,'+full.id+',\''+deleteUrl+'\''+
                  ',\''+'type=0'+'\')"  href="javascript:;" >删除</a>';
        }
      }
    ];


    var formatFunc = generateDetail;


    initDataTable(url,undefined,columns,formatFunc,[[0,'desc']]);
  });

  function generateDetail(data){
    var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
    console.log(data);
    sOut += '<tr><td>age:</td><td>'+(data.age == undefined ? '未填写':data.age)+'</td></tr>';
    sOut += '<tr><td>email:</td><td>'+(data.email == undefined ? '未填写':data.email)+'</td></tr>';
    sOut += '<tr><td>name:</td><td>'+(data.name== undefined ? '未填写':data.name)+'</td></tr>';
    sOut += '<tr><td>用户类型:</td><td>'+data.roleIdList[0]+'</td></tr>';

    sOut += '</table>';

    return sOut;
  }


</script>


<%--<!-- END JAVASCRIPTS -->--%>
<%--<script>--%>


  <%--jQuery(document).ready(function() {--%>
    <%--var col =  [--%>
      <%--{ "mData":"id" },--%>
      <%--{ "mData":"name" },--%>
      <%--{ "mData":"memo" },--%>
      <%--{ "mData":"level" },--%>
      <%--{ "mData":"themeColor" },--%>
      <%--{--%>
        <%--"mData": null,--%>
        <%--"bSortable": false,--%>
        <%--"mRender": function (data, type, full) {--%>
          <%--return '<a class="edit" href="javascript:;" >编辑</a>';--%>
        <%--}--%>
      <%--},--%>
      <%--{--%>
        <%--"mData": null,--%>
        <%--"bSortable": false,--%>
        <%--"mRender": function (data, type, full) {--%>
          <%--return '<a class="delete" href="javascript:;" >删除</a>';--%>
        <%--}--%>
      <%--}--%>
    <%--];--%>

    <%--EditableTable.init({--%>
      <%--id:'JobCategory',--%>
      <%--url:'/manage/jobcatelist',--%>
      <%--columns:col--%>
    <%--});--%>
  <%--});--%>
<%--</script>--%>


</body>
</html>

