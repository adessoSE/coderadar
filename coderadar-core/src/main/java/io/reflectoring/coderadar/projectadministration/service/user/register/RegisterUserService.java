package io.reflectoring.coderadar.projectadministration.service.user.register;

import io.reflectoring.coderadar.projectadministration.UsernameAlreadyInUseException;
import io.reflectoring.coderadar.projectadministration.domain.User;
import io.reflectoring.coderadar.projectadministration.port.driven.user.LoadUserPort;
import io.reflectoring.coderadar.projectadministration.port.driven.user.RegisterUserPort;
import io.reflectoring.coderadar.projectadministration.port.driver.user.register.RegisterUserCommand;
import io.reflectoring.coderadar.projectadministration.port.driver.user.register.RegisterUserUseCase;
import io.reflectoring.coderadar.projectadministration.service.user.security.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserService implements RegisterUserUseCase {

  private final RegisterUserPort port;
  private final LoadUserPort loadUserPort;

  @Autowired
  public RegisterUserService(RegisterUserPort port, LoadUserPort loadUserPort) {
    this.port = port;
    this.loadUserPort = loadUserPort;
  }

  @Override
  public Long register(RegisterUserCommand command) throws UsernameAlreadyInUseException {
    if (loadUserPort.loadUserByUsername(command.getUsername()).isPresent()) {
      throw new UsernameAlreadyInUseException(command.getUsername());
    }
    User user = new User();
    user.setUsername(command.getUsername());
    user.setPassword(PasswordUtil.hash(command.getPassword()));
    return port.register(user);
  }
}
