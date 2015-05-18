/**
 * Created by wynfrith on 15-5-18.
 */

//顶踩投诉等

$("#like").click(function(){
    var id = this.dataset.id;
    var p = $(this).next();
    $.ajax({
        type:"post",
        url:"/api/item/job/"+id+"/like",
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
        url:"/api/item/job/"+id+"/dislike",
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

$("#review-btn").click(function(){
    var $contents = $("#contents");
    var $parent = $('<div class="no-border-bottom"></div>');

    var id = this.dataset.id;
    var username = this.dataset.username;
    var content = $("#comment-input").val();
    var data = {
        content:content
    }
    console.log("review-btn");
    $.ajax({
        type:"POST",
        url:"/user/job/"+id+"/review/post",
        data:data,
        success:function(data){
            if(data.result){
                $parent.append('<img src="/images/pig.jpg" alt="">');
                $parent.append('<p>'+username+'</p>');
                $parent.append('<span>'+content+'</span>');
                $contents.append($parent);
            }else{
                alert(data.messages);
            }
        }
    });
});

$("#toComment").click(function(){
    var input = document.getElementById("comment-input");
    input.focus();
});

