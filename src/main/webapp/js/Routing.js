musicRecommendationSystem.config(['$routeProvider', '$httpProvider', '$locationProvider',
    function ($routeProvider) {
        //$httpProvider.interceptors.push('responseObserver');
        $routeProvider
            .when('#/landing', {
                templateUrl: '/html/landing.html',
                controller: 'MainPageController'
            })
    }
]);

