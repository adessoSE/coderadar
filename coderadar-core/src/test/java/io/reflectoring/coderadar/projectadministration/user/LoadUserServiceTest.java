package io.reflectoring.coderadar.projectadministration.user;

import io.reflectoring.coderadar.projectadministration.domain.User;
import io.reflectoring.coderadar.projectadministration.port.driven.user.LoadUserPort;
import io.reflectoring.coderadar.projectadministration.port.driver.user.load.LoadUserResponse;
import io.reflectoring.coderadar.projectadministration.service.user.load.LoadUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LoadUserServiceTest {

  @Mock private LoadUserPort loadUserPort;

  @Test
  void loadUserWithIdOne() {
    LoadUserService testSubject = new LoadUserService(loadUserPort);

    User user = new User();
    user.setId(1L);
    user.setUsername("username");

    Mockito.when(loadUserPort.loadUser(user.getId())).thenReturn(user);

    LoadUserResponse response = testSubject.loadUser(1L);

    Assertions.assertEquals(user.getUsername(), response.getUsername());
  }
}
