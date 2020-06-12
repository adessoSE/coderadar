package io.reflectoring.coderadar.useradministration.service.get;

import io.reflectoring.coderadar.useradministration.domain.User;
import io.reflectoring.coderadar.useradministration.port.driven.ListUsersPort;
import io.reflectoring.coderadar.useradministration.port.driver.get.ListUsersUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListUsersService implements ListUsersUseCase {

  private final ListUsersPort listUsersPort;

  public ListUsersService(ListUsersPort listUsersPort) {
    this.listUsersPort = listUsersPort;
  }

  @Override
  public List<User> listUsers() {
    return listUsersPort.listUsers();
  }
}
