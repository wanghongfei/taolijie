<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-5-18
  Time: 上午10:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%request.setCharacterEncoding("UTF-8") ;%>
<jsp:include page="block/start.jsp">
  <jsp:param name="title" value="兼职-${sh.title}"/>
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
    <div class="detail main">
      <div class="segment sh-main">
        <!-- 二手物品图片轮播 -->
        <div class="pics fl">
        </div>
        <!-- 二手物品详细信息 （右侧）-->
        <div class="fr sh-info">
          <p class="sh-price dark-green-bg">￥${sh.sellPrice}</p>
          <div class="sh-block">
            <div class="title">
              <span class="dot"></span>
              <span>发布人</span>
              <div class="bubble-arrow"></div>
              <div class="bubble-arrow-inner"></div>
            </div>
            <div class="name">
              <img src="/images/pig.jpg" alt="">
              <p>${poster.username}</p>
              <span>${posterRole.memo}</span>

            </div>
          </div>
          <div class="sh-block">
            <div class="title">
              <span class="dot"></span>
              <span>联系方式</span>
              <div class="bubble-arrow"></div>
              <div class="bubble-arrow-inner"></div>
            </div>
            <div class="sh-contact">
              <p><i class="fa fa-phone red"></i> ${poster.phone}</p>
              <p><i class="fa fa-qq blue"></i> ${poster.qq}</p>
            </div>
          </div>
          <div class="sh-block">
            <div class="title">
              <span class="dot"></span>
              <span>交易地点</span>
              <div class="bubble-arrow"></div>
              <div class="bubble-arrow-inner"></div>
            </div>
            <div class="place">
              <p>暂无字段</p>
            </div>
          </div>
        </div>
        <div style="clean:both"></div>
        <span class="sh-title">${sh.title}</span>
        <span class="fr">浏览量 ：${sh.likes}</span>
        <span class="fr">发布时间：${sh.postTime}</span>
        <!-- 分享（暂时不实现） -->
        <div class="share"></div>
      </div>

      <div class="segment sh-description">
        <p class="pin-title dark-green-bg">详情描述
          <i class="pin-arrow dark-green-arrow"></i>
        </p>

        <div>
          <img src="/images/pig.jpg" alt="">
				<span>${sh.description}
				</span>
        </div>
      </div>

      <div class="comment">
        <p class="pin-title dark-green-bg">用户评论
          <i class="pin-arrow dark-green-arrow"></i>
        </p>
        <div class="operates">
          <div class="operate">
            <span class="fa fa-thumbs-up" id="like"></span>
            <p>100</p>
          </div>
          <div class="operate">
            <span class="fa fa-thumbs-down" id="dislike"></span>
            <p>1</p>
          </div>
          <div class="operate">
            <span class="fa fa-comment" id="comment"></span>
            <p>3</p>
          </div>
          <div class="operate">
            <span class="text" id="compaint">举报</span>
          </div>
        </div>
        <div class="content">
          <div class="no-border-bottom">
            <img src="/images/pig.jpg" alt="">
            <p>wynfrith</p>
            <span>这里环境很好,还有跟孩子们一起特别高兴,在这里收获的不仅仅是快乐!</span>
          </div>
          <div class="no-border-bottom">
            <img src="/images/miao.jpg" alt="">
            <p>喵帕斯 </p>
            <span>这里环境很好,还有跟孩子们一起特别高兴,在这里收获的不仅仅是快乐!</span>
          </div>
          <div>
            <img src="/images/pig.jpg" alt="">
            <p>wynfrith <span>回复 喵帕斯</span></p>
            <span>这里环境很好,还有跟孩子们一起特别高兴,在这里收获的不仅仅是快乐!</span>
          </div>
        </div>
        <div class="review-bar">
          <img src="/images/pig.jpg" alt="">
          <input type="text" class="review-input" placeholder="发表评论">
          <span class="review-span">评论</span>
        </div>
      </div>

    </div>

</div>

<%--脚部--%>
<jsp:include page="block/footer.jsp"/>

</body>
</html>