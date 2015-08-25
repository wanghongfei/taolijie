/**
 *
 * Created by wynfrith on 15-5-17.
 */

$(".submit-btn").click(function(){
    var $form = $("#FeedBackForm");
    var formdata = $form.serialize();
    if(!$form[0].checkValidity()) {
        $form[0].reportValidity();
    }else{
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
    }

});
