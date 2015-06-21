

$('.nav-bar>.choose').mouseover(function(){
	$('.choose-menu').addClass('show');
});
$('.nav-bar>li').mouseout(function(){
	$('.choose-menu').removeClass('show');
});

//登陆验证
$("#login-form #sub-btn").click(function(){
	var formData = $("#login-form").serialize();
	var errorBox = $("#error-box");
	$.ajax({
		type:"POST",
		url:"/login",
		data:formData,
		success:function(data){
			if(data.result == false){
				errorBox[0].innerText = data.message ? data.message : "请输入用户名和密码";
			}else{
				window.location.href = "/";
			}
		}
	})
});

//注册验证
$("#reg-form #sub-btn").click(function(){
	var formData = $("#reg-form").serialize();
	var errorBox = $("#error-box");
    console.log(formData);
	$.ajax({
		type:"POST",
		url:"/register",
		data:formData,
		success:function(data){
			if(data.result == false){
				errorBox[0].innerText = data.message ? data.message : "请输入用户名和密码";
			}else{
				window.location.href = "/login";
			}
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


