var tlj = angular.module('tljApp',[]);

var num = 40;

var errorCode = {
  '3': '密码错误,请重新输入',
  '4': '用户名不存在',
  '8': '两次密码不一致'
}
$(window).bind('scroll', function () {
    if ($(window).scrollTop() > num) {
        $('.header').addClass('fixed');
    } else {
        $('.header').removeClass('fixed');
    }
});

$('#login-form').submit(function(e) {
    e.preventDefault();
    $.tlj.postForm('#login-form', '/login', function(data) {
        if(data.result == false) {
            $.tlj.notify(errorCode[data.message]);
        }else {
			location.href = '/';
        }
    })
});

$('#reg-form').submit(function(e) {
    e.preventDefault();
    $.tlj.postForm('#reg-form', '/register', function(data) {
        if(data.result == false) {
            $.tlj.notify(errorCode[data.message]);
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
