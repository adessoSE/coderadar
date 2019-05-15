package io.reflectoring.coderadar.core.projectadministration.service.user;

import io.reflectoring.coderadar.core.projectadministration.domain.User;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.RegisterUserPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.register.RegisterUserCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.register.RegisterUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("RegisterUserService")
public class RegisterUserService implements RegisterUserUseCase {

  private final RegisterUserPort port;

  @Autowired
  public RegisterUserService(@Qualifier("RegisterUserServiceNeo4j") RegisterUserPort port) {
    this.port = port;
  }

  @Override
  public Long register(RegisterUserCommand command) {
    User user = new User();
    user.setUsername(command.getUsername());
    user.setPassword(command.getPassword());
    return port.register(user);
  }
}
