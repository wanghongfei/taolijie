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
        success:function(data){
            console.log(data);
            if(data.result){
                //更新计数
                p.text(parseInt(p.text())+1) ;
            }else{
                alert(data.message);
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
                alert(data.message);
            }
        }
    });
});

$("#review-btn").on("click",function(){
    var $contents = $("#contents");
    var $parent = $('<div class="no-border-bottom"></div>');
    var $commetCount = $("#toComment").next();
    console.log($commetCount);
    var id = this.dataset.id;
    var username = $("#user-name-label").text();
    var content = $("#comment-input").val();
    var data = {
        content:content
    };
    $.ajax({
        type:"POST",
        url:"/user/job/"+id+"/review/post",
        data:data,
        success:function(data){
            if(data.result){
                var reviewId =data.parm.reviewId;
                $parent.append('<img src="/images/pig.jpg" alt="">');
                $parent.append('<p>'+username+'<a class="red delete-review" href="javascript:void(0);"  data-id="'+id+'" data-reviewId="'+reviewId+'"> 删除</a></p>');
                $parent.append('<span>'+content+'</span>');
                $contents.append($parent);
                $("#comment-input").val('');
                //更新评论条数
                $commetCount.text(parseInt($commetCount.text())+1);
            }else{
                alert(data.message);
            }
        }
    });
});

$(document).on("click",'.delete-review',function(){
    var id = this.dataset.id;
    var $commetCount = $("#toComment").next();
    var reviewId = this.getAttribute("data-reviewId");
    var $parent = $(this).parent().parent();
    $.ajax({
        type:"POST",
        url:"/user/job/"+id+"/review/delete/"+reviewId,
        success:function(data){
            if(data.result){
                $parent.remove();
                $commetCount.text(parseInt($commetCount.text())-1);
            }else{
                alert(data.message);
            }
        }
    });

});

$("#toComment").click(function(){
    var input = document.getElementById("comment-input");
    input.focus();
});

