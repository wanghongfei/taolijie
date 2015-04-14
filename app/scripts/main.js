$(function(){
   	$('.bxslider').bxSlider({
        // speed:500,
        auto:true
    });
  	
});

$('.nav-bar>.choose').mouseover(function(){
	$('.choose-menu').addClass('show');
});
$('.nav-bar>li').mouseout(function(){
	$('.choose-menu').removeClass('show');
});


