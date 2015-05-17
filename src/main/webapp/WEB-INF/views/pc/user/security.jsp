<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-5-17
  Time: 下午8:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%request.setCharacterEncoding("UTF-8") ;%>

<jsp:include page="../block/start.jsp" >
  <jsp:param name="title" value="安全设置"/>
</jsp:include>

<jsp:include page="../block/top-bar-reverse.jsp"></jsp:include>

<div class="container user">
  <jsp:include page="../block/user.jsp"></jsp:include>

  <div class="segment end-segment">
    <p class="pin-title style2 ">修改密码</p>
    <form action="" class="">
      <div class="form-group">
        <label for="">原密码</label>
        <input type="text" class="form-control">
      </div>
      <div class="form-group">
        <label for="">新密码</label>
        <input type="text" class="form-control">
      </div>
      <div class="form-group">
        <label for="">再确认</label>
        <input type="text" class="form-control">
      </div>
    </form>
  </div>
</div>

<jsp:include page="../block/user-footer.jsp"></jsp:include>
</body>
</html>
