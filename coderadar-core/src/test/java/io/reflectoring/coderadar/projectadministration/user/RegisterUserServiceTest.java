package io.reflectoring.coderadar.projectadministration.user;

import io.reflectoring.coderadar.projectadministration.port.driven.user.LoadUserPort;
import io.reflectoring.coderadar.projectadministration.port.driven.user.RegisterUserPort;
import io.reflectoring.coderadar.projectadministration.port.driver.user.register.RegisterUserCommand;
import io.reflectoring.coderadar.projectadministration.service.user.register.RegisterUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;

class RegisterUserServiceTest {
  private RegisterUserPort registerUserPort = mock(RegisterUserPort.class);
  private LoadUserPort loadUserPort = mock(LoadUserPort.class);

  @Test
  void returnsNewUserIdWhenRegister() {
    RegisterUserService testSubject = new RegisterUserService(registerUserPort, loadUserPort);

    Mockito.when(registerUserPort.register(any())).thenReturn(1L);
    Mockito.when(loadUserPort.existsByUsername(anyString())).thenReturn(Boolean.FALSE);

    RegisterUserCommand command = new RegisterUserCommand("username", "password");
    Long userId = testSubject.register(command);

    Assertions.assertEquals(1L, userId.longValue());
  }
}
