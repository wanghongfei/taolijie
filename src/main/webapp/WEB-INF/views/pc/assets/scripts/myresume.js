/**
 *
 * Created by wynfrith on 15-5-18.
 */

$(".submit-btn").click(function(){
    var data = $("#CreateResumeForm").serialize();
    var type = this.getAttribute("data-type");
    $(".dialog").jqm({
        overlayClass: 'jqmOverlay'
    })
    //首先先上传图片
    console.log(type);
    $.ajax({
        type:"POST",
        data:data,
        url:"/user/resume/"+type,
        success:function(data){
            console.log(data);
            $(".tlj_modal_content").html(data.message);
            console.log($('.dialog'));
            $('.dialog').jqmShow();
            if(data.result){
                setTimeout(function(){
                    location.href = "/user/resume/view";
                },1000);
            }
        }
    });
});

