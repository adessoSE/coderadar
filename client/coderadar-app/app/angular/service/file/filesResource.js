'use strict';

/**
 * API to access Files resources
 * @param $resource Angular $resource service.
 * @param _$q_ Angular $q service.
 * @constructor
 */
Coderadar.FilesResource = function ($resource, _$q_) {
    this.filesResource = $resource("/offline-data/commits/files/files-:commitId.json");
    this.$q = _$q_;
};


Coderadar.FilesResource.prototype.loadFilesForCommit = function (commitId) {
    var deferred = this.$q.defer();
    this.filesResource.get({commitId: commitId}, function (files) {
        deferred.resolve(files);
    });
    return deferred.promise;
};

// registering service with angular
angular.module('coderadarApp')
    .service('FilesResource', ['$resource', '$q', Coderadar.FilesResource]);