var tlj = angular.module('tljApp',[]);

$('.nav-bar > .choose').on('click',function(){
	$('.choose-menu').toggle();
});

//$('.nav-bar>.choose').mouseover(function(){
//	$('.choose-menu').addClass('show');
//});
//$('.nav-bar>li').mouseout(function(){
//	$('.choose-menu').removeClass('show');
//});

$('#login-form').submit(function(e) {
    e.preventDefault();
    $.tlj.postForm('#login-form', '/login', function(data) {
        if(data.result == false) {
            $.tlj.notify(data.message);
        }else {
			location.href = '/';
        }
    })
});

$('#reg-form').submit(function(e) {
    e.preventDefault();
    $.tlj.postForm('#reg-form', '/register', function(data) {
        if(data.result == false) {
            $.tlj.notify(data.message);
        }else {
			window.location.href = "/login";
        }
    })
});

$("#search-job").on("click",function() {
    $("#search-type").val("job");
    $("#search-form").submit();
});

$("#search-sh").on("click",function() {
    $("#search-type").val("sh");
    $("#search-form").submit();
});

$(function(){
    $('.bxslider').bxSlider({
        // speed:500,
        auto:true
    });

});
