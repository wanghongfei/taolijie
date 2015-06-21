<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-5-9
  Time: 下午3:18
  To change this template use File | Settings | File Templates.
--%>
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



    <!-- 正文 -->
    <div class="main index">
      <div class="segment">
        <p class="pin-title">校园聚焦点
          <i class="pin-arrow"></i>
        </p>
      </div>
      <div class="segment focus">
        <c:forEach items="${newsList}" var="aNews">
          <div class="focus_list">
            <div class="zixun zixun_left">
              <a href=""><img src="./images/zixun.png" alt=""></a>
            </div>
            <div class="zixun zixun_right">
              <p class="title"><a href="/detail/news/${aNews.id}">${aNews.title}</a></p>
              <p><i class="fa fa-history fa-lg"></i>时间:
                <fmt:formatDate value="${aNews.time}"  pattern="yyyy-MM-dd" />
              </p>
              <p>
                <i class="fa fa-file-text-o fa-lg"></i>
                简介:
                <c:set var="str" value="${aNews.content.replaceAll('<.*?>','')}"></c:set>
                <c:choose>
                  <c:when test="${str.length()>25}">
                    <c:out value="${fn:substring(str, 0, 25)}......" />
                  </c:when>
                  <c:otherwise>
                    <c:out value="${str}" />
                  </c:otherwise>
                </c:choose>
                <span><a href="/detail/news/${aNews.id}">查看详情 》</a></span>
              </p>
            </div>
          </div>
        </c:forEach>

      </div>
    </div>



</div>

<%--脚部--%>
<jsp:include page="block/footer.jsp"/>

</body>
</html>
