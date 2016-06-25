musicRecommendationSystem.controller('PlayListController', ['$scope', '$http', 'PlayListService',
    function ($scope, $http, PlayListService) {

        angular.element(document).ready(function () {
            PlayListService.getUserPlayList($http)
                .success(function (playlist) {
                    $scope.playlist = playlist;
                    console.log(playlist)
                })
                .error(function () {
                    console.log("error")
                })
        })
    }]);