package io.reflectoring.coderadar.core.projectadministration.service.user.load;

import io.reflectoring.coderadar.core.projectadministration.UserNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.domain.User;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.LoadUserPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.load.LoadUserResponse;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.load.LoadUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("LoadUserService")
public class LoadUserService implements LoadUserUseCase {

  private final LoadUserPort port;

  @Autowired
  public LoadUserService(@Qualifier("LoadUserServiceNeo4j") LoadUserPort port) {
    this.port = port;
  }

  @Override
  public LoadUserResponse loadUser(Long id) {
    Optional<User> user = port.loadUser(id);
    if (user.isPresent()) {
      return new LoadUserResponse(user.get().getId(), user.get().getUsername());
    } else {
      throw new UserNotFoundException(id);
    }
  }
}
