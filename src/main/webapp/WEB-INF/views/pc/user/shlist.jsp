<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-6-2
  Time: 下午4:23
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%request.setCharacterEncoding("UTF-8") ;%>

<jsp:include page="../block/start.jsp" >
  <jsp:param name="title" value="${isFav?'我的收藏':'我的发布'}"/>
</jsp:include>

<jsp:include page="../block/top-bar-reverse.jsp"></jsp:include>

<div class="container user">
  <jsp:include page="../block/user.jsp">
      <jsp:param name="navShow" value="${isFav?'favlist':'postlist'}"/>
    </jsp:include>
  <style>
    .col_main_top .title a:hover{
      color: #666 !important;
    }
  </style>

  <div class="segment infos resume link-segment">
    <div class="quickbtn">
      <ul>
        <li><a href="/user/job/${isFav?'myfav':'mypost'}" >兼职信息</a></li>
        <li><a href="/user/sh/${isFav?'myfav':'mypost'}" style="border-bottom: 2px #4ccda4 solid;">二手物品</a></li>
        <%--${isFav?'<li><a href="/user/resume/myfav">兼职简历</a></li>':''}--%>
      </ul>
    </div>
    <div style="clear:both"></div>
    <div class="col_content">
      <c:forEach items="${shs}" var="sh">
        <div class="col_list">
          <div class="col_choice" style="line-height:112px; width:45px;">
            <input name="collection" type="checkbox" value="" data-id="${sh.id}" class="col_del_check">
          </div>
          <div class="col_style" style="background-color: ${sh.category.themeColor};">${sh.category.name}

          </div></a>
          <div class="col_main" style="margin-left:40px; width:630px;">
            <div class="col_main_top">
              <div class="title" style="width:450px;"><a href="/item/sh/${sh.id}" style="color: #333">${sh.title}</a></div>
              <div class="style" style="width: 150px;">${sh.member.username}</div>
            </div>
            <div class="col_main_bottom">
              <div class="location" style="width:140px;"><i class="fa fa-map-marker fa-lg"></i> ${sh.tradePlace}</div>
              <div class="salary" style="color:#a47e3c;width: 120px;" ><i class="fa fa-jpy fa-lg"></i> ${sh.sellPrice.intValue()}元</div>
              <%--<div class="salarystyle" >&nbsp;&nbsp;${job.timeToPay}</div>--%>
              <div class="time"><i class="fa fa-clock-o fa-lg"></i>
                <fmt:formatDate value="${sh.postTime}" pattern ="yyyy-MM-dd hh:mm" />
              </div>

                <%--<div class="clickno">800</div>--%>
            </div>
          </div>
          <div class="col_delete">
            <a href="javascript:void(0);" class="UserDel" data-id="${sh.id}" data-type="sh"  data-option = "${isFav?'fav':''}" ><i class="fa fa-trash fa-2x"></i><br>删除</a>
          </div>
        </div>
      </c:forEach>

      <div class="page">
        <ul>
          <c:if test="${page != 1 && pageStatus !=0}">
            <li><a class="next" href="${isFav?'/user/sh/myfav':'/user/sh/mypost'}?page=${page-1}">上一页</a></li>
          </c:if>
          <c:if test="${pageStatus == 2}">
            <li><a class="next" href="${isFav?'/user/sh/myfav':'/user/sh/mypost'}?page=${page+1}">下一页</a></li>
          </c:if>
          <c:if test="${pageStatus == 0 }">
            <h2>没有更多了</h2>
          </c:if>
        </ul>
      </div>
      <div class="col_delete_all">
        <div class="col_choice">
          <input name="" type="checkbox" value="" id="checkAll">全选
          <button class="del_all"  data-type="sh"  data-option = "${isFav?'fav':''}">删除选中项</button>
        </div>
      </div>
    </div>
    <%--</div>--%>
  </div>
    </div>

  <jsp:include page="../block/user-footer.jsp"></jsp:include>
  <script src="/scripts/security.js"></script>
  </body>
  </html>

