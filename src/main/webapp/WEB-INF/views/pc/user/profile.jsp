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
    <jsp:include page="../block/user.jsp">
        <jsp:param name="navShow" value="profile"/>
    </jsp:include>

    <div class="segment personal">
        <p class="pin-title  dark-green-bg">账号信息
            <i class="pin-arrow  dark-green-arrow"></i>
        </p>
        <p  class="accout-info">账号： ${sessionScope.user.username}<span>${sessionScope.role.memo}</span></p>
        <p class="pin-title  dark-green-bg">基本信息
            <i class="pin-arrow  dark-green-arrow"></i>
        </p>
        <form action="" id="ProfileForm">
            <div class="form-group">
                <label for="">昵称<i class="theme-color">*</i></label>
                <input type="text" name="name" class="form-control" placeholder="姓名" value="${user.name}">
            </div>
            <div class="form-group">
                <label for="">性别<i class="star-symbol">*</i></label>
                <input type="radio" name="gender" value="男" ${user.gender == "男"?'checked="checked"':''}>男
                <input type="radio" name="gender" value="女" ${user.gender == "女"?'checked="checked"':''}>女
            </div>
            <div class="segment">
                <div class="submit-btn big-btn dark-green-bg">
                    <span href="javascript:void(0);">保存资料</span>
                </div>
            </div>
        </form>
    </div>
</div>


<jsp:include page="../block/user-footer.jsp"></jsp:include>
<script src="/scripts/profile.js"></script>
</body>
</html>
