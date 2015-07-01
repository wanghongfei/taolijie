$.tlj = {
    postForm: function(elem, url, callback) {
                  var $form = $(elem);
                  var form = $form[0];
                  var formdata = $form.serialize();
                  if(!form.checkValidity()) {
                      form.reportValidity();
                  }else{
                      $.ajax({
                          type:"POST",
                          url: url,
                          data:formdata,
                          success:function(data){
                              callback();
                          }
                      });
                  }
              },
    notify(msg, type) {
        var $notify = $('.notify');
        if($notify.length < 1) {
            $('body').append('<div class="notify"></div>');
            $notify = $('.notify');
        }
        $notify.text(msg).fadeIn(200, function(){
            $notify.fadeOut(3000);
        });;
    }
}
