'use strict';

describe('LabelProvider', function () {

    var labelProvider;

    beforeEach(module('coderadarApp'));

    beforeEach(inject(function (_LabelProvider_) {
        labelProvider = _LabelProvider_;
    }));

    it('should exist', function () {
        expect(labelProvider).toBeDefined();
    });

    describe('getLabelForCommit()', function(){
        it('returns a human readable label for a Commit', function(){
            var commit = {
                id: 'commitId',
                timestamp: 1234567891234
            };
            expect(labelProvider.getLabelForCommit(commit)).toBe('February 14, 2009 at 00:31:31 - commitId');
        })
    });

    describe('getLabelForMetric()', function(){
        it('returns a human readable label for a Metric', function(){
            var metric = {
                displayName: 'metricLabel'
            };
            expect(labelProvider.getLabelForMetric(metric)).toBe('metricLabel');
        })
    });
});

