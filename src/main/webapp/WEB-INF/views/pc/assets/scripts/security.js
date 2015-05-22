/**
 *
 * Created by wynfrith on 15-5-17.
 */
$(".submit-btn").click(function(){
    var data = $("#ChangPasswordForm").serialize();
    $.ajax({
        type:"POST",
        url:"/user/setting/security",
        data: data,
        success: function(data){
            console.log(data);
            if(data.result)
                alert("修改密码成功");
            else
                alert(data.message);
        }
    });
});
