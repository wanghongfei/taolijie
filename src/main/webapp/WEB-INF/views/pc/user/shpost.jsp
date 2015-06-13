<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-5-17
  Time: 下午8:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%request.setCharacterEncoding("UTF-8") ;%>
<jsp:include page="../block/start.jsp" >
    <jsp:param name="title" value="二手发布"/>
</jsp:include>

<jsp:include page="../block/top-bar-reverse.jsp"></jsp:include>

<div class="container user">
    <jsp:include page="../block/user.jsp"></jsp:include>
  <div class="segment end-segment">
    <p class="pin-title dark-green-bg ">二手物品介绍
      <i class="pin-arrow  dark-green-arrow"></i>
    </p>
    <form action="" id="ShPostForm">
      <div class="form-group">
        <label for="">商品名称</label>
        <input name="title" type="text" class="form-control" >
      </div>
      <div class="form-group">
        <label for="">商品分类</label>
        <select name="categoryId" >
          <option value="">选择分类</option>
          <c:forEach items="${cates}" var="cate">
            <option value="${cate.id}">${cate.name}</option>
          </c:forEach>
        </select>
      </div>
      <div class="form-group">
        <label for="">价格</label>
        <input name="sellPrice" type="text" class="form-control" >
      </div>
      <div class="form-group">
        <label for="">商品描述</label>
        <textarea name="description" class="form-control"></textarea>
      </div>
      <div class="form-group">
        <label for="">图片</label>
        <textarea name="picIds" class="form-control" value="11;12">11;12</textarea>
      </div>
      <%--<div class="form-group">--%>
        <%--<label for="">交易地点</label>--%>
        <%--<input name="" type="text" class="form-control" >--%>
      <%--</div>--%>

    <p class="pin-title dark-green-bg ">联系方式
      <i class="pin-arrow  dark-green-arrow"></i>
    </p>

      <div class="form-group">
        <label for="">联系人(暂无)</label>
        <input type="text" class="form-control" >
      </div>
      <div class="form-group">
        <label for="">手机号(暂无)</label>
        <input type="text" class="form-control" >
      </div>
      <div class="form-group">
        <label for="">QQ号(暂无)</label>
        <input type="text" class="form-control" >
      </div>
    </form>

    <div class="segment">
      <div class="submit-btn big-btn dark-green-bg">
        <span href="javascript:void(0);">发布二手</span>
      </div>
    </div>

  </div>
</div>

<jsp:include page="../block/user-footer.jsp"></jsp:include>
<script src="/scripts/shpost.js"></script>
</body>
</html>
