'use strict';

Coderadar.BrowseController = {};

angular.module('coderadarApp')
    .controller('BrowseController', [
        '$scope',
        'PageState',
        'CommitService',
        'MetricService',
        'FileService',
        '$q',
        function ($scope, pageState, commitService, metricService, _FileService_, _$q_) {

            var fileService = _FileService_;
            var $q = _$q_;

            pageState.setHeadline("Browse");
            pageState.setSubline("Dig through your codebase to find out about a selected metric.");

            $scope.onParametersChanged = function () {
                var filesPromise = fileService.loadFilesWithDeltaForFolder($scope.metric.id, $scope.folder, $scope.baselineCommit.id, $scope.deltaCommit.id);
                filesPromise.then(function (files) {
                    if (files && files.length > 0) {
                        $scope.fileList = files;
                        $scope.onFileClicked(Coderadar.BrowseController.getFirstFile(files));
                    }
                });
            };

            $scope.onFileClicked = function (file) {
                if (file.isFolder) {
                    $scope.folder = file.fullPath;
                    $scope.onParametersChanged();
                } else {
                    $scope.file = file;
                    $scope.fullFilePath = Coderadar.BrowseController.getFullFilePath($scope.baselineCommit.id, $scope.folder, $scope.file.name);
                    $scope.fileStack = Coderadar.BrowseController.filePathToStack($scope.file.fullPath);
                }
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

                    $scope.onParametersChanged();
                });

        }]);

Coderadar.BrowseController.SOURCE_FOLDER = '/offline-data/sourcecode/';

/**
 * Creats a path to where a file in a given commit and a given folder can be downloaded.
 * @param commitId ID of the commit from which to load the file.
 * @param folder Full path of the folder in which the searched file lies.
 * @param filename Name of the file.
 * @returns {string} Full path relative to page root where the file can be downloaded.
 */
Coderadar.BrowseController.getFullFilePath = function (commitId, folder, filename) {
    folder = folder.replace(/^\/|\/$/g, '');
    filename = filename.replace(/^\/|\/$/g, '');
    var prefix = Coderadar.BrowseController.SOURCE_FOLDER.replace(/\/$/g, '');
    var fullPath = prefix + '/';
    fullPath += commitId + '/';
    if (folder !== '') {
        fullPath += folder + '/';
    }
    fullPath += filename;
    return fullPath;
};

/**
 * Returns the first file that is not a folder from the given file list.
 * @param fileList the list to get the first file from.
 * @returns the first file that is not a folder.
 */
Coderadar.BrowseController.getFirstFile = function (fileList) {
    for (var i = 0; i < fileList.length; i++) {
        var file = fileList[i];
        if (!file.isFolder) {
            return file;
        }
    }
};

Coderadar.BrowseController.filePathToStack = function (fullPath) {
    fullPath = fullPath.replace(/^\//g, '');
    fullPath = fullPath.replace(/\/$/g, '');
    var fileStack = [];
    fileStack.push({
        name: 'root',
        fullPath: '/',
        isFolder: true
    });
    if (fullPath !== '') {
        var pathElements = fullPath.split('/');
        var currentPath = '';
        for (var i = 0; i < pathElements.length; i++) {
            var isLast = (i === (pathElements.length - 1));
            var pathElement = pathElements[i];
            currentPath += '/' + pathElement;
            fileStack.push({
                name: pathElement,
                fullPath: currentPath,
                isFolder: !isLast
            });
        }
    }
    return fileStack;
};