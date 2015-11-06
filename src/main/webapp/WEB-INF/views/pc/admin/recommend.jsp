<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                请求管理
            </h3>
            <ul class="breadcrumb">
                <li><a href="/manage"><i class="fa fa-home"></i> 控制台</a></li>
                <li><a href=""></i> 通知管理</a></li>
                <li><a href="">请求管理</a></li>
            </ul>

        </div>
        <!-- page heading end-->

        <!--body wrapper start-->
        <div class="wrapper">
            <section class="panel panel-primary">
                <header class="panel-heading">
                    ${validation?'已推荐列表':'推荐审核列表'}
								<span class="tools pull-right">
									<a href="javascript:;" class="fa fa-chevron-down"></a>
								</span>
                </header>



                <div class="panel-body">
                    <div class="btn-group">
                        <c:if test="${!validation}">
                        <a class="add-button btn btn-success"
                           href="/manage/recommend?validation=true">
                           查看已经推荐的兼职
                        </a>
                        </c:if>
                        <c:if test="${validation}">
                            <a class="add-button btn btn-info"
                               href="/manage/recommend">
                                查看未推荐的兼职
                            </a>
                        </c:if>
                    </div>
                    <div class="adv-table">
                        <table class="display table table-bordered " id="hidden-table-info">
                            <thead>
                            <tr>
                                <th>id</th>
                                <th>发布人</th>
                                <th style="min-width: 200px;">标题+链接</th>
                                <th style="min-width: 120px;" class="hidden-phone">反馈时间</th>
                                <c:if test="${!validation}">
                                    <th class="hidden-phone">点我推广</th>
                                </c:if>
                                <c:if test="${validation}">
                                    <th class="hidden-phone">更新置顶</th>
                                </c:if>
                                <th class="hidden-phone">回复</th>
                                <th class="hidden-phone">删除</th>
                                <th class="hidden-phone">分类</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${list}" var="item">
                                <tr class="">
                                    <td>${item.id}</td>
                                    <td>${item.jobPost.member.username}</td>
                                    <td class="hidden-phone">
                                        <a target="_blank" href="
                                        <c:if test="${item.jobId != null}">/item/job/${item.jobId}</c:if>
                                            <c:if test="${item.shId!= null}">/item/sh/${item.shId}</c:if>
                                            <c:if test="${item.resumeId!= null}">/item/resume/${item.resumeId}</c:if>
                                            "> ${item.postTitle}</a>
                                        </td>
                                    <td class="center hidden-phone"><fmt:formatDate value="${item.applyTime}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
                                    <c:if test="${!validation}">
                                    <td class="center hidden-phone">
                                        <button class="btn btn-xs btn-primary RecommendBtn" data-id="${item.id}">点我推广</button>
                                    </td>
                                    </c:if>
                                    <c:if test="${validation}">
                                        <td class="center hidden-phone">
                                            <button class="btn btn-xs btn-success RecommendBtn" data-id="${item.id}">更新</button>
                                        </td>
                                    </c:if>
                                    <td class="center hidden-phone">
                                        <button href="" class="btn btn-xs btn-info">回复</button>
                                    </td>
                                    <td class="center hidden-phone">
                                        <button href="javascript:"
                                                data-id="${item.id}"
                                                class="btn btn-danger btn-xs DelRecommend">
                                            删除</button>
                                    </td>
                                    <td>
                                        <button class="btn btn-xs btn-info">
                                            <c:if test="${item.jobId != null}">兼职</c:if>
                                            <c:if test="${item.shId!= null}">二手</c:if>
                                            <c:if test="${item.resumeId!= null}">简历</c:if>
                                            <%--<c:if test="${item.resumeId}">二手</c:if>--%>
                                            <%--<c:if test="${item.shId}">简历</c:if>--%>
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
        <!--body wrapper end-->

        <jsp:include page="block/footer.jsp"></jsp:include>

    </div>
    <!-- main content end-->
</section>

<jsp:include page="block/end.jsp"></jsp:include>
<script type="text/javascript" language="javascript" src="/admin/js/advanced-datatable/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="/admin/js/data-tables/DT_bootstrap.js"></script>

<!--dynamic table initialization -->
<script src="/admin/js/acustom/table-init.js"></script>
<script type="text/javascript" src="/admin/js/jquery.validate.min.js"></script>

<script>
    $(function(){

        var recommend = {

            bindEvents: function(){
                $('.RecommendBtn').on('click',this.setRecommend);
                $('.DelRecommend').on('click',this.deleteRecommend);
            },
            setRecommend: function(){
                if(confirm("确定要设为推荐帖子吗?")){
                    var id = $(this).data('id');
                    $.ajax({
                        type:'PUT',
                        url: '/api/manage/recommend/'+id+'?validation=true'
                    }).success(function(data){
                        if(data.message === "success"){
                            alert('申请推荐成功');
                            location.reload();
                        }else{
                            alert(data.message);
                        }
                    })
                }
            },
            deleteRecommend: function(){
               var $this = $(this);
                if(confirm("确定要删除吗")){
                    var id = $this.data('id');
                    $.ajax({
                        type:'DELETE',
                        url: '/api/manage/recommend/'+id
                    }).success(function(data){
                        if(data.message === "success"){
                            alert('删除成功');
                            location.reload();
                        }else{
                            alert(data.message);
                        }
                    })
                }
            }
        };

        recommend.bindEvents();
    });

</script>

</body>
</html>


