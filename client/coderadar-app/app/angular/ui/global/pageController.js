'use strict';

angular.module('coderadarApp')
    .controller('PageController', [
        '$scope',
        'PageState',
        'LabelProvider',
        function ($scope, pageState, labelProvider) {
            $scope.Page = pageState;
            $scope.labelProvider = labelProvider;
        }]);
