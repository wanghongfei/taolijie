/**
 * Created by wynfrith on 15-5-18.
 */

$(".submit-btn").click(function(){

    if ( !HTMLFormElement.prototype.reportValidity ) {
        HTMLFormElement.prototype.reportValidity = function() {
            var subBtn = this.querySelector("span[id=subBtn]");
                    subBtn.click();
                    return;
        }
    };

    var title = document.querySelector("input[name=title]"); //少于20字
    var description = document.querySelector("textarea[name=description]"); //15字以上
    var contactName = document.querySelector("input[name=contactName]");
    var msg = '';

    if(title.value.length > 20) {
        msg = '长度在20字以内';
    }
    title.setCustomValidity(msg);

    if(description.value.length < 15) {
        msg = '长度应在15字以上';
    }
    description.setCustomValidity(msg);
    if(contactName.value.length > 10){
        msg = '联系人姓名不得超过10字';
    }
    contactName.setCustomValidity(msg);

    $.tlj.postForm('#ShPostForm', location.pathname, function(data){
        console.log(data);
        if(data.result){
            location.href = '/user/sh/mypost';
        }else{
          alert(errorCode[data.message]);
        }
    });
});

//$(".submit-btn").click(function(){
//    var data = $("#ShPostForm").serialize();
//    $(".dialog").jqm({
//        overlayClass: 'jqmOverlay'
//    })
//
//    $.ajax({
//        type:"POST",
//        data:data,
//        url: location.pathname,
//        success:function(data){
//            $(".tlj_modal_content").html(data.message);
//            $('.dialog').jqmShow();
//            if(data.result){
//                setTimeout(function(){
//                    location.href = "/user/sh/mypost";
//                },500);
//            }
//        }
//    });
//});


