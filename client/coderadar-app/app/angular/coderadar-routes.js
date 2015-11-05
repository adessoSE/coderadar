angular.module('coderadarApp')
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider

            .when('/', {
                templateUrl: 'angular/ui/dashboard/dashboard.html',
                controller: 'DashboardController'
            })

        ;

    }]);