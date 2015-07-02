/**
 *
 * Created by wynfrith on 15-6-6.
 */


//收藏/取消收藏一条兼职或简历
$("#fav").on("click",function(){
    var jobId = this.getAttribute("data-id");
    var type = this.getAttribute("data-type");
    var $this = $(this);
    //收藏
    $.ajax({
        type:"POST",
        url:"/user/sh/fav/"+jobId,
        success:function(data){
            if(data.result){
                if(data.parm.status === "0"){
                    $this.html('<i class="fa fa-heart">&nbsp;&nbsp;</i>已收藏');
                }else if(data.parm.status === "1"){
                    $this.html('<i class="fa fa-heart-o">&nbsp;&nbsp;</i>收藏');
                }
            }else{
                alert(data.message);
            }
        }
    });
});


//顶踩投诉等

$("#like").click(function(){
    var id = this.dataset.id;
    var p = $(this).next();
    $.ajax({
        type:"post",
        url:"/user/sh/"+id+"/like",
        success:function(data){
            console.log(data);
            if(data.result){
                //更新计数
                p.text(parseInt(p.text())+1) ;
            } else {
                if(data.message == 'already liked')
                    alert('您已经喜欢过了!');
                if (data.message == 'not logged in now!') {
                    alert('登陆后才能执行该操作!');
                }
            }
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
                    alert('您已经喜欢过了!');
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
                alert("举报成功");
            }
        }
    });
});
