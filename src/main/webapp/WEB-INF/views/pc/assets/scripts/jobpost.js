/**
 *
 * Created by wynfrith on 15-5-17.
 */

//提交表单


$(".submit-btn").click(function(){

    $(".dialog").jqm({
        overlayClass: 'jqmOverlay'
    })

    var formdata = $("#JobPostForm").serialize();
    //console.log(formdata);
    $.ajax({
        type:"POST",
        url:"/user/job/post",
        data:formdata,
        success:function(data){
            $(".tlj_modal_content").html(data.message);
            console.log($('.dialog'));
            $('.dialog').jqmShow();
            if(data.result){
                location.href = "/user/job/mypost";
            }
        }
    });
});

