'use strict';

describe('CommitService', function () {

    var commitService, commitsResource, $q, $rootScope;

    beforeEach(module('coderadarApp'));

    beforeEach(inject(function (_CommitService_, _CommitsResource_, _$q_, _$rootScope_) {
        commitService = _CommitService_;
        commitsResource = _CommitsResource_;
        $q = _$q_;
        $rootScope = _$rootScope_;

        spyOn(commitsResource, 'loadLatestCommits').and.callFake(function (count) {
            var deferred = $q.defer();
            deferred.resolve(CommitServiceSpec.commits.slice(0, count));
            return deferred.promise;
        });
    }));

    it('should exist', function () {
        expect(commitService).toBeDefined();
    });

    describe('getLatestCommits()', function () {

        it('should load an array of Commit objects', function (done) {
            var promise = commitService.loadLatestCommits(2);

            promise.then(function (commits) {
                expect(commits).toBeAnArrayOfCommits();
                done();
            });

            // trigger resolving of promises
            $rootScope.$digest();
        });

        it('should load the correct number of Commit objects', function (done) {
            var promise = commitService.loadLatestCommits(2);
            promise.then(function (commits) {
                expect(commits.length).toBe(2);
                done();
            });

            // trigger resolving of promises
            $rootScope.$digest();
        });

    });
});
