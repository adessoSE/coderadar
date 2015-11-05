'use strict';

describe('PageController', function () {
    beforeEach(module('coderadarApp'));

    var $controller, pageState;

    beforeEach(inject(function (_$controller_, _PageState_) {
        $controller = _$controller_;
        pageState = _PageState_;
    }));

    it('exposes the PageState service to $scope', function () {
        var $scope = {};
        var controller = $controller('PageController', { $scope: $scope });
        expect($scope.Page.headline).toBe(pageState.headline);
        expect($scope.Page.subline).toBe(pageState.subline);
    })

});