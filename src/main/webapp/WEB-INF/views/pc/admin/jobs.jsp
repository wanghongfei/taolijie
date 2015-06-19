<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                                      <th>标题</th>
                                      <th class="hidden-phone">发布人</th>
                                      <th class="hidden-phone">发布时间</th>
                                      <th class="hidden-phone">投诉数</th>
                                      <th class="hidden-phone">顶</th>
                                      <th class="hidden-phone">踩</th>
                                      <th class="hidden-phone">删除</th>
                                  </tr>
                                  </thead>
                                  <tbody>
                                  <c:forEach items="${jobs}" var="job">
                                      <tr class="">
                                          <td>${job.id}</td>
                                          <td>${job.title}</td> <!-- 不超15字 -->
                                          <td class="hidden-phone">${job.member.username}</td>
                                          <td class="center hidden-phone">${job.postTime}</td>
                                          <td class="center hidden-phone">${job.complaint}</td>
                                          <td class="center hidden-phone">${job.likes}</td>
                                          <td class="center hidden-phone">${job.dislikes}</td>
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
<script src="/admin/js/acustom/table-init.js"></script>

<%--<script>--%>
  <%--$(document).ready(function(){--%>
    <%--var nCloneTd = InitDetailsColumn();--%>
    <%--var url = '/api/list/job/1';--%>
    <%--var detailUrl = '/api/item/job/';--%>
    <%--var deleteUrl = '/user/job/del/';--%>
    <%--var columns =  [--%>
      <%--{--%>
        <%--"mData": null,--%>
        <%--"bSortable": false,--%>
        <%--"mRender": function(o){ return nCloneTd.outerHTML}--%>
      <%--},--%>
      <%--{ "mData":"id" },--%>
      <%--{ "mData": "title" },--%>
      <%--{ "mData": "memberId" },--%>
      <%--{ "mData": "postTime" },--%>
      <%--{ "mData": "expiredTime" },--%>
      <%--{ "mData": "categoryName"},--%>
      <%--{ "mData": "likes" },--%>
      <%--{ "mData": "dislikes" },--%>
      <%--{--%>
        <%--"mData": null,--%>
        <%--"bSortable": false,--%>
        <%--"mRender": function (data, type, full) { @RequestMapping(value = "/job",method = RequestMethod.GET)
    public String jobCategory(Model model){
        int page = 1;
        int capacity = 9999;
        List<JobPostCategoryModel> list = jobPostCateService.getCategoryList(page-1, capacity, new ObjWrapper());
        model.addAttribute("jobCates",list);
        return "pc/admin/jobcategory";
    }--%>
          <%--return '<a href="javascript:void(0);" onclick="toDelete(this,'+full.id+',\''+deleteUrl+'\')" class="btn btn-xs btn-warning to-delete">删除</a>';--%>
        <%--}--%>
      <%--}--%>
    <%--];--%>


    <%--var formatFunc = generateDetail;--%>

    <%--initDataTable(url,detailUrl,columns,formatFunc);--%>
  <%--});--%>

  <%--function generateDetail(data){--%>
    <%--var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';--%>
    <%--sOut += '<tr><td>工作描述:</td><td>'+data.jobDetail+'</td></tr>';--%>
    <%--sOut += '<tr><td>工作详情:</td><td>'+data.jobDescription+'</td></tr>';--%>
    <%--sOut += '<tr><td>工资:</td><td>'+data.wage+data.salaryUnit+'/'+data.timeToPay+'</td></tr>';--%>
    <%--sOut += '</table>';--%>

    <%--return sOut;--%>
  <%--}--%>


<%--</script>--%>


</body>
</html>



