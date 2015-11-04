beforeEach(function () {
    jasmine.addMatchers({

        /**
         * Asserts that a given object is a Promise.
         */
        toBeAPromise: function () {
            return {
                compare: function (actual, expected) {
                    return {
                        pass: angular.isObject(actual) &&
                            actual.then instanceof Function &&
                            actual["catch"] instanceof Function &&
                            actual["finally"] instanceof Function
                    };
                }
            };
        }
    });
});