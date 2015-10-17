/**
 * Default options for datatables throughout the application.
 */
angular.module('coderadarApp')
    .value("defaultDatatableOptions", {
        "serverSide": false,
        "paging": false,
        "searching": false
    });