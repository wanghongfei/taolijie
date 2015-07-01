/**
 *
 * Created by wynfrith on 15-5-17.
 */

//提交表单


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
