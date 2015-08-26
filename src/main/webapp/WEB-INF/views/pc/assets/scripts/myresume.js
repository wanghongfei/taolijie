tlj.controller('myresumeCtrl', function($scope) {
    $scope.resume = resume;
    $scope.cates = cates
    $scope.intendIds = intendIds;
    $scope.setIntendIds = function(index) {
        var cate = $scope.cates[index];
        console.log($scope.intendIds.length);
        if(cate.selected == false && $scope.intendIds.length < 3) {
            $scope.intendIds.push(cate.id);
            cate.selected = !cate.selected;
        } else if(cate.selected == true){
            for(i in $scope.intendIds) {
                if($scope.intendIds[i] == cate.id) {
                    $scope.intendIds.splice(i, 1);
                }
            }
            cate.selected = !cate.selected;
        }
        $('input[name=intendIds]')[0].value = $scope.intendIds.join(';');
    }
    //$scope.currentUser = currentUser;
    //$scope.$on('onRepeatLast', function() {
    //    rslides();
    //})
});


$(".submit-btn").click(function(){
    var msg = '';

    var photo = document.querySelector( "input[name=photoPath]");
    if(!photo.value) {
        msg = '来一张最美的你吧';
    }
    photo.setCustomValidity(msg);

    var introduce = document.querySelector('textarea[name=introduce]');
    intro_len = introduce.value.length;
    if(intro_len < 15 || intro_len > 200) {
        msg = '长度在15-200字之间';
    }
    introduce.setCustomValidity(msg);
    $.tlj.postForm('#CreateResumeForm', location.pathname, function(data){
      if(data.result){
        location.href = '/user/resume/view';
      }else{
        alert(errorCode[data.message]);
      }
    });
});

//$(".submit-btn").click(function(){
//    var data = $("#CreateResumeForm").serialize();
//    var type = this.getAttribute("data-type");
//    $(".dialog").jqm({
//        overlayClass: 'jqmOverlay'
//    })
//    //首先先上传图片
//    console.log(type);
//    $.ajax({
//        type:"POST",
//        data:data,
//        url:"/user/resume/"+type,
//        success:function(data){
//            console.log(data);
//            $(".tlj_modal_content").html(data.message);
//            console.log($('.dialog'));
//            $('.dialog').jqmShow();
//            if(data.result){
//                setTimeout(function(){
//                    location.href = "/user/resume/view";
//                },1000);
//            }
//        }
//    });
//});
