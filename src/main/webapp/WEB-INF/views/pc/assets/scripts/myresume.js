/**
 *
 * Created by wynfrith on 15-5-18.
 */

$(".submit-btn").click(function(){
    var data = $("#CreateResumeForm").serialize();
    console.log(data);
    //首先先上传图片
    $.ajax({
        type:"POST",
        data:data,
        url:"/user/resume/create",
        success:function(data){
            console.log(data);
            if(data.result)
                alert("创建简历成功");
        }
    });
});

