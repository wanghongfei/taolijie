tlj.controller('resumeCtrl', function($scope, $http) {
    $scope.resume = resume;
    $scope.postUser = postUser;
    $scope.isShow = isShow;
    $scope.intendJobs = intendJobs;

    $scope.fav = function() {
        fav();
    };

    $scope.del = function() {
        $http.post('/user/resume/del')
        .success(function(data) {
            if(data.result){
                location.reload();
            }
        })
    };

    $scope.toRecommend = function(){
        var $recommend = $('#recommend'),
            id = $recommend.data('id'),
            type = $recommend.data('type'),
            title = $recommend.data('title');
        console.log(title);

        var data = {
            resumeId: id,
            order_index: 10,
            postTitle: title
        };

        $.post('/api/u/recommend', data)
            .success(function(data) {
                if(data.code === 0){
                    alert('申请成功!')
                }else if(data.code === 15){
                    alert('您已经申请过了');
                }else {
                    alert(errorCode[data.code]);
                }
         })
    }
});

//$("#fav").on("click",function(){
var fav = function(){
    $fav = $('#fav');
    var resumeId = $fav.data('id');
    var type = $fav.data('type');
    //收藏
    $.ajax({
        type:"POST",
        url:"/user/"+ type + "/fav/"+resumeId,
        success:function(data){
            if(data.result){
                if(data.parm.status === "0"){
                    $fav.html('<i class="fa fa-heart">&nbsp;&nbsp;</i>已收藏');
                }else if(data.parm.status === "1"){
                    $fav.html('<i class="fa fa-heart-o">&nbsp;&nbsp;</i>收藏');
                }
            }else{
                $.tlj.notify(data.message);
            }
        }
    });
};
//});
