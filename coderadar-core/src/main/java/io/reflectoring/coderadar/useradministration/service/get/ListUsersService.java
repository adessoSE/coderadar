package io.reflectoring.coderadar.useradministration.service.get;

import io.reflectoring.coderadar.domain.User;
import io.reflectoring.coderadar.useradministration.port.driven.ListUsersPort;
import io.reflectoring.coderadar.useradministration.port.driver.get.ListUsersUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListUsersService implements ListUsersUseCase {
  private final ListUsersPort listUsersPort;

  @Override
  public List<User> listUsers() {
    return listUsersPort.listUsers();
  }
}
