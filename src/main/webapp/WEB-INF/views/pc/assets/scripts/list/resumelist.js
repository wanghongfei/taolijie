var $ctrlScope;
var searchObj = {};
var maxSize = 8;
var currPageNumber = 0;
var url = "/api/resume/filter"

var $loading = $(".lists .loading-page");
$loading.show();
tlj.controller('resumeListCtrl', function($scope, $http) {
    $scope.cates = [];


    $scope.getCates = function() {
        $http.get('/api/job/cate/list')
        .success(function(data) {
            if (data.ok) {
                $scope.cates = data.data.list;
            }
        })
    }
    //推荐 .. 暂未实现
    $scope.getRecommend = function() {
        $http.get('/api/recommend/list?type=resume')
        .success(function(data) {
            console.log(data);
            if(data.ok) {
                $scope.list = data.list;
            }
        })
    }
    //过滤.. 未使用
    $scope.listFilter = function(type, spec) {
        if(spec) {
            spec = '/' + spec;
        }else {
            spec = '';
        }
        $http.get('/api/resume/' + type + spec)
        .success(function(data) {
            if(data.ok) {
                $scope.list = data.data.list || [];
            }
        })
    }

    //筛选
    $ctrlScope = $scope;

    var param = urlToObj(window.location.search);
    if(param.cate){
        searchObj['intendIds'] = param.cate;
    }else{
        delete searchObj['intendIds'];
    }

    //初次加载
    search(url, searchObj,function(data){
      console.log('初次加载');
        if(data.ok){
            $scope.list= data.data.list;
            $scope.resultCount = data.data.resultCount;
            $scope.$digest();
        }
        $loading.hide();
    })

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
                    $scope.resumes= data.data.list;
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
                        $scope.resumes = data.data.list;
                        $scope.$digest();
                    }
              //      $loading.hide();
                });
            }
        }
    };

    $scope.lastPage = function(){
        return $scope.resumes != null && currPageNumber != 0;
    };
    $scope.nextPage = function(){
        return $scope.resumes != null&&(currPageNumber+1)*maxSize <$scope.resultCount;
    }





    $scope.getCates()
});

(function() {
    $('.nav-bar').on('mouseenter', ".choose", function () {
        $(this).children('.choose-menu').show();
    });

    $('.nav-bar').on('mouseleave', ".choose", function () {
        $(this).children('.choose-menu').hide();
    });
})()
