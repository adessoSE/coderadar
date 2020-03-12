package io.reflectoring.coderadar.useradministration.service.load;

import io.reflectoring.coderadar.useradministration.UserNotFoundException;
import io.reflectoring.coderadar.useradministration.domain.User;
import io.reflectoring.coderadar.useradministration.port.driven.GetUserPort;
import io.reflectoring.coderadar.useradministration.port.driver.load.GetUserUseCase;
import org.springframework.stereotype.Service;

@Service
public class GetUserService implements GetUserUseCase {

  private final GetUserPort port;

  public GetUserService(GetUserPort port) {
    this.port = port;
  }

  @Override
  public User getUser(long id) {
    if (port.existsById(id)) {
      return port.getUser(id);
    } else {
      throw new UserNotFoundException(id);
    }
  }
}
