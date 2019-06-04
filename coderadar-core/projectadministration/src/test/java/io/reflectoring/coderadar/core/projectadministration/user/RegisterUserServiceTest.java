package io.reflectoring.coderadar.core.projectadministration.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.core.projectadministration.port.driven.user.LoadUserPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.RegisterUserPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.register.RegisterUserCommand;
import io.reflectoring.coderadar.core.projectadministration.service.user.register.RegisterUserService;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RegisterUserServiceTest {
  private RegisterUserPort registerUserPort = mock(RegisterUserPort.class);
  private LoadUserPort loadUserPort = mock(LoadUserPort.class);

  @Test
  void returnsNewUserIdWhenRegister() {
    RegisterUserService testSubject = new RegisterUserService(registerUserPort, loadUserPort);

    Mockito.when(registerUserPort.register(any())).thenReturn(1L);
    Mockito.when(loadUserPort.loadUserByUsername(anyString())).thenReturn(Optional.empty());

    RegisterUserCommand command = new RegisterUserCommand("username", "password");
    Long userId = testSubject.register(command);

    Assertions.assertEquals(1L, userId.longValue());
  }
}
