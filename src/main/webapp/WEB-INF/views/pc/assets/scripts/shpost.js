/**
 * Created by wynfrith on 15-5-18.
 */

$(".submit-btn").click(function(){
    var data = $("#ShPostForm").serialize();
    console.log(data);
    $.ajax({
        type:"POST",
        data:data,
        url:"/user/sh/post",
        success:function(data){
            console.log(data);
            if(data.result)
                alert("发布成功");
        }
    });
});
