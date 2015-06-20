<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <div class="focus_detail">
          <div class="zixun zixun_top">
            <a href=""><img src="/static/images/users/${news.headPicturePath})" alt=""></a>
          </div>

          <div class="zixun zixun_bottom">
            <p class="title"><a href="">${news.title}</a></p>
            <p><i class="fa fa-history fa-lg"></i>时间:
                <fmt:formatDate value="${news.time}" pattern="yyyy-MM-dd"/>
            </p>
            <%--<p><i class="fa fa-map-marker fa-lg"></i>地点:理工大西侧三体西侧</p>--%>
            <div class="jianjie">
              <i class="fa fa-file-text-o fa-lg"></i>
              简介:<br>
                ${news.content}
            </div></p>
          </div>
          <div style="clear:both"></div>
        </div>
      </div>
    </div>




</div>

<%--脚部--%>
<jsp:include page="block/footer.jsp"/>

</body>
</html>
