tlj.controller('shCtrl', function($scope) {
    $scope.sh = sh;
    $scope.currentUser = currentUser;
    $scope.$on('onRepeatLast', function() {
        rslides();
    })
});

tlj.directive('onLastRepeat', function() {
    return function(scope) {
        if(scope.$last) setTimeout(function() {
            scope.$emit('onRepeatLast');
        }, 1);
    }
});

/**
 *
 * Created by wynfrith on 15-6-6.
 */


(function(){
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
                $.tlj.notify(data.message);
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
        url:"/user/sh/" + id + "/like",
        success:function(data){
            if(data.result){
                //更新计数
                p.text(parseInt(p.text())+1) ;
            } else {
                $.tlj.notify(data.message);
                //if(data.message == 'already liked')
                //    $.tlj.notify('您已经喜欢过了!');
                //if (data.message == 'not logged in now!') {
                //    $.tlj.notify('登陆后才能执行该操作!');
                //}
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

})();

var rslides = function(){
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
}
