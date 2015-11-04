'use strict';

angular.module('coderadarApp')
    .controller('dashboardController', [
        '$scope',
        'MetricService',
        'CommitService',
        function ($scope, metricService, commitService) {
            commitService.loadLatestCommits(2, function (commits) {
                $scope.baselineCommit = commits[0];
                $scope.deltaCommit = commits[1];

                metricService.loadMetricsWithScore($scope.baselineCommit.id, $scope.deltaCommit.id, function (metrics) {

                    // adding a trend color to each metric
                    metrics.forEach(function (metric) {
                        var trendColor = 'yellow';
                        if((metric.delta > 0 && metric.valuationType == Coderadar.MetricsResource.ValuationType.POSITIVE)
                            || (metric.delta < 0 && metric.valuationType == Coderadar.MetricsResource.ValuationType.NEGATIVE)){
                            trendColor = 'green';
                        }else if((metric.delta < 0 && metric.valuationType == Coderadar.MetricsResource.ValuationType.POSITIVE)
                            || (metric.delta > 0 && metric.valuationType == Coderadar.MetricsResource.ValuationType.NEGATIVE)){
                            trendColor = 'red';
                        }
                        metric.trendColor = trendColor;
                    });

                    $scope.metrics = chunkArray(metrics, 4);

                    function chunkArray(array, chunkSize) {
                        var resultArray = [];
                        var i, j, temparray;
                        for (i = 0, j = array.length; i < j; i += chunkSize) {
                            temparray = array.slice(i, i + chunkSize);
                            resultArray.push(temparray);
                        }
                        return resultArray;
                    }
                })
            });
        }]);
