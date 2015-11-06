beforeEach(function () {

    jasmine.addMatchers({

        /**
         * Asserts that a given object is a Promise.
         */
        toBeACommit: function () {
            return {
                compare: function (object) {
                    var result = {};
                    result.pass = isCommit(object);
                    if (!result.pass) {
                        result.message = "Expected object to be a Commit: " + object;
                    }
                    return result;
                }
            };
        },

        toBeAnArrayOfCommits: function () {
            return {
                compare: function (array) {
                    var result = {};
                    if (angular.isArray(array)) {
                        result.pass = true;
                        for(var i = 0; i < array.length; i++){
                            if(!isCommit(array[i])){
                                result.pass = false;
                                result.message = 'Expected object at index ' + i + ' in array to be a Commit';
                                break;
                            }
                        }
                    } else {
                        result.pass = false;
                        result.message = 'Expected object to be an array of Commits';
                    }

                    return result;
                }
            }
        }

    });

    function isCommit(object) {
        return angular.isObject(object) &&
            typeof object.id === 'string' &&
            typeof object.timestamp === 'number' &&
            typeof object.committer === 'string' &&
            typeof object.message === 'string';
    }
});