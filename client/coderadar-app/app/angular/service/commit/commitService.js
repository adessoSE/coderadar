'use strict';

/**
 * Service that loads data related to commits and preprocesses it for use in the UI.
 * @param $resource Angular $resource service
 * @param _commitsResource_ Resource of Commit objects.
 * @param _$q_ Angular $q service.
 * @constructor
 */
Coderadar.CommitService = function ($resource, _commitsResource_, _$q_) {
    this.commitsResource = _commitsResource_;
    this.$q = _$q_;
};

/**
 * Loads the latest commits in descending chronological order (most recent commit is first in the resulting array).
 * Only loads the specified number of commits.
 * @param {number} count the number of commits to load.
 * @returns {Promise<Commit[]>} Promise resolving to the loaded list of commits, sorted chronologically in descending order.
 */
Coderadar.CommitService.prototype.loadLatestCommits = function (count) {
    return this.commitsResource.loadLatestCommits(count);
};

// registering service with angular
angular.module('coderadarApp')
    .service('CommitService', ['$resource', 'CommitsResource', '$q', Coderadar.CommitService]);