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

        it('should load the correct number of commits', function (done) {
            commitService.loadLatestCommits(2, function (commits) {
                expect(commits).toBeDefined();
                expect(commits.length).toBe(2);
                done();
            });

            // trigger resolving of promises
            $rootScope.$digest();
        });

        it('should load objects of the correct type', function (done) {
            commitService.loadLatestCommits(2, function (commits) {
                expect(commits[0].id).toBe("HEAD");
                expect(commits[0].timestamp).toBe(175010121254);
                expect(commits[0].committer).toBe("Tom");
                expect(commits[0].message).toBe("commit message");
                done();
            });

            // trigger resolving of promises
            $rootScope.$digest();
        });
    });
});

