/**
 *
 * Created by wyn on 15-7-24.
 */

var $ctrlScope;
var $loading = $(".lists .loading-page");
$loading.show();
tlj.controller('jobDetailCtrl', function($scope, $http) {
    $ctrlScope = $scope;

    var jobListRes = $http.get('/api/job/filter');
    jobListRes.success(function(data, status){
        $scope.jobList = data.data.list;
        $loading.hide();
    });

    $scope.hello = function(){
        console.log($scope.jobList);
    };
    $scope.pageChange = function(isNext){
        //解析pageNumber字段
        if(isNext){
            //下一页
        }
    };
    $scope.lastPage = function(){
        return $scope.jobList != null;
    };
    $scope.nextPage = function(){
        return $scope.jobList != null;
    }


});

$(function () {

    var searchObj = {};

    $('.nav-bar').on('mouseenter', ".choose", function () {
        $(this).children().last().show();
    });

    $('.nav-bar').on('mouseleave', ".choose", function () {
        $(this).children().last().hide();
    });

    $('.choose-menu').on('click','span',function(){
        var $this = $(this);
        var key = $this.parent().attr("data-type");
        var value = $this.text();
        var isAll = false;
        if(key!=null&&value!=null){
            //判断value为空的话从对象中移除改属性
            if(value === "全部"){
                delete searchObj[key];
                isAll = true;
            }else{
                searchObj[key] = value;
            }
        }else{
            console.log("error");
        }

        //点击后的用户反馈
        $this.parent().children().each(function(){
            $(this).removeClass("active");
        });
        $this.addClass("active");
        var $chooseTitle = $this.parent().parent().children().first();
        if(isAll){
            $chooseTitle.text($chooseTitle.attr("data-default"));
        }else{
            $chooseTitle.text(value);
        }
        $loading.show();

        search(searchObj,function(data){
            if(data.ok){
                $ctrlScope.jobList = data.data.list;
                $ctrlScope.$digest();
            }
            $loading.hide();
        });


    });

    /**
     * 根据指定的键筛选
     * @param searchObj 如{"region":"张店","timeToPay":"月结"}
     * @param callback
     */

    function search(searchObj, callback){
        //首先遍历searchObj拼接url
        var param = urlEncode(searchObj);
        console.log(param);
        //发送ajax请求
        $.ajax({
            url:"/api/job/filter?"+param,
            type:"get",
            success:callback
        });
    }

    /**
     * 转换对象为url参数
     * @param obj
     * @param key
     * @param encode
     * @returns {string}
     */
    function urlEncode(obj, key, encode) {
        if(obj==null) return '';
        var paramStr = '';
        var t = typeof (obj);
        if (t == 'string' || t == 'number' || t == 'boolean') {
            paramStr += '&' + key + '=' + ((encode==null||encode) ? encodeURIComponent(obj) : obj);
        } else {
            for (var i in obj) {
                var k = key == null ? i : key + (obj instanceof Array ? '[' + i + ']' : '.' + i);
                paramStr += urlEncode(obj[i], k, encode);
            }
        }
        return paramStr;
    };


});



