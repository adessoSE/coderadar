package io.reflectoring.coderadar.core.projectadministration.service.user;

import io.reflectoring.coderadar.core.projectadministration.domain.User;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.LoadUserPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.LoadUserCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.LoadUserUseCase;
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
  public User loadUser(LoadUserCommand command) {
    return port.loadUser(command.getId());
  }
}