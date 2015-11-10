'use strict';

var Coderadar = {};

/**
 * @ngdoc overview
 * @name coderadarApp
 * @description
 * # coderadarApp
 *
 * Main module of the application.
 */
angular
  .module('coderadarApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
  ]);

String.prototype.endsWith = function(suffix) {
    return this.indexOf(suffix, this.length - suffix.length) !== -1;
};

String.prototype.startsWith = function(prefix){
    return this.indexOf(prefix) === 0;
}