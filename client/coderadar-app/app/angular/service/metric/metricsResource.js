'use strict';

/**
 * API to access Metrics resource
 * @param $resource Angular $resource service.
 * @param _$q_ Angular $q service.
 * @constructor
 */
Coderadar.MetricsResource = function ($resource, _$q_) {
    this.metricsResource = $resource("/offline-data/metrics.json");
    this.$q = _$q_;
};

/**
 * Enumeration of possible valuation types of a metric. The valuationType defines if a metric score is
 * better with high or low values.
 * @enum {string}
 */
Coderadar.MetricsResource.ValuationType = {
    /**
     * The bigger the score, the better.
     */
    POSITIVE: "positive",

    /**
     * The smaller the score of the metric, the better.
     */
    NEGATIVE: "negative",

    /**
     *  The metric cannot be objectively valued.
     */
    UNVALUED: "unvalued"
}

/**
 * A data structure containing the base data of a metric.
 * @typedef {Object} Metric
 * @property {string} id The unique ID of the metric.
 * @property {string} displayName The display name of the metric.
 * @property {ValuationType} valuationType The valuationType of this metric.
 */

/**
 * Loads all metrics and returns a promise.
 * @returns {Promise<Metric[]>} The promise to be resolved into an array of metrics.
 */
Coderadar.MetricsResource.prototype.loadAllMetrics = function () {
    var deferred = this.$q.defer();
    this.metricsResource.query(function (metrics) {

        // check if all elements are of type MEtric
        metrics.forEach(function(metric){
            if(!Coderadar.MetricsResource.isMetric(metric)){
                throw 'Loaded object is not a Metric: ' + metric;
            }
        });

        deferred.resolve(metrics);
    });
    return deferred.promise;
};

/**
 * Static function to test if an object possesses all fields needed to make it a MetricWithScore.
 * @param object The object to test.
 * @returns {boolean} true if the object is a MetricWithScore, false if not.
 */
Coderadar.MetricsResource.isMetricWithScore = function (object) {
    return angular.isObject(object) &&
        typeof object.id === 'string' &&
        typeof object.displayName === 'string' &&
        typeof object.valuationType === 'string' &&
        typeof object.score === 'number' &&
        typeof object.delta === 'number';
};

/**
 * Static function to test if an object possesses all fields needed to make it a Metric.
 * @param object The object to test.
 * @returns {boolean} true if the object is a Metric, false if not.
 */
Coderadar.MetricsResource.isMetric = function (object) {
    return angular.isObject(object) &&
        typeof object.id === 'string' &&
        typeof object.displayName === 'string' &&
        typeof object.valuationType === 'string';
};

// registering service with angular
angular.module('coderadarApp')
    .service('MetricsResource', ['$resource', '$q', Coderadar.MetricsResource]);