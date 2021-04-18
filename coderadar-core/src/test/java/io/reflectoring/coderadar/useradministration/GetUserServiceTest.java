package io.reflectoring.coderadar.useradministration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import io.reflectoring.coderadar.domain.User;
import io.reflectoring.coderadar.useradministration.port.driven.GetUserPort;
import io.reflectoring.coderadar.useradministration.service.get.GetUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetUserServiceTest {

  @Mock private GetUserPort getUserPortMock;

  private GetUserService testSubject;

  @BeforeEach
  void setUp() {
    this.testSubject = new GetUserService(getUserPortMock);
  }

  @Test
  void loadUserWithIdOne() {
    // given
    long userId = 1L;
    String username = "username";
    User user = new User().setId(userId).setUsername(username);

    when(getUserPortMock.getUser(userId)).thenReturn(user);
    when(getUserPortMock.existsById(userId)).thenReturn(true);

    // when
    User actualResponse = testSubject.getUser(userId);

    // then
    assertThat(actualResponse).isEqualTo(user);
  }
}
