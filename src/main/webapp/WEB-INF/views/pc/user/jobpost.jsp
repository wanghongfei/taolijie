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

<jsp:include page="../block/top-bar-reverse.jsp"/>


<div class="container user">
  <jsp:include page="../block/post-nav.jsp">
    <jsp:param name="navShow" value="job"/>
  </jsp:include>

  <div class="segment end-segment">
    <p class="pin-title red-bg ">基本信息
      <i class="pin-arrow  dark-red-arrow"></i>
    </p>
    <form action="" id="JobPostForm">
      <div class="form-group">
          <label for="">兼职标题<span class="asterisk-red">*</span></label>
        <input type="text" class="form-control" name="title" placeholder="20字以内">
      </div>
      <div class="form-group">
        <label for="">选择分类<span class="asterisk-red">*</span></label>
        <select name="jobPostCategoryId" >
            <option value="">选择分类</option>
            <c:forEach items="${cates}" var="cate">
                <option value="${cate.id}">${cate.name}</option>
            </c:forEach>
        </select>
      </div>
      <div class="form-group">
        <label for="">工资待遇<span class="asterisk-red">*</span></label>
        <input type="text" class="form-control short-input wage" name="wage" placeholder="请输入有效数字">
        <span class="input-unit">元</span>
      </div>
      <div class="form-group">
        <label for="">结算方式<span class="asterisk-red">*</span></label>
        <select name="timeToPay">
          <option value="-1">选择分类</option>
          <option value="日结">日结</option>
          <option value="周结">周结</option>
          <option value="月结">月结</option>
        </select>
      </div>
      <div class="form-group">
        <label for="">截止时间<span class="asterisk-red">*</span></label>
        <input type="text" class="form-control datepicker" name="expiredTime" placeholder="保留该信息的截止日期">
      </div>

    <p class="pin-title red-bg ">兼职详情
      <i class="pin-arrow  dark-red-arrow"></i>
    </p>

      <div class="form-group">
        <label for="">工作时间<span class="asterisk-red">*</span></label>
        <input type="text" class="form-control" name="workTime" placeholder="请填写具体的工作时间">
      </div>
      <div class="form-group">
        <label for="">工作地点<span class="asterisk-red">*</span></label>
        <input type="text" class="form-control" name="workPlace" placeholder="填写一定要详细哟">
      </div>
      <div class="form-group">
        <label for="">工作内容<span class="asterisk-red">*</span></label>
        <textarea name="jobDetail" id="" cols="30" class="form-control" placeholder="填写工作具体内容"></textarea>
      </div>
      <div class="form-group">
        <label for="">工作要求<span class="asterisk-red">*</span></label>
        <textarea name="jobDescription" class="form-control"></textarea>
      </div>

    <p class="pin-title red-bg ">兼职详情
      <i class="pin-arrow  dark-red-arrow"></i>
    </p>
      <div class="form-group">
        <label for="">联系人<span class="asterisk-red">*</span></label>
        <input type="text" class="form-control" name="contact" >
      </div>
      <div class="form-group">
        <label for="">手机号<span class="asterisk-red">*</span></label>
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

    <div class="jqmWindow dialog" >
      <div class="tlj_modal_header">桃李街提示</div>
      <div class="tlj_modal_content"></div>
    </div>
  </div>
</div>


<jsp:include page="../block/user-footer.jsp"></jsp:include>
<script src="/scripts/jobpost.js"></script>
</body>
</html>
