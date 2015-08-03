tlj.controller('resumeListCtrl', function($scope, $http) {
    $scope.resumes = resumes;
    $scope.pages = pages;
    $scope.cates = [];
    $scope.getCates = function() {
        $http.get('/api/job/cate/list')
        .success(function(data) {
            if (data.ok) {
                $scope.cates = data.data.list;
            }
        })
    }
    $scope.getRecommend = function() {
        $http.get('/api/recommend/list?type=resume')
        .success(function(data) {
            console.log(data);
            if(data.ok) {
                $scope.resumes = data.list;
            }
        })
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
