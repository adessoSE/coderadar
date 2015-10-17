'use strict';

/**
 * The metricsService provides methods to load metrics data from a data source.
 */
angular.module('coderadarApp')
    .factory('metricsService', [function () {

        return {
            loadMetrics: loadMetrics,
            loadMetric: loadMetric
        };

        /**
         * Returns an array of all code metrics and their total values. Each metric has the following fields:
         * <ul>
         *     <li>metricId: the unique ID of the metric</li>
         *     <li>name: the display name of the metric</li>
         *     <li>language: the programming language this metric is associated with</li>
         *     <li>category: the category this metric is associated with</li>
         *     <li>value: the total value of this metric over the whole code base</li>
         * </ul>
         * @returns {Array} array of metrics as JSON objects
         */
        function loadMetrics() {
            var metrics = [];
            metrics.push({
                metricId: 'javaLOC',
                name: "Java Lines of Code",
                language: "Java",
                category: "Size",
                value: 1000
            });
            metrics.push({
                metricId: 'xmlLOC',
                name: "XML Lines of Code",
                language: "XML",
                category: "Size",
                value: 50
            });
            metrics.push({
                metricId: 'javaViolationsMajor',
                name: "Java Violations (Major)",
                language: "Java",
                category: "Findbugs",
                value: 150
            });
            metrics.push({
                metricId: 'javaViolationsCritical',
                name: "Java Violations (Critical)",
                language: "Java",
                category: "Findbugs",
                value: 10
            });
            return metrics;
        }

        /**
         * Returns a single metric with the following fields:
         * <ul>
         *     <li>metricId: the unique ID of the metric</li>
         *     <li>name: the display name of the metric</li>
         * </ul>
         * @returns JSON object of a single metric
         */
       function loadMetric(metricId){
            return {
                metricId: metricId,
                name: 'Java Lines of Code'
            }
        }
    }]);