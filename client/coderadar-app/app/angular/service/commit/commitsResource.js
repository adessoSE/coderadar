'use strict';

/**
 * API to access Commit resources:
 * @param $resource Angular $resource service.
 * @param _$q_ Angular $q service.
 * @constructor
 */
Coderadar.CommitsResource = function ($resource, _$q_) {
    this.commitsResource = $resource("/offline-data/commits.json");
    this.$q = _$q_;
};

/**
 * A data structure containing the base data of a commit.
 * @typedef {Object} Commit
 * @property {string} id The ID of the commit.
 * @property {number} timestamp The timestamp of the commit in milliseconds since epoch.
 * @property {string} committer Name of the person responsible for the commit.
 * @property {string} message Message of the commit.
 */

/**
 * Loads the latest commits.
 * @param count The number of latest commits to load.
 * @returns {Promise<Commit[]>} Promise resolving to the loaded list of commits, sorted chronologically in descending order.
 */
Coderadar.CommitsResource.prototype.loadLatestCommits = function (count) {
    var deferred = this.$q.defer();
    this.commitsResource.query(function (commits) {
        // in offline mode we only have ONE json file so we have to slice the first <count> commits out of it
        var latestCommits = commits.slice(0, count);
        deferred.resolve(latestCommits);
    });
    return deferred.promise;
};

// registering service with angular
angular.module('coderadarApp')
    .service('CommitsResource', ['$resource', '$q', Coderadar.CommitsResource]);