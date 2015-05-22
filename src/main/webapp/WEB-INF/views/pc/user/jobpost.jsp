<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-5-17
  Time: 下午8:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%request.setCharacterEncoding("UTF-8") ;%>
<jsp:include page="../block/start.jsp" >
    <jsp:param name="title" value="兼职发布"/>
</jsp:include>

<jsp:include page="../block/top-bar-reverse.jsp"></jsp:include>


<div class="container user">
    <jsp:include page="../block/user.jsp"></jsp:include>

  <div class="segment end-segment">
    <p class="pin-title red-bg ">基本信息
      <i class="pin-arrow  dark-red-arrow"></i>
    </p>
    <form action="" id="JobPostForm">
      <div class="form-group">
        <label for="">兼职标题</label>
        <input type="text" class="form-control" name="title">
      </div>
      <div class="form-group">
        <label for="">选择分类</label>

        <select name="categoryId" >
            <option value="">选择分类</option>
            <c:forEach items="${cates}" var="cate">
                <option value="${cate.id}">${cate.name}</option>
            </c:forEach>
        </select>
      </div>
      <div class="form-group">
        <label for="">工资待遇</label>
        <input type="text" class="form-control short-input" name="wage">
          <p style="float: left">元</p>
      </div>
      <div class="form-group">
        <label for="">结算方式</label>
        <select name="timeToPay">
          <option value="-1">选择分类</option>
          <option value="日结">日结</option>
          <option value="周结">周结</option>
          <option value="月结">月结</option>
        </select>
      </div>
      <div class="form-group">
        <label for="">有效时间</label>
        <input type="text" class="form-control" name="expiredTime" >
      </div>

    <p class="pin-title red-bg ">兼职详情
      <i class="pin-arrow  dark-red-arrow"></i>
    </p>

      <div class="form-group">
        <label for="">工作时间</label>
        <input type="text" class="form-control" name="workTime">
      </div>
      <div class="form-group">
        <label for="">工作地点</label>
        <input type="text" class="form-control" name="workPlace" >
      </div>
      <div class="form-group">
        <label for="">工作内容</label>
        <textarea name="jobDetail" id="" cols="30" rows="10"></textarea></textarea>
      </div>
      <div class="form-group">
        <label for="">工作要求</label>
        <textarea name="jobDescription" ></textarea>
      </div>

    <p class="pin-title red-bg ">兼职详情
      <i class="pin-arrow  dark-red-arrow"></i>
    </p>
      <div class="form-group">
        <label for="">联系人*</label>
        <input type="text" class="form-control" name="contact" >
      </div>
      <div class="form-group">
        <label for="">手机号*</label>
        <input type="text" class="form-control" name="contactPhone">
      </div>
      <div class="form-group">
        <label for="">QQ号</label>
        <input type="text" class="form-control" name="contactQq" >
      </div>

    <div class="segment">
      <div class="submit-btn big-btn red-bg">
        <span href="javascript:void(0);">发布兼职</span>
      </div>
    </div>
    </form>
  </div>
</div>


<jsp:include page="../block/user-footer.jsp"></jsp:include>
<script src="/scripts/jobpost.js"></script>

</body>
</html>
