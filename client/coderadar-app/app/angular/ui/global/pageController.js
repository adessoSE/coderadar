'use strict';

angular.module('coderadarApp')
    .controller('PageController', [
        '$scope',
        'PageState',
        'LabelProvider',
        function ($scope, pageState, labelProvider) {
            $scope.Page = pageState;
            $scope.labelProvider = labelProvider;

            /**
             * Function to return the color for the trend of a metric: 'green' for good, 'red' for bad and 'yellow' for
             * undefined.
             * @param {Metric} metric The metric whose trend color to calculate.
             * @param {number} delta The trend value (positive or negative).
             * @returns {string} the color as string, either 'red', 'green' or 'yellow'.
             */
            $scope.getTrendColor = function (metric, delta) {
                if(! typeof delta === 'number'){
                    throw new Error('Expected parameter \'delta\' is missing!');
                }
                var trendColor = 'yellow';
                if ((delta > 0 && metric.valuationType == Coderadar.MetricsResource.ValuationType.POSITIVE)
                    || (delta < 0 && metric.valuationType == Coderadar.MetricsResource.ValuationType.NEGATIVE)) {
                    trendColor = 'green';
                } else if ((delta < 0 && metric.valuationType == Coderadar.MetricsResource.ValuationType.POSITIVE)
                    || (delta > 0 && metric.valuationType == Coderadar.MetricsResource.ValuationType.NEGATIVE)) {
                    trendColor = 'red';
                }
                return trendColor;
            };

        }]);
