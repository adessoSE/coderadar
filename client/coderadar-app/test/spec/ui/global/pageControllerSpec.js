'use strict';

describe('PageController', function () {
    beforeEach(module('coderadarApp'));

    var $controller, pageState, pageController, $scope;

    beforeEach(inject(function (_$controller_, _PageState_) {
        $controller = _$controller_;
        pageState = _PageState_;
        $scope = {};
        pageController = $controller('PageController', { $scope: $scope });
    }));

    it('exposes the PageState service to $scope', function () {
        expect($scope.Page.headline).toBe(pageState.headline);
        expect($scope.Page.subline).toBe(pageState.subline);
    });

    it('exposes the LabelProvider service to $scope', function () {
        expect($scope.labelProvider.getLabelForCommit).toBeAFunction();
    })

});