'use strict';

describe('DashboardController', function () {
    beforeEach(module('coderadarApp'));

    var dashboardController, $scope, $rootScope, commitService, $q, metricService;

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

        spyOn(metricService, 'loadMetricsWithScore').and.callFake(function(baselineCommitId, deltaCommitId){
            var deferred = $q.defer();
            deferred.resolve(MetricServiceSpec.metricsWithScore);
            return deferred.promise;
        });

        $scope = {};
        dashboardController = $controller('DashboardController', { $scope: $scope });

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

    it('exposes metrics to $scope (as an array of arrays with length 4)', function () {
        $rootScope.$digest();
        expect($scope.metrics[0]).toBeAnArrayOfMetricWithScore();
        expect($scope.metrics[0].length).toBe(4);
    });

});