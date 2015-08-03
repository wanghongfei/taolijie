<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-5-9
  Time: 下午3:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- 页脚 -->
<div style="clear:both"></div>
<div class="footer">
  <div class="footer-content">
    <div class="row">
      <div class="col-xs-2">
        <h3>关于</h3>
        <ul>
          <li><a href="/about/index.html#secondPage">关于我们</a></li>
          <li><a href="/about/index.html#fourthPage">加入我们</a></li>
        </ul>
      </div>
      <div class="col-xs-2">
        <h3>合作</h3>
        <ul>
          <li><a href="/about/index.html#thirdPage">联系我们</a></li>
        </ul>
      </div>
      <div class="col-xs-2">
        <h3>帮助</h3>
        <ul>
          <li><a href="/about/index.html#firstPage">使用指南</a></li>
          <li><a href="/about/index.html#">意见反馈</a></li>
        </ul>
      </div>
      <div class="col-xs-2">
        <h3>友情链接</h3>
        <ul>
          <li><a href="http://www.sdut.edu.cn">山东理工大学</a></li>
          <li><a href="http://youthol.cn">青春在线</a></li>
        </ul>
      </div>
      <div class="col-xs-4">
        <h3>网站信息</h3>
        <ul>
          <li>Copyright &copy; 2015-2016 山东理工大学桃李街版权所有</li>
        </ul>
      </div>
    </div>
  </div>

</div>

<div class="showbox">
  <div class="weixin">
    <span><img src="/images/erweima.png" alt=""></span>
  </div>
  <p>官方微信</p>
  <div class="feedback">
  </div>
  <p>我要反馈</p>
  <div class="feedbox">
    <div class="logo"><img src="/images/xiaologo.png" alt=""></div>
    <form action="">
      <div class="textarea">
        <textarea name="" id="" placeholder="亲，小桃该做哪些改进呢？情说，么么哒（200字以内）"></textarea>
      </div>
      <div class="email">
        <input type="text" class="input_email" placeholder="留下你的邮箱方便我们沟通">
        <input type="submit" value="确定" class="input_btn">
      </div>
    </form>
  </div>
</div>


<!-- build:js scripts/vendor.js -->
<!-- bower:js -->
<script src="/scripts/angular/angular.min.js"></script>
<%--<script src="//cdn.bootcss.com/angular.js/1.4.1/angular.min.js"></script>--%>
<script src="/scripts/jquery11.js"></script>
<script src="/scripts/jquery_extend.js"></script>
<!-- endbower -->
<!-- endbuild -->
<script src="/scripts/jquery.bxslider.min.js"></script>

<script src="/scripts/jqModal.js"></script>

<!-- build:js scripts/main.js -->
<script src="/scripts/main.js"></script>
<!-- endbuild -->
<script>
  $(function(){
    $(".feedback").click(function(){
      $(".feedbox").toggle();
    })
  })
</script>
