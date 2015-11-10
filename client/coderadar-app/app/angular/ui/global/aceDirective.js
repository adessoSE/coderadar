angular.module('coderadarApp')
    .directive('crAce', ['$http', function ($http) {

        /**
         * Returns the ACE editor more for highlighting the syntax depending on the extension of a given file name.
         * @param filePath The file name, optionally with a full path. Must end with a file extension.
         * @returns {string} The ACE editor mode for highlighting the file's syntax correctly.
         */
        function getEditorModeForFile(filePath) {
            var fileExtension = filePath.substr(filePath.lastIndexOf('.') + 1);
            switch (fileExtension.toLowerCase()) {
                case 'abap':
                    return 'ace/mode/abap';
                case 'cpp':
                    return 'ace/mode/cpp';
                case 'js':
                    return 'ace/mode/javascript';
                case 'html':
                    return 'ace/mode/html';
                case 'java':
                    return 'ace/mode/java';
                case 'xml':
                    return 'ace/mode/xml';
                case 'json':
                    return 'ace/mode/json';
                case 'sql':
                    return 'ace/mode/sql';
                case 'css':
                    return 'ace/mode/css';
                default:
                    return 'ace/mode/text';
            }
        }

        return {
            restrict: 'A',
            link: function (scope, element, attrs) {
                var elementId = $(element).attr('id');

                var sourceFileVariable = attrs.crAceFileVar;

                var editor = ace.edit(elementId);
                editor.setTheme("ace/theme/github");
                editor.setReadOnly(true);
                editor.$blockScrolling = Infinity;

                /**
                 * Watches for changes in the scope variable that holds the path to the file to display in the editor.
                 * If it changes, the new file is loaded and its contents displayed in the editor.
                 */
                scope.$watch(sourceFileVariable, function (filePath) {
                    if (filePath && (typeof filePath === 'string')) {
                        $http({
                            method: 'GET',
                            url: filePath
                        }).then(
                            function (response) {
                                var fileContent = response.data;
                                editor.setValue(fileContent, -1); // -1 sets the cursor to the start of the editor
                                editor.getSession().setMode(getEditorModeForFile(filePath));
                            },
                            function (errorResponse) {
                                var fileContent = 'Error while loading file \'' + filePath + '\'!';
                                editor.setValue(fileContent, -1);
                                editor.getSession().setMode(getEditorModeForFile('default'));
                            });
                    }
                });
            }
        };
    }]);
