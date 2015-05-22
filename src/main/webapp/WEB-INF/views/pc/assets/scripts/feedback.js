/**
 *
 * Created by wynfrith on 15-5-17.
 */

$(".submit-btn").click(function(){
    var formdata = $("#FeedBackForm").serialize();
    console.log(formdata);
    $.ajax({
        type:"POST",
        url:"/user/feedback",
        data:formdata,
        success:function(data){
            console.log(data);
            if(data.result)
                alert("感谢您的反馈!");
        }
    });
});
