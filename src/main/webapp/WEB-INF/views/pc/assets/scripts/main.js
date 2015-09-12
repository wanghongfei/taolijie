var tlj = angular.module('tljApp',[]);

var num = 40;

var errorCode = {
  '-1':'操作失败',
  '0':'操作成功',
  '1':'操作过于频繁',
  '2':'用户名不合法',
  '3':'密码错误,请重新输入',
  '4':'用户名不存在',
  '5':'用户已存在',
  '6':'用户被封号',
  '7':'未登陆',
  '8':'两次密码不一致',
  '9':'分类不能为空',
  '10':'必填字段为空',
  '11':'非法参数',
  '12':'不存在',
  '13':'您还未登录',
  '14':'不能删除当前用户',
  '15':'已经存在',
  '16':'您已经喜欢过了',
  '17':'非法数字'
};

function escapeSpecialChars(jsonString) {

    return jsonString.replace(/\n/g, "\\n")
        .replace(/\r/g, "\\r")
        .replace(/\t/g, "\\t")
        .replace(/\f/g, "\\f");

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
    var $form = $('#login-form');
    var form = $form[0];
    var formdata = $form.serialize();
    $.tlj.post('/login', formdata, function(data) {
        if(data.result == false) {
            $.tlj.notify(errorCode[data.message]);
        }else {
            //location.go(-1);
            window.location.href ="/";
        }
    })
});

$('#reg-form').submit(function(e) {
    e.preventDefault();
    var $form = $('#reg-form');
    var form = $form[0];
    var formdata = $form.serialize();
    $.tlj.post('/register', formdata, function(data) {
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
