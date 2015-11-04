'use strict';

describe('MetricsResource', function () {

    var metricsResource;

    beforeEach(module('coderadarApp'));

    beforeEach(inject(function (_MetricsResource_) {
        metricsResource = _MetricsResource_;
    }));

    it('should exist', function () {
        expect(metricsResource).toBeDefined();
    });

});

