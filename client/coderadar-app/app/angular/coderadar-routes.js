angular.module('coderadarApp')
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider

            .when('/', {
                templateUrl: 'angular/ui/dashboard/dashboardView.html',
                controller: 'DashboardController'
            })

            .when('/browse', {
                templateUrl: 'angular/ui/browse/browseView.html',
                controller: 'BrowseController'
            })

        ;

    }]);