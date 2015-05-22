<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-5-20
  Time: 下午12:16
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>



<%request.setCharacterEncoding("UTF-8") ;%>
<%--html头部--%>
<jsp:include page="block/start.jsp">
  <jsp:param name="title" value="二手列表" />
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

      <div class="shlist main">
          <ul class="nav-bar">
              <li>最新发布</li>
              <li>最热二手</li>
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

          <div class="shs">
              <c:forEach var="sh" items="${shs}" varStatus="status">
                  <a href="/item/sh/${sh.id}">
                      <div class="sh-slip fl ${(status.index+1)%3 == 0 ? 'no-margin-right':''}" >
                          <img src="/images/pig.jpg" alt="">
                          <p class="titile">${sh.title}</p>
                          <div class="fl">
                              <p>${sh.categoryName}</p>
                              <span>${sh.memberId}</span>
                              <%--换成member的role--%>
                              <span class="theme-color">${sh.memberId}</span>
                          </div>
                          <span class="fr">￥${sh.sellPrice}</span>
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

