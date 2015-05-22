/**
 *
 * Created by wynfrith on 15-5-17.
 */

//提交表单

$(".submit-btn").click(function(){
    var formdata = $("#JobPostForm").serialize();
    //console.log(formdata);
    $.ajax({
        type:"POST",
        url:"/user/job/post",
        data:formdata,
        success:function(data){
            console.log(data);
        }
    });
});

