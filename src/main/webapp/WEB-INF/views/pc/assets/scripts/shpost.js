/**
 * Created by wynfrith on 15-5-18.
 */

$(".submit-btn").click(function(){
    var title = document.querySelector("input[name=title]"); //少于20字
    var description = document.querySelector("textarea[name=description]"); //15字以上
    var msg = '';

    if(title.value.length > 20) {
        msg = '长度在20字以内';
    }
    title.setCustomValidity(msg);

    if(description.value.length < 15) {
        msg = '长度应在15字以上';
    }
    description.setCustomValidity(msg);

    $.tlj.postForm('#ShPostForm', location.pathname, function(data){
        console.log(data);
        if(data.result){
            location.href = '/user/sh/mypost';
        }else{
            alert("表单提交失败...");
        }
    });
});

//$(".submit-btn").click(function(){
//    var data = $("#ShPostForm").serialize();
//    $(".dialog").jqm({
//        overlayClass: 'jqmOverlay'
//    })
//
//    $.ajax({
//        type:"POST",
//        data:data,
//        url: location.pathname,
//        success:function(data){
//            $(".tlj_modal_content").html(data.message);
//            $('.dialog').jqmShow();
//            if(data.result){
//                setTimeout(function(){
//                    location.href = "/user/sh/mypost";
//                },500);
//            }
//        }
//    });
//});

/*
 * del img uploaded
 *
 */
$('.img-list-wrapper').delegate('.btn-img-del','click', function(){
    if( $('.img-list-item').length == 4) {
        $('.img-list-btn').toggle();
    }
    var $img = $(this).parent('li');
    $img.remove()
    var picIds = $('input[name=picIds]').val().split(';');
    for( var i = 0;i < picIds.length; i++){
        if( $img.data('pid') == picIds[i] ) { 
            picIds.splice(i,1);
            break;
        }   
    }
    $('input[name=picIds]').val(picIds.join(';'));
});

/* upload img list init*/
var initImgList = function() {
    var ids = $('input[name=picIds]').val().split(';');
    if(ids[0] != '') {
        ids.forEach(function(data) {
            $('<li class="img-list-item" data-pid="'
                + data
                + '">'
                + '<img src="/static/images/users/'
                + data
                + '" class="img-list-img"/>'
                + '<span class="btn-img-del">x</span>'
                + '</li>').insertBefore('.img-list-btn');
        })
        if(ids.length > 3) {
            $('.img-list-btn').toggle();
        }
    }
}
initImgList();
