package io.reflectoring.coderadar.useradministration.service.load;

import io.reflectoring.coderadar.useradministration.domain.User;
import io.reflectoring.coderadar.useradministration.port.driven.LoadUserPort;
import io.reflectoring.coderadar.useradministration.port.driver.load.LoadUserResponse;
import io.reflectoring.coderadar.useradministration.port.driver.load.LoadUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoadUserService implements LoadUserUseCase {

  private final LoadUserPort port;

  @Autowired
  public LoadUserService(LoadUserPort port) {
    this.port = port;
  }

  @Override
  public LoadUserResponse loadUser(Long id) {
    User user = port.loadUser(id);
    return new LoadUserResponse(user.getId(), user.getUsername());
  }
}
