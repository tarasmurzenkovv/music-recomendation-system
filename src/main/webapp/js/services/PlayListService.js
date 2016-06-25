musicRecommendationSystem.service('PlayListService', [function () {
    this.getUserPlayList = function ($http) {
        return $http({
            method: 'get',
            url: 'http://localhost:8080/playlist'
        })
    };
}]);