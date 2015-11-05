'use strict';

describe('DashboardController', function () {
    beforeEach(module('coderadarApp'));

    var $controller;

    beforeEach(inject(function (_$controller_) {
        $controller = _$controller_;
    }));

    it('exposes getLabelForCommit to $scope', function(){
        var $scope = {};
        var controller = $controller('DashboardController', { $scope: $scope });
        expect($scope.getLabelForCommit).toBeAFunction();
    });

    describe('$scope.getTrendColor()', function () {
        it('returns the right trend color for a metric', function () {
            var $scope = {};
            var controller = $controller('DashboardController', { $scope: $scope });

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