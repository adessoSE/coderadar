'use strict';

describe('Controller: MetrictablecontrollerCtrl', function () {

  // load the controller's module
  beforeEach(module('coderadarApp'));

  var MetrictablecontrollerCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    MetrictablecontrollerCtrl = $controller('MetrictablecontrollerCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(MetrictablecontrollerCtrl.awesomeThings.length).toBe(3);
  });
});
