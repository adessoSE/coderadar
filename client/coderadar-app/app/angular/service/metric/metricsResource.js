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
        deferred.resolve(metrics);
    });
    return deferred.promise;
};

// registering service with angular
angular.module('coderadarApp')
    .service('MetricsResource', ['$resource', '$q', Coderadar.MetricsResource]);