'use strict';

/**
 * This is a stateful service that stores some global state for the web page and provides accessors to this state.
 * @constructor
 */
Coderadar.PageState = function () {
    this.headline = 'Default Headline';
    this.subline = 'Default Sub Line';
};

Coderadar.PageState.prototype.setHeadline = function (headline) {
    this.headline = headline;
};

Coderadar.PageState.prototype.setSubline = function (subline) {
    this.subline = subline;
};

// registering service with angular
angular.module('coderadarApp')
    .service('PageState', [Coderadar.PageState]);