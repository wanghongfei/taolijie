<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: wynfrith
  Date: 15-5-17
  Time: 下午8:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%request.setCharacterEncoding("UTF-8") ;%>


<jsp:include page="../block/start.jsp" >
    <jsp:param name="title" value="个人资料修改"/>
</jsp:include>

<style>
    .avatar{
        width: 150px;
        height: 150px;
        padding: 10px;
        /*border: 2px solid #66ccff;*/
        background-color: #eee;
        cursor: pointer;
    }
    .avatar:hover{
        background-color: #666;
    }
    .avatar img{
        border-radius: 100px;
        width: 150px;
        height: 150px;

    }
    .webuploader-element-invisible {
        position: absolute !important;
        clip: rect(1px 1px 1px 1px); /* IE6, IE7 */
        clip: rect(1px,1px,1px,1px);
    }
    .webuploader-pick {
        position: relative;
        display: inline-block;
        cursor: pointer;
        background: #4ccda4;
        padding: 10px 15px;
        color: #fff;
        text-align: center;
        border-radius: 3px;
        overflow: hidden;
    }
</style>

<jsp:include page="../block/top-bar-reverse.jsp"></jsp:include>

<div class="container user">
    <jsp:include page="../block/user.jsp">
        <jsp:param name="navShow" value="profile"/>
    </jsp:include>

    <div class="segment personal">
        <p class="pin-title  dark-green-bg">账号信息
            <i class="pin-arrow  dark-green-arrow"></i>
        </p>
        <p  class="accout-info">账号： ${sessionScope.user.username}<span>${sessionScope.role.memo}</span></p>
        <p class="pin-title  dark-green-bg">基本信息
            <i class="pin-arrow  dark-green-arrow"></i>
        </p>
        <form action="" id="ProfileForm">



            <div class="form-group">
                <label for="">昵称<i class="theme-color">*</i></label>
                <input type="text" name="name" class="form-control" placeholder="姓名" value="${user.name}">
            </div>
            <div class="form-group">
                <label for="">性别<i class="star-symbol">*</i></label>
                <input type="radio" name="gender" value="男" ${user.gender == "男"?'checked="checked"':''}>男
                <input type="radio" name="gender" value="女" ${user.gender == "女"?'checked="checked"':''}>女
            </div>

            <div id="uploader-demo" class="wu-example">
                <div id="fileList" class="uploader-list"
                     style="width: 75px;height: 75px; display: inline;">
                    <c:if  test="${sessionScope.user.profilePhotoId == 0}">
                        <img src="/images/default-img.jpg" alt="" style="border-radius: 10px" width="75" height="75" alt="">
                    </c:if>
                    <c:if  test="${sessionScope.user.profilePhotoId != 0}">
                        <img src="/static/images/users/${sessionScope.user.profilePhotoId}" style="border-radius: 10px" width="75" height="75" alt=""/>
                    </c:if>

                </div>
                <div id="filePicker" style="display: inline">修改头像</div>
            </div>



            <div class="segment">
                <div class="submit-btn big-btn dark-green-bg">
                    <span href="javascript:void(0);">保存资料</span>
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
<script>
    $(document).ready(function(){
        $(".dialog").jqm({
            overlayClass: 'jqmOverlay'
        });
    });

</script>
<script src="/scripts/profile.js"></script>
<%--<script src="/scripts/upload.js"></script>--%>
</body>
</html>
