package io.reflectoring.coderadar.core.projectadministration.user;

import static org.mockito.Mockito.mock;

import io.reflectoring.coderadar.core.projectadministration.domain.User;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.LoadUserPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.load.LoadUserResponse;
import io.reflectoring.coderadar.core.projectadministration.service.user.load.LoadUserService;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class LoadUserServiceTest {
  private LoadUserPort loadUserPort = mock(LoadUserPort.class);

  @Test
  void loadUserWithIdOne() {
    LoadUserService testSubject = new LoadUserService(loadUserPort);

    User user = new User();
    user.setId(1L);
    user.setUsername("username");

    Mockito.when(loadUserPort.loadUser(user.getId())).thenReturn(Optional.of(user));

    LoadUserResponse response = testSubject.loadUser(1L);

    Assertions.assertEquals(user.getUsername(), response.getUsername());
  }
}
