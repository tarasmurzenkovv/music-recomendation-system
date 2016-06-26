musicRecommendationSystem.config(['$routeProvider',
    function ($routeProvider) {
        //$httpProvider.interceptors.push('responseObserver');
        $routeProvider
            .when('/main', {
                templateUrl: '/html/main.html',
                controller: 'MainController'
            })
            .when('/login', {
                templateUrl: '/html/login.html',
                controller: 'AuthorisationController'
            })
            .when('/register', {
                templateUrl: '/html/register.html',
                controller: 'AuthorisationController'
            })
            .when('/playlist', {
                templateUrl: '/html/dashboard.html',
                controller: 'PlayListController'
            })
            .when('/upload_track',{
                templateUrl:'/html/upload_track.html',
                controller:'TrackController'
            })
    }
]);

