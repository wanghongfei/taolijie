/**
 * Created by wyn on 15-7-24.
 */


/**
 * 列表变色
 */
$(function(){
    $(".lists").on("mouseenter",".list",function(){
        var $cate= $(this).children().first();
        var themeColor = $(this).attr("data-color");
        $cate.css("background-color","#ffffff");
        $cate.css("border-color",themeColor);
        $cate.children().first().css("color", themeColor);
    });
    $(".lists").on("mouseleave",".list",function(){
        var themeColor = $(this).attr("data-color");
        var $cate= $(this).children().first();
        $cate.css("background-color",themeColor);
        $cate.children().first().css("color","#ffffff");
    });
});


$('.nav-bar').on('mouseenter',".choose", function(){
    console.log($(this).children());
    $(this).children().last().show();

});

$('.nav-bar').on('mouseleave',".choose", function(){
    $(this).children().last().hide();
});
