/**
 *
 * Created by wyn on 15-7-24.
 */

var $ctrlScope;
var searchObj = {};
var maxSize = 8;
var currPageNumber = 0;

var $loading = $(".lists .loading-page");
$loading.show();
tlj.controller('jobDetailCtrl', function($scope, $http) {
    $ctrlScope = $scope;

    var param = urlToObj(window.location.search);
    if(param.cate){
        searchObj['jobPostCategoryId'] = param.cate;
    }else{
        delete searchObj['jobPostCategoryId'];
    }

    search(searchObj,function(data){
        if(data.ok){
            $ctrlScope.jobList = data.data.list;
            $scope.resultCount = data.data.resultCount;
            $ctrlScope.$digest();
        }
        $loading.hide();
    });

    $scope.pageChange = function(isNext){

        if(isNext){//下一页
            currPageNumber +=1;
            searchObj['pageNumber'] = currPageNumber;
            $(document.body).animate({'scrollTop':160},200);
           // $loading.show();
            search(searchObj,function(data){
                if(data.ok){
                    $ctrlScope.jobList = data.data.list;
                    $ctrlScope.$digest();
                }
            //    $loading.hide();
            });
        }else{ //上一页
            if(currPageNumber > 0){
                currPageNumber -= 1;
                searchObj['pageNumber'] = currPageNumber;
                $(document.body).animate({'scrollTop':160},200);
             //   $loading.show();
                search(searchObj,function(data){
                    if(data.ok){
                        $ctrlScope.jobList = data.data.list;
                        $ctrlScope.$digest();
                    }
              //      $loading.hide();
                });
            }
        }
    };
    $scope.lastPage = function(){
        return $scope.jobList != null && currPageNumber != 0;
    };
    $scope.nextPage = function(){
        console.log(currPageNumber);
        return $scope.jobList != null&&(currPageNumber+1)*maxSize <$scope.resultCount;
    }


});


/**
 * 根据指定的键筛选
 * @param searchObj 如{"region":"张店","timeToPay":"月结"}
 * @param callback
 */

function search(searchObj, callback){
    //首先遍历searchObj拼接url
    console.log(searchObj);
    var param = urlEncode(searchObj);
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

/**
 * 转换url参数为js对象
 * @param search
 * @returns {{}}
 */
function urlToObj(search){
    var obj = {};
    var key,value,a;
    if(search == null) return obj;
    var arrays = search.substring(1).split("&");
    arrays.forEach(function(str){
        a =str.split("=");
        key = a[0], value = a[1];
        obj[key] = value;
    });
    return obj;
}



$(function () {



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
            //回到第一页
            delete searchObj['pageNumber'];
            currPageNumber = 0;
        }else{
            console.error("error");
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
                $ctrlScope.resultCount = data.data.resultCount;
                $ctrlScope.$digest();
            }
            $loading.hide();
        });


    });




});



