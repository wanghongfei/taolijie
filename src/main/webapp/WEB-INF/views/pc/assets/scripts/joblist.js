/**
 * Created by wyn on 15-7-24.
 */


$(function () {
    $('.nav-bar').on('mouseenter', ".choose", function () {
        console.log($(this).children());
        $(this).children().last().show();
    });

    $('.nav-bar').on('mouseleave', ".choose", function () {
        $(this).children().last().hide();
    });

    $('.choose-menu').on('click','span',function(){
        var $this = $(this);
        var region = $this.val();
        $.ajax({
            url:"/api/job/search?workRegion=张店",
            type:"get",
            success:function(data){
                console.log(data);
            }
        });
    });

});



