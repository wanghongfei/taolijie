/**
 *
 * Created by wynfrith on 15-5-17.
 */
$(".submit-btn").click(function(){
    var data = $("#ChangPasswordForm").serialize();
    $(".dialog").jqm({
        overlayClass: 'jqmOverlay'
    })

    $.ajax({
        type:"POST",
        url:"/user/setting/security",
        data: data,
        success: function(data){
            console.log(data);
            if(data.result){
                $(".tlj_modal_content").html("密码修改成功");
                $('.dialog').jqmShow();
                setTimeout(function(){
                    location.reload();
                },500)
            }else{
                $(".tlj_modal_content").html(data.message);
                $('.dialog').jqmShow();
            }

        }
    });
});
