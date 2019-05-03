package io.reflectoring.coderadar.core.projectadministration.user;

import io.reflectoring.coderadar.core.projectadministration.domain.User;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.LoginUserPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.login.LoginUserCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.login.LoginUserResponse;
import io.reflectoring.coderadar.core.projectadministration.service.user.LoginUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class LoginUserServiceTest {
  @Mock private LoginUserPort loginUserPort;
  @InjectMocks private LoginUserService testSubject;

  @Test
  void loginUserWithUsernameAndPassword() {
    User user = new User();
    user.setId(1L);
    user.setUsername("username");
    Mockito.when(loginUserPort.login(user)).thenReturn(user);

    LoginUserCommand command = new LoginUserCommand("username", "password");
    LoginUserResponse response = testSubject.login(command);

    Assertions.assertEquals("abalfgubhfuo[oi3y0823pdyu", response.getAccessToken());
    Assertions.assertEquals("ift021789f21897f2187fg", response.getRefreshToken());
  }
}
