package io.reflectoring.coderadar.core.projectadministration.user;

import io.reflectoring.coderadar.core.projectadministration.domain.User;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.LoginUserPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.login.LoginUserCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.login.LoginUserResponse;
import io.reflectoring.coderadar.core.projectadministration.service.user.LoginUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class LoginUserServiceTest {
  @Mock private LoginUserPort loginUserPort;
  private LoginUserService testSubject;

  @BeforeEach
  void setup() {
    testSubject = new LoginUserService(loginUserPort);
  }

  @Test
  void loginUserWithUsernameAndPassword() {
    User user = new User();
    user.setId(1L);
    user.setUsername("username");
    Mockito.when(loginUserPort.login(user)).thenReturn(user);

    LoginUserCommand command = new LoginUserCommand("username", "password");
    LoginUserResponse response = testSubject.login(command);

    response.getRefreshToken();
    response.getAccessToken();

    Assertions.fail();
  }
}
