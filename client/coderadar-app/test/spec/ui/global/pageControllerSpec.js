'use strict';

describe('PageController', function () {
    beforeEach(module('coderadarApp'));

    var $controller, pageState, pageController, $scope;

    beforeEach(inject(function (_$controller_, _PageState_) {
        $controller = _$controller_;
        pageState = _PageState_;
        $scope = {};
        pageController = $controller('PageController', { $scope: $scope });
    }));

    it('exposes the PageState service to $scope', function () {
        expect($scope.Page.headline).toBe(pageState.headline);
        expect($scope.Page.subline).toBe(pageState.subline);
    });

    it('exposes the LabelProvider service to $scope', function () {
        expect($scope.labelProvider.getLabelForCommit).toBeAFunction();
    });

    describe('$scope.getTrendColor()', function () {
        it('returns the right trend color for a metric', function () {
            var positiveMetric = {
                valuationType: Coderadar.MetricsResource.ValuationType.POSITIVE
            };
            expect($scope.getTrendColor(positiveMetric, 10)).toBe('green');
            expect($scope.getTrendColor(positiveMetric, -10)).toBe('red');
            expect($scope.getTrendColor(positiveMetric, 0)).toBe('yellow');

            var negativeMetric = {
                valuationType: Coderadar.MetricsResource.ValuationType.NEGATIVE
            };
            expect($scope.getTrendColor(negativeMetric, 10)).toBe('red');
            expect($scope.getTrendColor(negativeMetric, -10)).toBe('green');
            expect($scope.getTrendColor(negativeMetric, 0)).toBe('yellow');

            var unvaluedMetric = {
                valuationType: Coderadar.MetricsResource.ValuationType.UNVALUED
            };
            expect($scope.getTrendColor(unvaluedMetric, 0)).toBe('yellow');
            expect($scope.getTrendColor(unvaluedMetric, 10)).toBe('yellow');
            expect($scope.getTrendColor(unvaluedMetric, -10)).toBe('yellow');
        });
    });

});