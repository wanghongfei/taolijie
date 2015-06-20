/**
 * Created by wynfrith on 15-5-18.
 */

$(".submit-btn").click(function(){
    var data = $("#ShPostForm").serialize();
    $(".dialog").jqm({
        overlayClass: 'jqmOverlay'
    })

    $.ajax({
        type:"POST",
        data:data,
        url:"/user/sh/post",
        success:function(data){
            $(".tlj_modal_content").html(data.message);
            $('.dialog').jqmShow();
            if(data.result){
                setTimeout(function(){
                    location.href = "/user/sh/mypost";
                },500);
            }
        }
    });
});
