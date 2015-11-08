/**
 * Directive that adds a SlimScroll scrollbar to a DOM element.
 *
 * You must specify the height of the container, after which a scrollbar appears, with
 * the HTML attribute 'cr-slim-scroll-height'.
 *
 * Assumes that the JS file of the jquery slimscroll plugin is loaded.
 */
angular.module('coderadarApp')
    .directive('crSlimScroll', function () {
        return {
            restrict: 'A',
            link: function (scope, element, attrs) {
                var height = attrs.crSlimScrollHeight;
                $(element).slimScroll({
                    height: height
                });
            }
        };
    });
