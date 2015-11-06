<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--头部--%>
<jsp:include page="block/start.jsp"></jsp:include>
<%@ page session="false" %>

<body class="sticky-header">

<section>

  <jsp:include page="block/side.jsp"></jsp:include>

  <!-- main content start-->
  <div class="main-content" >


    <jsp:include page="block/header.jsp"></jsp:include>

    <!-- page heading start-->
    <div class="page-heading">
      <h3>
        发送消息
      </h3>
      <ul class="breadcrumb">
        <li><a href="/manage"><i class="fa fa-home"></i> 控制台</a></li>
        <li><a href=""></i> 通知管理</a></li>
        <li><a href="">发送消息</a></li>
      </ul>

    </div>
    <!-- page heading end-->

    <!--body wrapper start-->
    <div class="wrapper">

      <section class="panel panel-info">
        <header class="panel-heading">
          <h3 class="panel-title">发送消息</h3>
        </header>
        <div class="panel-body">
          <form action="#" class="form-horizontal" id="sendForm">

            <div class="form-group">
              <label class="control-label col-md-3" for="username">发送给谁</label>
              <div class="col-md-2 col-xs-11">
                <select name="" id="userType" class="form-control">
                  <%--<option value="1" data-val="ALL">全部用户</option>--%>
                  <option value="2" data-val="STUDENT">学生</option>
                  <option value="3" data-val="EMPLOYER">商家</option>
                  <%--<option value="4" data-val="ORGANIZATION">校内组织</option>--%>
                  <option value="0" data-val="USER">指定用户id</option>
                </select>
                <%--<input class="form-control" id="username" name="username" placeholder="输入用户名" type="text" value="" >--%>
              </div>
              <div class="col-md-2" style="display: none" id="nameBox">
                <input type="text" class="form-control" id="username" name="toMemberId" value="" placeholder="输入用户名"/>
              </div>
            </div>
            <div class="form-group">
              <label class="control-label col-md-3" for="password">通知标题</label>
              <div class="col-md-4 col-xs-11" >
                <input class="form-control" id="title" disabled value="通知暂无标题" name="title" placeholder="输入通知标题" type="text">
              </div>
            </div>


            <div class="form-group">
              <label class="control-label col-md-3" for="role">通知内容</label>
              <div class="col-md-6 col-xs-11" >
                <textarea name="content" id="content" class="form-control" placeholder="输入通知内容"></textarea>
              </div>

            </div>

            <button type="submit" style="margin-top:30px;margin-bottom:30px" class="col-md-offset-3 btn btn-primary">发送消息</button>
          </form>
        </div>
      </section>


    </div>
    <!--body wrapper end-->

    <jsp:include page="block/footer.jsp"></jsp:include>

  </div>
  <!-- main content end-->
</section>

<jsp:include page="block/end.jsp"></jsp:include>
<script type="text/javascript" src="/admin/js/jquery.validate.min.js"></script>
<script>
  $(function(){

    var $form = $('#sendForm');

    $('#sendForm').validate({
      rules: {
        title: {
          required: true
        },
        content: {
          required: true
        }
      },
      messages: {
        title: {
          required: '请填写标题'
        },
        content: {
          required: '请输入通知消息'
        }
      },
      submitHandler: function () {
        var $type = $('#userType'),
            $user = $('#username'),
            $title = $('#title'),
            $content = $('#content');

        //发送个人消息
        if($type.val() === '0'){
          if($user.val() === ""){
            alert('请填写用户名');
          }else{
            data = {
              toMemberId: $user.val(),
              memberId: ${currUser.id},
              title: '暂无',
              content: $content.val()
            };
            $.post('/api/manage/noti/pri', data)
                .success(function(data){
                      if(data.code === 0){
                        alert("通知发送成功!");
                        window.location.href = "/manage/note/send";
                      }else{
                        alert(data.message);
                      }
                })

          }
        }else{
          var range = "";
          switch ($type.val()){
            case '1': range = 'ALL'; break;
            case '2': range = 'STUDENT'; break;
            case '3': range = 'EMPLOYER'; break;
            case '4': range = 'ORGANIZATION'; break;
          }

          console.log(range);
          data = {
            accessRange: range,
            title: $title.val(),
            content: $content.val()
          };

          $.post('/api/manage/noti/sys',data)
                  .success(function(data){
                    if(data.code === 0){
                      alert("通知发送成功!");
                      window.location.href = "/manage/note/send";
                    }else{
                      alert(data.message);
                    }
                  });
        }
      }
    });

    $('#userType').on('change', function(){
      if($(this).val() == 0){
        $('#nameBox').show();
      }else{
        $('#nameBox').hide();
      }
    });

  });
</script>

</body>
</html>

