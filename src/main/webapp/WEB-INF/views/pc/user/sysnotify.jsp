<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%request.setCharacterEncoding("UTF-8") ;%>




<!doctype html>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <title>消息通知</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="/styles/animate.css"/>
    <link rel="stylesheet" href="/styles/style.css">
    <link rel="stylesheet" href="/styles/webuploader.css"/>
    <link rel="stylesheet" href="/styles/jquery.bxslider.css">
    <%--<link rel="stylesheet" href="http://libs.useso.com/js/font-awesome/4.2.0/css/font-awesome.min.css">--%>
    <link rel="stylesheet" href="/styles/font-awesome.min.css"/>
    <link rel="stylesheet" href="/styles/user/notify.css"/>

    <script src="/scripts/modernizr.js"></script>

</head>
<body>
<!--[if lt IE 10]>
<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
<![endif]-->


<jsp:include page="../block/top-bar-reverse.jsp"></jsp:include>

<div class="container user">
    <jsp:include page="../block/user.jsp">
        <jsp:param name="navShow" value="notify"/>
    </jsp:include>

    <div class="segment infos link-segment">
        <div class="nav">
            <ul>
                <li class=""><a href="/user/notify/pri">个人消息</a></li>
                <li class="active"><a href="/user/notify/sys">系统通知</a></li>
            </ul>
        </div>

        <table class="note-table">
            <thead>
            <tr>
                <%--<th ></th>--%>
                <th>发件人</th>
                <th width="540">消息内容</th>
                <th>发件时间</th>
                <th>设为已读</th>
                <%--<th>删除</th>--%>
            </tr>
            </thead>
            <tbody>
                <c:forEach items="${notes}" var="note">
                <tr class="${note.isRead?'read':''}" data-id="${note.id}">
                        <%--<td>--%>
                        <%--<input type="checkbox" class="choose" value="">--%>
                        <%--</td>--%>
                    <td>
                        <span class="note-poster">系统通知</span>
                    </td>
                    <td>
                        <span class="note-content read" href="">${note.content}</span>
                    </td>
                    <td ><span class="note-time read"><fmt:formatDate value="${note.time}" pattern="yyyy-MM-dd HH:mm:ss"/></span></td>
                    <td style="text-align:center">
                        <button type="button" name="" class="bell-btn"><i class="fa ${note.isRead ? 'fa-bell':'fa-bell-o'}"></i></button>
                    </td>
                        <%--<td style="text-align:center">--%>
                        <%--<button type="button" name="" class="note-del-btn"><i class="fa fa-trash-o"></i></button>--%>
                        <%--</td>--%>
                </tr>
                </c:forEach>
            </tbody>
        </table>

    </div>
    <jsp:include page="../block/user-footer.jsp"></jsp:include>

    <script>
        $(function(){
            $(document).on('click','.bell-btn', function () {
                var $tr = $(this).closest('tr'),
                        $bell = $tr.find('.bell-btn i');
                var $id = $tr.data('id');
                console.log($id);
                $.ajax({
                    type: 'PUT',
                    url: '/api/noti/sys/mark?notiId='+$id,
                }).success(function(data){
                    console.log(data);
                    if(data.ok){
                        $bell.removeClass('fa-bell-o');
                        $bell.addClass('fa-bell');
                        $tr.addClass('read');
                    }else{
                        console.log(data.message);
                    }
                });

            })
        });
    </script>
</body>
</html>

