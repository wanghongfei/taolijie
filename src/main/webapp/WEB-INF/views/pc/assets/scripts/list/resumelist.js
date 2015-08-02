tlj.controller('resumeListCtrl', function($scope, $http) {
    $scope.resumes = resumes;
    $scope.pages = pages;
    $scope.getRecommend = function() {
        $http.get('/api/recommend/list?type=resume')
        .success(function(data) {
            console.log(data);
            if(data.ok) {
                $scope.resumes = data.list;
            }
        })
    }
});
