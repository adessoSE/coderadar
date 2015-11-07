'use strict';

/**
 * Service that loads data related to code metrics and preprocesses it for use in the UI.
 * @param _$q_ Angular $q service.
 * @param _MetricsResource_ Coderadar MetricsResource service to access metrics resources.
 * @param _ScoreResource_ Coderadar ScoreResource service to access score resources.
 * @constructor
 */
Coderadar.MetricService = function (_$q_, _MetricsResource_, _ScoreResource_) {
    this.scoreResource = _ScoreResource_;
    this.metricsResource = _MetricsResource_;
    this.$q = _$q_;
};

/**
 * A data structure containing the base data of a metric, together with metric scores of the code base at the time of
 * a specific commit as well as delta scores comparing that commit with an earlier one.
 * @typedef {Object} MetricWithScore
 * @property {string} id The unique ID of the metric.
 * @property {string} displayName The display name of the metric.
 * @property {ValuationType} valuationType The valuationType of the metric.
 * @property {number} score The current score of this metric at a point in time.
 * @property {number} delta The delta of the score to another point in time.
 */

/**
 * Callback to be called after loading MetricWithScore objects.
 * @callback MetricsWithScoreLoadedCallback
 * @param {MetricWithScore[]} metrics and their score.
 */

/**
 * Loads all metrics with their basic data, their score in the specified baseline commit and the delta between the
 * baseline commit another specified commit.
 * @param {string} baselineCommitId ID of the commit in which to count the score.
 * @param {string} deltaCommitId ID of a commit previous to the baseline commit against which to calculate a score delta.
 * @returns {Promise<MetricWithScore>} Promise to load the metrics.
 */
Coderadar.MetricService.prototype.loadMetricsWithScore = function (baselineCommitId, deltaCommitId) {

    var deferred = this.$q.defer();

    var self = this;

    this.$q.all([
        self.scoreResource.loadCommitScore(baselineCommitId),
        self.scoreResource.loadCommitScore(deltaCommitId),
        self.metricsResource.loadAllMetrics()
    ]).then(function (data) {
        var baselineCommitScore = data[0];
        var deltaCommitScore = data[1];
        var metrics = data[2];

        metrics = addScoreToMetrics(metrics, baselineCommitScore);
        metrics = addDeltaToMetrics(metrics, deltaCommitScore);

        deferred.resolve(metrics);
    });

    return deferred.promise;

    function addScoreToMetrics(metrics, score) {
        metrics.forEach(function (metric) {
            metric.score = score[metric.id];
        });
        return metrics;
    }

    function addDeltaToMetrics(metrics, deltaScore) {
        metrics.forEach(function (metric) {
            metric.delta = metric.score - deltaScore[metric.id];
        });
        return metrics;
    }
};

Coderadar.MetricService.prototype.loadAllMetrics = function(){
    return this.metricsResource.loadAllMetrics();
};

// registering service with angular
angular.module('coderadarApp')
    .service('MetricService', ['$q', 'MetricsResource', 'ScoreResource', Coderadar.MetricService]);