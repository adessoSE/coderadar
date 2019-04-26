package io.reflectoring.coderadar.core.projectadministration.user;

import io.reflectoring.coderadar.core.projectadministration.domain.User;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.LoadUserPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.load.LoadUserResponse;
import io.reflectoring.coderadar.core.projectadministration.service.user.LoadUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class LoadUserServiceTest {
  @Mock private LoadUserPort loadUserPort;
  private LoadUserService testSubject;

  @BeforeEach
  void setup() {
    testSubject = new LoadUserService(loadUserPort);
  }

  @Test
  void loadUserWithIdOne() {
    User user = new User();
    user.setId(1L);
    user.setUsername("username");

    Mockito.when(loadUserPort.loadUser(user.getId())).thenReturn(user);

    LoadUserResponse response = testSubject.loadUser(1L);

    Assertions.assertEquals(user.getUsername(), response.getUsername());
  }
}
