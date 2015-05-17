<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-5-17
  Time: 下午8:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%request.setCharacterEncoding("UTF-8") ;%>


<jsp:include page="../block/start.jsp" >
    <jsp:param name="title" value="个人资料修改"/>
</jsp:include>

<jsp:include page="../block/top-bar-reverse.jsp"></jsp:include>

<div class="container user">
    <jsp:include page="../block/user.jsp"></jsp:include>

    <div class="segment personal">
        <p class="pin-title  dark-green-bg">账号信息
            <i class="pin-arrow  dark-green-arrow"></i>
        </p>
        <p  class="accout-info">账号： Wynfrith <span>学生</span></p>
        <p class="pin-title  dark-green-bg">基本信息
            <i class="pin-arrow  dark-green-arrow"></i>
        </p>
        <form action="">
            <div class="form-group">
                <label for="">昵称<i class="theme-color">*</i></label>
                <input type="text" class="form-control" placeholder="姓名">
            </div>
            <div class="form-group">
                <label for="">性别<i class="star-symbol">*</i></label>
                <input type="radio" name="render" value="">男
                <input type="radio" name="render" value="">女
            </div>
            <div></div>
            <div class="form-group">
                <label for="">学号</label>
                <input type="text" class="form-control" placeholder="填写你的学号">
            </div>
            <div class="form-group">
                <label for="">学校<i class="star-symbol">*</i></label>
                <input type="text" class="form-control" placeholder="填写学校">
            </div>
            <div class="form-group">
                <label for="">学院</label>
                <input type="text" class="form-control" placeholder="填写您的学院">
            </div>
        </form>
    </div>
</div>

<jsp:include page="../block/user-footer.jsp"></jsp:include>

</body>
</html>
