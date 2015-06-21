<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-6-2
  Time: 下午7:18
  To change this template use File | Settings | File Templates.
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%request.setCharacterEncoding("UTF-8");%>
<%--html头部--%>
<jsp:include page="block/start.jsp">
  <jsp:param name="title" value="兼职"/>
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


      <div class="resumelist main">
          <ul class="nav-bar">
              <li>求职意向</li>
              <li>选择性别</li>
              <li class="choose">选择专业&nbsp;&nbsp;&nbsp;&nbsp;<i class="fa fa-caret-down"></i>
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
              <c:forEach items="${resumes}" var="resume">
                  <a href="/item/resume/${resume.id}">
                      <div class="list">
                          <img src="${resume.photoPath}" alt="">
                          <div>
                              <div class="fl">
                                  <p class="info">${resume.name} <i class="fa fa-cog theme-color"></i> <span>${resume.age}岁</span></p>
                                  <p class="intent">
                                      <span class="intent-title">求职意向</span>
                                      <%--<span>${resume.}</span>--%>
                                  </p>
                              </div>

                              <div class="fr">
                                  <%--<p>${resume.}</p>--%>
                                  <p>
                                      <span>更新时间 ： ${resume.createdTime}</span>
                                  </p>
                              </div>
                          </div>
                      </div>
                  </a>

              </c:forEach>
          </div>
          <div style="clear:both"></div>
          <div class="page">
              <ul>
                  <c:if test="${page != 1 && pageStatus !=0}">
                      <li><a class="next" href="/list/resume?page=${page-1}">上一页</a></li>
                  </c:if>
                  <c:if test="${pageStatus == 2}">
                      <li><a class="next" href="/list/resume?page=${page+1}">下一页</a></li>
                  </c:if>
                  <c:if test="${pageStatus == 0 }">
                      <h2>没有更多了</h2>
                  </c:if>
              </ul>
          </div>
      </div>



</div>

<%--脚部--%>
<jsp:include page="block/footer.jsp"/>

</body>
</html>
