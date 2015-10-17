'use strict';

angular.module('coderadarApp')
    .controller('metricDrilldownController', [
        '$scope',
        '$routeParams',
        'defaultDatatableOptions',
        'metricsService',
        'fileTreeService',
        function ($scope, $routeParams, options, metricsService,fileTreeService) {

            $scope.options = options;
            $scope.metric = metricsService.loadMetric($routeParams.metricId);
            $scope.files = fileTreeService.loadSubTree($routeParams.folder);

        }]);
