'use strict';

angular.module('coderadarApp')
    .controller('DashboardController', [
        '$scope',
        'MetricService',
        'CommitService',
        '$filter',
        'PageState',
        'LabelProvider',
        function ($scope, metricService, commitService, $filter, pageState, labelProvider) {

            pageState.setHeadline("Dashboard");
            pageState.setSubline("View the metrics at the time of a commit and compare them with those of an earlier commit.");

            /**
             * Function to return the color for the trend of a metric: 'green' for good, 'red' for bad and 'yellow' for
             * undefined.
             * @param {MetricWithScore} metric The metric whose trend color to calculate.
             * @returns {string} the color as string, either 'red', 'green' or 'yellow'.
             */
            $scope.getTrendColor = function (metric) {
                var trendColor = 'yellow';
                if ((metric.delta > 0 && metric.valuationType == Coderadar.MetricsResource.ValuationType.POSITIVE)
                    || (metric.delta < 0 && metric.valuationType == Coderadar.MetricsResource.ValuationType.NEGATIVE)) {
                    trendColor = 'green';
                } else if ((metric.delta < 0 && metric.valuationType == Coderadar.MetricsResource.ValuationType.POSITIVE)
                    || (metric.delta > 0 && metric.valuationType == Coderadar.MetricsResource.ValuationType.NEGATIVE)) {
                    trendColor = 'red';
                }
                return trendColor;
            };

            /**
             * Updates the view depending on which commits are selected by the user for comparison.
             * To be called when the user selected a new baselineCommit or a new deltaCommit.
             */
            $scope.onCommitSelectionChanged = function () {
                var promise = metricService.loadMetricsWithScore($scope.baselineCommit.id, $scope.deltaCommit.id);
                promise.then(function (metrics) {
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
                });
            };

            /**
             * Load the latest commits to populate the commit dropdown.
             */
            var commitsPromise = commitService.loadLatestCommits(10);
            commitsPromise.then(function (commits) {
                $scope.commits = commits;
                $scope.baselineCommit = commits[0];
                $scope.deltaCommit = commits[1];
                $scope.onCommitSelectionChanged();
            });
        }]);
