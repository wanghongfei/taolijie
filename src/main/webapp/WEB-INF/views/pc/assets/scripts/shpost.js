/**
 * Created by wynfrith on 15-5-18.
 */

$(".submit-btn").click(function(){
    var data = $("#ShPostForm").serialize();
    $(".dialog").jqm({
        overlayClass: 'jqmOverlay'
    })

    $.ajax({
        type:"POST",
        data:data,
        url:"/user/sh/post",
        success:function(data){
            $(".tlj_modal_content").html(data.message);
            $('.dialog').jqmShow();
            if(data.result){
                setTimeout(function(){
                    location.href = "/user/sh/mypost";
                },500);
            }
        }
    });
});

/*
 * del img uploaded
 *
 */
$('.img-list-wrapper').delegate('.btn-img-del','click', function(){
    if( $('.img-list-item').length == 4) {
        $('.img-list-btn').toggle();
    }
    var $img = $(this).parent('li');
    $img.remove()
    var picIds = $('input[name=picIds]').val().split(';');
    for( var i = 0;i < picIds; i++){
        if( $img.data('pid') == picIds[i] ) { 
            picIds.splice(i,1);
            break;
        }   
    }
    $('input[name=picIds]').val(picIds.join(';'));
});
