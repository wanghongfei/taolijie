<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-5-7
  Time: 下午1:36
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
        ${isEdit ? ('修改新闻 -- '.concat(member.username)):'添加新闻'}
      </h3>
      <ul class="breadcrumb">
        <li><a href="/manage"><i class="fa fa-home"></i> 控制台</a></li>
        <li><a href=""></i> 新闻管理</a></li>
        <li><a href="">${isEdit ? ('修改新闻 -- '.concat(member.username)):'添加新闻'} </a></li>
      </ul>

    </div>
    <!-- page heading end-->

    <!--body wrapper start-->
    <div class="wrapper">

      <section class="panel panel-info">
        <header class="panel-heading">
          <h3 class="panel-title">${isEdit ? ('修改新闻 -- '.concat(member.username)):'添加新闻'}</h3>

        </header>
        <div class="panel-body">
          <form action="#" class="form-horizontal" id="newsForm">
            <div class="form-group">
              <label class="control-label col-md-2" for="title">新闻标题</label>
              <div class="col-md-6 col-xs-11">
                <input class="form-control" id="title" name="title" placeholder="输入新闻标题(30字以内)" type="text" value="${aNews.title}">
              </div>
            </div>
            <div class="form-group">
              <div class="col-md-offset-2 col-md-7">
                <textarea name="content" id="content" class="wysihtml5 form-control" rows="9" ></textarea>
              </div>
            </div>

            <input type="hidden" id="newsId" value="${aNews.id}"/>
            <input type="hidden" id="isEdit" value="${isEdit}"/>
            <button type="submit" style="margin-top:30px;margin-bottom:30px" class="col-md-offset-2 btn btn-warning">
              ${isEdit?'修改':'添加'}新闻</button>
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

<%--富文本--%>
<script type="text/javascript" src="/admin/js/bootstrap-wysihtml5/wysihtml5-0.3.0.js"></script>
<script type="text/javascript" src="/admin/js/bootstrap-wysihtml5/bootstrap-wysihtml5.js"></script>
<script>
  jQuery(document).ready(function(){
//
//    var myCustomTemplates = {
//      html : function(locale) {
//        return "<li>" +
//                "<div class='btn-group'>" +
//                "<a class='btn' data-wysihtml5-action='change_view' title='" + locale.html.edit + "'>HTML</a>" +
//                "</div>" +
//                "</li>";
//      }
//    };

// pass in your custom templates on init

   $('.wysihtml5').wysihtml5({
     customTemplates:"1212312"
   });
    $('.wysihtml5').html("${aNews.content}");

//    var editor = new wysihtml5.Editor(".content",{
//      placeholderText:'12313'
//    });
  });
</script>

</body>
</html>



