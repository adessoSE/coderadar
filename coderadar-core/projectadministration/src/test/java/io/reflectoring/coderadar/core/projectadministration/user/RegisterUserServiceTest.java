package io.reflectoring.coderadar.core.projectadministration.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import io.reflectoring.coderadar.core.projectadministration.port.driven.user.LoadUserPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.RegisterUserPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.register.RegisterUserCommand;
import io.reflectoring.coderadar.core.projectadministration.service.user.register.RegisterUserService;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class RegisterUserServiceTest {
  @Mock private RegisterUserPort registerUserPort;
  @Mock private LoadUserPort loadUserPort;

  @InjectMocks private RegisterUserService testSubject;

  @Test
  void returnsNewUserIdWhenRegister() {
    Mockito.when(registerUserPort.register(any())).thenReturn(1L);
    Mockito.when(loadUserPort.loadUserByUsername(anyString())).thenReturn(Optional.empty());

    RegisterUserCommand command = new RegisterUserCommand("username", "password");
    Long userId = testSubject.register(command);

    Assertions.assertEquals(1L, userId.longValue());
  }
}
