'use strict';

angular.module('coderadarApp')
    .controller('DashboardController', [
        '$scope',
        'MetricService',
        'CommitService',
        '$filter',
        'PageState',
        function ($scope, metricService, commitService, $filter, pageState) {

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
             * Creates a human readable label for a commit.
             * @param {Commit} commit The commit for which to create a label.
             * @returns {string} The label for the specified commit.
             */
            $scope.getLabelForCommit = function (commit) {
                var date = new Date(commit.timestamp);
                return $filter('date')(date, 'MMMM dd, yyyy \'at\' HH:mm:ss') + " - " + commit.id;
            };

            /**
             * Updates the displayed metrics depending on which commits are selected by the user for comparison.
             * To be called when the user selected a new baselineCommit or a new deltaCommit.
             */
            $scope.onCommitSelectionChanged = function () {
                metricService.loadMetricsWithScore($scope.baselineCommit.id, $scope.deltaCommit.id, function (metrics) {
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
            commitService.loadLatestCommits(10, function (commits) {
                $scope.commits = commits;
                $scope.baselineCommit = commits[0];
                $scope.deltaCommit = commits[1];
                $scope.onCommitSelectionChanged();
            });
        }]);
