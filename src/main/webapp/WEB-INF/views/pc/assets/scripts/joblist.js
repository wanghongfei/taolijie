/**
 *
 * Created by wyn on 15-7-24.
 */

var $ctrlScope;
var searchObj = {};
var maxSize = 8;
var currPageNumber = 0;
var url = "/api/job/filter";
var searchContent = ";"

var $loading = $(".lists .loading-page");
$loading.show();
tlj.controller('jobListCtrl', function($scope, $http) {

    $ctrlScope = $scope;

    var location = window.location.pathname;
    if(location == '/search'){
        var param = urlToObj(window.location.search);

        if(param.type == 'job'){
            url = "api/job/search";
        }else if(param.type == 'sh'){
            url ="api/sh/search";
        }

        searchContent = param.content;
        searchObj['title'] =decodeURI(searchContent);
    }
    var param = urlToObj(window.location.search);
    if(param.cate){
        searchObj['jobPostCategoryId'] = param.cate;
    }else{
        delete searchObj['jobPostCategoryId'];
    }

    //初次加载
    search(url, searchObj,function(data){
        if(data.ok){
            $scope.list= data.data.list;
            $scope.resultCount = data.data.resultCount;
            $scope.$digest();
        }
        $loading.hide();
    });

    /**
     * 热门推荐
     */
    $scope.getHot = function(){
        //api/recommend/list?type=job
        $http.get('/api/recommend/list?type=job')
            .success(function(data){
                console.log(data);
            })
            .error(function(xhr, error, thrown){
                console.log('error');
                console.log(xhr);
            });
    };

    /**
     * 分页切换
     * @param isNext
     */
    $scope.pageChange = function(isNext){

        if(isNext){//下一页
            currPageNumber +=1;
            searchObj['pageNumber'] = currPageNumber;
            $(document.body).animate({'scrollTop':160},200);
           // $loading.show();
            search(url, searchObj,function(data){
                if(data.ok){
                    $scope.list = data.data.list;
                    $scope.$digest();
                }
            //    $loading.hide();
            });
        }else{ //上一页
            if(currPageNumber > 0){
                currPageNumber -= 1;
                searchObj['pageNumber'] = currPageNumber;
                $(document.body).animate({'scrollTop':160},200);
             //   $loading.show();
                search(url, searchObj,function(data){
                    if(data.ok){
                        $scope.list = data.data.list;
                        $scope.$digest();
                    }
              //      $loading.hide();
                });
            }
        }
    };
    $scope.lastPage = function(){
        return $scope.list != null && currPageNumber != 0;
    };
    $scope.nextPage = function(){
        return $scope.list != null&&(currPageNumber+1)*maxSize <$scope.resultCount;
    }


});
