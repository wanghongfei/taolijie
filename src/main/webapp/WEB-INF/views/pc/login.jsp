<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-5-9
  Time: 下午5:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%request.setCharacterEncoding("UTF-8") ;%>
<%--html头部--%>
<jsp:include page="block/start.jsp">
  <jsp:param name="title" value="登录"/>
</jsp:include>
<!--JSP inheritance?-->
<link rel="stylesheet" href="/styles/root/login.css">
<%--顶栏--%>
<jsp:include page="block/top-bar.jsp"/>
<%--页首--%>
<jsp:include page="block/header2.jsp"/>

<!-- 正文 -->
<div class="login">
  <div class="login-container">
    <div class="login-box">
      <div class="login-body">
        <form action="/login" id="login-form">
          <%--<div class="form-group role-choose">--%>
            <%--<div class="col-6">--%>
              <%--<input type="radio" name="role" value=""> 学生--%>
            <%--</div>--%>
            <%--<div class="col-6">--%>
              <%--<input type="radio" name="role"> 商家--%>
            <%--</div>--%>
          <%--</div>--%>

          <div class="form-group horizontal">
            <label for="username" class="col-3 label-control">用户名</label>
            <div class="col-9">
              <input type="text" class="form-control" placeholder="输入用户名" id="username" name="username" >
            </div>
          </div>
          <div class="clear:both"></div>
          <div class="form-group horizontal">
            <label  for="password" class="label-control col-3" >密码</label>
            <div class="col-9">
              <input type="password" style="" class="form-control" placeholder="输入密码" id="password" name="password">
            </div>
          </div>

          <div class="col-12" >
            <label class="red" id="error-box"></label>
          </div>
          <div class="col-12 remember-me">
            <input type="checkbox" class="checkbox" id="rememberMe" name="rememberMe" value="true">记住密码
          </div>
          <div class="col-12 no-pd">
            <p class="login-btn btn theme-color-bg" id="sub-btn" >立即登陆</p>
          </div>
          <div class="col-12 no-pd">
            <a class="login-btn btn dark-green-bg" href="/register">没有账号?赶紧去注册吧</a>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>

<%--脚部--%>
<jsp:include page="block/footer.jsp"/>



</body>
</html>
