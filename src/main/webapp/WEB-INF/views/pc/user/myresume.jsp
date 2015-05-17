<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-5-17
  Time: 下午8:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%request.setCharacterEncoding("UTF-8") ;%>
<jsp:include page="../block/start.jsp" >
    <jsp:param name="title" value="我的简历"/>
</jsp:include>

<jsp:include page="../block/top-bar-reverse.jsp"></jsp:include>

<div class="container user">
    <jsp:include page="../block/user.jsp"></jsp:include>
  <div class="segment infos resume link-segment">
    <p class="pin-title">个人信息
      <i class="pin-arrow"></i>
    </p>
    <form>
      <div class="form-group">
        <label for="">真实姓名<i class="theme-color">*</i> </label>
        <input type="text" class="form-control" placeholder="姓名">
      </div>
      <div class="form-group">
        <label for="">性&nbsp;&nbsp;&nbsp;&nbsp;别<i class="theme-color">*</i></label>
        <input type="radio" name="render" value="">男
        <input type="radio" name="render" value="">女
        <!-- <input type="radio" value="">女 -->
      </div>
      <div></div>
      <div class="form-group">
        <label for="">身&nbsp;&nbsp;&nbsp;&nbsp;高<i class="theme-color">*</i></label>
        <input class="short-input form-control" type="text" placeholder="填写有效数字">
        <span for=""> cm</span>
      </div>
      <div class="form-group">
        <label for="">出生年月<i class="theme-color">*</i></label>
        <input class="short-input form-control" type="text" placeholder="">
        <span> 例如 <span class="dark-green">(1993-1-1)</span></span>
      </div>
      <div class="form-group">
        <label for="">城&nbsp;&nbsp;&nbsp;&nbsp;市<i class="theme-color">*</i></label>
        <input type="text" class="form-control" placeholder="填写您所在城市">
      </div>
      <div class="form-group">
        <label for="">学&nbsp;&nbsp;&nbsp;&nbsp;校<i class="theme-color">*</i></label>
        <input type="text" class="form-control" placeholder="填写您的学校">
      </div>
    </form>
    <p class="pin-title">求职意向
      <i class="pin-arrow"></i>
    </p>
    <form action="">
      <div class="form-group">
        <label for="">简历标题<i class="theme-color">*</i></label>
        <input type="text" class="form-control" placeholder="例：兼职派单员">
      </div>
      <div class="form-group">
        <label for="">求职意向<i class="theme-color">*</i></label>
        <input type="text" class="form-control" placeholder="填写您的学校">
      </div>
      <div class="form-group">
        <label for="">空闲时间<i class="theme-color">*</i></label>
        <input type="text" class="form-control" placeholder="自己具体填写">
      </div>
      <div class="form-group">
        <label for="">公开程度</label>
        <input type="radio"> 公开
        <input type="radio"> 对商家公开
        <input type="radio"> 不公开
      </div>
    </form>

    <div class="user-photo">
      <i></i>
    </div>
    <form action="">
      <div class="form-group text">
        <label for="">自我介绍</label>
        <textarea name="" id="" class="form-control" placeholder="200字以内"></textarea>
      </div>
      <div class="form-group text">
        <label for="">工作经历<i class="theme-color">*</i></label>
        <textarea name="" id="" class="form-control" placeholder="工作经历（200字以内）"></textarea>
      </div>
    </form>
    <form action="">
      <p class="pin-title no-offset">联系方式</p>
      <div class="form-group ">
        <label for=""><i class="fa fa-phone red"></i>&nbsp;&nbsp;手机<i class="theme-color">*</i></label>
        <input type="text" class="form-control" placeholder="">
      </div>
      <div class="form-group ">
        <label for=""><i class="fa fa-qq blue"></i>&nbsp;&nbsp;QQ</label>
        <input type="text" class="form-control" placeholder="">
      </div>
      <div class="form-group">
        <label for=""><i class="fa fa-weixin light-green"></i>&nbsp;&nbsp;微信</label>
        <input type="text" class="form-control" placeholder="">
      </div>
      <div class="form-group">
        <label for=""><i class="fa fa-envelope-o theme-color"></i>&nbsp;&nbsp;邮箱</label>
        <input type="text" class="form-control" placeholder="">
      </div>
    </form>



  </div>
  <div class="segment">
    <div class="submit-btn big-btn theme-color-bg">
      <span href="">发布简历</span>
    </div>
  </div>
</div>

<jsp:include page="../block/user-footer.jsp"></jsp:include>
</body>
</html>
