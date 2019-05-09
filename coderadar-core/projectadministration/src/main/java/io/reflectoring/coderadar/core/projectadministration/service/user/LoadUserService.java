package io.reflectoring.coderadar.core.projectadministration.service.user;

import io.reflectoring.coderadar.core.projectadministration.port.driven.user.LoadUserPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.load.LoadUserResponse;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.load.LoadUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("LoadUserService")
public class LoadUserService implements LoadUserUseCase {

  private final LoadUserPort port;

  @Autowired
  public LoadUserService(LoadUserPort port) {
    this.port = port;
  }

  @Override
  public LoadUserResponse loadUser(Long id) {
    return new LoadUserResponse(port.loadUser(id).getUsername());
  }
}
