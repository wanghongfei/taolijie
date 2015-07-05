$.tlj = {
    post: function(url, data, callback) {
              $loading = $('.loading-page');
              $loading.show();
              $.ajax({
                  type:"POST",
                  url: url,
                  data: data,
                  success:function(data){
                      callback(data);
                      $loading.hide();
                  }
              });
          },

    postForm: function(elem, url, callback) {
                  var $form = $(elem);
                  var form = $form[0];
                  var formdata = $form.serialize();
                  if(!form.checkValidity()) {
                      form.reportValidity();
                  }else{
                      $.tlj.post(url, formdata, callback);
                  }
              },

    notify: function(msg, type) {
        var $notify = $('.notify');
        if($notify.length < 1) {
            $('body').append('<div class="notify"></div>');
            $notify = $('.notify');
        }
        $notify.text(msg).fadeIn(200, function(){
            $notify.fadeOut(3000);
        });
    }
}
