/**
 *
 * Created by wyn on 15-8-8.
 */

var searchObj = {};
var url = "/api/sh/filter";
var maxSize = 9;
var currPageNumber = 0;
var $ctrlScope;

var $loading = $(".shlist .shs .loading-page");
$loading.show();
tlj.controller('ShListCtrl', function($scope, $http) {
    $ctrlScope = $scope;
    var param = urlToObj(window.location.search);
    if(param.cate){
        searchObj['secondHandPostCategoryId'] = param.cate;
    }else{
        delete searchObj['secondHandPostCategoryId'];
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

    $scope.getHot = function(){
        //api/recommend/list?type=sh
        var list = [];
        $http.get('/api/recommend/list?type=sh')
            .success(function(data){
                var datalist = data.data.list;
                for(var i=0; i<datalist.length;i++){
                    list.push(datalist[i].shPost);
                }
                $scope.list = list;
                $scope.resultCount =data.data.resultCount;
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
    $scope.hasLastPage = function(){
        return $scope.list != null && currPageNumber != 0;
    };
    $scope.hasNextPage = function(){
        return $scope.list != null&&(currPageNumber+1)*maxSize <$scope.resultCount;
    }


});

