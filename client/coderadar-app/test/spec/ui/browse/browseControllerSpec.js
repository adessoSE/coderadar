'use strict';

describe('BrowseController', function () {
    beforeEach(module('coderadarApp'));

    var browseController,
        $scope,
        $rootScope,
        commitService,
        $q,
        metricService,
        fileService;

    beforeEach(inject(function ($controller, _$rootScope_, _CommitService_, _$q_, _MetricService_, _FileService_) {
        $q = _$q_;
        commitService = _CommitService_;
        $rootScope = _$rootScope_;
        metricService = _MetricService_;
        fileService = _FileService_;

        spyOn(commitService, 'loadLatestCommits').and.callFake(function (count) {
            var deferred = $q.defer();
            deferred.resolve(CommitServiceSpec.commits);
            return deferred.promise;
        });

        spyOn(metricService, 'loadAllMetrics').and.callFake(function(){
            var deferred = $q.defer();
            deferred.resolve(MetricServiceSpec.metrics);
            return deferred.promise;
        });

        spyOn(fileService, 'loadFilesWithDeltaForFolder').and.callFake(function(){
            var deferred = $q.defer();
            deferred.resolve(Coderadar.FileServiceSpec.filesWithDelta);
            return deferred.promise;
        });

        $scope = {};
        browseController = $controller('BrowseController', { $scope: $scope });

    }));

    it('exposes a list of commits to $scope', function () {
        $rootScope.$digest();
        expect($scope.commits).toBeAnArrayOfCommits();
    });

    it('exposes baselineCommit to $scope', function () {
        $rootScope.$digest();
        expect($scope.baselineCommit).toBeACommit();
    });

    it('exposes deltaCommit to $scope', function () {
        $rootScope.$digest();
        expect($scope.deltaCommit).toBeACommit();
    });

    it('exposes metric to $scope', function () {
        $rootScope.$digest();
        expect($scope.metric).toBeAMetric();
    });

    describe('getFullFilePath()', function(){
        it('creates a correct file path without leading or trailing slashes', function(){
            expect(Coderadar.BrowseController.getFullFilePath('HEAD', 'folder', 'file')).toBe('/offline-data/sourcecode/HEAD/folder/file');
        });

        it('creates a correct file path with a folder with trailing slash', function(){
            expect(Coderadar.BrowseController.getFullFilePath('HEAD', 'folder/', 'file')).toBe('/offline-data/sourcecode/HEAD/folder/file');
        });

        it('creates a correct file path with a filename with leading slash', function(){
            expect(Coderadar.BrowseController.getFullFilePath('HEAD', 'folder', '/file')).toBe('/offline-data/sourcecode/HEAD/folder/file');
        });

        it('creates a correct file path with a filename with trailing slash', function(){
            expect(Coderadar.BrowseController.getFullFilePath('HEAD', 'folder', 'file/')).toBe('/offline-data/sourcecode/HEAD/folder/file');
        });

        it('creates a correct file path with a folder and filename with leading and trailing slashes', function(){
            expect(Coderadar.BrowseController.getFullFilePath('HEAD', 'folder/', '/file/')).toBe('/offline-data/sourcecode/HEAD/folder/file');
        });

        it('creates a correct file path with a root folder', function(){
            expect(Coderadar.BrowseController.getFullFilePath('HEAD', '/', 'file')).toBe('/offline-data/sourcecode/HEAD/file');
        });

    });

    describe('filePathToStack()', function() {

        function expectCorrectFileStack(fileStack){
            expect(fileStack.length).toBe(4);
            expect(fileStack[0].name).toBe('root');
            expect(fileStack[0].isFolder).toBe(true);
            expect(fileStack[0].fullPath).toBe('/');
            expect(fileStack[1].name).toBe('folder1');
            expect(fileStack[1].isFolder).toBe(true);
            expect(fileStack[1].fullPath).toBe('/folder1');
            expect(fileStack[2].name).toBe('folder2');
            expect(fileStack[2].isFolder).toBe(true);
            expect(fileStack[2].fullPath).toBe('/folder1/folder2');
            expect(fileStack[3].name).toBe('file.txt');
            expect(fileStack[3].isFolder).toBe(false);
            expect(fileStack[3].fullPath).toBe('/folder1/folder2/file.txt');
        }

        it('creates a correct file stack for a path several folders deep', function(){
            var fileStack = Coderadar.BrowseController.filePathToStack('/folder1/folder2/file.txt');
            expectCorrectFileStack(fileStack);
        });

        it('creates a correct file stack for root path \'/\'', function(){
            var fileStack = Coderadar.BrowseController.filePathToStack('/');
            expect(fileStack.length).toBe(1);
            expect(fileStack[0].name).toBe('root');
            expect(fileStack[0].isFolder).toBe(true);
            expect(fileStack[0].fullPath).toBe('/');
        });

        it('creates a correct file stack for a path with a trailing slash', function(){
            var fileStack = Coderadar.BrowseController.filePathToStack('/folder1/folder2/file.txt/');
            expectCorrectFileStack(fileStack);
        });

    });

});