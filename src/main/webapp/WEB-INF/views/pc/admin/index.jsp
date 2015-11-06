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
                控制台
            </h3>
            <ul class="breadcrumb">
                <li><a href="index"><i class="fa fa-home"></i> 控制台</a></li>
            </ul>

        </div>
        <!-- page heading end-->

        <!--body wrapper start-->
        <div class="wrapper">

        </div>
        <!--body wrapper end-->

        <jsp:include page="block/footer.jsp"></jsp:include>

    </div>
    <!-- main content end-->
</section>

<jsp:include page="block/end.jsp"></jsp:include>

</body>
</html>
