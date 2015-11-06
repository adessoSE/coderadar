'use strict';

angular.module('coderadarApp')
    .controller('BrowseController', [
        '$scope',
        'PageState',
        function ($scope, pageState) {

            pageState.setHeadline("Browse");
            pageState.setSubline("Dig through your codebase to find out about a selected metric.");

        }]);
