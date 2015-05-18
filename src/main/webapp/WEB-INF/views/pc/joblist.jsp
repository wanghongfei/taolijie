<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-5-9
  Time: 下午3:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>



<%request.setCharacterEncoding("UTF-8") ;%>
<%--html头部--%>
<jsp:include page="block/start.jsp">
  <jsp:param name="title" value="兼职" />
</jsp:include>
<%--顶栏--%>
<jsp:include page="block/top-bar.jsp"/>
<%--页首--%>
<jsp:include page="block/header.jsp"/>

<div class="container">


  <%--轮播--%>
  <jsp:include page="block/silder.jsp"/>
  <%--侧边栏--%>
  <jsp:include page="block/side.jsp"/>

  <!-- 正文 -->
  <div class="joblist main">
    <ul class="nav-bar">
      <li>最新发布</li>
      <li>最热兼职</li>
      <li class="choose">结算方式&nbsp;&nbsp;&nbsp;&nbsp;<i class="fa fa-caret-down"></i>
        <div class="choose-menu">
          <ul>
            <li class="actived">全部</li>
            <li>日结</li>
            <li>周结</li>
            <li class="no-border">月结</li>
          </ul>
        </div>
      </li>
    </ul>
    <div class="lists">
      <c:forEach var="job" items="${jobs}" varStatus="status">
        <a href="/item/job/${job.id}">
          <div class="list">
            <div class="list-type">

              <span>${job.categoryName}</span>
            </div>
            <div class="list-title">${job.title}<span>${job.verified ? '已认证': ''}</span></div>
            <span>${job.workPlace}</span>&nbsp;&nbsp;
            <span>${job.wage}元/${job.salaryUnit}</span>&nbsp;&nbsp;
            <span>${job.timeToPay}</span>
            <span>${job.postTime}</span>
          </div>
        </a>
      </c:forEach>
    </div>
    <div style="clear:both"></div>
    <div class="page">
      <ul>
        <li><a class="active" href="">1</a></li>
        <li><a href="">2</a></li>
        <li><a href="">3</a></li>
        <li><a href="">4</a></li>
        <li><a href="">5</a></li>
        <li><a href="">6</a></li>
        <li><a  class="next" href="">下一页</a></li>
      </ul>
    </div>
  </div>


</div>

<%--脚部--%>
<jsp:include page="block/footer.jsp"/>

</body>
</html>