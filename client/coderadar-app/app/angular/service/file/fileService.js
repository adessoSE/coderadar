'use strict';

Coderadar.FileService = function (_$q_, _FilesResource_) {
    this.filesResource = _FilesResource_;
    this.$q = _$q_;
};

Coderadar.FileService.prototype.loadFilesWithDeltaForFolder = function (metricId, folder, baselineCommitId, deltaCommitId) {
    var deferred = this.$q.defer();
    var baselinePromise = this.filesResource.loadFilesForCommit(baselineCommitId);
    var deltaPromise = this.filesResource.loadFilesForCommit(deltaCommitId);

    this.$q.all([
        baselinePromise,
        deltaPromise
    ]).then(function (data) {
        var baselineFiles = data[0];
        var deltaFiles = data[1];
        var fileList = [];
        var filesInFolder = baselineFiles[folder];
        if (filesInFolder) {
            filesInFolder.forEach(function (file) {
                var newFile = {};
                newFile.name = file.name;
                newFile.fullPath = file.fullPath;
                newFile.isFolder = file.isFolder;
                newFile.score = file.score[metricId];

                // searching for the same file in delta commit to calculate the delta value
                var folderPath = file.fullPath.substring(0, file.fullPath.lastIndexOf('/'));
                if(folderPath === ''){
                    folderPath = '/';
                }
                var deltaFolder = deltaFiles[folderPath];
                for(var i = 0; i < deltaFolder.length; i++){
                    var deltaFile = deltaFolder[i];
                    if(deltaFile.name === file.name){
                        newFile.delta = file.score[metricId] - deltaFile.score[metricId];
                        break;
                    }
                }

                // if no delta file found, assume delta value to be the score value itself
                if(!newFile.delta){
                    newFile.delta = file.score[metricId];
                }

                fileList.push(newFile);
            });
        }
        deferred.resolve(fileList);
    });

    return deferred.promise;
};


// registering service with angular
angular.module('coderadarApp')
    .service('FileService', ['$q', 'FilesResource', Coderadar.FileService]);