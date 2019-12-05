package io.reflectoring.coderadar.projectadministration.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import io.reflectoring.coderadar.useradministration.domain.User;
import io.reflectoring.coderadar.useradministration.port.driven.LoadUserPort;
import io.reflectoring.coderadar.useradministration.port.driver.load.LoadUserResponse;
import io.reflectoring.coderadar.useradministration.service.load.LoadUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LoadUserServiceTest {

  @Mock private LoadUserPort loadUserPortMock;

  private LoadUserService testSubject;

  @BeforeEach
  void setUp() {
    this.testSubject = new LoadUserService(loadUserPortMock);
  }

  @Test
  void loadUserWithIdOne() {
    // given
    long userId = 1L;
    String username = "username";
    User user = new User().setId(userId).setUsername(username);

    LoadUserResponse expectedResponse = new LoadUserResponse(userId, username);

    when(loadUserPortMock.loadUser(userId)).thenReturn(user);

    // when
    LoadUserResponse actualResponse = testSubject.loadUser(userId);

    // then
    assertThat(actualResponse).isEqualTo(expectedResponse);
  }
}
