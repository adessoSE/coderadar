'use strict';

angular.module('coderadarApp')
    .controller('metricListController', [
        '$scope',
        '$location',
        'metricsService',
        'defaultDatatableOptions',
        function ($scope, $location, metricsService, options) {

            $scope.options = options;
            options.rowCallback = rowCallback;

            $scope.metrics = metricsService.loadMetrics();

            /**
             * Handles click events on a table row.
             * @param data the data of the row as JSON object.
             */
            function onRowClicked(index) {
                var metric = $scope.metrics[index];
                $location
                    .path('drilldown')
                    .search('metricId', metric.metricId)
                    .search('folder', "/");
            }

            /**
             * Binds a click handler to a table row after the row has been created. Also
             * See https://datatables.net/reference/option/rowCallback.
             * @param row the <tr> DOM element
             * @param data the data in the row as JSON object
             * @param index the zero-based index of the current row
             * @returns the <tr> DOM element of the row
             */
            function rowCallback(row, data, index) {
                $('td', row).css('cursor', 'pointer');
                $('td', row).unbind('click');
                $('td', row).bind('click', function () {
                    $scope.$apply(function () {
                        onRowClicked(index);
                    });
                });
                return row;
            }

        }]);
