/**
 *
 * Created by wynfrith on 15-6-3.
 */

$(".submit-btn").click(function(){
    var data = $("#ProfileForm").serialize();

    $.ajax({
        type:"POST",
        url:"/user/profile",
        data: data,
        success: function(data){
            console.log(data);
            if(data.result){
                $(".tlj_modal_content").html("资料修改成功!");
                $('.dialog').jqmShow();
                setTimeout(function(){
                    location.reload();
                },500);
            }else{
                $(".tlj_modal_content").html(data.message);
                $('.dialog').jqmShow();
            }
        }
    });
});
