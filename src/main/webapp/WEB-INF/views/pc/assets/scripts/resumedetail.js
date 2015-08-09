tlj.controller('resumeCtrl', function($scope, $http) {
    $scope.resume = resume;
    $scope.postUser = postUser;
    $scope.isShow = isShow;
    $scope.intendJobs = intendJobs;

    $scope.fav = function() {
        fav();
    }

    $scope.del = function() {
        $http.post('/user/resume/del')
        .success(function(data) {
            if(data.result){
                location.reload();
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
