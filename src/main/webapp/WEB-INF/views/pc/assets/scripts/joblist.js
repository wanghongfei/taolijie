/**
 * Created by wyn on 15-7-24.
 */




$('.nav-bar').on('mouseenter',".choose", function(){
    console.log($(this).children());
    $(this).children().last().show();
});

$('.nav-bar').on('mouseleave',".choose", function(){
    $(this).children().last().hide();
});
