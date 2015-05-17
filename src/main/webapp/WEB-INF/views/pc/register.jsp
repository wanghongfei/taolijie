<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-5-9
  Time: 下午5:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%request.setCharacterEncoding("UTF-8") ;%>
<%--html头部--%>
<jsp:include page="block/start.jsp">
  <jsp:param name="title" value="注册"/>
</jsp:include>
<%--顶栏--%>
<jsp:include page="block/top-bar.jsp"/>
<%--页首--%>
<jsp:include page="block/header2.jsp"/>


<!-- 正文 -->
<div class="login">
  <div class="login-container">
    <div class="login-box">
      <div class="login-body">
        <form action="" id="reg-form">

          <div class="form-group">
            <label for="" class="col-4 label-control">用户名</label>
            <div class="col-8">
              <input type="text" class="form-control">
            </div>

          </div>
          <div class="form-group">
            <label for="" class="col-4 label-control">密码</label>
            <div class="col-8	">
              <input type="text" class="form-control">
            </div>
          </div>
          <div class="form-group">
            <label for="" class="col-4 label-control">确认密码</label>
            <div class="col-8">
              <input type="text" class="form-control">
            </div>
          </div>
          <div class="col-12 no-pd">
            <p class="login-btn btn theme-color-bg">注册</p>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>

<%--脚部--%>
<jsp:include page="block/footer.jsp"/>




