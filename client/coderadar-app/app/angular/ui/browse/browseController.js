'use strict';

angular.module('coderadarApp')
    .controller('BrowseController', [
        '$scope',
        'PageState',
        'CommitService',
        'MetricService',
        '$q',
        function ($scope, pageState, commitService, metricService, $q) {

            pageState.setHeadline("Browse");
            pageState.setSubline("Dig through your codebase to find out about a selected metric.");

            $scope.onParametersChanged = function(){
              console.log("parameters changed!");
            };

            /**
             * Load the latest commits to populate the commit dropdown.
             */
            var commitsPromise = commitService.loadLatestCommits(10);

            var metricsPromise = metricService.loadAllMetrics();

            $q.all([
                commitsPromise,
                metricsPromise])
                .then(function (data) {
                    $scope.metrics = data[1];
                    $scope.selectedMetric = $scope.metrics[0];
                    $scope.commits = data[0];
                    $scope.baselineCommit = $scope.commits[0];
                    $scope.deltaCommit = $scope.commits[1];
                });

        }]);
