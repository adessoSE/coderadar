beforeEach(function () {

    jasmine.addMatchers({

        /**
         * Asserts that a given object is a MetricWithScore.
         */
        toBeAMetricWithScore: function () {
            return {
                compare: function (object) {
                    var result = {};
                    result.pass = isMetricWithScore(object);
                    if (!result.pass) {
                        result.message = "Expected object to be a MetricWithScore: " + object;
                    }
                    return result;
                }
            };
        },

        /**
         * Asserts that a given object is a Metric.
         */
        toBeAMetric: function () {
            return {
                compare: function (object) {
                    var result = {};
                    result.pass = isMetric(object);
                    if (!result.pass) {
                        result.message = "Expected object to be a Metric: " + object;
                    }
                    return result;
                }
            };
        },

        toBeAnArrayOfMetricWithScore: function () {
            return {
                compare: function (array) {
                    var result = {};
                    if (angular.isArray(array)) {
                        result.pass = true;
                        for (var i = 0; i < array.length; i++) {
                            if (!isMetricWithScore(array[i])) {
                                console.log(array[i]);
                                result.pass = false;
                                result.message = 'Expected object at index ' + i + ' in array to be a MetricWithScore';
                                break;
                            }
                        }
                    } else {
                        result.pass = false;
                        result.message = 'Expected object to be an array of MetricWithScore';
                    }

                    return result;
                }
            }
        }

    });

    function isMetricWithScore(object) {
        return Coderadar.MetricsResource.isMetricWithScore(object);
    }

    function isMetric(object) {
        return Coderadar.MetricsResource.isMetric(object);
    }
});