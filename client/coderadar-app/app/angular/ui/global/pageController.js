'use strict';

angular.module('coderadarApp')
    .controller('PageController', [
        '$scope',
        'PageState',
        function ($scope, pageState) {
            $scope.Page = pageState;
        }]);
