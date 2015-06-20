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
                alert("修改资料成功");
                window.location.href = "/user/profile";
            }
            else
                alert(data.message);
        }
    });
});
