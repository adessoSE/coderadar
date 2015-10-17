angular.module('coderadarApp')
    .config(function($routeProvider){
        $routeProvider

            .when('/', {
                templateUrl: 'angular/components/metricList/metricListView.html',
                controller: 'metricListController'
            })

            .when('/drilldown', {
                templateUrl: 'angular/components/metricDrilldown/metricDrilldownView.html',
                controller: 'metricDrilldownController'
            })

        ;

    });