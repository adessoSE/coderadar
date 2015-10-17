'use strict';

/**
 * The fileTreeService provides methods to load trees of analyzed sourcecode files and their metadata.
 */
angular.module('coderadarApp')
    .factory('fileTreeService', [function () {

        return {
            loadSubTree: loadSubTree
        };

        function loadSubTree(path) {

            if (!(typeof path === "string")) {
                throw {
                    name: 'InvalidTypeException',
                    message: 'Parameter \'path\' is expected to be of type \'string\' instead of \'' + typeof path + '\''
                };
            }

            return fileTree()[path];

        }

        function fileTree() {
            return {
                "/": [
                    {
                        name: 'folder1',
                        type: 'folder',
                        metrics: {
                            "javaLOC": {
                                metricId: 'javaLOC',
                                value: 1000
                            }
                        }
                    },
                    {
                        name: 'folder2',
                        type: 'folder',
                        metrics: {
                            "javaLOC": {
                                metricId: 'javaLOC',
                                value: 500
                            }
                        }
                    }
                ],

                "/folder1": [
                    {
                        name: 'file1.java',
                        type: 'file',
                        metrics: {
                            "javaLOC": {
                                metricId: 'javaLOC',
                                value: 1000
                            }
                        }
                    },
                    {
                        name: 'file2.xml',
                        type: 'file',
                        metrics: {
                            "javaLOC": {
                                metricId: 'xmlLOC',
                                value: 500
                            }
                        }
                    }
                ],

                "/folder2": [
                    {
                        name: 'file1.txt',
                        type: 'file',
                        metrics: {
                            "javaLOC": {
                                metricId: 'javaLOC',
                                value: 1000
                            }
                        }
                    }
                ]
            }
        }
    }])
;