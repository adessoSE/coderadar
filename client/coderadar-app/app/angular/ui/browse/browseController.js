'use strict';

angular.module('coderadarApp')
    .controller('BrowseController', [
        '$scope',
        'PageState',
        'CommitService',
        'MetricService',
        '$q',
        function ($scope, pageState, commitService, metricService, $q) {

            var SOURCE_FOLDER = '/offline-data/sourcecode/';

            function getFullFilePath(folder, file) {
                var fullPath = folder;
                if (!fullPath.endsWith('/')) {
                    fullPath = fullPath + '/';
                }
                fullPath = fullPath + file;
                return SOURCE_FOLDER + fullPath;
            }

            pageState.setHeadline("Browse");
            pageState.setSubline("Dig through your codebase to find out about a selected metric.");

            $scope.onParametersChanged = function () {
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
                    $scope.metric = $scope.metrics[0];
                    $scope.commits = data[0];
                    $scope.baselineCommit = $scope.commits[0];
                    $scope.deltaCommit = $scope.commits[1];

                    $scope.folder = '/';
                    $scope.file = 'test.js';
                    $scope.fullFilePath = getFullFilePath($scope.folder, $scope.file);
                    $scope.filePathArray = ['root', 'test.js']; // TODO load from backend
                    $scope.fileList = [
                        {
                            isFolder: true,
                            name: '../',
                            score: 123,
                            delta: 5
                        },
                        {
                            isFolder: true,
                            name: 'folder2',
                            score: 123,
                            delta: 0
                        },
                        {
                            isFolder: true,
                            name: 'folder3',
                            score: 123,
                            delta: -5
                        },
                        {
                            isFolder: false,
                            name: 'file1.js',
                            score: 123,
                            delta: 5
                        }
                    ];
                });

        }]);
