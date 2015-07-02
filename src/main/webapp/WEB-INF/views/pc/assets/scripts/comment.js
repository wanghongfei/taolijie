//评论
$('.review-bar').submit(function(e) {
    e.preventDefault();
    var $contents = $("#contents");
    var $parent = $('<div class="no-border-bottom"></div>');
    var $commetCount = $("#toComment").next();
    var $data = $('#review-btn');
    var id = $data.data('id');
    var username = $("#user-name-label").text();
    var content = $("#comment-input").val();

    var data = {
        content:content
    };

    var url = '';
    var path = location.pathname;
    if(path.indexOf('job') > -1) {
        url = "/user/job/"+id+"/review/post";
    }else {
        url = "/user/sh/"+id+"/review/post";
    }
    $.tlj.post(url, data, function(data) {
        if(data.result){
            console.log(data);
            var reviewId =data.parm.reviewId;
            $parent.append('<img src="/static/images/users/" alt="">');
            $parent.append('<p>'+username+'<a class="red delete-review" href="javascript:void(0);"  data-id="'+id+'" data-reviewId="'+reviewId+'"> 删除</a></p>');
            $parent.append('<span>'+content+'</span>');
            $contents.append($parent);
            $("#comment-input").val('');
            //更新评论条数
            $commetCount.text(parseInt($commetCount.text())+1);
        }else{
            $.tlj.notify(data.message);
        }
    });
});

//删除一条评论
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
