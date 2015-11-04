'use strict';

describe('CommitsResource', function () {

    var commitsResource;

    beforeEach(module('coderadarApp'));

    beforeEach(inject(function (_CommitsResource_) {
        commitsResource = _CommitsResource_;
    }));

    it('should exist', function () {
        expect(commitsResource).toBeDefined();
    });

});

