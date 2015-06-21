<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-5-18
  Time: 上午10:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%request.setCharacterEncoding("UTF-8");%>
<jsp:include page="block/start.jsp">
    <jsp:param name="title" value="兼职-${sh.title}"/>
</jsp:include>
<link rel="stylesheet" href="/styles/responsiveslides.css"/>

<%--顶栏--%>
<jsp:include page="block/top-bar.jsp"/>
<%--页首--%>
<jsp:include page="block/header.jsp"/>

<div class="container">
    <style>
        .sh-fav{
           margin-right: 20px;
            margin-top: 5px;
            color: #4ccda4;
            cursor: pointer;
        }
        .sh-fav :hover{
         color: #4cae4c !important;
        }
    </style>


    <%--轮播--%>
    <jsp:include page="block/silder.jsp"/>
    <%--侧边栏--%>
    <jsp:include page="block/side.jsp"/>

    <!-- 正文 -->
    <div class="detail main">
        <div class="segment sh-main">
            <!-- 二手物品图片轮播 -->
            <div class="pics fl" style="position: relative">
                <ul class="rslides">
                    <c:forEach items="${pids}" var="pid">
                        <li>
                            <img src="/static/images/users/${pid}" alt="">
                            <%--<p class="caption"></p>--%>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <!-- 二手物品详细信息 （右侧）-->
            <div class="fr sh-info">
                <p class="sh-price dark-green-bg">￥${sh.sellPrice.intValue()}</p>

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
                        <p>${sh.tradePlace}</p>
                    </div>
                </div>
            </div>
            <div style="clean:both"></div>
            <span class="sh-title">${sh.title}</span>
            <%--<span class="fr">浏览量 ：${sh.likes}</span>--%>
            <span class="fr">发布时间：
            <fmt:formatDate value="${sh.postTime}" pattern="yyyy-MM-dd"/>
            </span>
            <!-- 分享（暂时不实现） -->
            <div class="share"></div>
        </div>

        <div class="segment sh-description">
            <p class="pin-title dark-green-bg">详情描述
                <i class="pin-arrow dark-green-arrow"></i>
            </p>
            <p class="fr sh-fav" id="fav" data-id="${sh.id}" data-type="sh"><i class="fa ${favStatus? 'fa-heart':'fa-heart-o'}"> ${favStatus? '已':''}收藏</i></p>

            <div>
				<span>${sh.description}
                </span>
            </div>
        </div>

  <!--      <div class="comment">
            <p class="pin-title">用户评论
                <i class="pin-arrow"></i>
            </p>
            <div class="operates">
                <div class="operate">
                    <span id="like" data-id="${sh.id}" class="fa fa-thumbs-up" style="cursor: pointer"></span>
                    <p >${sh.likes==null?'0':sh.likes}</p>
                </div>
                <%--<div class="operate">--%>
                <%--<span  id="dislike" data-id="${job.id}" class="fa fa-thumbs-down"></span>--%>
                <%--<p >${job.dislikes}</p>--%>
                <%--</div>--%>
                <div class="operate">
                    <span id="toComment" class="fa fa-comment" style="cursor: pointer" ></span>
                    <p >${reviews.size()}</p>
                </div>
                <div class="operate">
                    <span id="complaint" data-id="${sh.id}" class="text" style="cursor: pointer" >举报</span>
                </div>
            </div>
            <div class="content" id="contents">
                <c:forEach var="review" items="${reviews}" varStatus="status">
                    <div class="${status.index == status.count-1 ? 'no-border-bottom':null}" >
                        <img src="/images/pig.jpg" alt="">
                        <p>${review.member.username}
                                <%--判断是该用户发的显示删除按钮--%>
                            <c:if test="${sessionScope.user.id == review.member.id}">
                                <a class="red delete-review" href="javascript:void(0);"  data-id="${sh.id}" data-reviewId="${review.id}"> 删除</a>
                            </c:if>

                        </p>

                        <span>${review.content}</span>
                    </div>
                </c:forEach>
            </div>
            <div class="review-bar">
                <img src="/images/pig.jpg" alt="">
                <input type="text" class="review-input" placeholder="发表评论" id="comment-input">
                <span class="review-span" id="review-btn" data-id="${sh.id}" data-username="${poster.username}">评论</span>
            </div>
        </div>
 -->
  </div>

</div>

<%--脚部--%>
<jsp:include page="block/footer.jsp"/>
<script src="/scripts/shdetail.js"></script>
<script src="/scripts/responsiveslides.min.js"></script>

<script>
    $(".rslides").responsiveSlides({
        auto: true,
        pager: false,
        nav: true,
        speed: 500,
        namespace: "callbacks",
        before: function () {
            $('.events').append("<li>before event fired.</li>");
        },
        after: function () {
            $('.events').append("<li>after event fired.</li>");
        }
    });
</script>

</body>
</html>