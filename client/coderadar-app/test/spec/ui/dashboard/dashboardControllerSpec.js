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

    describe('$scope.getTrendColor()', function () {
        it('returns the right trend color for a metric', function () {
            var positiveMetricWithPositiveTrend = {
                valuationType: Coderadar.MetricsResource.ValuationType.POSITIVE,
                delta: 10
            };
            var positiveMetricWithNoTrend = {
                valuationType: Coderadar.MetricsResource.ValuationType.POSITIVE,
                delta: 0
            };
            var positiveMetricWithNegativeTrend = {
                valuationType: Coderadar.MetricsResource.ValuationType.POSITIVE,
                delta: -10
            };
            var negativeMetricWithNegativeTrend = {
                valuationType: Coderadar.MetricsResource.ValuationType.NEGATIVE,
                delta: -10
            };
            var negativeMetricWithPositiveTrend = {
                valuationType: Coderadar.MetricsResource.ValuationType.NEGATIVE,
                delta: 10
            };
            var negativeMetricWithNoTrend = {
                valuationType: Coderadar.MetricsResource.ValuationType.NEGATIVE,
                delta: 0
            };
            var unvaluedMetricWithPositiveTrend = {
                valuationType: Coderadar.MetricsResource.ValuationType.UNVALUED,
                delta: 10
            };
            var unvaluedMetricWithNegativeTrend = {
                valuationType: Coderadar.MetricsResource.ValuationType.UNVALUED,
                delta: -10
            };

            expect($scope.getTrendColor(positiveMetricWithPositiveTrend)).toBe('green');
            expect($scope.getTrendColor(positiveMetricWithNegativeTrend)).toBe('red');
            expect($scope.getTrendColor(positiveMetricWithNoTrend)).toBe('yellow');
            expect($scope.getTrendColor(negativeMetricWithPositiveTrend)).toBe('red');
            expect($scope.getTrendColor(negativeMetricWithNegativeTrend)).toBe('green');
            expect($scope.getTrendColor(negativeMetricWithNoTrend)).toBe('yellow');
            expect($scope.getTrendColor(unvaluedMetricWithPositiveTrend)).toBe('yellow');
            expect($scope.getTrendColor(unvaluedMetricWithNegativeTrend)).toBe('yellow');

        });
    });
});