tlj.controller('jobCtrl', function($scope) {
    $scope.job = job;
    $scope.currentUser = currentUser;
});

/**
 * Created by wynfrith on 15-5-18.
 */

//顶踩投诉等

$("#like").click(function(){
    var id = this.dataset.id;
    var p = $(this).next();
    $.ajax({
        type:"post",
        url:"/user/job/"+id+"/like",
        success:function(data, text, res){
          if(res.status === 302){
            $.tlj.notify('您还未登录!');
          }else{
            if(data.result){
                //更新计数
                p.text(parseInt(p.text())+1) ;
            }else{
                $.tlj.notify(errorCode[data.message]);
            }
          }
        },
        error: function(err) {
          $.tlj.notify('请先登录后再点赞!');
        }
    });
});

$("#dislike").click(function(){
    var id = this.dataset.id;
    var p = $(this).next();
    $.ajax({
        type:"post",
        url:"/user/job/"+id+"/dislike",
        success:function(data){
            console.log(data);
            if(data.result){
                //更新计数
                p.text(parseInt(p.text())+1) ;
            }else{
                if(data.message == '已操作')
                    $.tlj.notify('您已经喜欢过了!');
            }
        }
    });
});

$("#complaint").click(function(){
    var id = this.getAttribute("data-id");
    $.ajax({
        type:"post",
        url:"/user/job/complaint/"+id,
        success:function(data){
            console.log(data);
            if(data.result){
                $.tlj.notify("举报成功");
            }
        }
    });
});

//收藏/取消收藏一条兼职或简历
$("#fav").on("click",function(){
    var jobId = this.getAttribute("data-id");
    var type = this.getAttribute("data-type");
    var $this = $(this);
    //收藏
    $.ajax({
        type:"POST",
        url:"/user/"+type+"/fav/"+jobId,
        success:function(data){
            if(data.result){
                if(data.parm.status === "0"){
                    $this.html('<i class="fa fa-heart">&nbsp;&nbsp;</i>已收藏');
                }else if(data.parm.status === "1"){
                    $this.html('<i class="fa fa-heart-o">&nbsp;&nbsp;</i>收藏');
                }
            }else{
                $.tlj.notify(data.message);
            }
        },
        error: function(err) {
          $.tlj.notify('您还未登录!');
        }
    });
});

$("#del").on("click",function(){
    var id = this.getAttribute("data-id");
    var type = this.getAttribute("data-type");
    var $this = $(this);
    $.ajax({
        type:"POST",
        url:"/user/"+type+"/del",
        success:function(data){
            if(data.result){
                $.tlj.notify("简历已删除");
                window.location = '/';
            }
        },
        error: function(err) {
          $.tlj.notify('您还未登录!');
        }
    });
});
