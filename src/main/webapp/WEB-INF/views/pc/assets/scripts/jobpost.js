/**
 *
 * Created by wynfrith on 15-5-17.
 */

//提交表单
tlj.controller('jobPostCtrl', function($scope, $http){
    //省市区不完整
    $scope.division = {
        "北京市":{
            "北京市":["海淀区","昌平区"]
        },
        "山东省":{
            "淄博市":["张店区","淄川区","博山区"],
            "济南市":["历下区","历城区"]
        }
    };
    $scope.job= {
        province: "山东省",
        city: "淄博市",
        region: "张店区",
        salaryUnit:"时"
    };
});

//tlj.controller('jobPostCtrl', function($scope) {
//    $scope.sh = sh;
//    $scope.currentUser = currentUser;
//
//});
$(".submit-btn").click(function(){
    //var url = '/user/job/post';
    //var path = location.pathname;
    //if(path.indexOf('change') > -1) {
    //    url = path;
    //}
    //$.tlj.postForm('#JobPostForm', url, function(){

    $.tlj.postForm('#JobPostForm', location.pathname, function(){
        location.href = '/user/job/mypost';
    });
});
//JobPostForm.submit(functon(e){
//    e.preventDefault();
//
//    $(".dialog").jqm({
//        overlayClass: 'jqmOverlay'
//    })
//
//    var formdata = $("#JobPostForm").serialize();
//    //console.log(formdata);
//    var url = '/user/job/post';
//    var path = location.pathname;
//    if(path.indexOf('change') > -1) {
//        url = path;
//    }
//    $.ajax({
//        type:"POST",
//        url: url,
//        data:formdata,
//        success:function(data){
//            $(".tlj_modal_content").html(data.message);
//            console.log($('.dialog'));
//            $('.dialog').jqmShow();
//            if(data.result){
//                setTimeout(function(){
//                    location.href = "/user/job/mypost";
//                },1000);
//            }
//        }
//    });
//});
//
