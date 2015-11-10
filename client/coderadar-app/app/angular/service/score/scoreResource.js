'use strict';

/**
 * API to access resources related to metric scores.
 * @param $resource Angular $resource service
 * @param _$q_ Angular $q service
 * @constructor
 */
Coderadar.ScoreResource = function ($resource, _$q_) {
    this.scoreResource = $resource("/offline-data/commits/score/score-:commitId.json");
    this.$q = _$q_;
};

/**
 * Dictionary containing scores to several metrics at a given point in time. Using metric ids as key and a
 * numerical score value as value.
 * @typedef {Object} Scores
 * @type {Object.<string, number>}
 */

/**
 * Loads the scores to all metrics at the time of the specified commit.
 * @param commitId ID of the commit whose scores to load.
 * @returns {Promise<Scores>} the promise resolving to the loaded scores.
 */
Coderadar.ScoreResource.prototype.loadCommitScore = function (commitId) {
    var deferred = this.$q.defer();
    this.scoreResource.get({"commitId": commitId}, function (score) {
        deferred.resolve(score);
    });
    return deferred.promise;
};

// registering service with angular
angular.module('coderadarApp')
    .service('ScoreResource', ['$resource', '$q', Coderadar.ScoreResource]);