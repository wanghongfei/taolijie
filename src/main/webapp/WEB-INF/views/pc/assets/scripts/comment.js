var id = $('#review-btn').data('id');
var $contents = $("#contents");
var shownReviewNums = job.reviews.length || 0;
var reviewPage = 0;

$(function(){
  if(haveReviews()){
    $('.load-more').show();
  }
});
//评论
$('.review-bar').submit(function(e) {
    e.preventDefault();
    var $commetCount = $("#toComment").next();
    var username = $("#user-name-label").text();
    var content = $("#comment-input").val();
    var $parent = $('<div class="no-border-bottom"></div>');


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
    $.tlj.post(url, data, function(data, err) {
      if(err){
          $.tlj.notify('你还未登录');
      }else{
        if(data.result){
            var reviewId =data.parm.reviewId;
            //var reviewId = data.message;
            var photo_url = $('.comment-avatar').attr('src');
            $parent.append('<img src="' + photo_url + '" alt="">');
            $parent.append('<p>'+username+'<a class="red delete-review" href="javascript:void(0);"  data-id="'+id+'" data-reviewId="'+reviewId+'"> 删除</a></p>');
            $parent.append('<span>'+content+'</span>');
            $contents.prepend($parent);
            $("#comment-input").val('');
            //更新评论条数
            $commetCount.text(parseInt($commetCount.text())+1);
        }else{
          if(data.result){
              var reviewId =data.parm.reviewId;
              //var reviewId = data.message;
              var photo_url = $('.comment-avatar').attr('src');
              $parent.append('<img src="' + photo_url + '" alt="">');
              $parent.append('<p>'+username+'<a class="red delete-review" href="javascript:void(0);"  data-id="'+id+'" data-reviewId="'+reviewId+'"> 删除</a></p>');
              $parent.append('<span>'+content+'</span>');
              $contents.append($parent);
              $("#comment-input").val('');
              //更新评论条数
              $commetCount.text(parseInt($commetCount.text())+1);
          }else{
              $.tlj.notify(errorCode[data.message]);
          };
        }
      }

    });
});

//删除一条评论
$(document).on("click",'.delete-review',function(){
  if(confirm('确定要删除吗?')){
    var id = this.dataset.id;
    var $commetCount = $("#toComment").next();
    var reviewId = this.getAttribute("data-reviewId");
    var $parent = $(this).parent().parent();
    $.ajax({
        type:"POST",
        url:"/user/job/"+id+"/review/delete/"+reviewId,
        success:function(data, text, res){
            if(data.result){
                $parent.remove();
                $commetCount.text(parseInt($commetCount.text())-1);
            }else{
                alert(data.message);
            }
        },
        error: function(data){
          console.log(data);
        }
    });
  }
});

$("#toComment").click(function(){
    var input = document.getElementById("comment-input");
    input.focus();
});

//动态加载评论
$("#loadMore").click(function(){
  $.get('/api/review/job/'+id+'?pageNumber='+(++reviewPage))
    .success(function(data){
      var i = 0,
          list = data.data.list;
      for(; i<list.length; i++){
        var review = list[i],
            user = list[i].member,
            $parent = $('<div class="no-border-bottom"></div>'),
            photo_url = '/static/images/users/'+user.profilePhotoId;
        $parent.append('<img src="' + photo_url + '" alt="">');
        if(currentUser.id === user.id){
          $parent.append('<p>'+user.username+'<a class="red delete-review" href="javascript:void(0);"  data-id="'+id+'" data-reviewId="'+review.id+'"> 删除</a></p>');
        }
        $parent.append('<span>'+review.content+'</span>');
        $contents.append($parent);
      }
      shownReviewNums += list.length;
      if(!haveReviews()){
        $('.load-more').hide();
      }
    });
});

//检测是否有评论存在
function haveReviews(){
  if(job.reviewCount < 8 || shownReviewNums >= job.reviewCount){
    console.log('false');
    return false;
  }else{
    return true;
  }
}
