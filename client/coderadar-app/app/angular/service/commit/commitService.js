'use strict';

/**
 * Service that loads data related to commits and preprocesses it for use in the UI.
 * @param $resource Angular $resource service
 * @constructor
 */
Coderadar.CommitService = function ($resource, _commitsResource_, _$q_) {
    this.commitsResource = _commitsResource_;
    this.$q = _$q_;
};

/**
 * Callback to be called after an array of commits has been loaded.
 * @callback CommitsLoadedCallback
 * @param {Commit[]} commits Array of commits.s
 */

/**
 * Loads the latest commits in descending chronological order (most recent commit is first in the resulting array).
 * Only loads the specified number of commits.
 * @param {number} count the number of commits to load.
 * @param {CommitsLoadedCallback} successCallback the callback to pass the resulting list of Commit objects into.
 */
Coderadar.CommitService.prototype.loadLatestCommits = function (count, successCallback) {
    var self = this;
    this.$q.all([
        self.commitsResource.loadLatestCommits(count)
    ]).then(function (data) {
        successCallback(data[0]);
    });
};

// registering service with angular
angular.module('coderadarApp')
    .service('CommitService', ['$resource', 'CommitsResource', '$q', Coderadar.CommitService]);