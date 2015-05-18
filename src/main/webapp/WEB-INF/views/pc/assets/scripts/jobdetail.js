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
    var id = this.dataset.id;
    var data = {
        content:$("#comment-input").val()
    }
    console.log("review-btn");
    $.ajax({
        type:"POST",
        url:"/user/job/"+id+"/review/post",
        data:data,
        success:function(data){
            console.log(data);
        }
    });
});

$("#toComment").click(function(){
    var input = document.getElementById("comment-input");
    input.focus();
});

