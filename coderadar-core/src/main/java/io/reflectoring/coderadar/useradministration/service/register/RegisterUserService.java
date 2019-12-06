package io.reflectoring.coderadar.useradministration.service.register;

import io.reflectoring.coderadar.useradministration.UsernameAlreadyInUseException;
import io.reflectoring.coderadar.useradministration.domain.User;
import io.reflectoring.coderadar.useradministration.port.driven.LoadUserPort;
import io.reflectoring.coderadar.useradministration.port.driven.RegisterUserPort;
import io.reflectoring.coderadar.useradministration.port.driver.register.RegisterUserCommand;
import io.reflectoring.coderadar.useradministration.port.driver.register.RegisterUserUseCase;
import io.reflectoring.coderadar.useradministration.service.security.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserService implements RegisterUserUseCase {

  private final RegisterUserPort port;
  private final LoadUserPort loadUserPort;
  private final Logger logger = LoggerFactory.getLogger(RegisterUserService.class);

  public RegisterUserService(RegisterUserPort port, LoadUserPort loadUserPort) {
    this.port = port;
    this.loadUserPort = loadUserPort;
  }

  @Override
  public Long register(RegisterUserCommand command) {
    if (loadUserPort.existsByUsername(command.getUsername())) {
      throw new UsernameAlreadyInUseException(command.getUsername());
    }
    User user = new User();
    user.setUsername(command.getUsername());
    user.setPassword(PasswordUtil.hash(command.getPassword()));
    Long id = port.register(user);
    logger.info("Created user {}", user.getUsername());
    return id;
  }
}
