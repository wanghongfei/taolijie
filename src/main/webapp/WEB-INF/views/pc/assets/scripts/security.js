/**
 *
 * Created by wynfrith on 15-5-17.
 */
$(".submit-btn").click(function(){
    var $form = $("#ChangPasswordForm");
    var data = $form.serialize();

    $(".dialog").jqm({
        overlayClass: 'jqmOverlay'
    })

    if(!$form[0].checkValidity()) {
        $form[0].reportValidity();
    }

    else{
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
                console.log(data.message);
                  $(".tlj_modal_content").html(errorCode[data.message]);
                  $('.dialog').jqmShow();
              }

          }
      });
    }


});
