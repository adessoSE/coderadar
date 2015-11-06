'use strict';

describe('MetricService', function () {

    var metricService, $q, metricsResource, scoreResource, $rootScope;

    beforeEach(module('coderadarApp'));

    beforeEach(inject(function (_MetricService_, _$q_, _MetricsResource_, _ScoreResource_, _$rootScope_) {
        metricService = _MetricService_;
        $q = _$q_;
        metricsResource = _MetricsResource_;
        scoreResource = _ScoreResource_;
        $rootScope = _$rootScope_;

        spyOn(metricsResource, 'loadAllMetrics').and.callFake(function () {
            var deferred = $q.defer();
            deferred.resolve(MetricServiceSpec.metrics);
            return deferred.promise;
        });

        spyOn(scoreResource, 'loadCommitScore').and.callFake(function (commitId) {
            var deferred = $q.defer();
            if (commitId === 'HEAD') {
                deferred.resolve(MetricServiceSpec.baselineCommitScore);
            } else if (commitId === 'cafebabe') {
                deferred.resolve(MetricServiceSpec.deltaCommitScore);
            } else {
                throw 'unexpected commitId: ' + commitId;
            }
            return deferred.promise;
        });
    }));

    it('should exist', function () {
        expect(metricService).toBeDefined();
    });

    describe('loadMetricsWithScore()', function () {

        it('should load the correct number of metrics', function (done) {
            var promise = metricService.loadMetricsWithScore('HEAD', 'cafebabe');
            console.log(promise);
            promise.then(function (metricsWithScore) {
                expect(metricsWithScore).toBeDefined();
                expect(metricsWithScore.length).toBe(5);
                expect(metricsWithScore[0].id).toBe('javaLoc');
                expect(metricsWithScore[0].displayName).toBe('Java Lines of Code');
                expect(metricsWithScore[0].valuationType).toBe(Coderadar.MetricsResource.ValuationType.UNVALUED);
                expect(metricsWithScore[0].score).toBe(115000);
                expect(metricsWithScore[0].delta).toBe(10000);
                done();
            });

            // trigger resolving of promises
            $rootScope.$digest();
        });

        it('should load objects of the correct type', function (done) {
            var promise = metricService.loadMetricsWithScore('HEAD', 'cafebabe');
            promise.then(function (metricsWithScore) {
                expect(metricsWithScore[0].id).toBe('javaLoc');
                expect(metricsWithScore[0].displayName).toBe('Java Lines of Code');
                expect(metricsWithScore[0].score).toBe(115000);
                expect(metricsWithScore[0].delta).toBe(10000);
                done();
            });

            // trigger resolving of promises
            $rootScope.$digest();
        });

    });

});

