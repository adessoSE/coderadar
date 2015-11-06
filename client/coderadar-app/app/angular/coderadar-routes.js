angular.module('coderadarApp')
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider

            .when('/', {
                templateUrl: 'angular/ui/dashboard/dashboardView.html',
                controller: 'DashboardController'
            })

        ;

    }]);