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
              }
}
