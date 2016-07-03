musicRecommendationSystem.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider
            .when('/main', {
                templateUrl: '/html/landing.html',
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
            .when('/discover', {
                templateUrl: '/html/discover.html',
                controller: 'RecommendationController'
            })
            .when('/stream', {
                templateUrl: '/html/stream.html',
                controller: 'StreamController'
            })
            .when('/friend_activity', {
                templateUrl: '/html/friend_activity.html',
                controller: 'FriendActivityController'
            })
            .when('/collection', {
                templateUrl: '/html/collection.html',
                controller: 'CollectionController'
            })
            .when('/about', {
                templateUrl: '/html/about.html',
                controller: 'AboutController'
            })
    }
]);

