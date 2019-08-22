package io.reflectoring.coderadar.projectadministration.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import io.reflectoring.coderadar.projectadministration.port.driven.user.LoadUserPort;
import io.reflectoring.coderadar.projectadministration.port.driven.user.RegisterUserPort;
import io.reflectoring.coderadar.projectadministration.port.driver.user.register.RegisterUserCommand;
import io.reflectoring.coderadar.projectadministration.service.user.register.RegisterUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RegisterUserServiceTest {

  @Mock private RegisterUserPort registerUserPort;

  @Mock private LoadUserPort loadUserPort;

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
