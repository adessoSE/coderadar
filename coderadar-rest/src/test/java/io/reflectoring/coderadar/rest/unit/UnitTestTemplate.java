package io.reflectoring.coderadar.rest.unit;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.useradministration.service.security.AuthenticationService;

public abstract class UnitTestTemplate {
  protected final AuthenticationService authenticationService = mock(AuthenticationService.class);
}
