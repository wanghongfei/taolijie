<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-5-17
  Time: 下午8:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%request.setCharacterEncoding("UTF-8") ;%>
<jsp:include page="../block/start.jsp" >
    <jsp:param name="title" value="意见反馈"/>
</jsp:include>

<jsp:include page="../block/top-bar-reverse.jsp"></jsp:include>


<div class="container user">
    <jsp:include page="../block/user.jsp">
      <jsp:param name="navShow" value="feedback"/>
    </jsp:include>
  <div class="segment feedback end-segment">
    <div>
      <img src="/images/logo.jpg" alt="">
    </div>
    <form action="" class="" id="FeedBackForm">
      <div class="form-group">
        <textarea name="content" class="form-control" placeholder="把您最棒的意见，最有创意的想法，告诉小桃吧"></textarea >
        <div>
          <input type="text" name="email" placeholder="输入您的邮箱,方便与您沟通哦">
          <span class="fr big-btn theme-color-bg submit-btn">提交</span>
        </div>
      </div>
    </form>
  </div>
</div>

<jsp:include page="../block/user-footer.jsp"></jsp:include>
<script src="/scripts/feedback.js"></script>
</body>
</html>