package io.reflectoring.coderadar.core.projectadministration.user;

import static org.mockito.ArgumentMatchers.anyString;

import io.reflectoring.coderadar.core.projectadministration.domain.User;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.LoadUserPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.RegisterUserPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.register.RegisterUserCommand;
import io.reflectoring.coderadar.core.projectadministration.service.user.PasswordService;
import io.reflectoring.coderadar.core.projectadministration.service.user.RegisterUserService;
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
  @Mock private PasswordService passwordService;

  @InjectMocks private RegisterUserService testSubject;

  @Test
  void returnsNewUserIdWhenRegister() {
    User user = new User();
    user.setUsername("username");
    user.setPassword("password");
    Mockito.when(registerUserPort.register(user)).thenReturn(1L);
    Mockito.when(loadUserPort.loadUserByUsername(anyString())).thenReturn(Optional.empty());
    Mockito.when(passwordService.hash(anyString())).thenReturn("abc");

    RegisterUserCommand command = new RegisterUserCommand("username", "password");
    Long userId = testSubject.register(command);

    Assertions.assertEquals(1L, userId.longValue());
  }
}
