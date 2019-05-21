package io.reflectoring.coderadar.core.projectadministration.service.user;

import io.reflectoring.coderadar.core.projectadministration.UsernameAlreadyInUseException;
import io.reflectoring.coderadar.core.projectadministration.domain.User;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.LoadUserPort;
import io.reflectoring.coderadar.core.projectadministration.port.driven.user.RegisterUserPort;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.register.RegisterUserCommand;
import io.reflectoring.coderadar.core.projectadministration.port.driver.user.register.RegisterUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("RegisterUserService")
public class RegisterUserService implements RegisterUserUseCase {

  private final RegisterUserPort port;
  private final LoadUserPort loadUserPort;
  private final PasswordService passwordService;

  @Autowired
  public RegisterUserService(
          @Qualifier("RegisterUserServiceNeo4j") RegisterUserPort port,
          @Qualifier("LoadUserServiceNeo4j") LoadUserPort loadUserPort, PasswordService passwordService) {
    this.port = port;
    this.loadUserPort = loadUserPort;
    this.passwordService = passwordService;
  }

  @Override
  public Long register(RegisterUserCommand command) throws UsernameAlreadyInUseException {
    if (loadUserPort.loadUserByUsername(command.getUsername()).isPresent()) {
      throw new UsernameAlreadyInUseException(command.getUsername());
    }
    User user = new User();
    user.setUsername(command.getUsername());
    user.setPassword(passwordService.hash(command.getPassword()));
    return port.register(user);
  }
}
