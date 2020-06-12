package io.reflectoring.coderadar.projectadministration.user;

import io.reflectoring.coderadar.useradministration.UsernameAlreadyInUseException;
import io.reflectoring.coderadar.useradministration.domain.User;
import io.reflectoring.coderadar.useradministration.port.driven.GetUserPort;
import io.reflectoring.coderadar.useradministration.port.driven.RegisterUserPort;
import io.reflectoring.coderadar.useradministration.port.driver.register.RegisterUserCommand;
import io.reflectoring.coderadar.useradministration.service.register.RegisterUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterUserServiceTest {

  @Mock private RegisterUserPort registerUserPortMock;

  @Mock private GetUserPort getUserPortMock;

  @Captor private ArgumentCaptor<User> userArgumentCaptor;

  private RegisterUserService testSubject;

  @BeforeEach
  void setUp() {
    this.testSubject = new RegisterUserService(registerUserPortMock, getUserPortMock);
  }

  @Test
  void registerCreatesNewUserWhenUsernameNotInUse() {
    // given
    String username = "username";
    String password = "password";
    long expectedUserId = 123L;

    RegisterUserCommand registerUserCommand = new RegisterUserCommand(username, password);

    when(getUserPortMock.existsByUsername(username)).thenReturn(false);
    when(registerUserPortMock.register(any())).thenReturn(expectedUserId);

    // when
    long actualUserId = testSubject.register(registerUserCommand);

    // then
    verify(registerUserPortMock).register(userArgumentCaptor.capture());

    assertThat(actualUserId).isEqualTo(expectedUserId);
    assertThat(userArgumentCaptor.getValue().getUsername()).isEqualTo(username);
  }

  @Test
  void registerThrowsExceptionWhenUsernameAlreadyInUse() {
    // given
    String username = "username";
    String password = "password";

    RegisterUserCommand registerUserCommand = new RegisterUserCommand(username, password);

    when(getUserPortMock.existsByUsername(username)).thenReturn(true);

    // when / then
    assertThatThrownBy(() -> testSubject.register(registerUserCommand))
        .isInstanceOf(UsernameAlreadyInUseException.class);
  }
}
