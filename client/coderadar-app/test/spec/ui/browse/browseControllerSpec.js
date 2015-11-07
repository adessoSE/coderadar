'use strict';

describe('DashboardController', function () {
    beforeEach(module('coderadarApp'));

    var browseController, $scope, $rootScope, commitService, $q, metricService;

    beforeEach(inject(function ($controller, _$rootScope_, _CommitService_, _$q_, _MetricService_) {
        $q = _$q_;
        commitService = _CommitService_;
        $rootScope = _$rootScope_;
        metricService = _MetricService_;

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

    it('exposes selectedMetric to $scope', function () {
        $rootScope.$digest();
        expect($scope.selectedMetric).toBeAMetric();
    });

});