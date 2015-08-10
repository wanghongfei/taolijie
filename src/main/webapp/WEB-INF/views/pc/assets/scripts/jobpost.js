/**
 *
 * Created by wynfrith on 15-5-17.
 */

//提交表单
tlj.controller('jobPostCtrl', function($scope, $http){
    //省市区不完整
    $scope.division = {
        "山东省":{
            "淄博市":["张店区","淄川区","博山区","临淄区","周村区","桓台县","沂源县","高青县"]
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
    var msg = '';
    //var url = '/user/job/post';
    //var path = location.pathname;
    //if(path.indexOf('change') > -1) {
    //    url = path;
    //}
    //$.tlj.postForm('#JobPostForm', url, function(){

    var title = document.querySelector("input[name=title]"); //小于20字
    var wage  = document.querySelector("input[name=wage]");  //>0
    var workTime = document.querySelector("input[name=workTime]"); //小于25字
    var workPlace = document.querySelector("input[name=workPlace]"); //小于25字
    var jobDetail = document.querySelector("textarea[name=jobDetail]"); //10 500
    var jobDescription = document.querySelector("textarea[name=jobDescription]"); //10 500
    var contact = document.querySelector("input[name=contact]"); //<10
    var qq = document.querySelector("input[name=contactQq]"); //<15
    var expiredTime =document.querySelector("input[name=expiredTime]"); //不能小于今天的时间



    if(title.value.length > 20) {
        msg = '长度在20字以内';
    }
    title.setCustomValidity(msg);

    if(parseInt(wage.value) <= 0 && (parseInt(wage.value)) > 9999) {
        msg = '您输入的数字不符合要求，请重新输入';
    }
    wage.setCustomValidity(msg);

    if((new Date(expiredTime.value)).getTime()<new Date().getTime()){
        msg = '不能选择过去的时间';
    }
    expiredTime.setCustomValidity(msg);

    if(workTime.value.length > 20) {
        msg = '长度在20字以内';
    }
    workTime.setCustomValidity(msg);

    if(workPlace.value.length > 20) {
        msg = '长度在20字以内';
    }
    workPlace.setCustomValidity(msg);

    if(jobDetail.value.length <10 || jobDetail.value.length > 500) {
        msg = '长度应在10-500之间';
    }
    jobDetail.setCustomValidity(msg);

    if(jobDescription.value.length <10 || jobDescription.value.length > 500) {
        msg = '长度应在10-500之间';
    }
    jobDescription.setCustomValidity(msg);
    if(contact.value.length > 10) {
        msg = '联系人姓名过长';
    }
    contact.setCustomValidity(msg);

    if(qq.value.length >15){
        msg = 'qq号不合法';
    }
    qq.setCustomValidity(msg);


    $.tlj.postForm('#JobPostForm', location.pathname, function(data){
        if(data.result){
            location.href = '/user/job/mypost';
        }else{
            alert("表单提交失败...");
        }
        //
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
